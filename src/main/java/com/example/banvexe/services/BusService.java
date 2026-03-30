package com.example.banvexe.services;

import com.example.banvexe.models.entities.Bus;
import com.example.banvexe.repositories.BusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class BusService {
    @Autowired
    private BusRepository busRepository;

    public List<Bus> getAll() { return busRepository.findAll(); }

    public Bus getById(Long id) { 
        return busRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy xe")); 
    }

    public Bus save(Bus bus) { return busRepository.save(bus); }

    public Bus update(Long id, Bus details) {
        Bus bus = getById(id);
        bus.setBusNumber(details.getBusNumber());
        bus.setBusType(details.getBusType());
        bus.setCapacity(details.getCapacity());
        return busRepository.save(bus);
    }

    public void delete(Long id) { busRepository.deleteById(id); }
}