package org.pawfinder.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "stray_animal_reports")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StrayAnimalReport {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String animalType;

    private boolean injured;
    private boolean stray;
    private boolean malnourished;

    @Lob
    private String description;

    private String animalStatus;
    private String actionRequired;

    private String locationText;
    private String city;
    private String postalCode;
    private String locationDetails;

    private Double latitude;
    private Double longitude;

    private String contactName;
    private String contactPhone;
    private String contactEmail;

    private Long userId;
    private String username;

    @Column(name = "review_status")
    private String reviewStatus = "Pending";

    @ElementCollection
    private List<String> photoUrls;

    private LocalDateTime createdAt = LocalDateTime.now();

}

