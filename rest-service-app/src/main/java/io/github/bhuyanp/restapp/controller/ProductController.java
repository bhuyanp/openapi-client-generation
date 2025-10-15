package io.github.bhuyanp.restapp.controller;

import io.github.bhuyanp.restapp.dto.ErrorResponse;
import io.github.bhuyanp.restapp.exception.DownstreamException;
import io.github.bhuyanp.restapp.exception.ServiceException;
import io.github.bhuyanp.restapp.model.Product;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/products/v1")
@Tag(name = "Products")
public class ProductController {

    public static final String X_AUTH_TOKEN = "Authorization";

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(responseCode = "404", description = "No products found.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Unable to process the request.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok(List.of(
                        new Product("id1", "Product 1", 11.23, LocalDateTime.now()),
                        new Product("id2", "Product 2", 12.23, LocalDateTime.now()),
                        new Product("id3", "Product 3", 13.23, LocalDateTime.now())
                )
        );
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "404", description = "No product found matching the id.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Unable to process the request.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        if (id.equals("id1")) {
            return ResponseEntity.ok(new Product("id1", "Product 1", 12.23, LocalDateTime.now()));
        } else {
            throw new DownstreamException(HttpStatus.NOT_FOUND, "No product found with id " + id);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(responseCode = "400", description = "Invalid request.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Unable to save the product.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<Product> addProduct(@Valid @RequestBody Product product) {
        if ((int) (Math.random() * 2) == 0) {
            throw new ServiceException("Failed to create service record.");
        }
        return ResponseEntity.ok(new Product("id1", product.title(), product.price(), LocalDateTime.now()));
    }

}
