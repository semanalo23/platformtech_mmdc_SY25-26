package com.example.finmarkprojectfiner;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class SecurityConfigurationFinMark {

    // 🟢 DYNAMIC STORAGE: Shared memory accessible by both login and registration engines
    public static final Map<String, String> databaseCredentials = new HashMap<>();

    static {
        // Seed initial presentation profiles so your default accounts still work instantly
        databaseCredentials.put("testuser", "password123");
        databaseCredentials.put("admin", "admin123");
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            if (!databaseCredentials.containsKey(username)) {
                throw new UsernameNotFoundException("Corporate user not found: " + username);
            }
            
            String password = databaseCredentials.get(username);
            
            // Build dynamic authentication details
            return User.withUsername(username)
                    .password("{noop}" + password) // Uses plain text for development verification
                    .roles("USER")
                    .build();
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/register", "/css/**", "/js/**", "/images/**").permitAll()
                .requestMatchers("/home", "/financial-services", "/marketing-services", "/bi-services", "/consulting-services").authenticated()
                .anyRequest().permitAll()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/home", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
            );

        return http.build();
    }
}