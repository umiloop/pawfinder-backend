package org.pawfinder.service;

import org.pawfinder.entity.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    UserEntity registerUser(UserEntity user);
    UserEntity loginUser(String username, String password);
    List<Pet> getRehomeRequestsByUserId(String userId);
    List<ShelterPet> getShelterRequestsByUserId(String userId);
    List<StrayAnimalReport> getStrayAnimalReportRequestsByUserId(String userId);
    List<MissingPetReport> getMissingPetReportRequestsByUserId(String userId);
    List<PetAdoptionRequest> getAdoptionRequestsByUserId(Long userId);
    ResponseEntity<?> getPetDetails(String petType, Long petId);
}
