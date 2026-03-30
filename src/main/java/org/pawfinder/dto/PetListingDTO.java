package org.pawfinder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetListingDTO {
    private Long id;
    private String listingType; // "PET" or "SHELTER_PET"
    private String petName;
    private String petType;
    private String breed;
    private Integer age;
    private String ageUnit;
    private String gender;
    private String vaccinationStatus;
    private String spayedNeuteredStatus;
    private String location;
    private String contactNumber;
    private String description;
    private String reviewStatus;
    private List<String> photoUrls;
    
    // Additional fields for shelter pets
    private String shelterName;
    private String shelterAddress;
    
    // User information
    private String userId;
    private String username;
} 