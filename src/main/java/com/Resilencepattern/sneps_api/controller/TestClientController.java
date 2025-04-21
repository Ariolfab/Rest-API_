package com.Resilencepattern.sneps_api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/test-client")
public class TestClientController {

    private final RestTemplate restTemplate;

    @Autowired
    public TestClientController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
@GetMapping("/products")
public List<String> callProductsMultipleTimes() {
    List<String> results = new ArrayList<>();

    for (int i = 1; i <= 10; i++) {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/products", String.class);
            System.out.println("✅ Request " + i + " OK");
            results.add("OK " + i);
        } catch (Exception e) {
            System.out.println("❌ Request " + i + " failed: " + e.getMessage());
            results.add("FAIL " + i + " : " + e.getMessage());
        }
    }

    return results;
}
}
