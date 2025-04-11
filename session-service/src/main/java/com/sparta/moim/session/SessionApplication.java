package com.sparta.moim.session;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SessionApplication {

	public static void main(String[] args) {
		SpringApplication.run(SessionApplication.class, args);
	}

}
