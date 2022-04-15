package com.lefarmico.springjwtwebservice.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.lefarmico.springjwtwebservice.message.HttpRequestResource.Header.auth;
import static com.lefarmico.springjwtwebservice.message.HttpRequestResource.Header.bearerPref;
import static com.lefarmico.springjwtwebservice.message.RequestMessage.blankJWT;
import static com.lefarmico.springjwtwebservice.message.RequestMessage.invalidJWTMsg;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
            String authHeader = request.getHeader(auth);

            if (authHeader != null && !authHeader.isBlank() && authHeader.startsWith(bearerPref)) {
                String jwt = authHeader.substring(7);
                if (jwt.isBlank()) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, blankJWT);
                } else {
                    try {
                        String email = jwtUtil.validateAndTokenAndRetrieveSubject(jwt);
                        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                email,
                                userDetails.getPassword(),
                                userDetails.getAuthorities()
                        );
                        if (SecurityContextHolder.getContext().getAuthentication() == null) {
                            SecurityContextHolder.getContext().setAuthentication(authToken);
                        }
                    } catch (JWTVerificationException e) {
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, invalidJWTMsg);
                    }
                }
            }
            filterChain.doFilter(request, response);
    }
}
