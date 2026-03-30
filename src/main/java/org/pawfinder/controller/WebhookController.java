package org.pawfinder.controller;


import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.StripeObject;
import com.stripe.net.Webhook;
import org.pawfinder.service.impl.StripeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/webhook")
public class WebhookController {

    @Value("${stripe.webhook.secret}")
    private String webhookSecret;

    @Autowired
    private StripeService StripeService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> handleStripeWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String sigHeader) {

        Map<String, Object> response = new HashMap<>();

        try {
            // Verify the webhook signature
            Event event = Webhook.constructEvent(
                    payload, sigHeader, webhookSecret
            );

            System.out.println("Webhook received: " + event.getType());

            // Handle successful payments
            if ("payment_intent.succeeded".equals(event.getType())) {
                EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();

                if (dataObjectDeserializer.getObject().isPresent()) {
                    StripeObject stripeObject = dataObjectDeserializer.getObject().get();

                    if (stripeObject instanceof PaymentIntent) {
                        PaymentIntent paymentIntent = (PaymentIntent) stripeObject;
                        System.out.println("Payment succeeded: " + paymentIntent.getId());

                        // Process the successful payment
                        StripeService.processSuccessfulPayment(paymentIntent);
                    }
                }
            }

            response.put("status", "success");
            response.put("message", "Webhook processed successfully");
            return ResponseEntity.ok(response);

        } catch (SignatureVerificationException e) {
            // Invalid signature
            System.out.println("Invalid webhook signature: " + e.getMessage());
            response.put("status", "error");
            response.put("message", "Invalid webhook signature");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            // Other errors
            System.out.println("Webhook error: " + e.getMessage());
            response.put("status", "error");
            response.put("message", "Webhook processing error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}