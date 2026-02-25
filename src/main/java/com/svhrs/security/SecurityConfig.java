package com.svhrs.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());

        // Authorize requests
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(
                        "/", "/modules", "/register",
                        "/css/**", "/js/**", "/images/**",
                        "/login", "/error", "/api/locations/**",
                        "/admin/**"
                ).permitAll()
                .anyRequest().authenticated()
        );

        // Form login with role-based redirection
        http.formLogin(form -> form
                .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler((request, response, authentication) -> {
                    var roles = authentication.getAuthorities();
                    if (roles.stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"))) {
                        response.sendRedirect("/admin/dashboard");
                    } else if (roles.stream().anyMatch(r -> r.getAuthority().equals("ROLE_ASHA"))) {
                        response.sendRedirect("/asha/dashboard");
                    } else {
                        response.sendRedirect("/login?error");
                    }
                })
                .permitAll()
        );

        // Logout configuration
        http.logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
        );

        return http.build();
    }
}
