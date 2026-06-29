package com.example.lab4.repository;

import com.example.lab4.model.Organizer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrganizerRepository extends JpaRepository<Organizer, Long> {
    List<Organizer> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String firstName, String lastName, String email);
}
