package com.lefarmico.springjwtwebservice.exception;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Data
public class ErrorModel {
    private final HttpStatus httpStatus;
    private final String message;
}
