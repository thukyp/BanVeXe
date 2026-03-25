package com.example.banvexe.controllers;

import com.example.banvexe.models.dto.RegisterDTO;
import com.example.banvexe.repositories.UserRepository;
import com.example.banvexe.services.AuthService;

import jakarta.validation.Valid;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    // --- ĐĂNG NHẬP & ĐĂNG KÝ ---

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    @PostMapping("/api/auth/register")
    @ResponseBody // Thêm cái này để trả về nội dung text/json thay vì tìm file HTML
    public ResponseEntity<?> handleRegisterApi(@RequestBody RegisterDTO dto) {
        try {
            authService.register(dto);
            return ResponseEntity.ok("Đăng ký thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi: " + e.getMessage());
        }
    }

    @GetMapping("/api/auth/me")
    @ResponseBody
    public ResponseEntity<?> getCurrentUser(java.security.Principal principal) {
        if (principal == null)
            return ResponseEntity.status(401).build();
        return userRepository.findByUsername(principal.getName())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).build());
    }

    @PostMapping("/register")
    public String handleRegister(@Valid @ModelAttribute RegisterDTO dto, Model model) {
        try {
            authService.register(dto);
            return "redirect:/login?success=true";
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi: " + e.getMessage());
            return "register";
        }
    }

    // --- QUÊN MẬT KHẨU (PHẦN MỚI THÊM) ---

    // 1. Hiển thị trang nhập Email để nhận link reset
    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgot-password"; // Trả về file forgot-password.html
    }

    // 2. Hiển thị trang đặt lại mật khẩu mới (Reset Password)
    // Thường link từ Email sẽ có dạng: /reset-password?token=xyz
    @GetMapping("/reset-password")
    public String showResetPasswordForm(@Valid @RequestParam(value = "token", required = false) String token,
            Model model) {
        model.addAttribute("token", token);
        return "reset-password"; // Trả về file reset-password.html
    }
}