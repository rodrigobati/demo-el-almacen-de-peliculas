package unrn.rating.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * Configuración de Spring Security para validar JWT de Keycloak.
 * El microservicio actúa como OAuth2 Resource Server.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

                http
                                .cors(withDefaults())
                                .csrf(AbstractHttpConfigurer::disable);

                http.sessionManagement(sessionManagement -> sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

                http
                                .authorizeHttpRequests(registry -> registry
                                                .requestMatchers("/actuator/**", "/metrics/**").permitAll()
                                                // Permitir todas las peticiones ya que el API Gateway ya validó el JWT
                                                .anyRequest().permitAll());

                return http.build();
        }

        @Bean
        GrantedAuthorityDefaults grantedAuthorityDefaults() {
                return new GrantedAuthorityDefaults(""); // Remove the ROLE_ prefix
        }

        @Bean
        public JwtAuthenticationConverter jwtAuthenticationConverter() {
                JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
                converter.setJwtGrantedAuthoritiesConverter(new KeycloakGrantedAuthoritiesConverter());
                return converter;
        }
}
