package com.adv.profileservice.event;

import com.adv.profileservice.dto.ProfileRequestDto;
import com.adv.profileservice.service.StudentProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UserEventListener {

    private static final Logger logger = LoggerFactory.getLogger(UserEventListener.class);
    public static final String USER_CREATED_TOPIC = "user-created-topic";
    public static final String STUDENT_ROLE = "STUDENT";

    private final StudentProfileService profileService;

    public UserEventListener(StudentProfileService profileService) {
        this.profileService = profileService;
    }

    @KafkaListener(topics = USER_CREATED_TOPIC,
    groupId = "${spring.kafka.consumer.group-id}",
    containerFactory = "userCreatedEventConcurrentKafkaListenerContainerFactory")
    public void handleUserCreatedEvent(UserCreatedEvent event) {
        logger.info("Received UserCreatedEvent: {} for User ID: {}", event, event.getUserId());
        if (STUDENT_ROLE.equals(event.getRole())) {
            try {
                ProfileRequestDto profileRequestDto = ProfileRequestDto.builder()
                        .userId(event.getUserId())
                        .build();

                profileService.createStudentProfile(profileRequestDto);
                logger.info("Successfully created student profile for user ID: {}", event.getUserId());
            } catch (Exception e) {
                logger.error("Error creating student profile for user ID: {} from UserCreatedEvent", event.getUserId(), e);

                //ToDo  Implement error handling: e.g., send to a Dead Letter Topic (DLT), log for manual intervention.
            }
        } else {
            logger.info("User ID: {} is not a STUDENT. No profile created.", event.getUserId());
        }

    }
}
