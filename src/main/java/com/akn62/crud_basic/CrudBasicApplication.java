package com.akn62.crud_basic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class CrudBasicApplication {

	public static void main(String[] args) {

		SpringApplication.run(CrudBasicApplication.class, args);
		System.out.println("hello world");
	}

}
