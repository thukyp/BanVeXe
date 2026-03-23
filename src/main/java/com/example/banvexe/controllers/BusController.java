package com.example.banvexe.controllers;
import com.example.banvexe.models.entities.Bus;
import com.example.banvexe.services.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/buses")
public class BusController {
    @Autowired
    private BusService busService;

    @GetMapping
    public List<Bus> getAll() { return busService.getAll(); }

    @GetMapping("/{id}")
    public Bus getById(@PathVariable Long id) { return busService.getById(id); }

    @PostMapping
    public Bus create(@RequestBody Bus bus) { return busService.save(bus); }

    @PutMapping("/{id}")
    public Bus update(@PathVariable Long id, @RequestBody Bus bus) { 
        return busService.update(id, bus); 
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { busService.delete(id); }
}
