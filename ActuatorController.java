package com.example.lab4.service;

import com.example.lab4.dto.request.EventRequestDto;
import com.example.lab4.dto.response.EventResponseDto;
import com.example.lab4.exception.BadRequestException;
import com.example.lab4.exception.ResourceNotFoundException;
import com.example.lab4.mapper.DtoMapper;
import com.example.lab4.model.*;
import com.example.lab4.repository.*;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EventService {
    private final EventRepository eventRepository; private final OrganizerRepository organizerRepository; private final HallRepository hallRepository; private final BuildingRepository buildingRepository;
    public EventService(EventRepository eventRepository, OrganizerRepository organizerRepository, HallRepository hallRepository, BuildingRepository buildingRepository) { this.eventRepository = eventRepository; this.organizerRepository = organizerRepository; this.hallRepository = hallRepository; this.buildingRepository = buildingRepository; }
    public EventResponseDto create(EventRequestDto dto) { validateTime(dto); Event event = DtoMapper.toEvent(dto); applyRelations(event, dto); return DtoMapper.toEventDto(eventRepository.save(event)); }
    public List<EventResponseDto> getAll() { return eventRepository.findAll().stream().map(DtoMapper::toEventDto).toList(); }
    public Event getEntityById(Long id) { return eventRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Подію з id " + id + " не знайдено")); }
    public EventResponseDto getById(Long id) { return DtoMapper.toEventDto(getEntityById(id)); }
    public EventResponseDto update(Long id, EventRequestDto dto) { validateTime(dto); Event existing = getEntityById(id); existing.setTitle(dto.getTitle()); existing.setStartTime(dto.getStartTime()); existing.setEndTime(dto.getEndTime()); existing.setStatus(dto.getStatus()); applyRelations(existing, dto); return DtoMapper.toEventDto(eventRepository.save(existing)); }
    public void delete(Long id) { Event existing = getEntityById(id); existing.setStatus(EventStatus.CANCELLED); eventRepository.save(existing); }
    public List<EventResponseDto> getActive() { return eventRepository.findByStatus(EventStatus.ACTIVE).stream().map(DtoMapper::toEventDto).toList(); }
    public List<EventResponseDto> search(String query) { return eventRepository.findByTitleContainingIgnoreCase(query).stream().map(DtoMapper::toEventDto).toList(); }
    public long count() { return eventRepository.count(); }
    public EventResponseDto assignOrganizer(Long eventId, Long organizerId) { Event event = getEntityById(eventId); event.setOrganizer(findOrganizer(organizerId)); return DtoMapper.toEventDto(eventRepository.save(event)); }
    public EventResponseDto assignHall(Long eventId, Long hallId) { Event event = getEntityById(eventId); Hall hall = findHall(hallId); event.setHall(hall); if (hall.getBuilding()!=null) event.setBuilding(hall.getBuilding()); return DtoMapper.toEventDto(eventRepository.save(event)); }
    public EventResponseDto assignBuilding(Long eventId, Long buildingId) { Event event = getEntityById(eventId); event.setBuilding(findBuilding(buildingId)); return DtoMapper.toEventDto(eventRepository.save(event)); }
    private void applyRelations(Event event, EventRequestDto dto) { if (dto.getOrganizerId()!=null) event.setOrganizer(findOrganizer(dto.getOrganizerId())); if (dto.getHallId()!=null) { Hall hall = findHall(dto.getHallId()); event.setHall(hall); if (hall.getBuilding()!=null) event.setBuilding(hall.getBuilding()); } if (dto.getBuildingId()!=null) event.setBuilding(findBuilding(dto.getBuildingId())); }
    private void validateTime(EventRequestDto dto) { if (dto.getStartTime()!=null && dto.getEndTime()!=null && !dto.getEndTime().isAfter(dto.getStartTime())) throw new BadRequestException("Час завершення події має бути пізніше часу початку"); }
    private Organizer findOrganizer(Long id) { return organizerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Організатора з id " + id + " не знайдено")); }
    private Hall findHall(Long id) { return hallRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Зал з id " + id + " не знайдено")); }
    private Building findBuilding(Long id) { return buildingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Будівлю з id " + id + " не знайдено")); }
}
