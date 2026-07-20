package com.medaccess.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        // Allow frontend origins (add more as needed)
        config.setAllowedOrigins(List.of(
                "http://localhost:5173",    // Vite default port
                "http://localhost:5174",    // Vite fallback port
                "http://localhost:5171",    // VS Code Live Server default
                "http://127.0.0.1:5500",
                "http://localhost:3000",    // React/other dev server
                "http://localhost:4200",    // Angular
                "http://127.0.0.1:3000",
                "null"                      // file:// opened directly in browser
        ));

        // Allow all standard HTTP methods
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));

        // Allow all headers including Authorization for JWT
        config.setAllowedHeaders(List.of("*"));

        // Expose Authorization header so frontend can read it
        config.setExposedHeaders(List.of("Authorization"));

        // Allow cookies/credentials
        config.setAllowCredentials(true);

        // Cache preflight response for 1 hour
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
