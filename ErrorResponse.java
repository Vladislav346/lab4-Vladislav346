package com.example.lab4.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO для створення або оновлення будівлі")
public class BuildingRequestDto {
    @Schema(description = "Назва готелю або бізнес-центру", example = "Business Center Alpha", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Назва будівлі не може бути порожньою")
    @Size(min = 2, max = 100, message = "Назва будівлі має містити від 2 до 100 символів")
    private String name;

    @Schema(description = "Місто розташування будівлі", example = "Odesa", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Місто не може бути порожнім")
    private String city;

    @Schema(description = "Адреса будівлі", example = "Shevchenko Avenue 10", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Адреса не може бути порожньою")
    private String address;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}
