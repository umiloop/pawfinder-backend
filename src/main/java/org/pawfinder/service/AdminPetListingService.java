package org.pawfinder.service;

import org.pawfinder.dto.PetListingDTO;
import java.util.List;

public interface AdminPetListingService {
    List<PetListingDTO> getPendingPetListings();
    List<PetListingDTO> getApprovedPetListings();
    PetListingDTO getPetListingDetails(Long id, String listingType);
    void approvePetListing(Long id, String listingType);
    void rejectPetListing(Long id, String listingType);
} 