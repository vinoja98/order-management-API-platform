package com.management.orders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OrdersApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrdersApplication.class, args);
	}

	@EventListener
	public void onApplicationReadyEvent(ApplicationReadyEvent event) {
		System.out.println("************************");
		System.out.println("*** Order API Ready ***");
		System.out.println("************************");
	}

}
