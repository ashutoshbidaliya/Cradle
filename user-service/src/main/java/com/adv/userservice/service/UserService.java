package com.adv.userservice.service;

import com.adv.userservice.dto.UserLoginDTO;
import com.adv.userservice.dto.UserRegistrationDTO;
import com.adv.userservice.dto.UserResponseDTO;
import com.adv.userservice.event.UserCreatedEvent;
import com.adv.userservice.model.User;
import com.adv.userservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class); // Logger

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public static final String USER_CREATED_TOPIC = "user-created-topic";

    @Autowired
    public UserService(UserRepository repository, PasswordEncoder passwordEncoder, KafkaTemplate<String, Object> kafkaTemplate) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.kafkaTemplate = kafkaTemplate;
    }

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

    @Transactional
    public User createUser(UserRegistrationDTO userDto) {

        User user = User.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .build();

        User savedUser =  repository.save(user);
        logger.info("User created with ID: {}", savedUser.getUserId());

        // Trigger profile creation for STUDENT role
        if(savedUser.getRole() == User.Role.STUDENT) {
            UserCreatedEvent event = new UserCreatedEvent(savedUser.getUserId(),
                    savedUser.getRole().name(),
                    savedUser.getEmail());
            try {
                kafkaTemplate.send(USER_CREATED_TOPIC, savedUser.getUserId().toString(), event);

                logger.info("Sent UserCreatedEvent for user ID: {} to topic: {}", savedUser.getUserId(), USER_CREATED_TOPIC);

            } catch (Exception e) {
                logger.error("Error sending UserCreateEvent for user id: {} ", savedUser.getUserId(), e);
            }
        }
        return savedUser;
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
