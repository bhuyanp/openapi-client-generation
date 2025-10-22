package io.github.bhuyanp.restapp.dto;

import java.time.LocalDateTime;

public record Product(String id,
                      String title,
                      TYPE type,
                      Double price,
                      LocalDateTime createdDate,
                      LocalDateTime updatedDate) {

    public enum TYPE {
        ELECTRONICS, BABY, BEAUTY
    }
}
