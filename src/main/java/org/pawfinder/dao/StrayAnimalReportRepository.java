package org.pawfinder.dao;

import org.pawfinder.entity.ShelterPet;
import org.pawfinder.entity.StrayAnimalReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StrayAnimalReportRepository extends JpaRepository<StrayAnimalReport, Long> {
    @Query(value = "SELECT * FROM stray_animal_reports WHERE user_id = :userId", nativeQuery = true)
    List<StrayAnimalReport> findByUserId(String userId);
    
    List<StrayAnimalReport> findByReviewStatus(String reviewStatus);
}
