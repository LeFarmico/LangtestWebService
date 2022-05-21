package com.lefarmico.springjwtwebservice.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil {

    private final String secret = "36e66494be5f6a912cb52dee61df2ab4b480b2503e7be23969f890be1c81375af3ff0818e1ce7e2bb403fa634860f1cebaebd7a2e9da5c5e46173d1475a7ee82";

    public String generateToken(String email)
            throws IllegalArgumentException, JWTCreationException {
        return JWT.create()
                .withSubject("User Details")
                .withClaim("email", email)
                .withIssuedAt(new Date())
                .withIssuer("api/langtest/lefarmico")
//                .withExpiresAt(new Date(System.currentTimeMillis()))
                .sign(Algorithm.HMAC256(secret));
    }

    public String validateAndTokenAndRetrieveSubject(String token)
            throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User Details")
                .withIssuer("api/langtest/lefarmico")
                .build();
        DecodedJWT jwt = verifier.verify(token);

        return jwt.getClaim("email").asString();
    }
}
