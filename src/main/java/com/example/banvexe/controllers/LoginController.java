package com.example.banvexe.controllers;

import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

@Controller
public class LoginController {


@GetMapping("/")
public String index(Authentication authentication, Model model) {
    if (authentication != null && authentication.isAuthenticated()) {
        Object principal = authentication.getPrincipal();
        
        if (principal instanceof DefaultOidcUser) {
            DefaultOidcUser user = (DefaultOidcUser) principal;
            model.addAttribute("userName", user.getFullName());
            model.addAttribute("email", user.getEmail());
        } else if (principal instanceof DefaultOAuth2User) {
            DefaultOAuth2User user = (DefaultOAuth2User) principal;
            model.addAttribute("userName", user.getAttribute("name"));
            model.addAttribute("email", user.getAttribute("email"));
        }
    }
    return "index";
}

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/myticket")
    public String myticket() {
        return "myticket";
    }

    @GetMapping("/booking")
    public String booking() {
        // Spring sẽ tự tìm file src/main/resources/templates/booking.html
        return "booking";
    }

    @GetMapping("/payment")
    public String payment() {
        return "payment";
    }

}