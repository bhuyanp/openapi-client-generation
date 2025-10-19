package io.github.bhuyanp.restapp.service;

import io.github.bhuyanp.restapp.dto.AddProductRequest;
import io.github.bhuyanp.restapp.model.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    private static final List<Product> PRODUCTS = new ArrayList<>();

    static {
        PRODUCTS.add(new Product(UUID.randomUUID().toString(), "Product 1", Product.TYPE.ELECTRONICS,11.23, LocalDateTime.now()));
        PRODUCTS.add(new Product(UUID.randomUUID().toString(), "Product 2", Product.TYPE.BABY, 12.23, LocalDateTime.now()));
        PRODUCTS.add(new Product(UUID.randomUUID().toString(), "Product 3", Product.TYPE.BEAUTY, 13.23, LocalDateTime.now()));
    }

    public List<Product> getProducts() {
        return PRODUCTS;
    }

    public Product addProduct(@NotNull AddProductRequest addProductRequest) {
        Product productWithId = new Product(UUID.randomUUID().toString(), addProductRequest.title(), addProductRequest.type(), addProductRequest.price(), LocalDateTime.now());
        PRODUCTS.add(productWithId);
        return productWithId;
    }


    public Optional<Product> getProduct(@NotBlank String id) {
        return PRODUCTS.stream().filter(product -> product.id().equalsIgnoreCase(id)).findFirst();
    }

}
