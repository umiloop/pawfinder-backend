package org.pawfinder.controller;

import org.pawfinder.dto.AdoptionRequestResponseDTO;
import org.pawfinder.service.AdoptionRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/adoption-requests")
@CrossOrigin(origins = "*")
public class AdoptionRequestController {

    @Autowired
    private AdoptionRequestService adoptionRequestService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AdoptionRequestResponseDTO>> getAdoptionRequestsForUser(@PathVariable Long userId) {
        List<AdoptionRequestResponseDTO> requests = adoptionRequestService.getAdoptionRequestsForUser(userId);
        return ResponseEntity.ok(requests);
    }

    @PutMapping("/{requestId}/approve")
    public ResponseEntity<AdoptionRequestResponseDTO> approveAdoptionRequest(
            @PathVariable Long requestId) {
        AdoptionRequestResponseDTO approvedRequest = adoptionRequestService.approveAdoptionRequest(requestId);
        return ResponseEntity.ok(approvedRequest);
    }

    @PutMapping("/{requestId}/reject")
    public ResponseEntity<AdoptionRequestResponseDTO> rejectAdoptionRequest(
            @PathVariable Long requestId) {
        AdoptionRequestResponseDTO rejectedRequest = adoptionRequestService.rejectAdoptionRequest(requestId);
        return ResponseEntity.ok(rejectedRequest);
    }
} 