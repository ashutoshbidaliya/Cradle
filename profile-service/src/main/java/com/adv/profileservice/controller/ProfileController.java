package com.adv.profileservice.controller;

import com.adv.profileservice.model.StudentProfile;
import com.adv.profileservice.service.StudentProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/profiles")
public class ProfileController {

    @Autowired
    private final StudentProfileService service;

    @GetMapping("/{id}")
    public ResponseEntity<StudentProfile> getUser(@PathVariable Long id) {
        return service.getProfileByUserId(id)
                .map(profile -> ResponseEntity.status(HttpStatus.OK).body(profile))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());

    }


}
