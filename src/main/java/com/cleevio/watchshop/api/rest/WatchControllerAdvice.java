package com.cleevio.watchshop.api.rest;

import com.cleevio.watchshop.api.dto.ValidationError;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

import static com.cleevio.watchshop.api.ApiValues.VALIDATION_FAILED_ERROR_MESSAGE;

@ControllerAdvice
public class WatchControllerAdvice {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {

        List<String> collect = ex.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        ValidationError response = new ValidationError(VALIDATION_FAILED_ERROR_MESSAGE, collect);

        return ResponseEntity.badRequest().body(response);
    }
}
