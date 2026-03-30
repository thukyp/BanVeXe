package com.example.banvexe.controllers;

import com.example.banvexe.models.entities.Trip;
import com.example.banvexe.services.TripService;
import com.example.banvexe.repositories.TripRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page; // Quan trọng: Import của Spring Data
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.List;

@RestController
@RequestMapping("/api/trips")
@CrossOrigin(origins = "*")
public class TripController {

    @Autowired
    private TripService tripService;
    @Autowired
    private TripRepository tripRepository;

    // SỬA TẠI ĐÂY: Thêm phân trang cho API
    @GetMapping
    public ResponseEntity<Page<Trip>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        // Gọi hàm phân trang đã viết trong TripService
        Page<Trip> tripPage = tripService.getAllTripsPaginated(page, size);
        return ResponseEntity.ok(tripPage);

    }

    // Lấy chi tiết một chuyến xe (Dùng để load giá vé và thông tin ở trang Booking)
    @GetMapping("/{id}")
    public ResponseEntity<Trip> getTripById(@PathVariable Long id) {
        return tripRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Tạo mới chuyến xe
    @PostMapping
    public ResponseEntity<Trip> create(@Valid @RequestBody Trip trip) {
        Trip savedTrip = tripService.createTrip(trip);
        return ResponseEntity.ok(savedTrip);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Trip> update(
            @PathVariable Long id,
            @RequestBody Trip trip) {
        return ResponseEntity.ok(tripService.updateTrip(id, trip));
    }

    // Xóa chuyến xe
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tripService.deleteTrip(id);
        return ResponseEntity.ok().build();
    }

    // Tìm kiếm chuyến xe theo Điểm đi, Điểm đến và Ngày
    @GetMapping("/search")
    public ResponseEntity<List<Trip>> search(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam String date) {
        List<Trip> results = tripService.searchTrips(from, to, date);
        return ResponseEntity.ok(results);
    }
}
