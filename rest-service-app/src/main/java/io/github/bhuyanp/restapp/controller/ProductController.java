package io.github.bhuyanp.restapp.controller;

import io.github.bhuyanp.restapp.dto.AddProductRequest;
import io.github.bhuyanp.restapp.exception.DownstreamException;
import io.github.bhuyanp.restapp.exception.ServiceException;
import io.github.bhuyanp.restapp.model.Product;
import io.github.bhuyanp.restapp.service.ProductService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@Tag(name = "Products")
@RestController
@RequiredArgsConstructor
@RequestMapping(ProductController.PRODUCT_API_PATH)
@ApiResponse(responseCode = "403", description = "Forbidden. Invalid or missing bearer token.", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
public class ProductController {
    public static final String PRODUCT_API_PATH = "/v1/products";

    private final ProductService productService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(responseCode = "404", description = "No products found.", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    @ApiResponse(responseCode = "500", description = "Unable to process the request.", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    public ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "404", description = "No product found matching the id.", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    @ApiResponse(responseCode = "500", description = "Unable to process the request.", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        Product product = productService.getProduct(id).orElseThrow(() -> new DownstreamException(HttpStatus.NOT_FOUND, "No product found with id " + id));
        return ResponseEntity.ok(product);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(responseCode = "400", description = "Invalid request.", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    @ApiResponse(responseCode = "500", description = "Unable to save the product.", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    public ResponseEntity<Product> addProduct(@Valid @RequestBody AddProductRequest addProductRequest) {
        if (new Random().nextInt(3) == 0) {
            throw new ServiceException("Failed to create product record.");
        }
        Product addedProduct = productService.addProduct(addProductRequest);
        return ResponseEntity.ok(addedProduct);
    }

}
