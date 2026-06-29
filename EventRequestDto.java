package com.example.lab4.controller;

import com.example.lab4.dto.request.HallRequestDto;
import com.example.lab4.dto.response.HallResponseDto;
import com.example.lab4.exception.ErrorResponse;
import com.example.lab4.service.HallService;
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
@RequestMapping("/halls")
@Tag(name = "Конференц-зали", description = "Управління залами, їх місткістю, поверхом, будівлею та обладнанням")
public class HallController {
    private final HallService hallService;
    public HallController(HallService hallService) { this.hallService = hallService; }

    @Operation(summary = "Створити зал", description = "Створює конференц-зал. Назва, місткість та поверх є обов'язковими.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Зал створено", content = @Content(schema = @Schema(implementation = HallResponseDto.class))), @ApiResponse(responseCode = "400", description = "Помилка валідації", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @PostMapping public ResponseEntity<HallResponseDto> create(@Valid @RequestBody HallRequestDto dto) { return ResponseEntity.ok(hallService.create(dto)); }

    @Operation(summary = "Отримати зали", description = "Повертає список усіх конференц-залів.")
    @ApiResponse(responseCode = "200", description = "Список залів отримано", content = @Content(schema = @Schema(implementation = HallResponseDto.class)))
    @GetMapping public ResponseEntity<List<HallResponseDto>> getAll() { return ResponseEntity.ok(hallService.getAll()); }

    @Operation(summary = "Отримати зал за id", description = "Повертає зал за ідентифікатором.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Зал знайдено", content = @Content(schema = @Schema(implementation = HallResponseDto.class))), @ApiResponse(responseCode = "404", description = "Зал не знайдено", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @GetMapping("/{id}") public ResponseEntity<HallResponseDto> getById(@PathVariable Long id) { return ResponseEntity.ok(hallService.getById(id)); }

    @Operation(summary = "Оновити зал", description = "Оновлює дані конференц-залу та перевіряє вхідні обмеження.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Зал оновлено", content = @Content(schema = @Schema(implementation = HallResponseDto.class))), @ApiResponse(responseCode = "400", description = "Помилка валідації", content = @Content(schema = @Schema(implementation = ErrorResponse.class))), @ApiResponse(responseCode = "404", description = "Зал не знайдено", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @PutMapping("/{id}") public ResponseEntity<HallResponseDto> update(@PathVariable Long id, @Valid @RequestBody HallRequestDto dto) { return ResponseEntity.ok(hallService.update(id, dto)); }

    @Operation(summary = "Видалити зал", description = "Видаляє конференц-зал за ідентифікатором.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Зал видалено"), @ApiResponse(responseCode = "404", description = "Зал не знайдено", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @DeleteMapping("/{id}") public ResponseEntity<String> delete(@PathVariable Long id) { hallService.delete(id); return ResponseEntity.ok("Hall deleted"); }

    @Operation(summary = "Отримати зали будівлі", description = "Повертає зали, що знаходяться у будівлі з переданим buildingId.")
    @ApiResponse(responseCode = "200", description = "Зали отримано", content = @Content(schema = @Schema(implementation = HallResponseDto.class)))
    @GetMapping("/building/{buildingId}") public ResponseEntity<List<HallResponseDto>> getByBuilding(@PathVariable Long buildingId) { return ResponseEntity.ok(hallService.getByBuilding(buildingId)); }

    @Operation(summary = "Призначити будівлю", description = "Закріплює зал за конкретною будівлею.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Будівлю призначено", content = @Content(schema = @Schema(implementation = HallResponseDto.class))), @ApiResponse(responseCode = "404", description = "Зал або будівлю не знайдено", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @PostMapping("/{id}/building/{buildingId}") public ResponseEntity<HallResponseDto> assignBuilding(@PathVariable Long id, @PathVariable Long buildingId) { return ResponseEntity.ok(hallService.assignBuilding(id, buildingId)); }

    @Operation(summary = "Додати обладнання", description = "Додає обладнання до конференц-залу за схемою M:N.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Обладнання додано", content = @Content(schema = @Schema(implementation = HallResponseDto.class))), @ApiResponse(responseCode = "404", description = "Зал або обладнання не знайдено", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @PostMapping("/{id}/equipment/{equipmentId}") public ResponseEntity<HallResponseDto> addEquipment(@PathVariable Long id, @PathVariable Long equipmentId) { return ResponseEntity.ok(hallService.addEquipment(id, equipmentId)); }

    @Operation(summary = "Видалити обладнання із залу", description = "Прибирає зв'язок між залом і обладнанням.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Обладнання видалено із залу", content = @Content(schema = @Schema(implementation = HallResponseDto.class))), @ApiResponse(responseCode = "404", description = "Зал або обладнання не знайдено", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @DeleteMapping("/{id}/equipment/{equipmentId}") public ResponseEntity<HallResponseDto> removeEquipment(@PathVariable Long id, @PathVariable Long equipmentId) { return ResponseEntity.ok(hallService.removeEquipment(id, equipmentId)); }
}
