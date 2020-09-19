package com.tamimtechnology.roommateplus.security;

// Generate Token
// Validate Token
// Get UserID From Token

import com.tamimtechnology.roommateplus.model.BusinessEntities.User;
import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static com.tamimtechnology.roommateplus.security.SecurityConstants.*;

@Component
public class JwtTokenProvider {

    public String generateToken(Authentication authentication){
        User user = (User) authentication.getPrincipal();
        Date now = new Date(System.currentTimeMillis());
        Date expiry = new Date(now.getTime() + TOKEN_EXPIRATION_TIME);
        String userId = Long.toString(user.getId());

        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    public String generateTokenWithUserId(String userId){
        Date now = new Date(System.currentTimeMillis());
        Date expiry = new Date(now.getTime() + TOKEN_EXPIRATION_TIME);

        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    public String generateRefreshToken(String subject) {
        Date now = new Date(System.currentTimeMillis());
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }


    public boolean validateToken(String token, HttpServletRequest httpServletRequest){
        try{
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            return true;
        }catch(SignatureException signatureException){
            System.out.println("Invalid JWT Signature");
        }catch (MalformedJwtException malformedJwtException){
            System.out.println("Invalid JWT Token");
        }catch(ExpiredJwtException expiredJwtException){
            throw new ExpiredJwtException(expiredJwtException.getHeader(), expiredJwtException.getClaims(), "Token Expired");
        }catch(UnsupportedJwtException unsupportedJwtException){
            System.out.println("Unsupported JWT Token");
        }catch(IllegalArgumentException illegalArgumentException){
            System.out.println("JWT Claims String is empty");
        }
        return false;
    }

    public long getUserIdFromJwt(String token){
        Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        return Long.parseLong(claims.getSubject());
    }
}
