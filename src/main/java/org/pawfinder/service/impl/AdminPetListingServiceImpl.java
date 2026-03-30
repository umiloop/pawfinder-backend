package org.pawfinder.service.impl;

import org.pawfinder.dao.AdminPetListingRepository;
import org.pawfinder.dao.PetRepository;
import org.pawfinder.dto.PetListingDTO;
import org.pawfinder.entity.Pet;
import org.pawfinder.entity.ShelterPet;
import org.pawfinder.service.AdminPetListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminPetListingServiceImpl implements AdminPetListingService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private AdminPetListingRepository adminPetListingRepository;

    @Override
    public List<PetListingDTO> getPendingPetListings() {
        List<PetListingDTO> pendingListings = new ArrayList<>();
        
        // Get pending pets
        List<Pet> pendingPets = petRepository.findByReviewStatus("Pending");
        for (Pet pet : pendingPets) {
            PetListingDTO dto = convertPetToDTO(pet);
            dto.setListingType("PET");
            pendingListings.add(dto);
        }
        
        // Get pending shelter pets
        List<ShelterPet> pendingShelterPets = adminPetListingRepository.findByReviewStatus("Pending");
        for (ShelterPet shelterPet : pendingShelterPets) {
            PetListingDTO dto = convertShelterPetToDTO(shelterPet);
            dto.setListingType("SHELTER_PET");
            pendingListings.add(dto);
        }
        
        return pendingListings;
    }

    @Override
    public List<PetListingDTO> getApprovedPetListings() {
        List<PetListingDTO> approvedListings = new ArrayList<>();

        // Get pending pets
        List<Pet> approvedPets = petRepository.findByReviewStatus("Approved");
        for (Pet pet : approvedPets) {
            PetListingDTO dto = convertPetToDTO(pet);
            dto.setListingType("PET");
            approvedListings.add(dto);
        }

        // Get pending shelter pets
        List<ShelterPet> approvedShelterPets = adminPetListingRepository.findByReviewStatus("Approved");
        for (ShelterPet shelterPet : approvedShelterPets) {
            PetListingDTO dto = convertShelterPetToDTO(shelterPet);
            dto.setListingType("SHELTER_PET");
            approvedListings.add(dto);
        }

        return approvedListings;
    }

    @Override
    public PetListingDTO getPetListingDetails(Long id, String listingType) {
        if ("PET".equals(listingType)) {
            Optional<Pet> pet = petRepository.findById(id);
            if (pet.isPresent()) {
                PetListingDTO dto = convertPetToDTO(pet.get());
                dto.setListingType("PET");
                return dto;
            }
        } else if ("SHELTER_PET".equals(listingType)) {
            Optional<ShelterPet> shelterPet = adminPetListingRepository.findById(id);
            if (shelterPet.isPresent()) {
                PetListingDTO dto = convertShelterPetToDTO(shelterPet.get());
                dto.setListingType("SHELTER_PET");
                return dto;
            }
        }
        return null;
    }

    @Override
    @Transactional
    public void approvePetListing(Long id, String listingType) {
        if ("REHOME_PET".equals(listingType)) {
            Optional<Pet> pet = petRepository.findById(id);
            if (pet.isPresent()) {
                Pet petEntity = pet.get();
                petEntity.setReviewStatus("Approved");
                petRepository.save(petEntity);
            }
        } else if ("SHELTER_PET".equals(listingType)) {
            Optional<ShelterPet> shelterPet = adminPetListingRepository.findById(id);
            if (shelterPet.isPresent()) {
                ShelterPet shelterPetEntity = shelterPet.get();
                shelterPetEntity.setReviewStatus("Approved");
                adminPetListingRepository.save(shelterPetEntity);
            }
        }
    }

    @Override
    @Transactional
    public void rejectPetListing(Long id, String listingType) {
        if ("REHOME_PET".equals(listingType)) {
            petRepository.deleteById(id);
        } else if ("SHELTER_PET".equals(listingType)) {
            adminPetListingRepository.deleteById(id);
        }
    }

    private PetListingDTO convertPetToDTO(Pet pet) {
        PetListingDTO dto = new PetListingDTO();
        dto.setId(pet.getId());
        dto.setPetName(pet.getPetName());
        dto.setPetType(pet.getPetType());
        dto.setBreed(pet.getBreed());
        dto.setAge(pet.getAge());
        dto.setAgeUnit(pet.getAgeUnit());
        dto.setGender(pet.getGender());
        dto.setVaccinationStatus(pet.getVaccinationStatus());
        dto.setSpayedNeuteredStatus(pet.getSpayedNeuteredStatus());
        dto.setLocation(pet.getLocation());
        dto.setContactNumber(pet.getContactNumber());
        dto.setDescription(pet.getDescription());
        dto.setReviewStatus(pet.getReviewStatus());
        dto.setPhotoUrls(pet.getPhotoUrls());
        dto.setUserId(pet.getUserId());
        dto.setUsername(pet.getUsername());
        return dto;
    }

    private PetListingDTO convertShelterPetToDTO(ShelterPet shelterPet) {
        PetListingDTO dto = new PetListingDTO();
        dto.setId(shelterPet.getId());
        dto.setPetName(shelterPet.getPetName());
        dto.setPetType(shelterPet.getPetType());
        dto.setBreed(shelterPet.getBreed());
        dto.setAge(shelterPet.getAge());
        dto.setAgeUnit(shelterPet.getAgeUnit());
        dto.setGender(shelterPet.getGender());
        dto.setVaccinationStatus(shelterPet.getVaccinationStatus());
        dto.setSpayedNeuteredStatus(shelterPet.getSpayedNeuteredStatus());
        dto.setContactNumber(shelterPet.getContactNumber());
        dto.setDescription(shelterPet.getDescription());
        dto.setReviewStatus(shelterPet.getReviewStatus());
        dto.setPhotoUrls(shelterPet.getPhotoUrls());
        dto.setShelterName(shelterPet.getShelterName());
        dto.setShelterAddress(shelterPet.getShelterAddress());
        dto.setUserId(shelterPet.getUserId());
        dto.setUsername(shelterPet.getUsername());
        return dto;
    }
} 