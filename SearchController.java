package com.example.lab4.service;

import com.example.lab4.dto.request.BuildingRequestDto;
import com.example.lab4.dto.response.BuildingResponseDto;
import com.example.lab4.dto.response.EventResponseDto;
import com.example.lab4.dto.response.HallResponseDto;
import com.example.lab4.exception.ResourceNotFoundException;
import com.example.lab4.mapper.DtoMapper;
import com.example.lab4.model.Building;
import com.example.lab4.repository.BuildingRepository;
import com.example.lab4.repository.EventRepository;
import com.example.lab4.repository.HallRepository;
import org.springframework.stereotype.Service;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class BuildingService {
    private final BuildingRepository buildingRepository; private final HallRepository hallRepository; private final EventRepository eventRepository;
    public BuildingService(BuildingRepository buildingRepository, HallRepository hallRepository, EventRepository eventRepository) { this.buildingRepository = buildingRepository; this.hallRepository = hallRepository; this.eventRepository = eventRepository; }
    public BuildingResponseDto create(BuildingRequestDto dto) { return DtoMapper.toBuildingDto(buildingRepository.save(DtoMapper.toBuilding(dto))); }
    public List<BuildingResponseDto> getAll() { return buildingRepository.findAll().stream().map(DtoMapper::toBuildingDto).toList(); }
    public Building getEntityById(Long id) { return buildingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Будівлю з id " + id + " не знайдено")); }
    public BuildingResponseDto getById(Long id) { return DtoMapper.toBuildingDto(getEntityById(id)); }
    public BuildingResponseDto update(Long id, BuildingRequestDto dto) { Building existing = getEntityById(id); existing.setName(dto.getName()); existing.setCity(dto.getCity()); existing.setAddress(dto.getAddress()); return DtoMapper.toBuildingDto(buildingRepository.save(existing)); }
    public void delete(Long id) { Building existing = getEntityById(id); buildingRepository.delete(existing); }
    public List<HallResponseDto> getHalls(Long id) { getEntityById(id); return hallRepository.findByBuildingId(id).stream().map(DtoMapper::toHallDto).toList(); }
    public List<EventResponseDto> getEvents(Long id) { getEntityById(id); return eventRepository.findByBuildingId(id).stream().map(DtoMapper::toEventDto).toList(); }
    public Map<String, Long> workload() { Map<String, Long> result = new LinkedHashMap<>(); for (Building b : buildingRepository.findAll()) result.put(b.getName(), (long) eventRepository.findByBuildingId(b.getId()).size()); return result; }
}
