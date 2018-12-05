package com.example.idkw.issue894;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.idkw")
public class Issue894Application {

	public static void main(String[] args) {
		SpringApplication.run(Issue894Application.class, args);
	}
}
