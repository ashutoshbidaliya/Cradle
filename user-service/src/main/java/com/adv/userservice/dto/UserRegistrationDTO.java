package com.adv.userservice.dto;

import com.adv.userservice.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDTO {
    private User.Role role;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
