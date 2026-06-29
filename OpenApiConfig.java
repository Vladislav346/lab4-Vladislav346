package com.example.lab4.dto.request;

import com.example.lab4.model.EventStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Schema(description = "DTO для створення або оновлення події/бронювання")
public class EventRequestDto {
    @Schema(description = "Назва події", example = "IT Conference 2026", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Назва події не може бути порожньою")
    private String title;

    @Schema(description = "Дата та час початку події", example = "2026-07-01T10:00:00", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Дата та час початку обов'язкові")
    private LocalDateTime startTime;

    @Schema(description = "Дата та час завершення події", example = "2026-07-01T12:00:00", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Дата та час завершення обов'язкові")
    private LocalDateTime endTime;

    @Schema(description = "Статус події", example = "ACTIVE", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Статус події обов'язковий")
    private EventStatus status;

    @Schema(description = "Ідентифікатор організатора", example = "1")
    private Long organizerId;
    @Schema(description = "Ідентифікатор конференц-залу", example = "1")
    private Long hallId;
    @Schema(description = "Ідентифікатор будівлі", example = "1")
    private Long buildingId;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
    public EventStatus getStatus() { return status; }
    public void setStatus(EventStatus status) { this.status = status; }
    public Long getOrganizerId() { return organizerId; }
    public void setOrganizerId(Long organizerId) { this.organizerId = organizerId; }
    public Long getHallId() { return hallId; }
    public void setHallId(Long hallId) { this.hallId = hallId; }
    public Long getBuildingId() { return buildingId; }
    public void setBuildingId(Long buildingId) { this.buildingId = buildingId; }
}
