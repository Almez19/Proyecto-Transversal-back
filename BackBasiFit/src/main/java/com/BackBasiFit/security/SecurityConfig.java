package com.BackBasiFit.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> {})
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth

                // Endpoints publicos
                .requestMatchers("/", "/error").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/login").permitAll()

                // GET publicos 
                .requestMatchers(HttpMethod.GET, "/api/gimnasios/**", "/api/noticias/**", "/api/clases/**", "/api/salas/**", "/api/maquinas/**").permitAll()

                // Cliente autenticado
                // Reservar una clase
                .requestMatchers(HttpMethod.POST, "/api/clases/*/reservas").hasRole("CLIENTE")

                // Solo personal
                .requestMatchers(HttpMethod.GET, "/api/clientes/numeroclientes").hasAnyRole("ADMIN", "EMPLEADO")

                // Acceso a perfil por id 
                .requestMatchers(HttpMethod.GET, "/api/clientes/*", "/api/clientes/*/reservas", "/api/clientes/*/membresias", "/api/clientes/*/rutinas").authenticated()

                // Personal del gimnasio admin, empleado, entrenador
                .requestMatchers("/api/usuarios/**").hasRole("ADMIN")

                // Gestin de clientes 
                .requestMatchers("/api/clientes/**").hasAnyRole("ADMIN", "EMPLEADO")
                .requestMatchers("/api/reservas/**").hasAnyRole("ADMIN", "EMPLEADO")
                .requestMatchers("/api/membresias/**").hasAnyRole("ADMIN", "EMPLEADO")
                .requestMatchers("/api/rutinas/**").hasAnyRole("ADMIN", "EMPLEADO", "ENTRENADOR")
                .requestMatchers("/api/ejercicios/**").hasAnyRole("ADMIN", "EMPLEADO", "ENTRENADOR")

                // Solo personal
                .requestMatchers(HttpMethod.POST, "/api/gimnasios/**", "/api/noticias/**", "/api/clases/**", "/api/salas/**", "/api/maquinas/**").hasAnyRole("ADMIN", "EMPLEADO")
                .requestMatchers(HttpMethod.PUT, "/api/gimnasios/**", "/api/noticias/**", "/api/clases/**", "/api/salas/**", "/api/maquinas/**").hasAnyRole("ADMIN", "EMPLEADO")
                .requestMatchers(HttpMethod.DELETE, "/api/gimnasios/**", "/api/noticias/**", "/api/clases/**", "/api/salas/**", "/api/maquinas/**").hasAnyRole("ADMIN", "EMPLEADO")
                .anyRequest().authenticated()
            )

            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {

        return config.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:4200"));
        config.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        
        return source;
    }
}
