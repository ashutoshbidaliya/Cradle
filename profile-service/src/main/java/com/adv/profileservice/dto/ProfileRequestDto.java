package com.adv.profileservice.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@Builder
public class ProfileRequestDto {
    private Long userId;
    private Integer profileId;
    private String basicDetails;
    private String academics;
    private String achievements;
    private String workExperience;
    private String projectExperience;
    private String audioVideoLinks;
    private String externalLinks;
    private String documentLinks;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
