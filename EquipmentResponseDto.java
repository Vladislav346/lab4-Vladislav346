package com.example.lab4.controller;

import com.example.lab4.dto.response.EventResponseDto;
import com.example.lab4.service.BuildingService;
import com.example.lab4.service.EventService;
import com.example.lab4.service.HallService;
import com.example.lab4.service.OrganizerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {
    private final EventService eventService; private final OrganizerService organizerService; private final HallService hallService; private final BuildingService buildingService;
    public AnalyticsController(EventService eventService, OrganizerService organizerService, HallService hallService, BuildingService buildingService) { this.eventService = eventService; this.organizerService = organizerService; this.hallService = hallService; this.buildingService = buildingService; }
    @GetMapping("/events/count") public ResponseEntity<Long> eventsCount() { return ResponseEntity.ok(eventService.count()); }
    @GetMapping("/organizers/count") public ResponseEntity<Long> organizersCount() { return ResponseEntity.ok(organizerService.count()); }
    @GetMapping("/events/active") public ResponseEntity<List<EventResponseDto>> activeEvents() { return ResponseEntity.ok(eventService.getActive()); }
    @GetMapping("/halls/by-capacity") public ResponseEntity<Map<String, Long>> hallsByCapacity() { return ResponseEntity.ok(hallService.countByCapacity()); }
    @GetMapping("/buildings/workload") public ResponseEntity<Map<String, Long>> buildingsWorkload() { return ResponseEntity.ok(buildingService.workload()); }
}
