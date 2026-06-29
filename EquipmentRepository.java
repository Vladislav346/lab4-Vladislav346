package com.example.lab4.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "halls")
public class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer capacity;
    private Integer floor;

    @ManyToOne
    @JoinColumn(name = "building_id")
    @JsonIgnoreProperties({"halls", "events"})
    private Building building;

    @OneToMany(mappedBy = "hall", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"hall", "organizer", "building"})
    private List<Event> events = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "hall_equipment",
            joinColumns = @JoinColumn(name = "hall_id"),
            inverseJoinColumns = @JoinColumn(name = "equipment_id")
    )
    @JsonIgnoreProperties("halls")
    private Set<Equipment> equipment = new HashSet<>();

    public Hall() {}

    public Hall(Long id, String name, Integer capacity, Integer floor) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.floor = floor;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }
    public Integer getFloor() { return floor; }
    public void setFloor(Integer floor) { this.floor = floor; }
    public Building getBuilding() { return building; }
    public void setBuilding(Building building) { this.building = building; }
    public List<Event> getEvents() { return events; }
    public void setEvents(List<Event> events) { this.events = events; }
    public Set<Equipment> getEquipment() { return equipment; }
    public void setEquipment(Set<Equipment> equipment) { this.equipment = equipment; }
}
