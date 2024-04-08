package com.studytogether.config;


import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain2(HttpSecurity security) throws Exception {
        security.authorizeHttpRequests(authorizeRequests ->
            authorizeRequests
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .permitAll()
                .requestMatchers("/", "/sign-up", "/check-email", "/check-email-token",
                    "/email-login", "/check-email-login", "/login-link")
                .permitAll()
                .requestMatchers(HttpMethod.GET, "/profile/*")
                .permitAll()
                .anyRequest()
                .authenticated()
        );
        return security.build();
    }

}
