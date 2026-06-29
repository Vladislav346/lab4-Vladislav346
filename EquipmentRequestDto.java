package com.example.lab4.controller;

import com.example.lab4.dto.request.BuildingRequestDto;
import com.example.lab4.dto.response.BuildingResponseDto;
import com.example.lab4.dto.response.EventResponseDto;
import com.example.lab4.dto.response.HallResponseDto;
import com.example.lab4.exception.ErrorResponse;
import com.example.lab4.service.BuildingService;
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
@RequestMapping("/buildings")
@Tag(name = "Будівлі", description = "Управління готелями та бізнес-центрами, у яких розміщені конференц-зали")
public class BuildingController {
    private final BuildingService buildingService;
    public BuildingController(BuildingService buildingService) { this.buildingService = buildingService; }

    @Operation(summary = "Створити будівлю", description = "Створює нову будівлю. Назва, місто та адреса є обов'язковими.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Будівлю створено", content = @Content(schema = @Schema(implementation = BuildingResponseDto.class))), @ApiResponse(responseCode = "400", description = "Помилка валідації", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @PostMapping public ResponseEntity<BuildingResponseDto> create(@Valid @RequestBody BuildingRequestDto dto) { return ResponseEntity.ok(buildingService.create(dto)); }

    @Operation(summary = "Отримати будівлі", description = "Повертає список усіх будівель.")
    @ApiResponse(responseCode = "200", description = "Список отримано", content = @Content(schema = @Schema(implementation = BuildingResponseDto.class)))
    @GetMapping public ResponseEntity<List<BuildingResponseDto>> getAll() { return ResponseEntity.ok(buildingService.getAll()); }

    @Operation(summary = "Отримати будівлю за id", description = "Повертає будівлю за ідентифікатором.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Будівлю знайдено", content = @Content(schema = @Schema(implementation = BuildingResponseDto.class))), @ApiResponse(responseCode = "404", description = "Будівлю не знайдено", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @GetMapping("/{id}") public ResponseEntity<BuildingResponseDto> getById(@PathVariable Long id) { return ResponseEntity.ok(buildingService.getById(id)); }

    @Operation(summary = "Оновити будівлю", description = "Оновлює назву, місто та адресу будівлі.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Будівлю оновлено", content = @Content(schema = @Schema(implementation = BuildingResponseDto.class))), @ApiResponse(responseCode = "400", description = "Помилка валідації", content = @Content(schema = @Schema(implementation = ErrorResponse.class))), @ApiResponse(responseCode = "404", description = "Будівлю не знайдено", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @PutMapping("/{id}") public ResponseEntity<BuildingResponseDto> update(@PathVariable Long id, @Valid @RequestBody BuildingRequestDto dto) { return ResponseEntity.ok(buildingService.update(id, dto)); }

    @Operation(summary = "Видалити будівлю", description = "Видаляє будівлю за ідентифікатором.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Будівлю видалено"), @ApiResponse(responseCode = "404", description = "Будівлю не знайдено", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @DeleteMapping("/{id}") public ResponseEntity<String> delete(@PathVariable Long id) { buildingService.delete(id); return ResponseEntity.ok("Building deleted"); }

    @Operation(summary = "Отримати зали будівлі", description = "Повертає конференц-зали, що належать конкретній будівлі.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Зали отримано", content = @Content(schema = @Schema(implementation = HallResponseDto.class))), @ApiResponse(responseCode = "404", description = "Будівлю не знайдено", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @GetMapping("/{id}/halls") public ResponseEntity<List<HallResponseDto>> getHalls(@PathVariable Long id) { return ResponseEntity.ok(buildingService.getHalls(id)); }

    @Operation(summary = "Отримати події будівлі", description = "Повертає всі події, заплановані у конкретній будівлі.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Події отримано", content = @Content(schema = @Schema(implementation = EventResponseDto.class))), @ApiResponse(responseCode = "404", description = "Будівлю не знайдено", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @GetMapping("/{id}/events") public ResponseEntity<List<EventResponseDto>> getEvents(@PathVariable Long id) { return ResponseEntity.ok(buildingService.getEvents(id)); }
}
