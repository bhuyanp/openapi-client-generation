package io.github.bhuyanp.clientapp.controller;

import io.github.bhuyanp.restapp.client.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ClientApplicationController {
    private final io.github.bhuyanp.restapp.client.api.ProductApi productApi;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts() {
        return productApi.getProducts();
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        return productApi.getProductById(id);
    }

}
