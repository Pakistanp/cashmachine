package com.ppapierz.cashmachine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
public class CashmachineApplication {

	public static void main(String[] args) {
		SpringApplication.run(CashmachineApplication.class, args);
	}

}
