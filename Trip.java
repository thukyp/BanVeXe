package com.huy;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String origin;
    private String destination;
    private LocalDateTime departureTime;
    private Double price;
}

@Entity
@Data
class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    private Trip trip;
    
    private String seatNumber;
    private String status; // AVAILABLE, HOLD, BOOKED
    private LocalDateTime holdExpiresAt;
}