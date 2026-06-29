package com.example.lab4.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Відповідь із даними конференц-залу")
public class HallResponseDto {
    @Schema(description = "Ідентифікатор залу", example = "1")
    private Long id;
    @Schema(description = "Назва залу", example = "Main Conference Hall")
    private String name;
    @Schema(description = "Місткість залу", example = "120")
    private Integer capacity;
    @Schema(description = "Поверх", example = "2")
    private Integer floor;
    @Schema(description = "Ідентифікатор будівлі", example = "1")
    private Long buildingId;
    @Schema(description = "Назва будівлі", example = "Business Center Alpha")
    private String buildingName;
    public HallResponseDto(Long id, String name, Integer capacity, Integer floor, Long buildingId, String buildingName) { this.id=id; this.name=name; this.capacity=capacity; this.floor=floor; this.buildingId=buildingId; this.buildingName=buildingName; }
    public Long getId(){return id;} public String getName(){return name;} public Integer getCapacity(){return capacity;} public Integer getFloor(){return floor;} public Long getBuildingId(){return buildingId;} public String getBuildingName(){return buildingName;}
}
