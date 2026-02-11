package com.BackBasiFit.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Ruta a utilizar para el Token /api/login

        http
            .csrf(csrf -> csrf.disable())
            .cors(Customizer.withDefaults())
            .authorizeHttpRequests(auth -> auth
                // GET sin login
                .requestMatchers(HttpMethod.GET, "/api/**").permitAll()

                // Request para toda la api
                // .requestMatchers("/api/**").authenticated()

                // Solo ADMIN puede consultar usuarios
                .requestMatchers("/api/usuarios/**").hasRole("admin")

                // Reservas, rutinas, membresias, tiene que estar autenticado como cliente o usuario para consultarlas
                .requestMatchers("/api/reservas/**", "/api/rutinas/**", "/api/membresias/**").authenticated()

                // Todo lo demas
                .anyRequest().authenticated()
            )

            .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
