package com.example.lab4.service;

import com.example.lab4.dto.request.EquipmentRequestDto;
import com.example.lab4.dto.response.EquipmentResponseDto;
import com.example.lab4.exception.ResourceNotFoundException;
import com.example.lab4.mapper.DtoMapper;
import com.example.lab4.model.Equipment;
import com.example.lab4.repository.EquipmentRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EquipmentService {
    private final EquipmentRepository equipmentRepository;
    public EquipmentService(EquipmentRepository equipmentRepository) { this.equipmentRepository = equipmentRepository; }
    public EquipmentResponseDto create(EquipmentRequestDto dto) { return DtoMapper.toEquipmentDto(equipmentRepository.save(DtoMapper.toEquipment(dto))); }
    public List<EquipmentResponseDto> getAll() { return equipmentRepository.findAll().stream().map(DtoMapper::toEquipmentDto).toList(); }
    public Equipment getEntityById(Long id) { return equipmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Обладнання з id " + id + " не знайдено")); }
    public EquipmentResponseDto getById(Long id) { return DtoMapper.toEquipmentDto(getEntityById(id)); }
    public void delete(Long id) { Equipment existing = getEntityById(id); equipmentRepository.delete(existing); }
}
