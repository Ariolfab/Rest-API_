package com.Resilencepattern.sneps_api.client;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

@Service
public class RestClientService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String baseUrl = "http://localhost:8080/products";

    @RateLimiter(name = "clientRateLimiter", fallbackMethod = "fallback")
    public String callProductApi() {
        return restTemplate.getForObject(baseUrl, String.class);
    }

    public String fallback(Throwable t) {
        return "❌ Too many requests - client rate limit hit.";
    }
}