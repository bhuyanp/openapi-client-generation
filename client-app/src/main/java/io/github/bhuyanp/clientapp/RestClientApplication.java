package io.github.bhuyanp.clientapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class RestClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(RestClientApplication.class, args);
    }
}
