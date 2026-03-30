package org.pawfinder.controller;

import org.pawfinder.dto.LoginRequest;
import org.pawfinder.entity.*;
import org.pawfinder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*") // Allow frontend access
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserEntity> registerUser(@RequestBody UserEntity user) {
        UserEntity registeredUser = userService.registerUser(user);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<UserEntity> loginUser(@RequestBody LoginRequest loginRequest) {
        UserEntity loggedInUser = userService.loginUser(loginRequest.getUsername(), loginRequest.getPassword());
        return ResponseEntity.ok(loggedInUser);
    }

    @GetMapping("/rehomerequests")
    public ResponseEntity<List<Pet>> getRehomeRequestsByUserId(String userId){
        List<Pet> rehomeRequestsByUserId = userService.getRehomeRequestsByUserId(userId);
        return ResponseEntity.ok(rehomeRequestsByUserId);
    }

    @GetMapping("/shelterrequests")
    public ResponseEntity<List<ShelterPet>> getShelterRequestsByUserId(String userId){
        List<ShelterPet> shelterRequestsByUserId = userService.getShelterRequestsByUserId(userId);
        return ResponseEntity.ok(shelterRequestsByUserId);
    }

    @GetMapping("/reportstrayrequests")
    public ResponseEntity<List<StrayAnimalReport>> getStrayAnimalReportRequestsByUserId(String userId){
        List<StrayAnimalReport> strayAnimalReportsByUserId = userService.getStrayAnimalReportRequestsByUserId(userId);
        return ResponseEntity.ok(strayAnimalReportsByUserId);
    }

    @GetMapping("/reportmissingpetsrequests")
    public ResponseEntity<List<MissingPetReport>> getMissingPetReportRequestsByUserId(String userId){
        List<MissingPetReport> missingPetReportsByUserId = userService.getMissingPetReportRequestsByUserId(userId);
        return ResponseEntity.ok(missingPetReportsByUserId);
    }

    @GetMapping("/adoptionrequest")
    public ResponseEntity<List<PetAdoptionRequest>> getAdoptionRequestsByUserId(@RequestParam Long userId) {
        List<PetAdoptionRequest> requests = userService.getAdoptionRequestsByUserId(userId);
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/pet-details")
    public ResponseEntity<?> getPetDetails(@RequestParam String petType, @RequestParam Long petId) {
        return userService.getPetDetails(petType, petId);
    }

}