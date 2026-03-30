package org.pawfinder.service.impl;

import org.mindrot.jbcrypt.BCrypt;
import org.pawfinder.dao.*;
import org.pawfinder.entity.*;
import org.pawfinder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private PetSheltersRepository shelterPetRepository;
    @Autowired
    private StrayAnimalReportRepository strayAnimalReportRepository;
    @Autowired
    private MissingPetReportRepository missingPetReportRepository;
    @Autowired
    private PetAdoptionRequestRepository repository;

    @Override
    public UserEntity registerUser(UserEntity user) {
        // Check if email or username already exists
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        // Set the password directly without hashing
        user.setPasswordHash(user.getPasswordHash());

        // Save user to database
        return userRepository.save(user);
    }

    @Override
    public UserEntity loginUser(String username, String password) {
        // Find user by username
        Optional<UserEntity> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        UserEntity user = userOptional.get();

        // Verify the password
//        if (!BCrypt.checkpw(password, user.getPasswordHash())) {
//            throw new RuntimeException("Invalid password");
//        }
//        if (password != user.getPasswordHash()) {
//            throw new RuntimeException("Invalid password");
//        }

//        // Return the user if login is successful
        System.out.println("Stored Hashed Password: " + user.getPasswordHash());
        System.out.println("Input Plain Password: " + password);

        if (password.equals(user.getPasswordHash())){
            return user;
        }else{
            throw new RuntimeException("Invalid password");
        }
    }

    @Override
    public List<Pet> getRehomeRequestsByUserId(String userId){
        return  petRepository.findByUserId(userId);
    }

    @Override
    public List<ShelterPet> getShelterRequestsByUserId(String userId){
        return shelterPetRepository.findByUserId(userId);
    }

    @Override
    public List<StrayAnimalReport> getStrayAnimalReportRequestsByUserId(String userId) {
        return strayAnimalReportRepository.findByUserId(userId);
    }

    @Override
    public List<MissingPetReport> getMissingPetReportRequestsByUserId(String userId) {
        return missingPetReportRepository.findByUserId(userId);
    }

    public List<PetAdoptionRequest> getAdoptionRequestsByUserId(Long userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public ResponseEntity<?> getPetDetails(String petType, Long petId) {
        if ("SHELTER".equalsIgnoreCase(petType)) {
            return shelterPetRepository.findById(petId)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } else if ("REHOME".equalsIgnoreCase(petType)) {
            return petRepository.findById(petId)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } else {
            return ResponseEntity.badRequest().body("Invalid petType");
        }
    }


}