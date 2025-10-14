package io.github.bhuyanp.restapp.dto;

import io.github.bhuyanp.restapp.exception.DownstreamException;
import io.github.bhuyanp.restapp.exception.ServiceException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
@RequiredArgsConstructor
public class ErrorResponse {
    private final String errorMessage;
    private final String errorDetails;
    private final String exception;


    public ErrorResponse(Exception e){
        this.errorMessage = "Unable to process your request.";
        this.errorDetails = e.getMessage();
        this.exception = e.getClass().getSimpleName();
    }
    public ErrorResponse(ServiceException e){
        this.errorMessage = e.getMessage();
        this.errorDetails = e.getMessage();
        this.exception = e.getClass().getSimpleName();
    }

    public ErrorResponse(DownstreamException e){
        this.errorMessage = e.getMessage();
        this.errorDetails = e.getMessage();
        this.exception = e.getClass().getSimpleName();
    }
}
