package com.example.banvexe.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 1. Tắt CSRF để làm việc với API dễ hơn (tạm thời)
            .csrf(csrf -> csrf.disable())
            
            // 2. Cấu hình phân quyền
            .authorizeHttpRequests(auth -> auth
                // Cho phép các file tĩnh (CSS, JS, Images) và trang login/register
                .requestMatchers("/login/**", "/register/**", "/css/**", "/js/**", "/images/**", "/error").permitAll()
                // Mở khóa trang Admin để bạn làm việc
                .requestMatchers("/admin/**", "/api/admin/**").permitAll() 
                .requestMatchers("/api/**").permitAll()
                // Các trang còn lại bắt buộc đăng nhập
                .anyRequest().authenticated()
            )
            
            // 3. Cấu hình trang Login tự chế (Form Login)
            .formLogin(form -> form
                .loginPage("/login") 
                .defaultSuccessUrl("/admin/dashboard", true) // Login xong phi thẳng vào Dashboard
                .permitAll()
            )
            
            // 4. Cấu hình OAuth2 (Google) - Dùng chung trang login của mình
            .oauth2Login(oauth2 -> oauth2
                .loginPage("/login") // Ép Spring dùng trang login.html của Kỳ
                .defaultSuccessUrl("/admin/dashboard", true) // Login Google xong cũng vào Dashboard
            )
            
            // 5. Cấu hình Logout
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .deleteCookies("JSESSIONID")
                .permitAll()
            );
            
        return http.build();
    }

    /**
     * Bộ điều hướng thông minh sau khi đăng nhập thành công.
     * Nếu là ADMIN -> Vào Dashboard quản trị.
     * Nếu là USER (CUSTOMER) -> Về trang chủ mua vé.
     */
    @Bean
    public AuthenticationSuccessHandler customSuccessHandler() {
        return (request, response, authentication) -> {
            var authorities = authentication.getAuthorities();

            // Kiểm tra quyền của người dùng
            for (var authority : authorities) {
                String role = authority.getAuthority();

                // Lưu ý: Spring Security thường thêm tiền tố "ROLE_" tự động
                if (role.equals("ROLE_ADMIN") || role.equals("ADMIN")) {
                    response.sendRedirect("/admin/dashboard");
                    return;
                }
            }

            // Nếu không phải Admin thì mặc định về trang chủ cho Customer
            response.sendRedirect("/");
        };
    }
}