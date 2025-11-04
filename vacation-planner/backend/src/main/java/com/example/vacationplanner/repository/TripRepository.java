package com.example.vacationplanner.repository;

import com.example.vacationplanner.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository extends JpaRepository<Trip, Long> {
}