package com.adv.userservice.controller;

import com.adv.userservice.dto.UserLoginDTO;
import com.adv.userservice.dto.UserRegistrationDTO;
import com.adv.userservice.dto.UserResponseDTO;
import com.adv.userservice.model.User;
import com.adv.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody UserRegistrationDTO userDto) {
        return userService.getUserByEmail(userDto.getEmail())
                .map(existingUser -> ResponseEntity.status(HttpStatus.CONFLICT).body(existingUser))
                .orElseGet(() -> {
                    User createdUser = userService.createUser(userDto);
                    return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
                });
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(user -> ResponseEntity.status(HttpStatus.OK).body(user))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> loginUser(@RequestBody UserLoginDTO loginDto) throws Exception {
        UserResponseDTO user = userService.login(loginDto);
        return ResponseEntity.status(HttpStatus.OK).body(user) ;
    }

    /*@GetMapping
    public ResponseEntity<UserResponseDTO> getUser(@RequestParam(name = "user")  String userName) {
        UserResponseDTO userDto = userService.getUserByName(userName);


        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }*/

    @GetMapping("/email")
    public ResponseEntity<User> getUserByEmail(@RequestParam(name = "email")  String email) {
        return userService.getUserByEmail(email)
                .map(user -> ResponseEntity.status(HttpStatus.OK).body(user))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /*@DeleteMapping("/email")
    public ResponseEntity<List<Long>> deleteUsersByEmail(@RequestParam(name="email") String email) {
        List<Long> ids = userService.deleteUsers(email);
        return ResponseEntity.status(HttpStatus.OK).body(ids);
    }*/
}
