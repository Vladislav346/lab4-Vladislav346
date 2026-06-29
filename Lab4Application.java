package com.example.lab4.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO для створення обладнання конференц-залу")
public class EquipmentRequestDto {
    @Schema(description = "Назва обладнання", example = "Projector", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Назва обладнання не може бути порожньою")
    private String name;

    @Schema(description = "Опис обладнання", example = "Full HD projector for presentations")
    @Size(max = 255, message = "Опис не може перевищувати 255 символів")
    private String description;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
