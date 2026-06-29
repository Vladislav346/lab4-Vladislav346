package com.example.lab4.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "DTO для створення або оновлення конференц-залу")
public class HallRequestDto {
    @Schema(description = "Назва конференц-залу", example = "Main Conference Hall", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Назва залу не може бути порожньою")
    private String name;

    @Schema(description = "Максимальна кількість учасників", example = "120", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Місткість залу обов'язкова")
    @Min(value = 1, message = "Місткість залу має бути більше 0")
    private Integer capacity;

    @Schema(description = "Поверх, на якому знаходиться зал", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Поверх обов'язковий")
    @Min(value = 0, message = "Поверх не може бути від'ємним")
    private Integer floor;

    @Schema(description = "Ідентифікатор будівлі, до якої належить зал", example = "1")
    private Long buildingId;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }
    public Integer getFloor() { return floor; }
    public void setFloor(Integer floor) { this.floor = floor; }
    public Long getBuildingId() { return buildingId; }
    public void setBuildingId(Long buildingId) { this.buildingId = buildingId; }
}
