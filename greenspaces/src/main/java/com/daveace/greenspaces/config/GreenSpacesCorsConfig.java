package com.daveace.greenspaces.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class GreenSpacesCorsConfig {

    @Bean
    public CorsConfigurationSource greenSpacesCorsConfigurationSource(){
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(
            //this list will be updated in future
            List.of(
                    "http://localhost",
                    "http://127.0.0.1",
                    "http://10.0.2.2",
                    "https://localhost",
                    "https://127.0.0.1",
                    "https://10.0.2.2"
            )
        );
        config.setAllowedMethods(List.of("GET", "POST","PUT","DELETE"));
        config.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
