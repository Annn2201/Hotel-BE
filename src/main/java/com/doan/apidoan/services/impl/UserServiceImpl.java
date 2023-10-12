package com.doan.apidoan.services.impl;

import com.doan.apidoan.dtos.LoginDTO;
import com.doan.apidoan.dtos.UserDTO;
import com.doan.apidoan.dtos.response.ResponseMessage;
import com.doan.apidoan.exceptions.CustomException;
import com.doan.apidoan.models.Roles;
import com.doan.apidoan.models.Users;
import com.doan.apidoan.repositories.RoleRepository;
import com.doan.apidoan.repositories.UserRepository;
import com.doan.apidoan.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public ResponseEntity<?> saveUser(Users user) {
        Users exitedUser = userRepository.findByUsername(user.getUsername()).orElse(null);
        if (exitedUser != null) {
            return ResponseEntity.badRequest().body(new ResponseMessage<>("User da ton tai" ,HttpStatus.BAD_REQUEST));
        }
        Roles roles = roleRepository.findByRoleName("ROLE_USER").orElseThrow(() -> new CustomException("Khong tim thay role"));
        if (roles == null) {
            roles = roleRepository.save(new Roles("ROLE_USER"));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUserRole(List.of(roles));
        userRepository.save(user);
        return ResponseEntity.ok().body(new ResponseMessage<>("Dang ki thanh cong" ,HttpStatus.OK));
    }

    @Override
    public UserDTO getDetailUser(LoginDTO loginDTO) {
        Users user = userRepository.findByUsername(loginDTO.getUsername()).orElseThrow(() -> new CustomException("Khong tim thay user"));
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }

    @Override
    public Users findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new CustomException("Không tìm thấy user", HttpStatus.BAD_REQUEST));
    }
}
