package org.pawfinder.service;

import org.pawfinder.dto.MissingPetReportDTO;
import org.pawfinder.dto.PetDTO;
import org.pawfinder.dto.StrayAnimalReportDTO;
import org.pawfinder.entity.MissingPetReport;
import org.pawfinder.entity.Pet;
import org.pawfinder.entity.PetAdoptionRequest;
import org.pawfinder.entity.ShelterPet;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PetService {
    List<PetDTO> getRehomePets();
    List<Pet> getApprovedPets();
    List<ShelterPet> getApprovedShelterPets();
    List<PetDTO> getShelterPets();

    void savePet(String petName, String petType, String breed, Integer age,
                 String ageUnit, String gender, String vaccinationStatus,
                 String spayedNeuteredStatus, String location,
                 String contactNumber, String reason, String description,
                 List<String> photos, String userId, String username);
    void saveShelterPet(String shelterName, String shelterAddress, String petName, String petType, String breed, int age, String ageUnit, String gender,
                        String vaccinationStatus, String spayedNeuteredStatus, String contactNumber, String description, List<String> photos, String userId, String username);

    void saveReport(StrayAnimalReportDTO dto);

    void saveMissingPetReport(MissingPetReportDTO dto);

    PetAdoptionRequest saveAdoptionRequest(PetAdoptionRequest request);

    List<MissingPetReport> getAApprovedMissingPetReports();
}