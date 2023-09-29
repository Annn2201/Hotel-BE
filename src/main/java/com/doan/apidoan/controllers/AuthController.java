package com.doan.apidoan.controllers;

import com.doan.apidoan.dtos.response.JwtResponse;
import com.doan.apidoan.config.JwtUtilities;
import com.doan.apidoan.dtos.LoginDTO;
import com.doan.apidoan.dtos.RegisterDTO;
import com.doan.apidoan.models.Users;
import com.doan.apidoan.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtilities jwtUtil;
    @PostMapping("/register")
    ResponseEntity<?> registerUser(@RequestBody RegisterDTO registerDTO) {
        Users user = new Users();
        BeanUtils.copyProperties(registerDTO, user);
        return userService.saveUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.generateToken(authentication);
        Users user = (Users) authentication.getPrincipal();
        List<String> roles = user.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt,
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                roles));
    }

}
