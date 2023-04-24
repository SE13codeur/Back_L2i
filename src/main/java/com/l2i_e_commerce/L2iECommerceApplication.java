package com.l2i_e_commerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class L2iECommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(L2iECommerceApplication.class, args);
	}
}