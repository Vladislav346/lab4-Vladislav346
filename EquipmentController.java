package com.example.lab4.service;

import com.example.lab4.dto.request.OrganizerRequestDto;
import com.example.lab4.dto.response.EventResponseDto;
import com.example.lab4.dto.response.OrganizerResponseDto;
import com.example.lab4.exception.ResourceNotFoundException;
import com.example.lab4.mapper.DtoMapper;
import com.example.lab4.model.Organizer;
import com.example.lab4.repository.EventRepository;
import com.example.lab4.repository.OrganizerRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrganizerService {
    private final OrganizerRepository organizerRepository;
    private final EventRepository eventRepository;
    public OrganizerService(OrganizerRepository organizerRepository, EventRepository eventRepository) { this.organizerRepository = organizerRepository; this.eventRepository = eventRepository; }
    public OrganizerResponseDto create(OrganizerRequestDto dto) { return DtoMapper.toOrganizerDto(organizerRepository.save(DtoMapper.toOrganizer(dto))); }
    public List<OrganizerResponseDto> getAll() { return organizerRepository.findAll().stream().map(DtoMapper::toOrganizerDto).toList(); }
    public Organizer getEntityById(Long id) { return organizerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Організатора з id " + id + " не знайдено")); }
    public OrganizerResponseDto getById(Long id) { return DtoMapper.toOrganizerDto(getEntityById(id)); }
    public OrganizerResponseDto update(Long id, OrganizerRequestDto dto) { Organizer existing = getEntityById(id); existing.setFirstName(dto.getFirstName()); existing.setLastName(dto.getLastName()); existing.setEmail(dto.getEmail()); existing.setPhone(dto.getPhone()); return DtoMapper.toOrganizerDto(organizerRepository.save(existing)); }
    public void delete(Long id) { Organizer existing = getEntityById(id); organizerRepository.delete(existing); }
    public List<EventResponseDto> getEvents(Long id) { getEntityById(id); return eventRepository.findByOrganizerId(id).stream().map(DtoMapper::toEventDto).toList(); }
    public List<OrganizerResponseDto> search(String query) { return organizerRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(query, query, query).stream().map(DtoMapper::toOrganizerDto).toList(); }
    public long count() { return organizerRepository.count(); }
}
