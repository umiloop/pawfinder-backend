package org.pawfinder.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "shelter_pets")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShelterPetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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