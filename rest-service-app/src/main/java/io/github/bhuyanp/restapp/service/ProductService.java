package io.github.bhuyanp.restapp.service;

import io.github.bhuyanp.restapp.dto.Mappers;
import io.github.bhuyanp.restapp.dto.Product;
import io.github.bhuyanp.restapp.dto.ProductRequest;
import io.github.bhuyanp.restapp.entity.ProductEntity;
import io.github.bhuyanp.restapp.exception.DownstreamException;
import io.github.bhuyanp.restapp.repo.ProductsRepo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductsRepo productsRepo;


    public List<Product> getProducts() {
        return productsRepo.findAll().stream().map(Mappers.PRODUCT_ENTITY_TO_PRODUCT).toList();
    }

    public Product addProduct(@NotNull ProductRequest productRequest) {
        ProductEntity productEntity = Mappers.ADD_PRODUCT_REQ_TO_PRODUCT_ENTITY.apply(productRequest);
        ProductEntity savedProductEntity = productsRepo.save(productEntity);
        return Mappers.PRODUCT_ENTITY_TO_PRODUCT.apply(savedProductEntity);
    }


    public Product getProduct(@NotBlank String id) {
        return productsRepo.findById(id).map(Mappers.PRODUCT_ENTITY_TO_PRODUCT).orElseThrow(() -> new DownstreamException(HttpStatus.NOT_FOUND, "No product found with id " + id));
    }

    public Product updateProduct(@NotBlank String id, @NotNull ProductRequest incomingProductRequest) {
        Product existingProduct = getProduct(id);
        Product updatedProduct = new Product(existingProduct.id(),incomingProductRequest.title(), incomingProductRequest.type(), incomingProductRequest.price(), existingProduct.createdDate(), LocalDateTime.now());
        ProductEntity savedProductEntity = productsRepo.save(Mappers.PRODUCT_TO_PRODUCT_ENTITY.apply(updatedProduct));
        return Mappers.PRODUCT_ENTITY_TO_PRODUCT.apply(savedProductEntity);
    }

    public Product partiallyUpdateProduct(@NotBlank String id, @NotNull ProductRequest incomingProductRequest) {
        Product existingProduct = getProduct(id);
        ProductEntity savedProductEntity = productsRepo.save(Mappers.MERGE_PRODUCTS_TO_PRODUCT.apply(existingProduct, incomingProductRequest));
        return Mappers.PRODUCT_ENTITY_TO_PRODUCT.apply(savedProductEntity);
    }

}
