package com.app.service;

import com.app.model.UserEntity;
import com.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    
	@Autowired
    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
   
        return repository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));
    }    
}