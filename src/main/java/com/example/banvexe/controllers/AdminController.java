package com.example.banvexe.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // Thêm Model nếu bạn dùng layout chung
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.banvexe.repositories.BusRepository;
import com.example.banvexe.repositories.RouteRepository;
import com.example.banvexe.repositories.TripRepository; // Cần thêm repo này
import com.example.banvexe.repositories.UserRepository; // Cần thêm repo này

import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired private RouteRepository routeRepository;
    @Autowired private BusRepository busRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private TripRepository tripRepository;

    @GetMapping("/api/admin/stats")
    @ResponseBody
    public Map<String, Long> getStats() {
        return Map.of(
            "totalRoutes", routeRepository.count(),
            "totalBuses", busRepository.count(),
            "totalUsers", userRepository.count(),
            "totalTrips", tripRepository.count()
        );
    }

    // Nếu bạn dùng Layout chung (Thymeleaf Fragments), hãy dùng Model để truyền tên fragment
    // Ở đây tôi trả về view trực tiếp theo cấu trúc file của bạn
    
    @GetMapping("/dashboard")
    public String dashboard() { return "admin/dashboard"; }

    @GetMapping("/routes")
    public String routes() { return "admin/routes"; }

    @GetMapping("/buses")
    public String buses() { return "admin/buses"; }

    @GetMapping("/trips")
    public String trips() { return "admin/trips"; }

    @GetMapping("/tickets")
    public String tickets() { return "admin/tickets"; }

    @GetMapping("/users")
    public String users() { return "admin/users"; }
}