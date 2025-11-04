package com.example.vacationplanner.controller;

import com.example.vacationplanner.model.Trip;
import com.example.vacationplanner.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trips")
public class TripController {

    @Autowired
    private TripRepository tripRepository;

    @GetMapping
    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trip> getTripById(@PathVariable(value = "id") Long tripId) {
        return tripRepository.findById(tripId)
                .map(trip -> ResponseEntity.ok().body(trip))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Trip createTrip(@RequestBody Trip trip) {
        return tripRepository.save(trip);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Trip> updateTrip(@PathVariable(value = "id") Long tripId,
                                           @RequestBody Trip tripDetails) {
        return tripRepository.findById(tripId)
                .map(trip -> {
                    trip.setTitle(tripDetails.getTitle());
                    trip.setDestination(tripDetails.getDestination());
                    trip.setStartDate(tripDetails.getStartDate());
                    trip.setEndDate(tripDetails.getEndDate());
                    trip.setBudget(tripDetails.getBudget());
                    Trip updatedTrip = tripRepository.save(trip);
                    return ResponseEntity.ok().body(updatedTrip);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTrip(@PathVariable(value = "id") Long tripId) {
        return tripRepository.findById(tripId)
                .map(trip -> {
                    tripRepository.delete(trip);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }
}