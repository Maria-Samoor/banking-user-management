package com.exalt.training.users.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration class for Spring Security.
 * This class sets up the security configuration, including password encoding and HTTP security settings.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Bean definition for PasswordEncoder.
     *
     * @return a BCryptPasswordEncoder instance used for encoding passwords.
     */
    @Bean
    public PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean definition for SecurityFilterChain.
     * Configures HTTP security to permit all requests and disables CSRF protection.
     *
     * @param http the HttpSecurity object to configure.
     * @return a SecurityFilterChain instance with the defined security configuration.
     * @throws Exception if an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll()
                )
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
}
