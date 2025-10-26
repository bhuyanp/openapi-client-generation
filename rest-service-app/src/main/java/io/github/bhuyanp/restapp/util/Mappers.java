package io.github.bhuyanp.restapp.util;

import io.github.bhuyanp.restapp.dto.Product;
import io.github.bhuyanp.restapp.dto.ProductRequest;
import io.github.bhuyanp.restapp.entity.ProductEntity;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Mappers {

    private Mappers(){}

    public static final Function<ProductRequest, ProductEntity> PRODUCT_REQ_TO_PRODUCT_ENTITY = product -> new ProductEntity(null, product.title(), product.type(), product.price(), LocalDateTime.now(), null);
//    public static final Function<Product, ProductEntity> PRODUCT_TO_PRODUCT_ENTITY = product -> new ProductEntity(product.id(), product.title(), product.type(), product.price(), product.createdDate(), product.updatedDate());
    public static final Function<ProductEntity, Product> PRODUCT_ENTITY_TO_PRODUCT = productEntity -> new Product(productEntity.getId(), productEntity.getTitle(), productEntity.getType(), productEntity.getPrice(), productEntity.getCreatedDate(), productEntity.getUpdatedDate());
    public static final BiFunction<Product, ProductRequest, ProductEntity> MERGE_PRODUCTS_TO_PRODUCT_ENTITY = (existingProduct, incomingProductRequest) ->
            new ProductEntity(
                    existingProduct.id(),
                    Objects.requireNonNullElse(incomingProductRequest.title(), existingProduct.title()),
                    Objects.requireNonNullElse(incomingProductRequest.type(), existingProduct.type()),
                    Objects.requireNonNullElse(incomingProductRequest.price(), existingProduct.price()),
                    existingProduct.createdDate(),
                    LocalDateTime.now()
            );


}
