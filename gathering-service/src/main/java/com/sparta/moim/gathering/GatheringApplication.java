package com.sparta.moim.gathering;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class GatheringApplication {

	/**
	 * 애플리케이션의 진입점으로, Spring Boot 애플리케이션을 실행합니다.
	 *
	 * @param args 커맨드라인 인자
	 */
	public static void main(String[] args) {
		SpringApplication.run(GatheringApplication.class, args);
	}

}
