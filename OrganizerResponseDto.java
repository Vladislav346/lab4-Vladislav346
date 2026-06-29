package com.example.lab4.controller;

import com.example.lab4.dto.request.EventRequestDto;
import com.example.lab4.dto.response.EventResponseDto;
import com.example.lab4.exception.ErrorResponse;
import com.example.lab4.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/events")
@Tag(name = "Події", description = "Створення, редагування, скасування та пошук активних бронювань конференц-залів")
public class EventController {
    private final EventService eventService;
    public EventController(EventService eventService) { this.eventService = eventService; }

    @Operation(summary = "Створити подію", description = "Створює бронювання для події. Назва, час початку, час завершення та статус є обов'язковими.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Подію створено", content = @Content(schema = @Schema(implementation = EventResponseDto.class))), @ApiResponse(responseCode = "400", description = "Некоректні дані", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @PostMapping public ResponseEntity<EventResponseDto> create(@Valid @RequestBody EventRequestDto dto) { return ResponseEntity.ok(eventService.create(dto)); }

    @Operation(summary = "Отримати події", description = "Повертає всі події, які збережені в системі бронювання.")
    @ApiResponse(responseCode = "200", description = "Список подій отримано", content = @Content(schema = @Schema(implementation = EventResponseDto.class)))
    @GetMapping public ResponseEntity<List<EventResponseDto>> getAll() { return ResponseEntity.ok(eventService.getAll()); }

    @Operation(summary = "Отримати подію за id", description = "Повертає одну подію за її ідентифікатором.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Подію знайдено", content = @Content(schema = @Schema(implementation = EventResponseDto.class))), @ApiResponse(responseCode = "404", description = "Подію не знайдено", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @GetMapping("/{id}") public ResponseEntity<EventResponseDto> getById(@PathVariable Long id) { return ResponseEntity.ok(eventService.getById(id)); }

    @Operation(summary = "Оновити подію", description = "Оновлює дані бронювання та перевіряє коректність вхідного DTO.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Подію оновлено", content = @Content(schema = @Schema(implementation = EventResponseDto.class))), @ApiResponse(responseCode = "400", description = "Некоректні дані", content = @Content(schema = @Schema(implementation = ErrorResponse.class))), @ApiResponse(responseCode = "404", description = "Подію не знайдено", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @PutMapping("/{id}") public ResponseEntity<EventResponseDto> update(@PathVariable Long id, @Valid @RequestBody EventRequestDto dto) { return ResponseEntity.ok(eventService.update(id, dto)); }

    @Operation(summary = "Скасувати подію", description = "Видаляє або скасовує подію за ідентифікатором.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Подію скасовано"), @ApiResponse(responseCode = "404", description = "Подію не знайдено", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @DeleteMapping("/{id}") public ResponseEntity<String> delete(@PathVariable Long id) { eventService.delete(id); return ResponseEntity.ok("Event cancelled"); }

    @Operation(summary = "Отримати активні події", description = "Повертає події зі статусом ACTIVE.")
    @ApiResponse(responseCode = "200", description = "Активні події отримано", content = @Content(schema = @Schema(implementation = EventResponseDto.class)))
    @GetMapping("/active") public ResponseEntity<List<EventResponseDto>> getActive() { return ResponseEntity.ok(eventService.getActive()); }

    @Operation(summary = "Призначити організатора", description = "Пов'язує подію з організатором за їх ідентифікаторами.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Організатора призначено", content = @Content(schema = @Schema(implementation = EventResponseDto.class))), @ApiResponse(responseCode = "404", description = "Подію або організатора не знайдено", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @PostMapping("/{eventId}/organizer/{organizerId}") public ResponseEntity<EventResponseDto> assignOrganizer(@PathVariable Long eventId, @PathVariable Long organizerId) { return ResponseEntity.ok(eventService.assignOrganizer(eventId, organizerId)); }

    @Operation(summary = "Призначити зал", description = "Пов'язує подію з конференц-залом.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Зал призначено", content = @Content(schema = @Schema(implementation = EventResponseDto.class))), @ApiResponse(responseCode = "404", description = "Подію або зал не знайдено", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @PostMapping("/{eventId}/hall/{hallId}") public ResponseEntity<EventResponseDto> assignHall(@PathVariable Long eventId, @PathVariable Long hallId) { return ResponseEntity.ok(eventService.assignHall(eventId, hallId)); }

    @Operation(summary = "Призначити будівлю", description = "Пов'язує подію з будівлею, у якій проходить бронювання.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Будівлю призначено", content = @Content(schema = @Schema(implementation = EventResponseDto.class))), @ApiResponse(responseCode = "404", description = "Подію або будівлю не знайдено", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @PostMapping("/{eventId}/building/{buildingId}") public ResponseEntity<EventResponseDto> assignBuilding(@PathVariable Long eventId, @PathVariable Long buildingId) { return ResponseEntity.ok(eventService.assignBuilding(eventId, buildingId)); }
}
