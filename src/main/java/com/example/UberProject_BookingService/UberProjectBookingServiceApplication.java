package com.example.UberProject_BookingService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.example.UberProject_EntityService.models")
@EnableJpaAuditing
@EnableDiscoveryClient
public class UberProjectBookingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UberProjectBookingServiceApplication.class, args);
	}

}
