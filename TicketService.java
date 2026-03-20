package com.huy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;

    @Transactional
    public String holdSeat(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElse(null);
        
        if (ticket == null) return "Vé không tồn tại!";

        // Nếu ghế trống HOẶC đã hết hạn giữ
        boolean isAvailable = "AVAILABLE".equals(ticket.getStatus());
        boolean isExpired = ticket.getHoldExpiresAt() != null && ticket.getHoldExpiresAt().isBefore(LocalDateTime.now());

        if (isAvailable || isExpired) {
            ticket.setStatus("HOLD");
            ticket.setHoldExpiresAt(LocalDateTime.now().plusMinutes(10));
            ticketRepository.save(ticket);
            return "Giữ ghế thành công trong 10 phút!";
        }
        
        return "Ghế này đang có người khác giữ hoặc đã đặt.";
    }
}