package com.medaccess.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class WebSecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity){
        httpSecurity.
                cors(Customizer.withDefaults())   // enable CORS — reads config from CorsConfig.java
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sessioncofig->sessioncofig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth->auth
//                        .requestMatchers(HttpMethod.GET,
//                                "/api/v1/medicine/**",
//                                "/api/v1/pharmacy/**",
//                                "/api/v1/stock/get/medicine/**").permitAll()
//                        .requestMatchers(HttpMethod.POST,
//                                "/api/v1/auth/**").permitAll()
//                        .requestMatchers("/api/v1/cart/**").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/v1/stock/**").permitAll()
//                        .requestMatchers("/api/v1/order/**").permitAll()
//                        .requestMatchers(HttpMethod.POST,"/api/v1/medicine/add").permitAll()
//                        .anyRequest().authenticated()
                                .anyRequest().permitAll()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

}
