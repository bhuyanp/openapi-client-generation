package io.github.bhuyanp.restapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record AuthRequest(
        @Schema(description = "Username", example = "user")
        @NotBlank String username,
        @Schema(description = "Passwrod", example = "pass1")
        @NotBlank String password) {
}
