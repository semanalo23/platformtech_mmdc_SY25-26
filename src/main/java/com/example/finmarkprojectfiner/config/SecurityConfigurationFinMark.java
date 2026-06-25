package com.example.finmarkprojectfiner.config;

import com.example.finmarkprojectfiner.home.HomeControllerFinMark;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfigurationFinMark {

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            if (!HomeControllerFinMark.databaseCredentials.containsKey(username)) {
                throw new UsernameNotFoundException("Corporate user not found: " + username);
            }
            
            String password = HomeControllerFinMark.databaseCredentials.get(username);
            String assignedRole = "admin".equalsIgnoreCase(username) ? "ADMIN" : "USER";
            
            return User.withUsername(username)
                    .password("{noop}" + password)
                    .roles(assignedRole)
                    .build();
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/register", "/css/**", "/js/**", "/images/**").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers(
                    "/home", 
                    "/financial-services", 
                    "/marketing-services", 
                    "/bi-services", 
                    "/consulting-services",
                    "/feedback",
                    "/submit-feedback",
                    "/cart", 
                    "/cart/add", 
                    "/cart/checkout", // 🟢 Cart paths
                    "/order/track"
                ).authenticated()
                .anyRequest().permitAll()
            )
            .formLogin(form -> form
                .loginPage("/login")
                // 🟢 DYNAMIC GATEWAY ROUTING: Check roles right after user authenticates
                .successHandler((request, response, authentication) -> {
                    var roles = authentication.getAuthorities();
                    boolean isAdmin = roles.stream()
                            .anyMatch(authRole -> authRole.getAuthority().equals("ROLE_ADMIN"));
                    
                    if (isAdmin) {
                        // Admin accounts automatically jump straight into the feedback monitoring log table
                        response.sendRedirect("/admin/feedback-dashboard");
                    } else {
                        // Clients automatically land on their 4 Corporate Services grid panel
                        response.sendRedirect("/home");
                    }
                })
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