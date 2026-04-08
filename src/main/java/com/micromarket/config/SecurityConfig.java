package com.micromarket.config;

import com.micromarket.repository.UserRepository;
import com.micromarket.security.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

/**
 * Configuración central de seguridad de toda la aplicación.
 * Aquí se define qué rutas son públicas, cuáles requieren token,
 * cómo se cargan los usuarios y cómo se encriptan las contraseñas.
 */
@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final UserRepository userRepository;

    public SecurityConfig(JwtFilter jwtFilter, UserRepository userRepository) {
        this.jwtFilter = jwtFilter;
        this.userRepository = userRepository;
    }

    /**
     * Define las reglas de acceso HTTP:
     * - /api/auth/** es público (login no necesita token, obvio)
     * - /h2-console/** es público para poder ver la BD en desarrollo
     * - todo lo demás requiere estar autenticado
     * - sin sesiones: cada petición debe traer su propio token
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())                                                          // desactivamos CSRF porque usamos JWT, no cookies
            .headers(headers -> headers.frameOptions(frame -> frame.disable()))                   // necesario para que el iframe de H2 console funcione
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))     // sin estado: no guardamos sesión en el servidor
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()                                       // login es libre para todos
                .requestMatchers("/h2-console/**").permitAll()                                    // consola H2 accesible en desarrollo
                .anyRequest().authenticated()                                                     // el resto necesita token válido
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);              // nuestro filtro JWT corre antes del filtro estándar de Spring
        return http.build();
    }

    /**
     * Le dice a Spring cómo buscar un usuario en la base de datos cuando llega un token.
     * También construye el objeto de seguridad con el rol del usuario (ej: ROLE_ADMIN).
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            // Buscamos el usuario en BD; si no existe, Spring lanza 401 automáticamente
            var user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
            return org.springframework.security.core.userdetails.User
                    .withUsername(user.getUsername())
                    .password(user.getPassword())
                    .authorities(List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole())))  // el rol viene de BD y se prefija con ROLE_
                    .build();
        };
    }

    /**
     * Encoder de contraseñas con BCrypt.
     * BCrypt es lento a propósito — eso hace que los ataques de fuerza bruta sean muy costosos.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Expone el AuthenticationManager como bean para que AuthService pueda usarlo
     * y delegar la validación de credenciales a Spring Security.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
