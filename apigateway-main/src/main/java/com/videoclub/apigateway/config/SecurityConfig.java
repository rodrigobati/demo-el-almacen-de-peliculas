package com.videoclub.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        // Rutas públicas (sin autenticación)
                        .pathMatchers("/auth/**", "/realms/**").permitAll()
                        .pathMatchers("/actuator/health").permitAll()

                        // Catálogo: solo lectura pública (GET), escritura protegida (POST, PUT, DELETE)
                        .pathMatchers(org.springframework.http.HttpMethod.GET, "/api/peliculas/**").permitAll()
                        .pathMatchers("/api/peliculas/**").authenticated()

                        // Categorías: GET público
                        .pathMatchers(org.springframework.http.HttpMethod.GET, "/api/categorias/**").permitAll()

                        // Ratings: GET público, POST/PUT/DELETE requieren autenticación
                        .pathMatchers(org.springframework.http.HttpMethod.GET, "/api/ratings/**").permitAll()
                        .pathMatchers("/api/ratings/**").authenticated()

                        // Cualquier otra ruta por defecto es pública
                        .anyExchange().permitAll())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtDecoder(jwtDecoder())));

        return http.build();
    }

    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        // URL del JWK Set (debe ser accesible desde el API Gateway)
        String jwkSetUri = "http://keycloak:8080/realms/videoclub/protocol/openid-connect/certs";

        NimbusReactiveJwtDecoder jwtDecoder = NimbusReactiveJwtDecoder.withJwkSetUri(jwkSetUri).build();

        // Configurar validador personalizado que acepte ambos issuers
        OAuth2TokenValidator<Jwt> issuerValidator = new OAuth2TokenValidator<Jwt>() {
            private final List<String> validIssuers = Arrays.asList(
                    "http://keycloak:8080/realms/videoclub",
                    "http://localhost:9090/realms/videoclub");

            @Override
            public org.springframework.security.oauth2.core.OAuth2TokenValidatorResult validate(Jwt token) {
                String issuer = token.getIssuer() != null ? token.getIssuer().toString() : null;
                if (issuer != null && validIssuers.contains(issuer)) {
                    return org.springframework.security.oauth2.core.OAuth2TokenValidatorResult.success();
                }
                return org.springframework.security.oauth2.core.OAuth2TokenValidatorResult.failure(
                        new org.springframework.security.oauth2.core.OAuth2Error("invalid_token", "Invalid issuer",
                                null));
            }
        };

        OAuth2TokenValidator<Jwt> withIssuer = new DelegatingOAuth2TokenValidator<Jwt>(
                new JwtTimestampValidator(),
                issuerValidator);

        jwtDecoder.setJwtValidator(withIssuer);
        return jwtDecoder;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setExposedHeaders(List.of("Authorization"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
