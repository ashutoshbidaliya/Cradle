package com.adv.userservice.service;

import com.adv.userservice.dto.UserLoginDTO;
import com.adv.userservice.dto.UserRegistrationDTO;
import com.adv.userservice.dto.UserResponseDTO;
import com.adv.userservice.model.User;
import com.adv.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<List<User>> getUserByName(String firstName) {
        return repository.findByFirstName(firstName);
    }

    public Optional<User> getUserByEmail(String email) {
        return repository.findByEmail(email);
    }

    public Optional<User> getUserById(Long userId) {
        return repository.findById(userId);
    }

   /* public List<Long> deleteUsers(String email) {
        List<User> users = repository.findAllByEmail(email);
        List<Long> ids = users.stream().filter(Objects::nonNull).map(User::getId)
                .toList();

        repository.deleteAllById(ids);
        return ids;

    }*/

    public User createUser(UserRegistrationDTO userDto) {

        User user = User.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .build();

        return repository.save(user);
    }

    public UserResponseDTO login(UserLoginDTO loginDto) throws Exception {
        User user = repository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new Exception("Invalid email id"));
        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
           throw  new Exception("Invalid  password");
        }

        return UserResponseDTO.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }

}
