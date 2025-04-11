package com.sparta.moim.user.integration;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@AutoConfigureMockMvc
public abstract class IntegrationTest {

  private static final DockerImageName MYSQL_IMAGE_NAME = DockerImageName.parse("mysql:8.0");

  private static final MySQLContainer<?> mySQLContainer;

  static {
    mySQLContainer = new MySQLContainer<>(MYSQL_IMAGE_NAME);
    mySQLContainer.start();
  }

  @DynamicPropertySource
  private static void dynamicProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
    registry.add("spring.datasource.driver-class-name", mySQLContainer::getDriverClassName);
    registry.add("spring.datasource.username", mySQLContainer::getUsername);
    registry.add("spring.datasource.password", mySQLContainer::getPassword);
  }
}
