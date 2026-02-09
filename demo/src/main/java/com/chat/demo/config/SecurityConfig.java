package com.chat.demo.config;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.chat.demo.service.UserDetailsAdapterService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Value("${cors.allowed-origins}")
    private String corsAllowedOrigins;

    @Value("${cors.allowed-methods}")
    private String corsAllowedMethods;

    @Value("${cors.allowed-headers}")
    private String corsAllowedHeaders;

    private final UserDetailsAdapterService userDetailsAdapterService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider AuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsAdapterService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config){
        try {
           return config.getAuthenticationManager();
        } catch (Exception e) {
            throw new RuntimeException("exceçlão não tratada");
        }
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        String allowedOriginsValue = corsAllowedOrigins == null ? "" : corsAllowedOrigins.trim();
        boolean allowAnyOrigin = allowedOriginsValue.isEmpty() || "*".equals(allowedOriginsValue);

        List<String> origins;
        if (allowAnyOrigin) {
            origins = List.of("*");
        } else {
            origins = Arrays.stream(allowedOriginsValue.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toList());
        }

        CorsConfiguration configuration = new CorsConfiguration();
        if (allowAnyOrigin) {
            configuration.setAllowedOriginPatterns(origins); 
            configuration.setAllowedOrigins(origins); 
        }
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")); // inclui PATCH
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true); // necessário se o frontend envia cookies ou tokens via header

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // aplica globalmente

        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
                .requestMatchers(
                    "/auth/**",
                    "/health/**",
                    "/error",
                    "/swagger-ui.html",
                    "/swagger-ui/**",
                    "/v3/api-docs",
                    "/v3/api-docs/**"
                ).permitAll()
                .anyRequest()
                .permitAll()
            )
            .build();
    }
    
}
