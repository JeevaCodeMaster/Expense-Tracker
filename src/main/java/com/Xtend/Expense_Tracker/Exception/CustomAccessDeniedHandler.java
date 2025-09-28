package com.Xtend.Expense_Tracker.Exception;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
	@Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException)
            throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        String message;
        if (accessDeniedException instanceof AuthorizationDeniedException) {
            message = accessDeniedException.getMessage();
        } else {
            message = "Forbidden - You don't have permission";
        }

        response.getWriter().write("{\"error\": \"" + message + "\"}");
        
    }

}
