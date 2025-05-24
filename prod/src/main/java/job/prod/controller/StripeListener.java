package job.prod.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonSyntaxException;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.model.StripeObject;
import com.stripe.net.ApiResource;
import com.stripe.net.Webhook;
import jakarta.servlet.http.HttpServletRequest;


@RestController
public class StripeListener {
    
    @Value("${stripe.webhook.secret}")
    private String endpointSecret;

    @PostMapping("/webhook")
    public ResponseEntity<String> stripeWebhook(@RequestBody String payload, HttpServletRequest request) {
        Event event;
        
        // First, verify the event
        try {
            // Parse the event from the JSON payload
            event = ApiResource.GSON.fromJson(payload, Event.class);
            
            // Verify the signature if endpoint secret is configured
            String sigHeader = request.getHeader("Stripe-Signature");
            if (endpointSecret != null && sigHeader != null) {
                event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
            }
        } catch (JsonSyntaxException e) {
            // Invalid payload
            System.out.println("⚠️  Webhook error while parsing basic request: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid payload");
        } catch (SignatureVerificationException e) {
            // Invalid signature
            System.out.println("⚠️  Webhook error while validating signature: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature");
        }

        // Deserialize the nested object inside the event
        EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
        StripeObject stripeObject;
        
        if (dataObjectDeserializer.getObject().isPresent()) {
            stripeObject = dataObjectDeserializer.getObject().get();
        } else {
            // Deserialization failed, probably due to an API version mismatch
            System.out.println("⚠️  Webhook error: Unable to deserialize event object");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to deserialize event object");
        }

        // Handle the event based on type
        switch (event.getType()) {
            case "payment_intent.succeeded":
                PaymentIntent paymentIntent = (PaymentIntent) stripeObject;
                System.out.println("Payment for " + paymentIntent.getAmount() + " succeeded.");
                // Handle the successful payment intent - implement this method
                handlePaymentIntentSucceeded(paymentIntent);
                break;
                
            case "payment_method.attached":
                PaymentMethod paymentMethod = (PaymentMethod) stripeObject;
                // Handle the successful attachment of a PaymentMethod - implement this method
                handlePaymentMethodAttached(paymentMethod);
                break;
                
            default:
                System.out.println("Unhandled event type: " + event.getType());
                break;
        }
        
        // Return a 200 response to indicate receipt of the event
        return ResponseEntity.ok("Webhook received successfully");
    }
    
    /**
     * Handle successful payment intent
     */
    private void handlePaymentIntentSucceeded(PaymentIntent paymentIntent) {
        // Implement your business logic here
        // For example: Update order status, send confirmation email, etc.
        System.out.println("Processing payment intent: " + paymentIntent.getId());
    }
    
    /**
     * Handle payment method attached event
     */
    private void handlePaymentMethodAttached(PaymentMethod paymentMethod) {
        // Implement your business logic here
        System.out.println("Processing payment method: " + paymentMethod.getId());
    }
}