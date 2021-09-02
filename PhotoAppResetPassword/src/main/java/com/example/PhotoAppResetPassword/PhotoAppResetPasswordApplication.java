package com.example.PhotoAppResetPassword;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication @EnableDiscoveryClient
public class PhotoAppResetPasswordApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhotoAppResetPasswordApplication.class, args);
	}

}
