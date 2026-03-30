package org.pawfinder.dao;

import org.pawfinder.entity.MissingPetReport;
import org.pawfinder.entity.ShelterPet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MissingPetReportRepository extends JpaRepository<MissingPetReport, Long> {
    @Query(value = "SELECT * FROM missing_pets_reports WHERE user_id = :userId", nativeQuery = true)
    List<MissingPetReport> findByUserId(String userId);
    List<MissingPetReport> findByReviewStatus(String reviewStatus);
}

