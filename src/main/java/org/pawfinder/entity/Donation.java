package org.pawfinder.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "donations")
@Data
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String stripePaymentId;
    private BigDecimal amount;
    private String donorName;
    private String donorEmail;

    @Column(name = "donation_date")
    private LocalDateTime donationDate;

    private String status;
    private String notes;
    
    // Manual getters and setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getStripePaymentId() {
        return stripePaymentId;
    }
    
    public void setStripePaymentId(String stripePaymentId) {
        this.stripePaymentId = stripePaymentId;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    public String getDonorName() {
        return donorName;
    }
    
    public void setDonorName(String donorName) {
        this.donorName = donorName;
    }
    
    public String getDonorEmail() {
        return donorEmail;
    }
    
    public void setDonorEmail(String donorEmail) {
        this.donorEmail = donorEmail;
    }
    
    public LocalDateTime getDonationDate() {
        return donationDate;
    }
    
    public void setDonationDate(LocalDateTime donationDate) {
        this.donationDate = donationDate;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
}

