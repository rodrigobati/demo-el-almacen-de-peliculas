package com.videoclub.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        // Rutas públicas (sin autenticación)
                        .pathMatchers("/auth/**", "/realms/**").permitAll()
                        .pathMatchers("/actuator/health").permitAll()

                        // Catálogo: solo lectura pública (GET), escritura protegida (POST, PUT, DELETE)
                        .pathMatchers(org.springframework.http.HttpMethod.GET, "/api/peliculas/**").permitAll()
                        .pathMatchers("/api/peliculas/**").authenticated()

                        // Ratings: requieren autenticación (el usuario debe estar logueado para
                        // calificar)
                        .pathMatchers("/api/ratings/**").authenticated()

                        // Cualquier otra ruta por defecto es pública
                        .anyExchange().permitAll())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> {
                        }));

        return http.build();
    }
}
