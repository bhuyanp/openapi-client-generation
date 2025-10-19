package io.github.bhuyanp.restapp.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record Product(@NotBlank String id, @NotBlank String title, @NotNull @Positive TYPE type ,@NotNull @Positive double price, @NotNull LocalDateTime createdDate) {

    public enum TYPE{
        ELECTRONICS, BABY, BEAUTY
    }
}
