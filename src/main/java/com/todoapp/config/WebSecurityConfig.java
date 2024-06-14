package com.todoapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.
        csrf((csrf) -> csrf.ignoringRequestMatchers("/h2-console/**"))
        .headers((headers) -> headers.frameOptions().sameOrigin())
        .authorizeHttpRequests((requests) -> requests
                    .requestMatchers("/", "/js/**", "/css/**","/h2-console/**").permitAll()
                    .anyRequest().authenticated()
        ).formLogin((form) -> form
                    .loginPage("/login")
                    .defaultSuccessUrl("/todo", true)
                    .permitAll()
        ).logout((logout) -> logout.logoutSuccessUrl("/").permitAll());
        return http.build();
    }
}
