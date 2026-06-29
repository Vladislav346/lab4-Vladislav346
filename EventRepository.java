package com.example.lab4.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "buildings")
public class Building {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String city;
    private String address;

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"building", "events", "equipment"})
    private List<Hall> halls = new ArrayList<>();

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"building", "organizer", "hall"})
    private List<Event> events = new ArrayList<>();

    public Building() {}

    public Building(Long id, String name, String city, String address) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.address = address;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public List<Hall> getHalls() { return halls; }
    public void setHalls(List<Hall> halls) { this.halls = halls; }
    public List<Event> getEvents() { return events; }
    public void setEvents(List<Event> events) { this.events = events; }
}
