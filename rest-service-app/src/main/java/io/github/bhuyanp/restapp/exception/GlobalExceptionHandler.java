package io.github.bhuyanp.restapp.exception;

import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ProblemDetail> handleException(Exception ex) {
        logger.error("Exception occurred.", ex);
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        if (ex instanceof DownstreamException downstreamException) {
            httpStatus = downstreamException.getHttpStatus();
        }

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(httpStatus, ex.getMessage());
        problemDetail.setProperty("exception", ex.getClass().getSimpleName());
        return ResponseEntity.status(httpStatus).body(problemDetail);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        ProblemDetail problemDetail = ex.getBody();
        problemDetail.setProperties(Map.of(
                "validationErrors", ex.getFieldErrors().stream().map(fe -> fe.getField() + ":" + fe.getDefaultMessage()).toList()
        ));
        return ResponseEntity.status(status).body(problemDetail);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        ProblemDetail problemDetail = createProblemDetail(ex, status, "Failed to read request", null, null, request);
        problemDetail.setProperties(Map.of(
                "additionalMessage", ex.getMessage()
        ));
        return ResponseEntity.status(status).body(problemDetail);
    }

}
