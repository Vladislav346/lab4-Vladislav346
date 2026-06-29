package com.example.lab4.repository;

import com.example.lab4.model.Event;
import com.example.lab4.model.EventStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByOrganizerId(Long organizerId);
    List<Event> findByHallId(Long hallId);
    List<Event> findByBuildingId(Long buildingId);
    List<Event> findByStatus(EventStatus status);
    List<Event> findByTitleContainingIgnoreCase(String title);
}
