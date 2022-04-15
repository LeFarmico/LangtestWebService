package com.lefarmico.springjwtwebservice.message;

import org.springframework.security.web.header.Header;

public class HttpRequestResource {

    public static class Header {

        public static final String auth = "Authorization";

        public static final String bearerPref = "Bearer ";
    }
}
