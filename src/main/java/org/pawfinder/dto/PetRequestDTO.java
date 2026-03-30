package org.pawfinder.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class PetRequestDTO {
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
    private String description;
    private List<String> photos; // (Optional: Base64 encoded images)
}
