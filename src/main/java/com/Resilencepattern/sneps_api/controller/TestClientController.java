package com.Resilencepattern.sneps_api.controller;

import com.Resilencepattern.sneps_api.client.RestClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/test-client")
public class TestClientController {

    @Autowired
    private RestClientService restClientService;

    @GetMapping("/products")
    public List<String> callProductsMultipleTimes() {
        List<String> results = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            try {
                String response = restClientService.callProductApi();
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

