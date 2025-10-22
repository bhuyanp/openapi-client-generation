package io.github.bhuyanp.clientapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleException(Exception ex) {
        logger.error("Exception occurred.",ex);
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        
        
        if(ex instanceof DownstreamException downstreamException){
            httpStatus = downstreamException.getHttpStatus();
        }
        
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(httpStatus, ex.getMessage());
        problemDetail.setProperty("exception",ex.getClass().getSimpleName());
        return ResponseEntity.status(httpStatus).body(problemDetail);
    }
}
