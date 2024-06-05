package com.grocery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.grocery.repository")
public class AdminGroceryManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminGroceryManagementApplication.class, args);
	}

}
