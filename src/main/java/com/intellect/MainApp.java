package com.intellect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Starting point of the application
 * 
 * @author Balaji, Chandrasekaran
 *
 */
@SpringBootApplication (scanBasePackages={"com.intellect"})
@EnableScheduling
public class MainApp {
	public static void main(String[] args) {
		SpringApplication.run(MainApp.class, args);
	}
}
