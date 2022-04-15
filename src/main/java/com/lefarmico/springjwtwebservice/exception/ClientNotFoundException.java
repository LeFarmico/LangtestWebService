package com.lefarmico.springjwtwebservice.exception;

import org.springframework.http.HttpStatus;

public class ClientNotFoundException extends RequestException{

    public ClientNotFoundException(String msg) {
        super(msg);
    }

    @Override
    public ErrorModel getRequestError() {
        return new ErrorModel(HttpStatus.NOT_FOUND, getLocalizedMessage());
    }
}
