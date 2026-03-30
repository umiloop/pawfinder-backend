package org.pawfinder.dao;

import org.pawfinder.entity.Pet;
import org.pawfinder.entity.ShelterPet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetSheltersRepository extends JpaRepository<ShelterPet, Long> {
    List<ShelterPet> findByReviewStatus(String reviewStatus);

    @Query(value = "SELECT * FROM shelterpets WHERE user_id = :userId", nativeQuery = true)
    List<ShelterPet> findByUserId(String userId);
}
