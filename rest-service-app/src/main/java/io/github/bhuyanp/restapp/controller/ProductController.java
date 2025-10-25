package io.github.bhuyanp.restapp.controller;

import io.github.bhuyanp.restapp.dto.Product;
import io.github.bhuyanp.restapp.dto.ProductRequest;
import io.github.bhuyanp.restapp.service.ProductService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "Products")
@RestController
@RequiredArgsConstructor
@RequestMapping(ProductController.PRODUCT_API_PATH)
@ApiResponse(responseCode = "403", description = "Forbidden. Invalid or missing bearer token.", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
public class ProductController {
    public static final String PRODUCT_API_PATH = "/api/v1/products";

    private final ProductService productService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(responseCode = "500", description = "Unable to process the request.", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    public ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "404", description = "No product found matching the id.", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    @ApiResponse(responseCode = "500", description = "Unable to process the request.", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        return ResponseEntity.ok(productService.getProduct(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(responseCode = "400", description = "Invalid request.", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    @ApiResponse(responseCode = "500", description = "Unable to add the product.", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    public ResponseEntity<Product> addProduct(@Valid @RequestBody ProductRequest productRequest) {
        Product addedProduct = productService.addProduct(productRequest);
        log.info("Product added: {}",addedProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedProduct);
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponse(responseCode = "400", description = "Invalid request.", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    @ApiResponse(responseCode = "500", description = "Unable to update the product.", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    public ResponseEntity<Product> updateProduct(@Valid @RequestBody ProductRequest incomingProductRequest, @PathVariable String id) {
        productService.updateProduct(id, incomingProductRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponse(responseCode = "400", description = "Invalid request.", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    @ApiResponse(responseCode = "500", description = "Unable to partially update the product.", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    public ResponseEntity<Void> partiallyUpdateProduct(@RequestBody ProductRequest incomingProductRequest, @PathVariable String id) {
        productService.updateProduct(id, incomingProductRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
