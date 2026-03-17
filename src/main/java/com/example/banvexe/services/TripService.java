package com.example.banvexe.services;

import com.example.banvexe.models.entities.Trip;
import com.example.banvexe.repositories.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TripService {

    @Autowired
    private TripRepository tripRepository;

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

    public void deleteTrip(Long id) {
        tripRepository.deleteById(id);
    }
}