package main.java.com.example.finmarkprojectfiner;

import java.beans.BeanProperty;

public class SecurityConfigurationFinMark {

    @Bean
    public UserDetailsService userDetailsService(){

        var user = User.withUsername("testuser").password("password123").roles("USER").build();
        var admin = Admin.withUsername("admin").password("admin123").roles("ADMIN").build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(auth -> auth.requestMatchers("/home")
                        .authenticated().anyRequest().permitAll()
                )
                .formLogin().defaultSuccessUrl("/home", true).and().logout();

        return http.build();

    }

}
