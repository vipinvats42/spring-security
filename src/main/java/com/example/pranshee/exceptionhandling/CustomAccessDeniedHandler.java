package com.example.pranshee.exceptionhandling;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
 This class is for custom access denied execption for 403 forbidden error 
 and it will send a custom error message in the response header when the user is
  not authorized to access the resource and it will also send a 403 forbidden status 
  code in the response.
 */

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setHeader("security-error-reason", "Authorization- failed");
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("applicatiion/json;charset=UTF-8");

        // populate dynamic error details in the response header
        LocalDateTime timeStamp = LocalDateTime.now();
        String message = (accessDeniedException != null && accessDeniedException.getMessage() != null)
                ? accessDeniedException.getMessage()
                : "Authorization failed";
        String path = request.getRequestURI();

        // Constructing a JSON response with the error details
        String jsonResponse = String.format(
                "{\"timestamp\":\"%s\",\"status\":%s,\"error\":\"%s\",\"message\":\"%s\",\"path\":\"%s\"}",
                timeStamp, HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.getReasonPhrase(), message, path);

        response.getWriter().write(jsonResponse);
    }

}
