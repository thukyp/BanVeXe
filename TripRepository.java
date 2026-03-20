package com.huy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    List<Trip> findByOriginAndDestinationAndDepartureTimeBetween(
        String origin, String destination, LocalDateTime start, LocalDateTime end);
}

@Repository
interface TicketRepository extends JpaRepository<Ticket, Long> {
}