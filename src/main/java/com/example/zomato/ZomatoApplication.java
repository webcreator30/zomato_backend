package com.example.zomato;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

import io.github.cdimascio.dotenv.Dotenv;

@EnableAsync
@SpringBootApplication
@EnableCaching
public class ZomatoApplication {

	public static void main(String[] args) {

		Dotenv dotenv = Dotenv.load();
		System.setProperty("SPRING_DATASOURCE_URL", dotenv.get("SPRING_DATASOURCE_URL"));
		System.setProperty("SPRING_DATASOURCE_USERNAME", dotenv.get("SPRING_DATASOURCE_USERNAME"));
		System.setProperty("SPRING_DATASOURCE_PASSWORD", dotenv.get("SPRING_DATASOURCE_PASSWORD"));
		System.setProperty("SPRING_REDIS_HOST", dotenv.get("SPRING_REDIS_HOST"));
		System.setProperty("SPRING_REDIS_PORT", dotenv.get("SPRING_REDIS_PORT"));
		System.setProperty("FAST2SMS_API_KEY", dotenv.get("FAST2SMS_API_KEY"));
		System.setProperty("RAZORPAY_API_KEY", dotenv.get("RAZORPAY_API_KEY"));
		System.setProperty("RAZORPAY_KEY_SECRET", dotenv.get("RAZORPAY_KEY_SECRET"));
		SpringApplication.run(ZomatoApplication.class, args);
	}

}
