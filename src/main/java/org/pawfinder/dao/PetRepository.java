package org.pawfinder.dao;

import org.pawfinder.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByReviewStatus(String reviewStatus);
    @Query(value = "SELECT * FROM pets WHERE user_id = :userId", nativeQuery = true)
    List<Pet> findByUserId(String userId);
}
