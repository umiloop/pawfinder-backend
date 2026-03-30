package org.pawfinder.dto;

public class PaymentIntentResponse {
    private String clientSecret;
    
    // Default constructor
    public PaymentIntentResponse() {
    }
    
    // Constructor with clientSecret parameter
    public PaymentIntentResponse(String clientSecret) {
        this.clientSecret = clientSecret;
    }
    
    // Getter and setter
    public String getClientSecret() {
        return clientSecret;
    }
    
    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
}