package com.adv.profileservice.service;

import com.adv.profileservice.dto.ProfileRequestDto;
import com.adv.profileservice.model.StudentProfile;

import java.util.Optional;

public interface StudentProfileService {

    public StudentProfile createStudentProfile(ProfileRequestDto request);

    public void deleteStudentProfile(Integer profileId);

    Optional<StudentProfile> getProfileByUserId(Long userId);
}
