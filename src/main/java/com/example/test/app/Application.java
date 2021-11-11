package com.example.test.app;

import com.example.test.app.controller.Controller;
import com.example.test.app.models.Account;
import com.example.test.app.models.Product;
import com.example.test.app.models.User;
import com.example.test.app.repository.AccountRepository;
import com.example.test.app.repository.ProductRepository;
import com.example.test.app.repository.UserRepository;
import com.example.test.app.request.CreateUserRequest;
import com.example.test.app.service.AdminService;
import com.example.test.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
@EnableSwagger2
@EnableJpaRepositories
@EnableScheduling
public class Application<accounts> {
	@Autowired
	ProductRepository productRepository;

	@Autowired
	Controller controller;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner run (AdminService adminService)
	{

		return args -> {
			Product product1 = new Product(null,"SAVINGS ACCOUNT","savings account", 1000000L,"superadmin",LocalDateTime.now());
			Product product2 = new Product(null,"CURRENT ACCOUNT","current account", 10000000L,"superadmin",LocalDateTime.now());
			productRepository.save(product1);
			productRepository.save(product2);
			controller.createSuperAdmin("superadmin","superadmin@gmail.com","@superadmin123","+237653368683");

				CreateUserRequest createUserRequest = new CreateUserRequest();
				createUserRequest.setPassword("@admin123");
				createUserRequest.setUserName("rouclec123");
				createUserRequest.setPhoneNumber("+237650184172");
				createUserRequest.setEmail("senatorasonganyi97@gmail.com");
				createUserRequest.setAccountDurationInMonths(5L);
				controller.signUp(createUserRequest);

		};
	}


}
