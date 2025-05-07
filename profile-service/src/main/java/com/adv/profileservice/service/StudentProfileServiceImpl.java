package com.adv.profileservice.service;

import com.adv.profileservice.dto.ProfileRequestDto;
import com.adv.profileservice.model.StudentProfile;
import com.adv.profileservice.repository.StudentProfileRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Data
public class StudentProfileServiceImpl implements StudentProfileService {

    private final StudentProfileRepository repository;

    @Autowired
    public StudentProfileServiceImpl(StudentProfileRepository studentProfileRepository) {
        this.repository = studentProfileRepository;
    }

    @Override
    public StudentProfile createStudentProfile(ProfileRequestDto request) {

        Optional<StudentProfile> existingProfile = repository.findByUserId(request.getUserId());
         if (existingProfile.isPresent()) {
             throw new IllegalStateException("Profile already exists for user ID: " + request.getUserId());
         }
        StudentProfile newProfile = new StudentProfile();

         newProfile.setUserId(request.getUserId());
        // Set default values for other fields as per your StudentProfile model
        newProfile.setBasicDetails(request.getBasicDetails()); // Or some default JSON structure
        newProfile.setAcademics(request.getAcademics());
        newProfile.setAchievements(request.getAchievements());
        newProfile.setWorkExperience(request.getWorkExperience());
        newProfile.setProjectExperience(request.getProjectExperience());
        newProfile.setAudioVideoLinks(request.getAudioVideoLinks());
        newProfile.setExternalLinks(request.getExternalLinks());
        newProfile.setDocumentLinks(request.getDocumentLinks());

        return repository.save(newProfile);
    }

    @Override
    public void deleteStudentProfile(Integer profileId) {
        if (!repository.existsById(profileId)) { // JpaRepository expects Long here
            // Or throw a specific ProfileNotFoundException
            throw new RuntimeException("StudentProfile not found with id: " + profileId);
        }
         repository.deleteById(profileId);
    }

    @Override
    public Optional<StudentProfile> getProfileByUserId(Long userId) {
         return repository.findByUserId(userId);
    }

}
