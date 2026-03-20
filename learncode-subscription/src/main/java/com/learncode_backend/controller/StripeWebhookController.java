package com.learncode_backend.controller;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learncode_backend.model.StripeEvent;
import com.learncode_backend.repository.StripeEventRepository;
import com.learncode_backend.service.PaymentService;
import com.learncode_backend.service.SubscriptionService;
import com.nimbusds.jose.shaded.gson.JsonObject;
import com.nimbusds.jose.shaded.gson.JsonParser;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;

@RestController
@RequestMapping("/api/stripe")
public class StripeWebhookController {

    @Value("${stripe.webhook.secret}")
    private String webhookSecret;

    private final SubscriptionService subService;
    private final StripeEventRepository eventRepo;
    private final PaymentService paymentService;

    public StripeWebhookController(
        SubscriptionService subService,
        StripeEventRepository eventRepo,
        PaymentService paymentService
    ) {
        this.subService = subService;
        this.eventRepo = eventRepo;
		this.paymentService = paymentService;
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(
        @RequestBody String payload,
        @RequestHeader("Stripe-Signature") String sig
    ) {

        try {

            Event event = Webhook.constructEvent(
                payload,
                sig,
                webhookSecret
            );

            // Anti duplicado
            if (eventRepo.existsById(event.getId())) {
                return ResponseEntity.ok("Duplicado");
            }

            StripeEvent ev = new StripeEvent();
            ev.setId(event.getId());
            eventRepo.save(ev);

            System.out.println("EVENTO: " + event.getType());

            /* ===============================
               1️⃣ PRIMER PAGO
            =============================== */
            if ("checkout.session.completed".equals(event.getType())) {

                Session session = getSessionSafe(event, payload);

                String userId = session.getMetadata().get("userId");
                String planCode = session.getMetadata().get("planCode");

                if (userId == null || planCode == null) {
                    System.out.println("⚠️ Metadata vacía");
                    return ResponseEntity.ok("No metadata");
                }

                // Activar plan
                subService.activatePlanSafe(
                    UUID.fromString(userId),
                    planCode
                );

	                // Guardar primer pago
	                paymentService.registerPayment(
	                    userId,
	                    planCode,
	                    session.getAmountTotal().intValue(),
	                    session.getCurrency(),
	                    null,
	                    session.getId(),
	                    session.getCreated()
	                );

                System.out.println("✅ PRIMER PAGO OK");
            }

            /* ===============================
               2️⃣ RENOVACIONES
            =============================== */
            if ("invoice.payment_succeeded".equals(event.getType())) {

                JsonObject json = JsonParser.parseString(payload)
                    .getAsJsonObject();

                JsonObject invoice = json
                    .getAsJsonObject("data")
                    .getAsJsonObject("object");

                if (!invoice.has("subscription")
                    || invoice.get("subscription").isJsonNull()) {

                    System.out.println("⚠️ Invoice sin subscription");
                    return ResponseEntity.ok("No subscription");
                }

                String subscriptionId =
                    invoice.get("subscription").getAsString();

                Session session = Session.list(
                    Map.of("subscription", subscriptionId)
                ).getData().get(0);

                String userId = session.getMetadata().get("userId");
                String planCode = session.getMetadata().get("planCode");

                String paymentIntent = null;

                if (invoice.has("payment_intent")
                    && !invoice.get("payment_intent").isJsonNull()) {

                    paymentIntent =
                        invoice.get("payment_intent").getAsString();
                }

                paymentService.registerPayment(
                    userId,
                    planCode,
                    invoice.get("amount_paid").getAsInt(),
                    invoice.get("currency").getAsString(),
                    paymentIntent,
                    session.getId(),
                    invoice.get("created").getAsLong()
                );

                System.out.println("💰 RENOVACIÓN OK");
            }

            return ResponseEntity.ok("OK");

        } catch (Exception e) {

            System.err.println("❌ ERROR WEBHOOK:");
            e.printStackTrace();

            return ResponseEntity.ok("Handled"); // 👈 NUNCA 400
        }
    }

    private Session getSessionSafe(Event event, String payload)
    	    throws Exception {

    	    var obj = event.getDataObjectDeserializer().getObject();

    	    if (obj.isPresent()) {
    	        return (Session) obj.get();
    	    }

    	    // Fallback JSON
    	    JsonObject json = JsonParser.parseString(payload)
    	        .getAsJsonObject();

    	    String sessionId = json
    	        .getAsJsonObject("data")
    	        .getAsJsonObject("object")
    	        .get("id")
    	        .getAsString();

    	    return Session.retrieve(sessionId);
    	}

}

