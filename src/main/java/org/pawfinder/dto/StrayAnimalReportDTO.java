package org.pawfinder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StrayAnimalReportDTO {
    private String animalType;
    private boolean injured;
    private boolean stray;
    private boolean malnourished;
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

    private List<String> photoUrls;
}
