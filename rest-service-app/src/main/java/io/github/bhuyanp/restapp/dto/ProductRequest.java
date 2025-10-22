package io.github.bhuyanp.restapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductRequest(
        @Schema(description = "Title of the product.", example = "Product Title A")
        @NotBlank
        String title,

        @Schema(description = "Product type.", example = "ELECTRONICS")
        @NotNull
        Product.TYPE type,

        @Schema(description = "A non negative product price.", example = "30.20")
        @Positive
        @NotNull
        Double price) {
}
