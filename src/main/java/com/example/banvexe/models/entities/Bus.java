package com.example.banvexe.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "buses")
@Data
@NoArgsConstructor
public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String busNumber;
    private String busType;
    private Integer capacity;

    @OneToMany(mappedBy = "bus")
    @JsonIgnore // THÊM DÒNG NÀY ĐỂ TRÁNH LỖI JSON
    private List<Trip> trips;
}