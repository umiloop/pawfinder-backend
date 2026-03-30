package org.pawfinder.service;

import org.pawfinder.dao.PetAdoptionRequestRepository;
import org.pawfinder.dto.AdoptionRequestResponseDTO;
import org.pawfinder.entity.PetAdoptionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdoptionRequestService {

    @Autowired
    private PetAdoptionRequestRepository adoptionRequestRepository;

    public List<AdoptionRequestResponseDTO> getAdoptionRequestsForUser(Long userId) {
        List<PetAdoptionRequest> requests = adoptionRequestRepository.findByPetOwnerId(userId);
        return requests.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private AdoptionRequestResponseDTO convertToDTO(PetAdoptionRequest request) {
        AdoptionRequestResponseDTO dto = new AdoptionRequestResponseDTO();
        dto.setRequestId(request.getId());
        dto.setRequesterName(request.getName());
        dto.setRequesterEmail(request.getEmail());
        dto.setRequesterContactNumber(request.getContactNumber());
        dto.setRequesterAddress(request.getAddress());
        dto.setLivingSituation(request.getLivingSituation());
        dto.setHasOtherPets(request.getHasOtherPets());
        dto.setExperienceWithPets(request.getExperienceWithPets());
        dto.setWorkSchedule(request.getWorkSchedule());
        dto.setReasonForAdoption(request.getReasonForAdoption());
        dto.setReviewStatus(request.getReviewStatus());
        dto.setSubmissionDate(request.getSubmissionDate());
        
        // Set pet details
        dto.setPetId(request.getPetId());
        dto.setPetType(request.getPetType());
        dto.setPetName(request.getPetName());
        dto.setPetAge(request.getPetAge());
        dto.setPetBreed(request.getPetBreed());
        dto.setPetGender(request.getPetGender());
        dto.setPetImageUrl(request.getPetImageUrl());
        
        return dto;
    }

    public AdoptionRequestResponseDTO approveAdoptionRequest(Long requestId) {
        try {
            PetAdoptionRequest request = adoptionRequestRepository.findById(requestId)
                    .orElseThrow(() -> new RuntimeException("Adoption request not found with id: " + requestId));

            request.setReviewStatus("Approved");
//            request.setResponseDate(LocalDateTime.now().toString());

            PetAdoptionRequest updatedRequest = adoptionRequestRepository.save(request);
            return convertToDTO(updatedRequest);
        } catch (Exception e) {
            // Log the error
            System.err.println("Error approving adoption request: " + e.getMessage());
            throw new RuntimeException("Failed to approve adoption request", e);
        }
    }

    public AdoptionRequestResponseDTO rejectAdoptionRequest(Long requestId) {
        try {
            PetAdoptionRequest request = adoptionRequestRepository.findById(requestId)
                    .orElseThrow(() -> new RuntimeException("Adoption request not found with id: " + requestId));

            request.setReviewStatus("Rejected");
//            request.setResponseDate(LocalDateTime.now().toString());

            PetAdoptionRequest updatedRequest = adoptionRequestRepository.save(request);
            return convertToDTO(updatedRequest);
        } catch (Exception e) {
            // Log the error
            System.err.println("Error rejecting adoption request: " + e.getMessage());
            throw new RuntimeException("Failed to reject adoption request", e);
        }
    }
} 