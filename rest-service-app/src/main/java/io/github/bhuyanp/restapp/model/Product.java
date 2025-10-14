package io.github.bhuyanp.restapp.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record Product(String id, @NotBlank String title, @Positive double price, LocalDateTime createdDate) {
}
