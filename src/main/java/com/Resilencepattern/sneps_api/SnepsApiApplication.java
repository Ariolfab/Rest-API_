package com.Resilencepattern.sneps_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SnepsApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SnepsApiApplication.class, args);
	}

@Bean
public RestTemplate restTemplate() {
    return new RestTemplate();
}

}
