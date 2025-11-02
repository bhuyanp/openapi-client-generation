package io.github.bhuyanp.restapp.product;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.ParameterType;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bhuyanp.restapp.client.api.ProductsApi;
import io.github.bhuyanp.restapp.client.model.Product;
import io.github.bhuyanp.restapp.client.model.ProductRequest;
import io.github.bhuyanp.restapp.exception.DownstreamException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@RequiredArgsConstructor
public class ProductStepDefinitions {
    public static final String PRODUCT_TAG = "@Product";
    private final ProductContext context;
    private final ProductsApi productsApi;

    @ParameterType("ELECTRONICS|BABY|BEAUTY")
    public ProductRequest.TypeEnum productRequestType(String type) {
        return ProductRequest.TypeEnum.valueOf(type);
    }

    @ParameterType("ELECTRONICS|BABY|BEAUTY")
    public Product.TypeEnum productType(String type) {
        return Product.TypeEnum.valueOf(type);
    }

    @Before(PRODUCT_TAG)
    public void beforeEachProductScenario(Scenario scenario) {
        log.info("Product Context Before {}: {}", scenario.getName(), context);
    }

    @After(PRODUCT_TAG)
    public void afterEachProductScenario(Scenario scenario) {
        log.info("Product Context After {}: {}", scenario.getName(), context);
        context.getProducts()
                .forEach(product -> {
                    log.info("Deleting product id: {}", product.getId());
                    productsApi.deleteProduct(product.getId());
                });
    }


    @When("user adds new product with title {string}, type {productRequestType} and price {double}")
    public void userAddsNewProductWithTitleTestProductNewAndPrice(String title, ProductRequest.TypeEnum type, double price) {
        try {
            ResponseEntity<Product> productResponseEntity = productsApi.addProduct(new ProductRequest(title, type, price));
            context.setHttpStatusCode(productResponseEntity.getStatusCode().value());
            if (productResponseEntity.getStatusCode().is2xxSuccessful()) {
                Product product = productResponseEntity.getBody();
                log.info("Product getting added to the context: {}", product);
                context.setProducts(List.of(product));
            }
        } catch (DownstreamException dse) {
            log.info("DownstreamException inside add product.", dse);
            context.setHttpStatusCode(dse.getHttpStatus().value());
            context.setErrorMessage(dse.getMessage());
        }
    }

    @Given("a product exists with title {string}, type {productRequestType} and price {double}")
    public void productExistsWithTitleAndPrice(String title, ProductRequest.TypeEnum type, double price) {
        try {
            ResponseEntity<Product> productResponseEntity = productsApi.addProduct(new ProductRequest(title, type, price));
            context.setHttpStatusCode(productResponseEntity.getStatusCode().value());
            if (productResponseEntity.getStatusCode().is2xxSuccessful()) {
                Product product = productResponseEntity.getBody();
                log.info("Product getting added to the context: {}", product);
                context.setProducts(List.of(product));
            }
        } catch (DownstreamException dse) {
            log.info("DownstreamException inside add product.", dse);
            context.setHttpStatusCode(dse.getHttpStatus().value());
            context.setErrorMessage(dse.getMessage());
        }
    }

    @When("user fetches existing product")
    public void userFetchesExistingProduct() {
        try {
            ResponseEntity<Product> productResponseEntity = productsApi.getProductById(context.getProducts().getFirst().getId());
            context.setHttpStatusCode(productResponseEntity.getStatusCode().value());
            if (productResponseEntity.getStatusCode().is2xxSuccessful()) {
                Product product = productResponseEntity.getBody();
                context.setProducts(List.of(product));
            }
        } catch (DownstreamException dse) {
            log.info("DownstreamException inside get product.", dse);
            context.setHttpStatusCode(dse.getHttpStatus().value());
            context.setErrorMessage(dse.getMessage());
        }
    }

    @When("user fetches a non existing product")
    public void userFetchesANonExistingProduct() {
        try {
            ResponseEntity<Product> productResponseEntity = productsApi.getProductById(UUID.randomUUID().toString());
            context.setHttpStatusCode(productResponseEntity.getStatusCode().value());
            if (productResponseEntity.getStatusCode().is2xxSuccessful()) {
                Product product = productResponseEntity.getBody();
                context.setProducts(List.of(product));
            }
        } catch (DownstreamException dse) {
            log.info("DownstreamException inside get product.", dse);
            context.setHttpStatusCode(dse.getHttpStatus().value());
            context.setErrorMessage(dse.getMessage());
        }
    }

    @When("user deletes an existing product")
    public void userDeletesAnExistingProduct() {
        try {
            ResponseEntity<Void> productResponseEntity = productsApi.deleteProduct(context.getProducts().getFirst().getId());
            context.setHttpStatusCode(productResponseEntity.getStatusCode().value());
            context.setProducts(Collections.EMPTY_LIST);
        } catch (DownstreamException dse) {
            log.info("DownstreamException inside delete product.", dse);
            context.setHttpStatusCode(dse.getHttpStatus().value());
            context.setErrorMessage(dse.getMessage());
        }
    }

    @When("user deletes a non existing product")
    public void userDeletesANonExistingProduct() {
        try {
            ResponseEntity<Void> productResponseEntity = productsApi.deleteProduct(UUID.randomUUID().toString());
            context.setHttpStatusCode(productResponseEntity.getStatusCode().value());
        } catch (DownstreamException dse) {
            log.info("DownstreamException inside delete product.", dse);
            context.setHttpStatusCode(dse.getHttpStatus().value());
            context.setErrorMessage(dse.getMessage());
        }
    }

    @Then("product has id {string}")
    public void productHasId(String id) {
        assertThat(id).isEqualTo(context.getProducts().getFirst().getId());
    }

    @And("response status is {int}")
    public void responseStatusIs(int expectedStatusCode) {
        assertThat(context.getHttpStatusCode()).isEqualTo(expectedStatusCode);
    }


    @And("product title is {string}")
    public void productTitleIs(String expectedTitle) {
        assertThat(context.getProducts().getFirst().getTitle()).isEqualTo(expectedTitle);
    }

    @And("product price is {double}")
    public void productPriceIs(double expectedPrice) {
        assertThat(context.getProducts().getFirst().getPrice()).isEqualTo(expectedPrice);
    }

    @And("product type is {productType}")
    public void productTypeIs(Product.TypeEnum type) {
        assertThat(context.getProducts().getFirst().getType()).isEqualTo(type);
    }

    @And("product id is not blank")
    public void productIdIsNotBlank() {
        assertThat(context.getProducts().getFirst().getId()).isNotBlank();
    }

    @And("product id is UUID")
    public void productIdIsUUID() {
        assertThat(UUID.fromString(context.getProducts().getFirst().getId())).isNotNull();
    }
}
