package io.github.bhuyanp.restapp;

import io.github.bhuyanp.restapp.util.Mappers;
import io.github.bhuyanp.restapp.dto.Product;
import io.github.bhuyanp.restapp.repo.ProductsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class RestServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(RestServiceApplication.class, args);
	}


//	@Autowired
//	ProductsRepo productsRepo;
//	@Bean
//	ApplicationRunner applicationRunner(){
//		return args->{
//			productsRepo.save(Mappers.PRODUCT_TO_PRODUCT_ENTITY.apply(new Product(null, "Product 1", Product.TYPE.ELECTRONICS,11.23, LocalDateTime.now(), null)));
//			productsRepo.save(Mappers.PRODUCT_TO_PRODUCT_ENTITY.apply(new Product(null, "Product 2", Product.TYPE.BABY, 12.23, LocalDateTime.now(), null)));
//		};
//	}
}
