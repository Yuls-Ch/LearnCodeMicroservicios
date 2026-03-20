package com.learncode_backend.client;

import java.math.BigDecimal;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import com.learncode_backend.config.FeignConfig;

@FeignClient(
		name = "SUBSCRIPTION-SERVICE",
	    configuration = FeignConfig.class
)
public interface SubscriptionClient {
    @GetMapping("/api/subscription/daily-income")
    BigDecimal dailyIncome();
}