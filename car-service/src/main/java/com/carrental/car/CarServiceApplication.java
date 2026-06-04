package com.carrental.car;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Notice: NO @EnableEurekaClient annotation needed here.
 *
 * In Spring Cloud 2021+ the Eureka client is auto-configured just by having
 * spring-cloud-starter-netflix-eureka-client on the classpath.
 * The eureka.client.service-url in application.properties is enough.
 *
 * On startup this service will:
 *   1. Start on port 8081
 *   2. Register itself with Eureka as "CAR-SERVICE"
 *   3. Start sending heartbeats every 5 seconds
 *   4. Appear in the Eureka dashboard at http://localhost:8761
 */
@SpringBootApplication
public class CarServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarServiceApplication.class, args);
    }
}
