package com.adv.userservice.dto;

import java.time.LocalDateTime;

public record UserCacheDTO(Long id,
                           String firstName,
                           String lastName,
                           String email,
                           String role,
                           LocalDateTime createdAt,
                           LocalDateTime updatedAt) {
}
