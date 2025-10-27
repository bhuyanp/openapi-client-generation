package io.github.bhuyanp.restapp;

import io.github.bhuyanp.restapp.controller.AuthenticationController;
import io.github.bhuyanp.restapp.controller.ProductController;
import io.github.bhuyanp.restapp.dto.AuthRequest;
import io.github.bhuyanp.restapp.dto.Product;
import io.github.bhuyanp.restapp.dto.ProductRequest;
import io.github.bhuyanp.restapp.dto.TokenResponse;
import io.github.bhuyanp.restapp.repo.ProductsRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Prasanta Bhuyan
 */

@Testcontainers
@Transactional
@ActiveProfiles("integrationtest")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTests {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.0");

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    ProductsRepo productsRepo;

    @Test
    void connectionEstablished() {
        assertThat(postgres.isCreated()).isTrue();
        assertThat(postgres.isRunning()).isTrue();
    }

    @Value("${token.user}")
    private String user;
    @Value("${token.pass}")
    private String password;

    @Cacheable("authToken")
    String getToken() {
        return restTemplate.postForObject(AuthenticationController.AUTHENTICATION_API_PATH,
                new AuthRequest(user, password), TokenResponse.class).token();
    }


    @Test
    void getProducts_shouldFindAllProducts() {
        String token = getToken();
        RequestEntity<Void> requestEntity = RequestEntity.get(ProductController.PRODUCT_API_PATH)
                .header("Authorization", "Bearer " + token)
                .build();
        ResponseEntity<List<Product>> responseEntity = restTemplate.exchange(requestEntity, new ParameterizedTypeReference<List<Product>>() {
        });
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().size()).isGreaterThanOrEqualTo(2);
    }

    @Test
    void getProduct_shouldFindProductById() {
        String id = productsRepo.findAll().getFirst().getId().toString();
        String token = getToken();
        RequestEntity<Void> requestEntity = RequestEntity.get(ProductController.PRODUCT_API_PATH + "/" + id)
                .header("Authorization", "Bearer " + token)
                .build();
        ResponseEntity<Product> responseEntity = restTemplate.exchange(requestEntity, Product.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
    }

    @Test
    void getProduct_shouldThrow404WithInvalidProductId() {
        String token = getToken();
        RequestEntity<Void> requestEntity = RequestEntity.get(ProductController.PRODUCT_API_PATH + "/" + UUID.randomUUID())
                .header("Authorization", "Bearer " + token)
                .build();
        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @Rollback
    void addProduct_shouldAddNewProduct() {
        String token = getToken();
        RequestEntity<ProductRequest> requestEntity = RequestEntity.post(ProductController.PRODUCT_API_PATH)
                .header("Authorization", "Bearer " + token)
                .body(new ProductRequest("Title XYZ", Product.TYPE.BEAUTY, 33.33));
        ResponseEntity<Product> responseEntity = restTemplate.exchange(requestEntity, Product.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody()).isNotNull()
                .hasFieldOrPropertyWithValue("title", "Title XYZ")
                .hasFieldOrPropertyWithValue("type", Product.TYPE.BEAUTY)
                .hasFieldOrPropertyWithValue("price", 33.33);
    }

    @Test
    @Rollback
    void addProduct_shouldFailWith400Error() {
        String token = getToken();
        RequestEntity<ProductRequest> requestEntity = RequestEntity.post(ProductController.PRODUCT_API_PATH)
                .header("Authorization", "Bearer " + token)
                .body(new ProductRequest("Title XYZ", Product.TYPE.BEAUTY, null));
        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @Rollback
    void updateProduct_shouldUpdateAnExistingProduct() {
        String id = productsRepo.findAll().getFirst().getId().toString();
        String token = getToken();
        RequestEntity<ProductRequest> requestEntity = RequestEntity.put(ProductController.PRODUCT_API_PATH+"/"+id)
                .header("Authorization", "Bearer " + token)
                .body(new ProductRequest("Title 123", Product.TYPE.BABY, 43.33));
        ResponseEntity<Product> responseEntity = restTemplate.exchange(requestEntity, Product.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @Rollback
    void updateProduct_shouldThrow400BadRequest() {
        String id = productsRepo.findAll().getFirst().getId().toString();
        String token = getToken();
        RequestEntity<ProductRequest> requestEntity = RequestEntity.put(ProductController.PRODUCT_API_PATH+"/"+id)
                .header("Authorization", "Bearer " + token)
                .body(new ProductRequest("Title 123", null, 43.33));
        ResponseEntity<Void> responseEntity = restTemplate.exchange(requestEntity, Void.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @Rollback
    void partialUpdateProduct_shouldUpdateAnExistingProduct() {
        String id = productsRepo.findAll().getFirst().getId().toString();
        String token = getToken();
        RequestEntity<ProductRequest> requestEntity = RequestEntity.patch(ProductController.PRODUCT_API_PATH+"/"+id)
                .header("Authorization", "Bearer " + token)
                .body(new ProductRequest("Title 123", null, 43.33));
        ResponseEntity<Void> responseEntity = restTemplate.exchange(requestEntity, Void.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

}
