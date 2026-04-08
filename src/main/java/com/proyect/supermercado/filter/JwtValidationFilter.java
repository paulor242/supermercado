package com.proyect.supermercado.filter;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.proyect.supermercado.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;


// se realiza la configuracion de todo el jwt se configura que datos son los que va llevar
//  el jwt se coloca tambien el refresh tocken   y los necesarios revisar el autentication = JwtValidationFilter



@Component
@RequiredArgsConstructor
public class JwtValidationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    private static final List<String> PROTECTED_SALES_PATHS = List.of(
            "/sales",
            "/detailSale"
    );

    private static final List<String> ALLOWED_ROLES = List.of("ADMIN", "CAJERO");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();


        boolean isProtectedSalesPath = PROTECTED_SALES_PATHS.stream()
                .anyMatch(path::contains);

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Header Authorization is missing in the request\"}");
            return;
        }


        String token = authHeader.substring(7);

        try {
            if (!jwtService.isTokenValid(token)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"Token is invalid or expired\"}");
                return;
            }

            String username = jwtService.extractUsername(token);
            Long userId = jwtService.extractUserId(token);
            String role = jwtService.extractRol(token);

            if (isProtectedSalesPath) {
                if (role == null || !ALLOWED_ROLES.contains(role.toUpperCase())) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403 Forbidden
                    response.setContentType("application/json");
                    response.getWriter().write("{\"error\": \"Access denied. Required role: ADMIN or CAJERO\"}");
                    return;
                }
            }


            request.setAttribute("username", username);
            request.setAttribute("userId", userId);
            request.setAttribute("role", role);

            filterChain.doFilter(request, response);

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Token validation failed: " + e.getMessage() + "\"}");
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();

        return path.equals("/api/v1/users/auth/register") ||
                path.endsWith("/login") ||
                path.endsWith("/refresh");
    }
}