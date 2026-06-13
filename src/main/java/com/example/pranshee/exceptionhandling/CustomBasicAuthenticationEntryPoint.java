package com.example.pranshee.exceptionhandling;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * CustomBasicAuthenticationEntryPoint is a custom implementation of the security execption that are going 
 * during the authentication process and it will send a custom error message in the response header when
 *  the user is not authorized to access the resource and it will also send a 401 unauthorized 
 * status code in the response.
 */

/**
 * CustomBasicAuthenticationEntryPoint is a custom implementation of the
 * AuthenticationEntryPoint interface.
 * It handles unauthorized access attempts by sending an appropriate HTTP
 * response.
 */
public class CustomBasicAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        response.setHeader("security-error-reason", "Authorization- failed");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("applicatiion/json;charset=UTF-8");

        // populate dynamic error details in the response header
        LocalDateTime timeStamp = LocalDateTime.now();
        String message = (authException != null && authException.getMessage() != null) ? authException.getMessage()
                : "Unauthorized access";
        String path = request.getRequestURI();

        // Constructing a JSON response with the error details
        String jsonResponse = String.format(
                "{\"timestamp\":\"%s\",\"status\":%s,\"error\":\"%s\",\"message\":\"%s\",\"path\":\"%s\"}",
                timeStamp, HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase(), message, path);

        response.getWriter().write(jsonResponse);

    }

}
