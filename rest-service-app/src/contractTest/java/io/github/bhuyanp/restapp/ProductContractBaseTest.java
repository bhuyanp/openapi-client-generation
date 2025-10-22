package io.github.bhuyanp.restapp;


import io.github.bhuyanp.restapp.controller.ProductController;
import io.github.bhuyanp.restapp.dto.Product;
import io.github.bhuyanp.restapp.service.ProductService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = RestServiceApplication.class)
public class ProductContractBaseTest {

    @Autowired
    private ProductController productController;

    @MockitoBean
    private ProductService productService;

    @BeforeEach
    public void setupProduct() {
        RestAssuredMockMvc.standaloneSetup(productController);
        // When
        when(productService.getProducts()).thenReturn(List.of(
                new Product("id1", "Product A", Product.TYPE.ELECTRONICS, 12.12, LocalDateTime.of(2024, 1, 1, 12, 12, 12), null),
                new Product("id2", "Product B", Product.TYPE.ELECTRONICS, 12.21, LocalDateTime.of(2025, 1, 1, 12, 12, 12), null)
        ));
        when(productService.getProduct("id1")).thenReturn(
                new Product("id1", "Product A", Product.TYPE.ELECTRONICS, 12.12, LocalDateTime.of(2024, 1, 1, 12, 12, 12), null)
        );
        when(productService.addProduct(any())).thenReturn(
                new Product("id3", "Product AAA", Product.TYPE.ELECTRONICS, 22.12, LocalDateTime.of(2025, 1, 1, 12, 12, 12), null)
        );
        when(productService.updateProduct(any(), any())).thenReturn(
                new Product("id3", "Product AAA", Product.TYPE.ELECTRONICS, 33.12, LocalDateTime.of(2025, 1, 2, 12, 12, 12), null)
        );
    }
}
