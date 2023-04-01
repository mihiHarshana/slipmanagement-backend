package com.mhsoft;

import com.mhsoft.service.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootSlipManagementBackEnd {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSlipManagementBackEnd.class, args);

	}

}