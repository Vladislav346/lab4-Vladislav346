package com.example.lab4.service;

import com.example.lab4.dto.request.HallRequestDto;
import com.example.lab4.dto.response.HallResponseDto;
import com.example.lab4.exception.ResourceNotFoundException;
import com.example.lab4.mapper.DtoMapper;
import com.example.lab4.model.Building;
import com.example.lab4.model.Equipment;
import com.example.lab4.model.Hall;
import com.example.lab4.repository.BuildingRepository;
import com.example.lab4.repository.EquipmentRepository;
import com.example.lab4.repository.HallRepository;
import org.springframework.stereotype.Service;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class HallService {
    private final HallRepository hallRepository; private final BuildingRepository buildingRepository; private final EquipmentRepository equipmentRepository;
    public HallService(HallRepository hallRepository, BuildingRepository buildingRepository, EquipmentRepository equipmentRepository) { this.hallRepository = hallRepository; this.buildingRepository = buildingRepository; this.equipmentRepository = equipmentRepository; }
    public HallResponseDto create(HallRequestDto dto) { Hall hall = DtoMapper.toHall(dto); setBuildingIfPresent(hall, dto.getBuildingId()); return DtoMapper.toHallDto(hallRepository.save(hall)); }
    public List<HallResponseDto> getAll() { return hallRepository.findAll().stream().map(DtoMapper::toHallDto).toList(); }
    public Hall getEntityById(Long id) { return hallRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Зал з id " + id + " не знайдено")); }
    public HallResponseDto getById(Long id) { return DtoMapper.toHallDto(getEntityById(id)); }
    public HallResponseDto update(Long id, HallRequestDto dto) { Hall existing = getEntityById(id); existing.setName(dto.getName()); existing.setCapacity(dto.getCapacity()); existing.setFloor(dto.getFloor()); setBuildingIfPresent(existing, dto.getBuildingId()); return DtoMapper.toHallDto(hallRepository.save(existing)); }
    public void delete(Long id) { Hall existing = getEntityById(id); hallRepository.delete(existing); }
    public List<HallResponseDto> getByBuilding(Long buildingId) { findBuilding(buildingId); return hallRepository.findByBuildingId(buildingId).stream().map(DtoMapper::toHallDto).toList(); }
    public HallResponseDto assignBuilding(Long hallId, Long buildingId) { Hall hall = getEntityById(hallId); hall.setBuilding(findBuilding(buildingId)); return DtoMapper.toHallDto(hallRepository.save(hall)); }
    public HallResponseDto addEquipment(Long hallId, Long equipmentId) { Hall hall = getEntityById(hallId); Equipment equipment = findEquipment(equipmentId); hall.getEquipment().add(equipment); return DtoMapper.toHallDto(hallRepository.save(hall)); }
    public HallResponseDto removeEquipment(Long hallId, Long equipmentId) { Hall hall = getEntityById(hallId); Equipment equipment = findEquipment(equipmentId); hall.getEquipment().remove(equipment); return DtoMapper.toHallDto(hallRepository.save(hall)); }
    public List<HallResponseDto> search(String query) { return hallRepository.findByNameContainingIgnoreCase(query).stream().map(DtoMapper::toHallDto).toList(); }
    public Map<String, Long> countByCapacity() { List<Hall> halls = hallRepository.findAll(); Map<String, Long> result = new LinkedHashMap<>(); result.put("small_0_50", halls.stream().filter(h -> h.getCapacity()!=null && h.getCapacity()<=50).count()); result.put("medium_51_150", halls.stream().filter(h -> h.getCapacity()!=null && h.getCapacity()>50 && h.getCapacity()<=150).count()); result.put("large_151_plus", halls.stream().filter(h -> h.getCapacity()!=null && h.getCapacity()>150).count()); return result; }
    private void setBuildingIfPresent(Hall hall, Long buildingId) { if (buildingId != null) hall.setBuilding(findBuilding(buildingId)); }
    private Building findBuilding(Long id) { return buildingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Будівлю з id " + id + " не знайдено")); }
    private Equipment findEquipment(Long id) { return equipmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Обладнання з id " + id + " не знайдено")); }
}
