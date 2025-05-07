package com.adv.userservice.service;

import com.adv.userservice.dto.UserLoginDTO;
import com.adv.userservice.dto.UserResponseDTO;
import com.adv.userservice.model.User;
import com.adv.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    public UserResponseDTO login(UserLoginDTO loginDto) throws Exception {
        User user = userRepo.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new Exception("Invalid Email ID"));

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new Exception("Invalid Password");
        }

         return UserResponseDTO.builder()
                .id(user.getUserId()) // Ensure ID is mapped
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .createdAt(user.getCreatedAt()) // Ensure these are mapped
                .updatedAt(user.getUpdatedAt()) // Ensure these are mapped
                .build();
    }
}
