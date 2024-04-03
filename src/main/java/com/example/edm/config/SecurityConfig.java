package com.example.edm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

//    @Bean
//    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//            .authorizeHttpRequests(authorize -> authorize
//                .requestMatchers("/api/**").permitAll()
//                .anyRequest().authenticated()
//            )
//            .httpBasic(Customizer.withDefaults());
//        return http.build();
//    }
//    
	@Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/api/**")) // Ignore CSRF protection for /api/**
            .authorizeHttpRequests(authorize -> {
                authorize
                    .requestMatchers("/api/**").permitAll() // Permit all requests to /api/**
                    .anyRequest().authenticated(); // Require authentication for other requests
            })
            .httpBasic(Customizer.withDefaults());
        return http.build();
	}
    
	@Bean
	InMemoryUserDetailsManager userDetailsService() {
		UserDetails user = User.builder().username("admin").password(passwordEncoder().encode("admin@123"))
				.roles("ADMIN").build();
		return new InMemoryUserDetailsManager(user);
	}
}