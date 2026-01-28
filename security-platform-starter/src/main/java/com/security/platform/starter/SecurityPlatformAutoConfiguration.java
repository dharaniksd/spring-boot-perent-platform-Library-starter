package com.security.platform.starter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * Auto-configuration for Security Platform.
 * Provides secure defaults and best practices for Spring Boot applications.
 */
@AutoConfiguration
@EnableWebSecurity
@EnableConfigurationProperties(SecurityPlatformProperties.class)
@ConditionalOnProperty(prefix = "security.platform", name = "enabled", havingValue = "true", matchIfMissing = true)
public class SecurityPlatformAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(SecurityPlatformAutoConfiguration.class);

    private final SecurityPlatformProperties properties;

    public SecurityPlatformAutoConfiguration(SecurityPlatformProperties properties) {
        this.properties = properties;
        logger.info("Security Platform Starter initialized with secure defaults");
    }

    /**
     * Configure security filter chain with best practices
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        logger.info("Configuring security filter chain with secure defaults");

        // Apply security headers if enabled
        if (properties.isSecurityHeadersEnabled()) {
            http.headers(headers -> headers
                .contentSecurityPolicy(csp -> csp.policyDirectives("default-src 'self'"))
                .referrerPolicy(referrer -> referrer.policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN))
                .frameOptions(frameOptions -> frameOptions.deny())
                .xssProtection(xss -> xss.headerValue(XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK))
                .httpStrictTransportSecurity(hsts -> hsts
                    .includeSubDomains(true)
                    .maxAgeInSeconds(31536000))
            );
            logger.info("Security headers enabled");
        }

        // Configure CSRF
        if (properties.isCsrfEnabled()) {
            http.csrf(csrf -> csrf.ignoringRequestMatchers("/actuator/**"));
            logger.info("CSRF protection enabled");
        } else {
            http.csrf(csrf -> csrf.disable());
            logger.info("CSRF protection disabled");
        }

        // Configure CORS if enabled
        if (properties.isCorsEnabled()) {
            http.cors(cors -> cors.configurationSource(corsConfigurationSource()));
            logger.info("CORS enabled");
        }

        // Configure actuator endpoints security
        if (properties.isActuatorSecurityEnabled()) {
            http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/actuator/health", "/actuator/info").permitAll()
                .requestMatchers("/actuator/**").authenticated()
                .anyRequest().permitAll()
            );
            logger.info("Actuator security enabled");
        } else {
            http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        }

        return http.build();
    }

    /**
     * CORS configuration with secure defaults
     */
    @Bean
    @ConditionalOnProperty(prefix = "security.platform", name = "cors-enabled", havingValue = "true")
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("http://localhost:*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        
        return source;
    }
}
