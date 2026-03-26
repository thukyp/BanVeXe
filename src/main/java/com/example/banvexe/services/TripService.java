package com.example.banvexe.services;

import com.example.banvexe.models.entities.Trip;
import com.example.banvexe.repositories.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import com.example.banvexe.repositories.RouteRepository;

@Service
public class TripService {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private RouteRepository routeRepository;

    public TripService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }

    public Trip createTrip(Trip trip) {
        // Logic: Nếu số ghế trống gửi lên bị trống, lấy từ sức chứa của xe khách
        if (trip.getAvailableSeats() == null && trip.getBus() != null) {
            trip.setAvailableSeats(trip.getBus().getCapacity());
        }
        return tripRepository.save(trip);
    }

    public List<Trip> searchTrips(String from, String to, String date) {

        LocalDate localDate = LocalDate.parse(date);

        LocalDateTime start = localDate.atStartOfDay();
        LocalDateTime end = localDate.plusDays(1).atStartOfDay();

        return routeRepository.searchTrips(from, to, start, end);
    }

    public void deleteTrip(Long id) {
        tripRepository.deleteById(id);
    }
}