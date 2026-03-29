package com.example.banvexe.controllers;

import com.example.banvexe.models.entities.Ticket;
import com.example.banvexe.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page; // QUAN TRỌNG: Thêm dòng này
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import com.example.banvexe.services.InvoiceService;
import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@CrossOrigin(origins = "*")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private InvoiceService invoiceService;

    // API phân trang
    @GetMapping
    public ResponseEntity<Page<Ticket>> getAllTickets(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(ticketService.getAllTicketsPaginated(page, size));
    }

    // Lấy 1 ticket
    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long id) {
        Ticket ticket = ticketService.getTicketById(id);
        if (ticket != null) {
            return ResponseEntity.ok(ticket);
        }
        return ResponseEntity.notFound().build();
    }

    // Xóa ticket
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }

    // Xuất PDF hóa đơn
    @GetMapping("/invoice/{ticketId}/pdf")
    public ResponseEntity<byte[]> exportPdf(@PathVariable Long ticketId) {

        byte[] pdf = invoiceService.generatePdf(ticketId);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header("Content-Disposition", "inline; filename=invoice.pdf")
                .body(pdf);
    }
}
