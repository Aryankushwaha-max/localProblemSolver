package org.example.localproblemsolver.repository;


import org.example.localproblemsolver.entity.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComplaintRepository
        extends JpaRepository<Complaint, Long> {
    List<Complaint> findByUserId(Long userId);
}