package com.example.spring_boot_scheduled;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringBootScheduledApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootScheduledApplication.class, args);
	}

}
