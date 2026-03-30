package org.pawfinder.service.impl;

import org.pawfinder.dao.*;
import org.pawfinder.dto.MissingPetReportDTO;
import org.pawfinder.dto.PetDTO;
import org.pawfinder.dto.StrayAnimalReportDTO;
import org.pawfinder.entity.*;
import org.pawfinder.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PetServiceImpl implements PetService {

    @Autowired
    private RehomePetRepository rehomePetRepository;
    @Autowired
    private ShelterPetRepository shelterPetRepository;
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private PetSheltersRepository petShelterRepository;
    @Autowired
    private StrayAnimalReportRepository reportRepository;
    @Autowired
    private MissingPetReportRepository reportMissingPetRepository;
    @Autowired
    private PetAdoptionRequestRepository repository;

    @Override
    public List<PetDTO> getRehomePets() {
        List<RehomePetEntity> rehomePets = (List<RehomePetEntity>) rehomePetRepository.findAll();
        return rehomePets.stream().map(rehomePet -> new PetDTO(
                rehomePet.getPetId(),
                rehomePet.getPetName(),
                rehomePet.getPetAge(),
                rehomePet.getPetLocation(),
                rehomePet.getPetAvailabilityStatus(),
                rehomePet.getPetPicture(),
                rehomePet.getPetBreed(),
                rehomePet.getPetGender(),
                rehomePet.getContactPersonNumber()
        )).collect(Collectors.toList());
    }

    @Override
    public List<Pet> getApprovedPets() {
        return petRepository.findByReviewStatus("Approved");
    }

    @Override
    public List<ShelterPet> getApprovedShelterPets(){
        return petShelterRepository.findByReviewStatus("Approved");
    }

    @Override
    public List<PetDTO> getShelterPets() {
        List<ShelterPetEntity> shelterPets = (List<ShelterPetEntity>) shelterPetRepository.findAll();
        return shelterPets.stream().map(shelterPet -> new PetDTO(
                shelterPet.getPetId(),
                shelterPet.getPetName(),
                shelterPet.getPetAge(),
                shelterPet.getPetLocation(),
                shelterPet.getPetAvailabilityStatus(),
                shelterPet.getPetPicture(),
                shelterPet.getPetBreed(),
                shelterPet.getPetGender(),
                shelterPet.getContactPersonNumber()
        )).collect(Collectors.toList());
    }

    @Override
    public void savePet(String petName, String petType, String breed, Integer age,
                        String ageUnit, String gender, String vaccinationStatus,
                        String spayedNeuteredStatus, String location, String contactNumber,
                        String reason, String description, List<String> photos, String userId, String username) {

        Pet pet = new Pet();
        pet.setPetName(petName);
        pet.setPetType(petType);
        pet.setBreed(breed);
        pet.setAge(age);
        pet.setAgeUnit(ageUnit);
        pet.setGender(gender);
        pet.setVaccinationStatus(vaccinationStatus);
        pet.setSpayedNeuteredStatus(spayedNeuteredStatus);
        pet.setLocation(location);
        pet.setContactNumber(contactNumber);
        pet.setReason(reason);
        pet.setDescription(description);
        pet.setPhotoUrls(photos);
        pet.setUserId(userId);
        pet.setUsername(username);

        petRepository.save(pet);
    }

    @Override
    public void saveShelterPet(String shelterName, String shelterAddress, String petName,
                               String petType, String breed, int age, String ageUnit,
                               String gender, String vaccinationStatus, String spayedNeuteredStatus,
                               String contactNumber, String description, List<String> photos, String userId , String username){
        ShelterPet shelterpet = new ShelterPet();
        shelterpet.setShelterName(shelterName);
        shelterpet.setShelterAddress(shelterAddress);
        shelterpet.setPetName(petName);
        shelterpet.setPetType(petType);
        shelterpet.setBreed(breed);
        shelterpet.setAge(age);
        shelterpet.setAgeUnit(ageUnit);
        shelterpet.setGender(gender);
        shelterpet.setVaccinationStatus(vaccinationStatus);
        shelterpet.setSpayedNeuteredStatus(spayedNeuteredStatus);
        shelterpet.setContactNumber(contactNumber);
        shelterpet.setDescription(description);
        shelterpet.setPhotoUrls(photos);
        shelterpet.setUserId(userId);
        shelterpet.setUsername(username);

        petShelterRepository.save(shelterpet);
    }

    @Override
    public void saveReport(StrayAnimalReportDTO dto) {
        StrayAnimalReport report = new StrayAnimalReport();
        report.setAnimalType(dto.getAnimalType());
        report.setInjured(dto.isInjured());
        report.setStray(dto.isStray());
        report.setMalnourished(dto.isMalnourished());
        report.setDescription(dto.getDescription());
        report.setAnimalStatus(dto.getAnimalStatus());
        report.setActionRequired(dto.getActionRequired());

        report.setLocationText(dto.getLocationText());
        report.setCity(dto.getCity());
        report.setPostalCode(dto.getPostalCode());
        report.setLocationDetails(dto.getLocationDetails());

        report.setLatitude(dto.getLatitude());
        report.setLongitude(dto.getLongitude());

        report.setContactName(dto.getContactName());
        report.setContactPhone(dto.getContactPhone());
        report.setContactEmail(dto.getContactEmail());

        report.setUserId(dto.getUserId());
        report.setUsername(dto.getUsername());

        report.setPhotoUrls(dto.getPhotoUrls());

        reportRepository.save(report);
    }

    @Override
    public void saveMissingPetReport(MissingPetReportDTO dto){
        MissingPetReport report = new MissingPetReport();

        report.setPetName(dto.getPetName());
        report.setPetType(dto.getPetType());
        report.setBreed(dto.getBreed());
        report.setAge(dto.getAge());
        report.setAgeUnit(dto.getAgeUnit());
        report.setGender(dto.getGender());
        report.setDescription(dto.getDescription());

        report.setLocation_address(dto.getLocation_address());
        report.setLocation_city(dto.getLocation_city());
        report.setLocation_coordinates(dto.getLocation_coordinates());
        report.setLocation_details(dto.getLocation_details());

        report.setUserId(dto.getUserId());
        report.setUsername(dto.getUsername());

        report.setOwnerName(dto.getOwnerName());
        report.setPhoneNumber(dto.getPhoneNumber());
        report.setEmail(dto.getEmail());
        report.setContactPreference(dto.getContactPreference());

        report.setLastSeenDate(dto.getLastSeenDate());
        report.setLastSeenTime(dto.getLastSeenTime());

        report.setOfferReward(dto.getOfferReward());
        report.setRewardAmount(dto.getRewardAmount());

        report.setPhotoURLs(dto.getPhotoURLs());

        reportMissingPetRepository.save(report);
    }

    @Override
    public PetAdoptionRequest saveAdoptionRequest(PetAdoptionRequest request) {
        return repository.save(request);
    }

    @Override
    public List<MissingPetReport> getAApprovedMissingPetReports() {
        return reportMissingPetRepository.findByReviewStatus("Approved");
    }
}