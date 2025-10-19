package io.github.bhuyanp.restapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record TokenResponse(
        @Schema(description = "Valid JWT token.")
        String token) {

}
