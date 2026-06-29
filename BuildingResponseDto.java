package com.example.lab4.controller;

import com.example.lab4.dto.request.EquipmentRequestDto;
import com.example.lab4.dto.response.EquipmentResponseDto;
import com.example.lab4.exception.ErrorResponse;
import com.example.lab4.service.EquipmentService;
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
@RequestMapping("/equipment")
@Tag(name = "Обладнання", description = "Управління обладнанням конференц-залів: проекторами, аудіосистемами та відеоконференціями")
public class EquipmentController {
    private final EquipmentService equipmentService;
    public EquipmentController(EquipmentService equipmentService) { this.equipmentService = equipmentService; }

    @Operation(summary = "Створити обладнання", description = "Створює новий тип обладнання. Назва є обов'язковою, опис може бути порожнім.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Обладнання створено", content = @Content(schema = @Schema(implementation = EquipmentResponseDto.class))), @ApiResponse(responseCode = "400", description = "Помилка валідації", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @PostMapping public ResponseEntity<EquipmentResponseDto> create(@Valid @RequestBody EquipmentRequestDto dto) { return ResponseEntity.ok(equipmentService.create(dto)); }

    @Operation(summary = "Отримати обладнання", description = "Повертає список усього обладнання.")
    @ApiResponse(responseCode = "200", description = "Список обладнання отримано", content = @Content(schema = @Schema(implementation = EquipmentResponseDto.class)))
    @GetMapping public ResponseEntity<List<EquipmentResponseDto>> getAll() { return ResponseEntity.ok(equipmentService.getAll()); }

    @Operation(summary = "Отримати обладнання за id", description = "Повертає один запис обладнання за ідентифікатором.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Обладнання знайдено", content = @Content(schema = @Schema(implementation = EquipmentResponseDto.class))), @ApiResponse(responseCode = "404", description = "Обладнання не знайдено", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @GetMapping("/{id}") public ResponseEntity<EquipmentResponseDto> getById(@PathVariable Long id) { return ResponseEntity.ok(equipmentService.getById(id)); }

    @Operation(summary = "Видалити обладнання", description = "Видаляє обладнання за ідентифікатором.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Обладнання видалено"), @ApiResponse(responseCode = "404", description = "Обладнання не знайдено", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @DeleteMapping("/{id}") public ResponseEntity<String> delete(@PathVariable Long id) { equipmentService.delete(id); return ResponseEntity.ok("Equipment deleted"); }
}
