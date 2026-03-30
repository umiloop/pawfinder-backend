package org.pawfinder.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "pets")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

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
    private String reason;
    private String userId;
    private String username;

    @Column(length = 250)
    private String description;

    @Column(name = "review_status")
    private String reviewStatus = "Pending";

    @ElementCollection
    private List<String> photoUrls;
}