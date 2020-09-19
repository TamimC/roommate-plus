package com.tamimtechnology.roommateplus.services;

import com.tamimtechnology.roommateplus.model.BusinessEntities.TakeOutTrash;
import com.tamimtechnology.roommateplus.model.BusinessEntities.User;
import com.tamimtechnology.roommateplus.respositories.TrashRepository;
import com.tamimtechnology.roommateplus.respositories.UserRepository;
import com.tamimtechnology.roommateplus.security.JwtAuthenticationFilter;
import com.tamimtechnology.roommateplus.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
@AllArgsConstructor
public class TaskService {

    private final TrashRepository trashRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public ResponseEntity<?> takeOutTrash(HttpServletRequest httpServletRequest){
        String jwt = JwtAuthenticationFilter.getJwtFromRequest(httpServletRequest);
        Long userId = jwtTokenProvider.getUserIdFromJwt(jwt);
        User trashUser = userRepository.findUserById(userId).orElseThrow(() -> new UsernameNotFoundException("Could not find user with id " + userId));
        TakeOutTrash takeOutTrash = new TakeOutTrash(trashUser, new Date()) ;
        trashRepository.save(takeOutTrash);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
