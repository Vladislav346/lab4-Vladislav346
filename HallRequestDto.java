package com.example.lab4.controller;

import com.example.lab4.dto.request.OrganizerRequestDto;
import com.example.lab4.dto.response.EventResponseDto;
import com.example.lab4.dto.response.OrganizerResponseDto;
import com.example.lab4.exception.ErrorResponse;
import com.example.lab4.service.OrganizerService;
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
@RequestMapping("/organizers")
@Tag(name = "Організатори", description = "Управління організаторами заходів, їх контактами та пов'язаними подіями")
public class OrganizerController {
    private final OrganizerService organizerService;
    public OrganizerController(OrganizerService organizerService) { this.organizerService = organizerService; }

    @Operation(summary = "Створити організатора", description = "Створює нового організатора. Ім'я, прізвище, email і телефон є обов'язковими, email має бути коректним.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Організатора створено", content = @Content(schema = @Schema(implementation = OrganizerResponseDto.class))), @ApiResponse(responseCode = "400", description = "Помилка валідації", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @PostMapping public ResponseEntity<OrganizerResponseDto> create(@Valid @RequestBody OrganizerRequestDto dto) { return ResponseEntity.ok(organizerService.create(dto)); }

    @Operation(summary = "Отримати організаторів", description = "Повертає список усіх організаторів, збережених у базі даних.")
    @ApiResponse(responseCode = "200", description = "Список отримано", content = @Content(schema = @Schema(implementation = OrganizerResponseDto.class)))
    @GetMapping public ResponseEntity<List<OrganizerResponseDto>> getAll() { return ResponseEntity.ok(organizerService.getAll()); }

    @Operation(summary = "Отримати організатора за id", description = "Повертає дані організатора за ідентифікатором. Якщо запис не знайдено, повертається 404.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Організатора знайдено", content = @Content(schema = @Schema(implementation = OrganizerResponseDto.class))), @ApiResponse(responseCode = "404", description = "Організатора не знайдено", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @GetMapping("/{id}") public ResponseEntity<OrganizerResponseDto> getById(@PathVariable Long id) { return ResponseEntity.ok(organizerService.getById(id)); }

    @Operation(summary = "Оновити організатора", description = "Оновлює контактні дані організатора. Перед збереженням виконується перевірка DTO.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Організатора оновлено", content = @Content(schema = @Schema(implementation = OrganizerResponseDto.class))), @ApiResponse(responseCode = "400", description = "Помилка валідації", content = @Content(schema = @Schema(implementation = ErrorResponse.class))), @ApiResponse(responseCode = "404", description = "Організатора не знайдено", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @PutMapping("/{id}") public ResponseEntity<OrganizerResponseDto> update(@PathVariable Long id, @Valid @RequestBody OrganizerRequestDto dto) { return ResponseEntity.ok(organizerService.update(id, dto)); }

    @Operation(summary = "Видалити організатора", description = "Видаляє організатора за id. Якщо організатора немає, повертається помилка 404.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Організатора видалено"), @ApiResponse(responseCode = "404", description = "Організатора не знайдено", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @DeleteMapping("/{id}") public ResponseEntity<String> delete(@PathVariable Long id) { organizerService.delete(id); return ResponseEntity.ok("Organizer deleted"); }

    @Operation(summary = "Отримати події організатора", description = "Повертає список подій, які належать конкретному організатору.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Список подій отримано", content = @Content(schema = @Schema(implementation = EventResponseDto.class))), @ApiResponse(responseCode = "404", description = "Організатора не знайдено", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @GetMapping("/{id}/events") public ResponseEntity<List<EventResponseDto>> getEvents(@PathVariable Long id) { return ResponseEntity.ok(organizerService.getEvents(id)); }
}
