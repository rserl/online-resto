package com.example.onlineresto;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Online Restaurant App"))
public class OnlineRestoApplication {
	public static void main(String[] args) {
		SpringApplication.run(OnlineRestoApplication.class, args);
	}

}
