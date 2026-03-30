package org.pawfinder.entity;


import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "donation_stats")
@Data
public class DonationStats {
    @Id
    private Long id;

    private BigDecimal totalRaised = BigDecimal.ZERO;
    private int donorCount = 0;
    private LocalDateTime lastUpdated;

    @PrePersist
    @PreUpdate
    public void updateTimestamp() {
        this.lastUpdated = LocalDateTime.now();
    }
    
    // Manual getters and setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public BigDecimal getTotalRaised() {
        return totalRaised;
    }
    
    public void setTotalRaised(BigDecimal totalRaised) {
        this.totalRaised = totalRaised;
    }
    
    public int getDonorCount() {
        return donorCount;
    }
    
    public void setDonorCount(int donorCount) {
        this.donorCount = donorCount;
    }
    
    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }
    
    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}