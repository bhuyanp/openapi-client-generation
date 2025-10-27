package io.github.bhuyanp.restapp.service;

import io.github.bhuyanp.restapp.dto.Product;
import io.github.bhuyanp.restapp.dto.ProductRequest;
import io.github.bhuyanp.restapp.entity.ProductEntity;
import io.github.bhuyanp.restapp.exception.DownstreamException;
import io.github.bhuyanp.restapp.repo.ProductsRepo;
import io.github.bhuyanp.restapp.util.Mappers;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductsRepo productsRepo;


    public List<Product> getProducts() {
        return productsRepo.findAll().stream().map(Mappers.PRODUCT_ENTITY_TO_PRODUCT).toList();
    }

    public Product addProduct(@NotNull ProductRequest productRequest) {
        ProductEntity productEntity = Mappers.PRODUCT_REQ_TO_PRODUCT_ENTITY.apply(productRequest);
        ProductEntity savedProductEntity = productsRepo.save(productEntity);
        return Mappers.PRODUCT_ENTITY_TO_PRODUCT.apply(savedProductEntity);
    }


    public Product getProduct(@NotBlank String id) {
        return productsRepo.findById(UUID.fromString(id)).map(Mappers.PRODUCT_ENTITY_TO_PRODUCT).orElseThrow(() -> new DownstreamException(HttpStatus.NOT_FOUND, "No product found with id " + id));
    }

    public Product updateProduct(@NotBlank String id, @NotNull ProductRequest incomingProductRequest) {
        Product existingProduct = getProduct(id);
        ProductEntity savedProductEntity = productsRepo.save(Mappers.MERGE_PRODUCTS_TO_PRODUCT_ENTITY.apply(existingProduct, incomingProductRequest));
        return Mappers.PRODUCT_ENTITY_TO_PRODUCT.apply(savedProductEntity);
    }

}
