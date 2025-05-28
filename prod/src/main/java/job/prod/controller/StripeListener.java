package job.prod.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.stripe.model.checkout.Session;

import com.google.gson.JsonSyntaxException;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.*;
import com.stripe.net.ApiResource;
import com.stripe.net.Webhook;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import job.prod.repo.User;
import job.prod.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class StripeListener {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private HttpSession session;

    @Value("${stripe.webhook.secret}")
    private String endpointSecret;

    @PostMapping("/webhook")
    public ResponseEntity<String> stripeWebhook(@RequestBody String payload, HttpServletRequest request) {
        Event event;

        try {
            System.out.println("Raw payload: " + payload);
            String sigHeader = request.getHeader("Stripe-Signature");
            if (endpointSecret != null && sigHeader != null) {
                event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
            } else {
                event = ApiResource.GSON.fromJson(payload, Event.class);
            }
        } catch (JsonSyntaxException e) {
            System.out.println("⚠️  Webhook error while parsing request: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid payload");
        } catch (SignatureVerificationException e) {
            System.out.println("⚠️  Webhook error while validating signature: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature");
        }

        // Handle the event
        switch (event.getType()) {
            case "payment_intent.succeeded":
                handlePaymentIntentSucceeded(event);
                break;

            case "payment_intent.created":
                System.out.println("Payment intent created: " + event.getId());
                break;

            case "charge.succeeded":
                handleChargeSucceeded(event);
                break;

            case "payment_method.attached":
                handlePaymentMethodAttached(event);
                break;

            case "charge.failed":
                handleChargeFailed(event);
                break;

            case "checkout.session.completed":
                handleCheckoutSessionCompleted(event);
                break;

            default:
                System.out.println("Unhandled event type: " + event.getType());
                break;
        }

        return ResponseEntity.ok("Webhook received successfully");
    }

    private void handlePaymentIntentSucceeded(Event event) {
        try {
            EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
            if (dataObjectDeserializer.getObject().isPresent()) {
                PaymentIntent paymentIntent = (PaymentIntent) dataObjectDeserializer.getObject().get();
                System.out.println("PaymentIntent succeeded: " + paymentIntent.getId());
                
                String userIdStr = paymentIntent.getMetadata().get("userId");
                if (userIdStr != null) {
                    updateUserPaidStatus(userIdStr);
                }
            } else {
                System.out.println("⚠️  Unable to deserialize PaymentIntent object");
            }
        } catch (Exception e) {
            System.out.println("⚠️  Error handling PaymentIntent: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void handleChargeSucceeded(Event event) {
        try {
            EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
            if (dataObjectDeserializer.getObject().isPresent()) {
                Charge charge = (Charge) dataObjectDeserializer.getObject().get();
                System.out.println("Charge succeeded: " + charge.getId() + ", amount: " + charge.getAmount());
                // Additional business logic here if needed
            } else {
                System.out.println("⚠️  Unable to deserialize Charge object");
            }
        } catch (Exception e) {
            System.out.println("⚠️  Error handling Charge succeeded: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void handleChargeFailed(Event event) {
        try {
            EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
            if (dataObjectDeserializer.getObject().isPresent()) {
                Charge charge = (Charge) dataObjectDeserializer.getObject().get();
                System.out.println("Charge failed: " + charge.getId() + ", failure message: " + charge.getFailureMessage());
            } else {
                System.out.println("⚠️  Unable to deserialize Charge object");
            }
        } catch (Exception e) {
            System.out.println("⚠️  Error handling Charge failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void handlePaymentMethodAttached(Event event) {
        try {
            EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
            if (dataObjectDeserializer.getObject().isPresent()) {
                PaymentMethod paymentMethod = (PaymentMethod) dataObjectDeserializer.getObject().get();
                System.out.println("Processing payment method attached: " + paymentMethod.getId());
                // Additional business logic here
            } else {
                System.out.println("⚠️  Unable to deserialize PaymentMethod object");
            }
        } catch (Exception e) {
            System.out.println("⚠️  Error handling PaymentMethod attached: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void handleCheckoutSessionCompleted(Event event) {
        try {
            Session session = (Session) event.getDataObjectDeserializer().getObject().orElse(null);
            
            if (session == null) {
                System.out.println("Direct deserialization failed");
                String jsonData = ApiResource.GSON.toJson(event.getData().getObject());
                session = ApiResource.GSON.fromJson(jsonData, Session.class);
            }
            
            if (session != null) {
                System.out.println("Checkout session completed: " + session.getId());
                System.out.println("Session status: " + session.getStatus());
                System.out.println("Payment status: " + session.getPaymentStatus());
                
                String userIdStr = session.getMetadata() != null ? session.getMetadata().get("userId") : null;
                System.out.println("User ID from metadata: " + userIdStr);
                
                if (userIdStr != null) {
                    updateUserPaidStatus(userIdStr);
                } else {
                    System.out.println("⚠️  No userId found in session metadata");
                }
            } else {
                System.out.println("⚠️  Unable to deserialize Session object at all");
            }
        } catch (Exception e) {
            System.out.println("⚠️  Error handling checkout session completed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void updateUserPaidStatus(String userIdStr) {
        try {
            Long userId = Long.parseLong(userIdStr);
            User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
            user.setHasPaid(true);
            userRepo.save(user);
            System.out.println("Successfully updated user " + userId + " as paid");
        } catch (NumberFormatException e) {
            System.out.println("Invalid userId in metadata: " + userIdStr);
        } catch (Exception e) {
            System.out.println("Error updating user paid status: " + e.getMessage());
        }
    }
}