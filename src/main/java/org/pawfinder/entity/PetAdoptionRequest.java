package org.pawfinder.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "PetAdoptionRequests")
public class PetAdoptionRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String email;
    private String contactNumber;
    private String address;

    private String livingSituation;
    private String hasOtherPets;
    private String experienceWithPets;
    private String workSchedule;
    private String reasonForAdoption;

    private Long userId;  // ID of the person requesting adoption
    private String username;

    private Long petId;
    private Long petOwnerId;  // ID of the person who posted the pet
    private String petType;
    private String petName;
    private int petAge;
    private String petBreed;
    private String petGender;
    private String petImageUrl;

    @Column(name = "review_status")
    private String reviewStatus = "Pending";

    private String submissionDate;
}