package com.ProjectHub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
              .csrf(csrf -> csrf.disable()) // Desativa CSRF para facilitar testes
            .authorizeHttpRequests(auth -> auth
                // Aqui você lista as rotas que quer BLOQUEAR
                .requestMatchers("/admin/**").authenticated()
                .requestMatchers("/private/**").authenticated()
                
                // Todas as outras ficam liberadas
                .anyRequest().permitAll()
            )
            .httpBasic(httpBasic -> httpBasic.disable()) // Remove popup HTTP Basic
            .formLogin(form -> form.disable()); // Remove form padrão
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }        
}
