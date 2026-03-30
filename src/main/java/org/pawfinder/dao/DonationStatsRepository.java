package org.pawfinder.dao;

import org.pawfinder.entity.DonationStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface DonationStatsRepository extends JpaRepository<DonationStats, Long> {
    Optional<DonationStats> findById(long id);
}
