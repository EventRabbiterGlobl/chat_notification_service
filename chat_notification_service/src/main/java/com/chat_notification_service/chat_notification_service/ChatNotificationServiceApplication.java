package com.chat_notification_service.chat_notification_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.UUID;

@SpringBootApplication
@EnableFeignClients
public class ChatNotificationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatNotificationServiceApplication.class, args);
	}

}
