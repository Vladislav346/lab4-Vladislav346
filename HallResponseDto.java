package com.example.lab4.controller;

import com.example.lab4.dto.response.EventResponseDto;
import com.example.lab4.dto.response.HallResponseDto;
import com.example.lab4.dto.response.OrganizerResponseDto;
import com.example.lab4.service.EventService;
import com.example.lab4.service.HallService;
import com.example.lab4.service.OrganizerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {
    private final EventService eventService; private final OrganizerService organizerService; private final HallService hallService;
    public SearchController(EventService eventService, OrganizerService organizerService, HallService hallService) { this.eventService = eventService; this.organizerService = organizerService; this.hallService = hallService; }
    @GetMapping("/events") public ResponseEntity<List<EventResponseDto>> searchEvents(@RequestParam String query) { return ResponseEntity.ok(eventService.search(query)); }
    @GetMapping("/organizers") public ResponseEntity<List<OrganizerResponseDto>> searchOrganizers(@RequestParam String query) { return ResponseEntity.ok(organizerService.search(query)); }
    @GetMapping("/halls") public ResponseEntity<List<HallResponseDto>> searchHalls(@RequestParam String query) { return ResponseEntity.ok(hallService.search(query)); }
}
