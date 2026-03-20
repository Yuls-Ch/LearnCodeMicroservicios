package com.learncode_backend.service;

import org.springframework.stereotype.Service;

import com.learncode_backend.model.Plan;
import com.learncode_backend.model.Subscription;
import com.learncode_backend.repository.PlanRepository;
import com.learncode_backend.repository.SubscriptionRepository;

import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
public class SubscriptionService {

	private final SubscriptionRepository repo;
    private final PlanRepository planRepo;

    public SubscriptionService(
        SubscriptionRepository repo,
        PlanRepository planRepo
    ) {
        this.repo = repo;
        this.planRepo = planRepo;
    }

    @Transactional
    public void activatePlanSafe(UUID userId, String planCode) {

        Optional<Subscription> existing = repo.findByUserId(userId);
        LocalDateTime now = LocalDateTime.now();

        Subscription sub = existing.orElseGet(Subscription::new);

        sub.setUserId(userId);
        sub.setPlanCode(planCode);
        sub.setStatus("ACTIVE");
        sub.setStartAt(now);
        sub.setUpdatedAt(now);

        if ("FREE".equals(planCode)) {
            sub.setEndAt(null); 
        } else {
            Plan plan = planRepo.findByCode(planCode).orElseThrow();
            sub.setEndAt(now.plusDays(plan.getDurationDays())); 
        }

        repo.save(sub);
    }



    public String getUserPlan(UUID userId) {

        return repo.findByUserId(userId)
            .filter(s -> "ACTIVE".equals(s.getStatus()))
            .map(Subscription::getPlanCode)
            .orElse("FREE");
    }
    
    public Subscription getSubscription(UUID userId) {

        return repo.findByUserId(userId)
            .orElse(null);
    }
    
    @Transactional
    public void cancelSubscription(UUID userId) {

        Subscription sub = repo.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("No tiene suscripción activa"));

        sub.setStatus("CANCELED");
        sub.setUpdatedAt(LocalDateTime.now());

        repo.save(sub);
    }



}
