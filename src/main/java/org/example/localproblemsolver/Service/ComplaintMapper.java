package org.example.localproblemsolver.Service;



import org.example.localproblemsolver.dto.ComplaintResponse;
import org.example.localproblemsolver.entity.Complaint;
import org.example.localproblemsolver.entity.Incident;
import org.springframework.stereotype.Component;
@Component
public class ComplaintMapper {
    public ComplaintResponse toResponse(Complaint complaint ) {
        Incident incident = complaint.getIncident();
        return new ComplaintResponse(
                complaint.getId(),
                incident.getId(),
                complaint.getDescription(),
                complaint.getCreatedAt(),
                incident.getDepartment().getName(),
                incident.getSeverity(),
                incident.getStatus().name() );

    }
}