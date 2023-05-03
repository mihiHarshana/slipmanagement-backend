package com.mhsoft.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {
/*    @Bean
    public WebMvcConfigurer corsConfigurer () {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/customer-details").allowedOrigins("http://localhost:8080");
                registry.addMapping("/api/call-center-agent-details").allowedOrigins("http://localhost:8080");
                registry.addMapping("/api/agent-details").allowedOrigins("http://localhost:8080");
                registry.addMapping("*").allowedHeaders("*");
                registry.addMapping("*").allowedMethods("*");
            }
        };
    }*/
}
