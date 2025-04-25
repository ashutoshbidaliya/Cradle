package com.adv.userservice.service;

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

    public Optional<User> login(String email, String password) {
        return userRepo.findByEmail(email)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()));
    }
}
