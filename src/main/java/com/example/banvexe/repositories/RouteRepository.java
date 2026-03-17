package com.example.banvexe.repositories;

import com.example.banvexe.models.entities.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface RouteRepository extends JpaRepository<Route, Long> {
    // 1. Tìm tuyến đường theo điểm đi và điểm đến chính xác (Dùng cho ô tìm kiếm)
    List<Route> findByDepartureLocationAndArrivalLocation(String departureLocation, String arrivalLocation);

    // 2. Tìm các tuyến đường có điểm đi cụ thể
    List<Route> findByDepartureLocation(String departureLocation);

    // 3. Lấy danh sách các tuyến phổ biến (Ví dụ: Tuyến có nhiều chuyến xe nhất
    // hoặc được đặt nhiều)
    // Nếu bạn chưa có trường 'isPopular', có thể lấy ngẫu nhiên hoặc lấy Top 6
    @Query("SELECT r FROM Route r")
    List<Route> findTopRoutes();

    // 4. Tìm kiếm gần đúng (Dùng cho chức năng gợi ý - Suggestion)
    List<Route> findByDepartureLocationContainingIgnoreCase(String departureLocation);
}