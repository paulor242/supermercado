package com.micromarket.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro que intercepta CADA petición HTTP antes de que llegue al controlador.
 * Su trabajo es revisar si viene un token JWT válido en el header Authorization.
 * Si el token es bueno, autentica al usuario en el contexto de seguridad de Spring.
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public JwtFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Aquí ocurre la magia: se ejecuta una sola vez por petición.
     * Busca el header "Authorization: Bearer <token>", lo valida
     * y si está bien, deja pasar al usuario como autenticado.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        // Solo procesamos si el header existe y tiene el formato correcto "Bearer xxx"
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // quitamos el prefijo "Bearer " para quedarnos solo con el token

            // Validamos el token y nos aseguramos de no autenticar dos veces la misma petición
            if (jwtUtil.isTokenValid(token) && SecurityContextHolder.getContext().getAuthentication() == null) {
                String username = jwtUtil.extractUsername(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // Creamos el objeto de autenticación con los roles del usuario y lo metemos al contexto
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        // Siempre dejamos continuar la cadena de filtros, con o sin autenticación
        chain.doFilter(request, response);
    }
}
