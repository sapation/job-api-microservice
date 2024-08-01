package com.webtech.service_rg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ServiceRgApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceRgApplication.class, args);
	}

}
