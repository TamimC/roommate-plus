package com.tamimtechnology.roommateplus.services;

import com.tamimtechnology.roommateplus.model.BusinessEntities.User;
import com.tamimtechnology.roommateplus.respositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return user;
    }

    @Transactional
    public UserDetails loadUserById (Long id){
        User user = userRepository.findUserById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return user;
    }


}
