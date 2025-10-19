package io.github.bhuyanp.clientapp.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class DownstreamException extends RuntimeException {

    @Getter
    private final HttpStatus httpStatus;
    @Getter
    private final String details;
    public DownstreamException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.details = null;
    }

    public DownstreamException(HttpStatus httpStatus, String message, String details) {
        super(message);
        this.httpStatus = httpStatus;
        this.details = details;
    }
}
