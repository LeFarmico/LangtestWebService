package com.lefarmico.springjwtwebservice.exception;

import org.springframework.http.HttpStatus;

public class DataNotFoundException extends RequestException{

    public DataNotFoundException(String msg) {
        super(msg);
    }

    @Override
    public ErrorModel getRequestError() {
        return new ErrorModel(HttpStatus.NOT_FOUND, getLocalizedMessage());
    }
}
