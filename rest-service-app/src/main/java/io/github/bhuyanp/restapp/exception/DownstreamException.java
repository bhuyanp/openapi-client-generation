package io.github.bhuyanp.restapp.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class DownstreamException extends RuntimeException {

    @Getter
    private final HttpStatus httpStatus;
    public DownstreamException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
