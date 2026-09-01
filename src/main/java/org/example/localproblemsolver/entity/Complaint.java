package org.example.localproblemsolver.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "complaints")
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "incident_id", nullable = false)
    private Incident incident;

    @Column(nullable = false, length = 5000)
    private String description;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    protected Complaint() {
    }

    public Complaint(
            User user,
            Incident incident,
            String description
    ) {
        this.user = user;
        this.incident = incident;
        this.description = description;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Incident getIncident() {
        return incident;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setIncident(Incident incident) {
        this.incident = incident;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
