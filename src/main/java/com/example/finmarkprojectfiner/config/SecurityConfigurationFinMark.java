package com.example.finmarkprojectfiner.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfigurationFinMark {

    @Bean
    public UserDetailsService userDetailsService(){

        var user = User.withUsername("testuser").password("{noop}password123").roles("USER").build();
        var admin = User.withUsername("admin").password("{noop}admin123").roles("ADMIN").build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth
                        // Require login for home and products
                        .requestMatchers("/home", "/products/**").authenticated()
                        // Allow login, register, reset pages without authentication
                        .requestMatchers("/login", "/register", "/reset").permitAll()
                        // Allow static resources
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                        // Everything else requires authentication
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")              // custom login page
                        .defaultSuccessUrl("/home", true) // always go to dashboard after login
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/logout-success") // points to the logout page
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                );

        return http.build();

    }

}
