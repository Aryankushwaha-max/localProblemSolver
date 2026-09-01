package org.example.localproblemsolver.repository;

import org.example.localproblemsolver.entity.Incident;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncidentRepository
        extends JpaRepository<Incident, Long> {
}