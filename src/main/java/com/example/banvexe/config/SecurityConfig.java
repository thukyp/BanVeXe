package com.example.banvexe.config;

import com.example.banvexe.services.UserService;
import com.example.banvexe.utils.JwtUtils;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/login/**", "/error", "/api/**").permitAll()
                .requestMatchers("/admin/**").permitAll() // Tạm thời permitAll để bạn làm dashboard
                .anyRequest().authenticated()
            )
            .oauth2Login(oauth2 -> oauth2
                // Khi login thành công, Spring sẽ tự gọi về đây
                .defaultSuccessUrl("/", true) 
            );
            
        return http.build();
    }
}