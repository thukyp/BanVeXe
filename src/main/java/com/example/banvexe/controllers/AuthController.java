package com.example.banvexe.controllers;

import com.example.banvexe.models.dto.RegisterDTO;
import com.example.banvexe.services.AuthService; // Đảm bảo bạn đã đổi tên UserService thành AuthService hoặc ngược lại
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map; // Quan trọng: Phải có cái này để dùng Map

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService; // Thống nhất dùng tên authService

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO dto) {
        try {
            // Gọi đúng hàm register mà mình đã viết trong AuthService
            return ResponseEntity.ok(authService.register(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> loginData) {

        return authService.login(
                loginData.get("username"),
                loginData.get("password"));
    }
}