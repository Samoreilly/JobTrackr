package job.prod.controller;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import job.prod.entity.FrontendConfig;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    
    private final FrontendConfig frontendConfig;

    public PaymentController(FrontendConfig frontendConfig) {
        this.frontendConfig = frontendConfig;
    }
    
    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @PostMapping("/create-checkout-session")
    public Map<String, Object> createCheckoutSession(@RequestParam Long userId) throws StripeException {
        Stripe.apiKey = stripeApiKey;
//price_1RHORDJfaVgCaIbYOT4TcRuh -- test
//price_1RHNfCJfaVgCaIbYVoCY8lVJ -- real
        String baseUrl = frontendConfig.getUrl();

        SessionCreateParams params = SessionCreateParams.builder()
            .setMode(SessionCreateParams.Mode.PAYMENT)
            .setSuccessUrl(baseUrl + "/home")
            .setCancelUrl(baseUrl +"/home")
            .addLineItem(SessionCreateParams.LineItem.builder()
                .setPrice("price_1RHORDJfaVgCaIbYOT4TcRuh")
                .setQuantity(1L)
                .build())
            .putMetadata("userId", userId.toString())
            .build();

        Session session = Session.create(params);

        Map<String, Object> response = new HashMap<>();
        response.put("checkoutUrl", session.getUrl());
        return response;
    }
}
