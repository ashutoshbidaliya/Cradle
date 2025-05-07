package com.adv.profileservice.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ProfileRequestDto {
    private Integer profileId;
    private Long userId;
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
