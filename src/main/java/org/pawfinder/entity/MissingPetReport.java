package org.pawfinder.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "missing_pets_reports")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MissingPetReport {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String petName;
    private String petType;
    private String breed;
    private Integer age;
    private String ageUnit;
    private String gender;
    private String description;

    private String location_coordinates;
    private String location_address;
    private String location_city;
    private String location_details;

    private Long userId;
    private String username;
    private String ownerName;
    private String phoneNumber;
    private String email;
    private String contactPreference;

    private Boolean offerReward;
    private Double rewardAmount;

    private String lastSeenDate;
    private String lastSeenTime;

    @Column(name = "review_status")
    private String reviewStatus = "Pending";

    @ElementCollection
    private List<String> photoURLs;
}