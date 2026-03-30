package org.pawfinder.controller;

import org.pawfinder.dto.MissingPetReportDTO;
import org.pawfinder.dto.PetDTO;
import org.pawfinder.dto.PetRequestDTO;
import org.pawfinder.dto.StrayAnimalReportDTO;
import org.pawfinder.entity.MissingPetReport;
import org.pawfinder.entity.Pet;
import org.pawfinder.entity.PetAdoptionRequest;
import org.pawfinder.entity.ShelterPet;
import org.pawfinder.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
@CrossOrigin("*") // Allow frontend access
public class PetController {

    @Autowired
    private PetService petService;

    @GetMapping("/rehomepetlist")
    public List<PetDTO> getRehomePets() {
        return petService.getRehomePets();
    }

    @GetMapping("/approved")
    public ResponseEntity<List<Pet>> getApprovedPets() {          //get approved rehome pets
        List<Pet> approvedPets = petService.getApprovedPets();
        return ResponseEntity.ok(approvedPets);
    }

    @GetMapping("/approvedshelterpets")
    public ResponseEntity<List<ShelterPet>> getApprovedShelterPets() {                 //get approved shelter pets
        List<ShelterPet> approvedShelterPets = petService.getApprovedShelterPets();
        return ResponseEntity.ok(approvedShelterPets);
    }


    @GetMapping("/shelterpetlist")
    public List<PetDTO> getShelterPets() {
        return petService.getShelterPets();
    }

    @PostMapping(value = "/rehomepet" ,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> rehomePet(
            @RequestParam String petName,
            @RequestParam String petType,
            @RequestParam String breed,
            @RequestParam Integer age,
            @RequestParam String ageUnit,
            @RequestParam String gender,
            @RequestParam String vaccinationStatus,
            @RequestParam String spayedNeuteredStatus,
            @RequestParam String location,
            @RequestParam String contactNumber,
            @RequestParam String reason,
            @RequestParam String description,
            @RequestParam(value = "photos", required = false) List<String> photos,
            @RequestParam String userId,
            @RequestParam String username
    ) {
        petService.savePet(petName, petType, breed, age, ageUnit, gender,
                vaccinationStatus, spayedNeuteredStatus,
                location, contactNumber, reason, description, photos, userId, username);
        return ResponseEntity.ok("Pet saved successfully");
    }

    @PostMapping(value = "/shelterpet" ,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> shelterPet(
            @RequestParam String shelterName,
            @RequestParam String shelterAddress,
            @RequestParam String petName,
            @RequestParam String petType,
            @RequestParam String breed,
            @RequestParam Integer age,
            @RequestParam String ageUnit,
            @RequestParam String gender,
            @RequestParam String vaccinationStatus,
            @RequestParam String spayedNeuteredStatus,
            @RequestParam String contactNumber,
            @RequestParam String description,
            @RequestParam(value = "photos", required = false) List<String> photos,
            @RequestParam String userId,
            @RequestParam String username
    ){
        petService.saveShelterPet(shelterName, shelterAddress, petName, petType, breed, age, ageUnit, gender,
                vaccinationStatus, spayedNeuteredStatus, contactNumber, description, photos, userId, username);
        return ResponseEntity.ok("Shelter pet saved successfully");
    }

    @PostMapping("/reportstray")
    public ResponseEntity<String> reportStrayAnimal(@RequestBody StrayAnimalReportDTO dto) {
        petService.saveReport(dto);
        return ResponseEntity.ok("Stray animal report submitted successfully.");
    }

    @PostMapping("/reportmissingpet")
    public ResponseEntity<String> reportMissingPet(@RequestBody MissingPetReportDTO dto) {
        System.out.println("Received missing pet report request: " + dto);
        try {
            petService.saveMissingPetReport(dto);
            return ResponseEntity.ok("Missing pet report submitted successfully.");
        } catch (Exception e) {
            System.err.println("Error processing missing pet report: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error processing request: " + e.getMessage());
        }
    }

    @PostMapping("/adoptionrequest")
    public ResponseEntity<String> submitAdoptionRequest(@RequestBody PetAdoptionRequest request) {
        petService.saveAdoptionRequest(request);
        return ResponseEntity.ok("Pet adoption form submitted successfully.");
    }

    @GetMapping("/approvedmissingpets")
    public ResponseEntity<List<MissingPetReport>> getApprovedMissingPetReports() {
        List<MissingPetReport> reports = petService.getAApprovedMissingPetReports();
        return ResponseEntity.ok(reports);
    }
}
