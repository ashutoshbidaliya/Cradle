package com.adv.profileservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "student_profiles")
public class StudentProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    @EqualsAndHashCode.Include
    private Integer profileId;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "basic_details", columnDefinition = "TEXT")
    private String basicDetails;

    @Lob
    @Column(name = "academics", columnDefinition = "TEXT")
    private String academics;

    @Column(name = "achievements", columnDefinition = "TEXT")
    private String achievements;

    @Column(name = "work_experience", columnDefinition = "TEXT")
    private String workExperience;

    @Column(name = "project_experience", columnDefinition = "TEXT")
    private String projectExperience;

    @Column(name = "audio_video_links", columnDefinition = "TEXT")
    private String audioVideoLinks;

    @Column(name = "external_links", columnDefinition = "TEXT")
    private String externalLinks;

    @Column(name = "document_links", columnDefinition = "TEXT")
    private String documentLinks;

   /* @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_guardian_id", referencedColumnName = "user_id", nullable = true)
    private User parentGuardian;*/

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
