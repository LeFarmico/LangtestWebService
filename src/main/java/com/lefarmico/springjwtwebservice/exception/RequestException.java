package com.lefarmico.springjwtwebservice.exception;

abstract public class RequestException extends Exception {

    public RequestException(String msg) {
        super(msg);
    }

    public abstract ErrorModel getRequestError();
}
