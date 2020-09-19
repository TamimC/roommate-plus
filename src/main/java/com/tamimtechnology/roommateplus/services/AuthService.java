package com.tamimtechnology.roommateplus.services;

import com.tamimtechnology.roommateplus.exceptions.EmailAlreadyExistsException;
import com.tamimtechnology.roommateplus.exceptions.InvalidRefreshTokenException;
import com.tamimtechnology.roommateplus.exceptions.RequestHasNoRefreshTokenException;
import com.tamimtechnology.roommateplus.exceptions.UsernameAlreadyExistsException;
import com.tamimtechnology.roommateplus.model.SecurityEntities.RefreshToken;
import com.tamimtechnology.roommateplus.payload.request.LoginRequest;
import com.tamimtechnology.roommateplus.payload.response.LoginResponse;
import com.tamimtechnology.roommateplus.respositories.RefreshTokenRepository;
import com.tamimtechnology.roommateplus.respositories.UserRepository;
import com.tamimtechnology.roommateplus.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import com.tamimtechnology.roommateplus.model.BusinessEntities.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Date;

import static com.tamimtechnology.roommateplus.security.SecurityConstants.REFRESH_TOKEN_EXPIRATION_TIME;
import static com.tamimtechnology.roommateplus.security.SecurityConstants.TOKEN_PREFIX;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    // Authentication Dependencies
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private JwtTokenProvider jwtTokenProvider;
    private AuthenticationManager authenticationManager;

    @Transactional
    public void signup(User registerRequest){
        if (userRepository.existsByUsername(registerRequest.getUsername())) throw new UsernameAlreadyExistsException("Username is taken,");
        if (userRepository.existsByEmail(registerRequest.getEmail())) throw new EmailAlreadyExistsException("Email is taken.");
        User newUser = new User();
        newUser.setUsername(registerRequest.getUsername());
        newUser.setPassword(bCryptPasswordEncoder.encode(registerRequest.getPassword()));
        newUser.setEmail(registerRequest.getEmail());
        newUser.setTimeCreated(Instant.now());
        newUser.setEnabled(true);
        userRepository.save(newUser);
    }

    @Transactional
    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest) {
        try{
            Authentication authentication = authenticationManager.authenticate
                    (new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtTokenProvider.generateToken(authentication);
            String fullToken = TOKEN_PREFIX + token;
            Long userid = jwtTokenProvider.getUserIdFromJwt(token);
            String refreshToken = jwtTokenProvider.generateRefreshToken(Long.toString(userid));
            refreshTokenRepository.deleteAllByUser(userRepository.findUserById(userid).orElse(null));
            refreshTokenRepository.save(createRefreshToken(refreshToken, loginRequest.getUsername()));
            return new ResponseEntity<>(new LoginResponse(true, fullToken, refreshToken), HttpStatus.OK);
        }catch (BadCredentialsException e){
            return new ResponseEntity<>(new LoginResponse(false, "Invalid Credentials - Token Not Granted", "Invalid Credentials - Token Not Granted"), HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public ResponseEntity<?> refresh(HttpServletRequest httpServletRequest) {
        String hasRefreshToken = httpServletRequest.getHeader("RefreshToken");
        if (hasRefreshToken == null) throw new RequestHasNoRefreshTokenException("The request has no Refresh Token!");
        if (!(refreshTokenRepository.existsByToken(hasRefreshToken))) throw new InvalidRefreshTokenException("The refresh token is invalid.");
        if (refreshTokenRepository.findRefreshTokenByToken(hasRefreshToken).getExpiresAt().before(new Date(System.currentTimeMillis())))
            throw new InvalidRefreshTokenException("The refresh token has expired, please log in again.");

        Long refreshUserId = refreshTokenRepository.findUserIdByToken(hasRefreshToken);
        User refreshUser = userRepository.findUserById(refreshUserId).orElseThrow(() -> new UsernameNotFoundException("Cannot find user by ID"));
        String newToken = TOKEN_PREFIX + jwtTokenProvider.generateTokenWithUserId(refreshUser.getId().toString());
        return new ResponseEntity<>(new LoginResponse(true, newToken, hasRefreshToken), HttpStatus.OK);
    }

    @Transactional
    public RefreshToken createRefreshToken(String refreshToken, String username) {

        Date now = new Date(System.currentTimeMillis());
        Date expiry = new Date(now.getTime() + REFRESH_TOKEN_EXPIRATION_TIME);

        User user = userRepository.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username was not found"));
        return RefreshToken.builder()
                .token(refreshToken)
                .createdDate(now)
                .expiresAt(expiry)
                .user(user)
                .build();
    }

}

