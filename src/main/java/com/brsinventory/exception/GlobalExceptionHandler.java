package com.brsinventory.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    @ExceptionHandler(BRSResourceNotFoundException.class)
    public BRSError handleBRSResourceNotFoundException(BRSResourceNotFoundException ex) {
        BRSError errorDTO = new BRSError(BRSErrorType.NOT_FOUND_ERROR, ex.getMessage(), new Date(), null);
        return errorDTO;
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
//        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred");
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
//    }
}