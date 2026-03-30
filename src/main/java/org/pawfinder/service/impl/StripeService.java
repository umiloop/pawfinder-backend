package org.pawfinder.service.impl;

import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.pawfinder.dto.PaymentIntentRequest;
import org.pawfinder.dto.PaymentIntentResponse;
import org.pawfinder.entity.Donation;
import org.pawfinder.entity.DonationStats;
import org.pawfinder.dao.DonationRepository;
import org.pawfinder.dao.DonationStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class StripeService {

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    @Autowired
    private DonationRepository donationRepository;

    @Autowired
    private DonationStatsRepository donationStatsRepository;

    public PaymentIntentResponse createPaymentIntent(PaymentIntentRequest request) {
        try {
            // Initialize Stripe with secret key
            Stripe.apiKey = stripeSecretKey;

            // Convert amount to cents (Stripe uses smallest currency unit)
            long amountInCents = request.getAmount().multiply(new BigDecimal("100")).longValue();

            // Create payment intent parameters
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(amountInCents)
                .setCurrency("usd")
                .setAutomaticPaymentMethods(
                    PaymentIntentCreateParams.AutomaticPaymentMethods
                        .builder()
                        .setEnabled(true)
                        .build()
                )
                .putMetadata("donorName", request.getDonorName())
                .putMetadata("donorEmail", request.getDonorEmail())
                .build();

            // Create the PaymentIntent
            PaymentIntent paymentIntent = PaymentIntent.create(params);

            // Return the client secret
            return new PaymentIntentResponse(paymentIntent.getClientSecret());
        } catch (com.stripe.exception.StripeException e) {
            // Log the exception and wrap it in a RuntimeException
            System.err.println("Stripe payment processing failed: " + e.getMessage());
            throw new RuntimeException("Stripe payment processing failed: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create payment intent: " + e.getMessage());
        }
    }

    /**
     * Process a successful payment from Stripe webhook
     */
    @Transactional
    public void processSuccessfulPayment(PaymentIntent paymentIntent) {
        System.out.println("Processing payment: " + paymentIntent.getId());

        try {
            // Extract payment details
            String paymentId = paymentIntent.getId();
            long amountInCents = paymentIntent.getAmount();
            BigDecimal amount = new BigDecimal(amountInCents).divide(new BigDecimal(100));

            // Get donor information from metadata
            String donorName = paymentIntent.getMetadata().get("donorName");
            String donorEmail = paymentIntent.getMetadata().get("donorEmail");

            System.out.println("Payment details: ID=" + paymentId + ", Amount=" + amount + ", Donor=" + donorName + ", Email=" + donorEmail);

            // Create donation record
            Donation donation = new Donation();
            donation.setStripePaymentId(paymentId);
            donation.setAmount(amount);
            donation.setDonorName(donorName != null ? donorName : "Anonymous");
            donation.setDonorEmail(donorEmail != null ? donorEmail : "anonymous@example.com");
            donation.setDonationDate(LocalDateTime.now());
            donation.setStatus("completed");
            donation.setNotes("Processed via webhook");

            // Save donation
            System.out.println("Saving donation record...");
            Donation savedDonation = donationRepository.save(donation);
            System.out.println("Donation record saved with ID: " + savedDonation.getId());

            // Update donation statistics
            System.out.println("Updating donation statistics...");
            Optional<DonationStats> statsOptional = donationStatsRepository.findById(1L);
            DonationStats stats;

            if (statsOptional.isEmpty()) {
                // Create stats record if not exists
                System.out.println("Creating new donation stats record");
                stats = new DonationStats();
                stats.setId(1L);
                stats.setTotalRaised(amount);
                stats.setDonorCount(1);
                stats.setLastUpdated(LocalDateTime.now());
            } else {
                // Update existing stats
                System.out.println("Updating existing donation stats record");
                stats = statsOptional.get();
                stats.setTotalRaised(stats.getTotalRaised().add(amount));
                stats.setDonorCount(stats.getDonorCount() + 1);
                stats.setLastUpdated(LocalDateTime.now());
            }

            // Save updated stats
            DonationStats savedStats = donationStatsRepository.save(stats);
            System.out.println("Donation stats updated: ID=" + savedStats.getId() + ", Total raised=" + savedStats.getTotalRaised() + ", Donor count=" + savedStats.getDonorCount());

        } catch (Exception e) {
            System.err.println("Error processing payment: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error processing payment: " + e.getMessage(), e);
        }
    }

    /**
     * Get current donation statistics
     */
    public Optional<DonationStats> getDonationStats() {
        Optional<DonationStats> stats = donationStatsRepository.findById(1L);
        if (stats.isEmpty()) {
            DonationStats newStats = new DonationStats();
            newStats.setId(1L);
            newStats.setLastUpdated(LocalDateTime.now());
            donationStatsRepository.save(newStats);
            stats = Optional.of(newStats);
        }
        return stats;
    }

    public String getStripeSecretKey() {
        return stripeSecretKey;
    }
}