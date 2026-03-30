package org.pawfinder.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class PaymentIntentRequest {
    private BigDecimal amount;
    private String donorName;
    private String donorEmail;
    
    // Manual getters and setters
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
}
