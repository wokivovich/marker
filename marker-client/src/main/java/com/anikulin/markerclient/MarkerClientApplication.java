package com.anikulin.markerclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MarkerClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarkerClientApplication.class, args);
	}

}
