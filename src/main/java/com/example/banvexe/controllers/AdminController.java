package com.example.banvexe.controllers;

import com.example.banvexe.models.entities.Trip;
import com.example.banvexe.models.entities.Ticket; // Import thêm Ticket entity
import com.example.banvexe.services.TripService;
import com.example.banvexe.services.TicketService; // Import thêm TicketService
import com.example.banvexe.models.dto.AdminDashboardDTO;
import com.example.banvexe.models.entities.Ticket;
import com.example.banvexe.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;
import com.example.banvexe.repositories.TicketRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *  * Controller quản lý khu vực Admin
 *  * Đã tích hợp Service để lấy dữ liệu thực tế từ Database
 *  
 */
@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private TripService tripService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private AdminService adminService;

    // Dashboard
    @GetMapping({"", "/", "/dashboard"})
    public String dashboardPage() {
        return "admin/dashboard";
    }

    @GetMapping("/routes")
    public String routesPage() {
        return "admin/routes";
    }

    @GetMapping("/buses")
    public String busesPage() {
        return "admin/buses";
    }

    @GetMapping("/trips")
    public String tripsPage(Model model,
                           @RequestParam(defaultValue = "0") int page) {

        int pageSize = 10;
        Page<Trip> tripPage = tripService.getAllTripsPaginated(page, pageSize);

        model.addAttribute("trips", tripPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", tripPage.getTotalPages());

        return "admin/trips";
    }

    @GetMapping("/tickets")
    public String ticketsPage(Model model,
                             @RequestParam(defaultValue = "0") int page) {

        int pageSize = 10;
        Page<Ticket> ticketPage = ticketService.getAllTicketsPaginated(page, pageSize);

        model.addAttribute("tickets", ticketPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", ticketPage.getTotalPages());

        return "admin/tickets";
    }

    @GetMapping("/users")
    public String usersPage() {
        return "admin/users";
    }

    // API stats (JSON)
    @GetMapping("/stats")
    @ResponseBody
    public ResponseEntity<AdminDashboardDTO> getDashboardStats() {
        try {
            AdminDashboardDTO stats = adminService.getDashboardStats();
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}