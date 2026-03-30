package org.pawfinder.dto;

import lombok.Data;

@Data
public class AdoptionRequestResponseDTO {
    private Long requestId;
    private String requesterName;
    private String requesterEmail;
    private String requesterContactNumber;
    private String requesterAddress;
    private String livingSituation;
    private String hasOtherPets;
    private String experienceWithPets;
    private String workSchedule;
    private String reasonForAdoption;
    private String reviewStatus;
    private String submissionDate;
    
    // Pet details
    private Long petId;
    private String petType;
    private String petName;
    private int petAge;
    private String petBreed;
    private String petGender;
    private String petImageUrl;
} 