package com.todoapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.todoapp.security.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.
        csrf((csrf) -> csrf.ignoringRequestMatchers("/h2-console/**"))
        .headers((headers) -> headers
                                .frameOptions(frameOptions -> frameOptions.sameOrigin()))
        .authorizeHttpRequests((requests) -> requests
                    .requestMatchers("/", "/js/**", "/css/**","/h2-console/**", "/error").permitAll()
                    .requestMatchers("/todo/{id}/share/{passcode}").permitAll()
                    .anyRequest().authenticated()
        )
        .formLogin((form) -> form
                    .loginPage("/login")
                    .defaultSuccessUrl("/todo", true)
                    .permitAll()
        ).rememberMe(rememberMe -> rememberMe.key("rememberMeSecretKey"))
        .logout((logout) -> logout.logoutSuccessUrl("/").permitAll());
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
