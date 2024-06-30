package com.daveace.greenspaces;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class GreenSpacesApplication {

	public static void main(String[] args) {
		SpringApplication.run(GreenSpacesApplication.class, args);
	}

}