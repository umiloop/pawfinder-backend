package org.pawfinder.controller;

import org.pawfinder.dto.PetListingDTO;
import org.pawfinder.service.AdminPetListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/pet-listings")
@CrossOrigin(origins = "*")
public class AdminPetListingController {

    @Autowired
    private AdminPetListingService adminPetListingService;

    @GetMapping("/pending")
    public ResponseEntity<List<PetListingDTO>> getPendingPetListings() {
        List<PetListingDTO> pendingListings = adminPetListingService.getPendingPetListings();
        return ResponseEntity.ok(pendingListings);
    }

    @GetMapping("/approved")
    public ResponseEntity<List<PetListingDTO>> getApprovedPetListings() {
        List<PetListingDTO> approvedListings = adminPetListingService.getApprovedPetListings();
        return ResponseEntity.ok(approvedListings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetListingDTO> getPetListingDetails(
            @PathVariable Long id,
            @RequestParam String listingType) {
        PetListingDTO listing = adminPetListingService.getPetListingDetails(id, listingType);
        if (listing != null) {
            return ResponseEntity.ok(listing);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<Void> approvePetListing(
            @PathVariable Long id,
            @RequestParam String listingType) {
        adminPetListingService.approvePetListing(id, listingType);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<Void> rejectPetListing(
            @PathVariable Long id,
            @RequestParam String listingType) {
        adminPetListingService.rejectPetListing(id, listingType);
        return ResponseEntity.ok().build();
    }
} 