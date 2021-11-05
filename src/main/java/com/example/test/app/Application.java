package com.example.test.app;

import com.example.test.app.controller.Controller;
import com.example.test.app.models.Account;
import com.example.test.app.models.Product;
import com.example.test.app.models.User;
import com.example.test.app.repository.AccountRepository;
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
	UserRepository userRepository;

	@Autowired
	Controller controller;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner run (AdminService adminService)
	{

		return args -> {
			adminService.createProduct(new Product(null,"SAVINGS ACCOUNT","savings account", 1000000L));
			adminService.createProduct(new Product(null,"CURRENT ACCOUNT","current account", 10000000L));

			controller.createSuperAdmin("superadmin","superadmin@gmail.com","@superadmin123","+237653368683");

				CreateUserRequest createUserRequest = new CreateUserRequest();
				createUserRequest.setPassword("@admin123");
				createUserRequest.setUserName("rouclec123");
				createUserRequest.setPhoneNumber("+237650184172");
				createUserRequest.setEmail("senatorasonganyi97@gmail.com");
				createUserRequest.setAccountNumber("1234");
				createUserRequest.setAccountDurationInMonths(5L);
				createUserRequest.setProductName("current account");

				controller.signUp(createUserRequest);

		};
	}


}
