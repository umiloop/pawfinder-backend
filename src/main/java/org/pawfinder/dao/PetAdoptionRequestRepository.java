package org.pawfinder.dao;

import org.pawfinder.entity.PetAdoptionRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetAdoptionRequestRepository extends JpaRepository<PetAdoptionRequest, Long> {
    List<PetAdoptionRequest> findByUserId(Long userId);

    List<PetAdoptionRequest> findByPetOwnerId(Long petOwnerId);
}