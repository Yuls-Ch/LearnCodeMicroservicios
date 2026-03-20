package com.learncode_backend.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import com.learncode_backend.config.FeignConfig;

@FeignClient(
	    name = "CATALOG-SERVICE",
	    configuration = FeignConfig.class
)
public interface CatalogClient {

    @GetMapping("/api/catalog/count")
    long countCourses();
}
