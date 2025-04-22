package com.reservationapp.reservationapp;

import org.springframework.core.env.Environment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ReservationappApplicationTests {

	@Autowired
	Environment env;

	@Test
	void contextLoads() {
		System.out.println("==== TEST CONFIG ====");
		System.out.println("URL: " + env.getProperty("spring.datasource.url"));
		System.out.println("Username: " + env.getProperty("spring.datasource.username"));
		System.out.println("Password: " + env.getProperty("spring.datasource.password"));
		System.out.println("======================");
	}
}

