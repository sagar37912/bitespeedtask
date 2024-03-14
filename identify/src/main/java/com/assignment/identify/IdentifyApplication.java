package com.assignment.identify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.assignment.identify.repositories"})
public class IdentifyApplication {

	public static void main(String[] args) {
		SpringApplication.run(IdentifyApplication.class, args);
	}

}
