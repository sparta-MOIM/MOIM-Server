package com.sparata.moim.gathering;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class GatheringApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatheringApplication.class, args);
	}

}
