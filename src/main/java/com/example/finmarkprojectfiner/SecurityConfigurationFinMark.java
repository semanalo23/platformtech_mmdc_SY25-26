package com.example.finmarkprojectfiner;

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

        http.authorizeHttpRequests(auth -> auth.requestMatchers("/home")
                        .authenticated().anyRequest().permitAll()
                )
                .formLogin(form -> form
                .defaultSuccessUrl("/home", true)
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                );

        return http.build();

    }

}
