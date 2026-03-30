package org.pawfinder.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PetDTO {
    private Long petId;
    private String petName;
    private int petAge;
    private String petLocation;
    private String petAvailabilityStatus;
    private String petPicture;
    private String petBreed;
    private String petGender;
    private String contactPersonNumber;
}