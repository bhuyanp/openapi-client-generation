package io.github.bhuyanp.restapp.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthRequest(
        @NotBlank String userName,
        @NotBlank String password) {
}
