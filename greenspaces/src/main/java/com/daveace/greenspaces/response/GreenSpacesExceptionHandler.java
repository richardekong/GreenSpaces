package com.daveace.greenspaces.response;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GreenSpacesExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<GreenSpacesResponse> handleException(GreenSpacesException ce) {
        GreenSpacesResponse res = new GreenSpacesResponse();
        return createErrorResponse(res, ce.getStatus(), ce.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<GreenSpacesResponse> handleException(NumberFormatException ne) {
        GreenSpacesResponse res = new GreenSpacesResponse();
        return createErrorResponse(res, HttpStatus.BAD_REQUEST, ne.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<GreenSpacesResponse> handleException(MethodArgumentNotValidException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        GreenSpacesResponse res = new GreenSpacesResponse();
        StringBuilder errorMessage = new StringBuilder();
        List<ObjectError> errors = ex.getAllErrors();
        errors.forEach(error -> {
            int errorIndex = errors.indexOf(error);
            int lastErrorIndex = errors.size() - 1;
            String defaultErrorMessage = error.getDefaultMessage();
            errorMessage.append(errorIndex < lastErrorIndex ? defaultErrorMessage + ",\u0020and\u0020" : defaultErrorMessage);
        });
        return createErrorResponse(res, status, errorMessage.toString());
    }


    private ResponseEntity<GreenSpacesResponse> createErrorResponse(GreenSpacesResponse res, HttpStatus status, String message) {
        res.setStatus(status.value());
        res.setMessage(message);
        res.setTime(System.currentTimeMillis());
        return new ResponseEntity<>(res, status);
    }

}
