package org.pawfinder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MissingPetReportDTO {
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

    private String lastSeenDate;
    private String lastSeenTime;

    private Boolean offerReward;
    private Double rewardAmount;

    private List<String> photoURLs;
}
