package com.doan.apidoan.services.impl;

import com.doan.apidoan.dtos.response.ResponseMessage;
import com.doan.apidoan.exceptions.CustomException;
import com.doan.apidoan.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new CustomException("Khong tim thay user", HttpStatus.BAD_REQUEST));
    }
}
