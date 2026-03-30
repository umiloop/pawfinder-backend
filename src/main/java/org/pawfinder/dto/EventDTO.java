package org.pawfinder.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
public class EventDTO {
    private Long id;

    @NotBlank(message = "Month is required")
    @Size(min = 3, max = 3, message = "Month must be exactly 3 characters")
    private String month;

    @NotBlank(message = "Day is required")
    @Size(min = 1, max = 2, message = "Day must be 1 or 2 digits")
    private String day;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Location is required")
    private String location;

    @NotBlank(message = "Description is required")
    private String description;

    private String link;
}
