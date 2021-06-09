package com.kinishinai.contacttracingapp;

import com.kinishinai.contacttracingapp.config.SwaggerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ContacttracingappApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContacttracingappApplication.class, args);
	}

}
