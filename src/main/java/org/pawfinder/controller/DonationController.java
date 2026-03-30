package org.pawfinder.controller;

import org.pawfinder.dto.PaymentIntentRequest;
import org.pawfinder.dto.PaymentIntentResponse;
import org.pawfinder.entity.DonationStats;
import org.pawfinder.service.impl.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.math.BigDecimal;
import java.util.Optional;
import com.stripe.Stripe;

@RestController
@RequestMapping("/api/donation")
@CrossOrigin("*") // Allow frontend access
public class DonationController {

    @Autowired
    private StripeService stripeService;
    
    @Value("${stripe.webhook.secret}")
    private String webhookSecret;

    @PostMapping("/create-payment-intent")
    public ResponseEntity<?> createPaymentIntent(@RequestBody PaymentIntentRequest request) {
        try {
            if (request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Invalid donation amount"));
            }

            if (request.getDonorName() == null || request.getDonorName().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Donor name is required"));
            }

            if (request.getDonorEmail() == null || request.getDonorEmail().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Donor email is required"));
            }

            PaymentIntentResponse response = stripeService.createPaymentIntent(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "An unexpected error occurred"));
        }
    }

    @GetMapping("/donation-stats")
    public ResponseEntity<?> getDonationStats() {
        try {
            Optional<DonationStats> statsOptional = stripeService.getDonationStats();
            if (statsOptional.isPresent()) {
                return ResponseEntity.ok(statsOptional.get());
            } else {
                return ResponseEntity.ok(new DonationStats());
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to fetch donation statistics"));
        }
    }
    
    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
        System.out.println("Received webhook request with signature: " + sigHeader);
        try {
            Event event = Webhook.constructEvent(payload, sigHeader, webhookSecret);
            System.out.println("Webhook event type: " + event.getType());
            
            // Handle the event
            if ("payment_intent.succeeded".equals(event.getType())) {
                System.out.println("Processing payment_intent.succeeded event");
                PaymentIntent paymentIntent = (PaymentIntent) event.getData().getObject();
                System.out.println("Payment Intent ID: " + paymentIntent.getId());
                stripeService.processSuccessfulPayment(paymentIntent);
                return ResponseEntity.ok("Payment processed successfully");
            } else {
                System.out.println("Ignoring event type: " + event.getType());
                return ResponseEntity.ok("Event received: " + event.getType());
            }
        } catch (Exception e) {
            System.err.println("Webhook error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Webhook error: " + e.getMessage());
        }
    }

    @PostMapping("/test-payment")
    public ResponseEntity<String> testPayment(@RequestParam String paymentIntentId) {
        System.out.println("Test payment endpoint called with paymentIntentId: " + paymentIntentId);
        try {
            // Initialize Stripe with secret key
            Stripe.apiKey = stripeService.getStripeSecretKey();
            
            // Retrieve the payment intent
            System.out.println("Retrieving payment intent from Stripe");
            PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
            System.out.println("Payment intent retrieved: " + paymentIntent.getId() + ", Status: " + paymentIntent.getStatus());
            
            // Process the payment
            System.out.println("Processing payment");
            stripeService.processSuccessfulPayment(paymentIntent);
            
            return ResponseEntity.ok("Test payment processed successfully");
        } catch (Exception e) {
            System.err.println("Error processing test payment: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing test payment: " + e.getMessage());
        }
    }
}