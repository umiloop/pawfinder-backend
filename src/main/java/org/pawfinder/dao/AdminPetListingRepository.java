package org.pawfinder.dao;

import org.pawfinder.entity.ShelterPet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminPetListingRepository extends JpaRepository<ShelterPet, Long> {
    List<ShelterPet> findByReviewStatus(String reviewStatus);
} 