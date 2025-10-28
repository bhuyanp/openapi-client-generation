package io.github.bhuyanp.restapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class RestServiceApplication {
	public static void main(String[] args) {
		System.out.println("Incoming arguments : "+Arrays.toString(args));
		SpringApplication.run(RestServiceApplication.class, args);
	}
}
