package io.github.bhuyanp.restapp.dto;

import io.github.bhuyanp.restapp.model.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AddProductRequest(
        @Schema(description = "Title of the product.", example = "Product Title A")
        @NotBlank
        String title,

        @Schema(description = "Product type.", example = "ELECTRONICS, BABY, BEAUTY")
        @NotNull
        Product.TYPE type,

        @Schema(description = "A non negative product price.", example = "30.20")
        @Positive @NotNull
        double price) {
}
