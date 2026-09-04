package org.example.localproblemsolver.Service;



import org.example.localproblemsolver.dto.AdminComplaintResponse;
import org.example.localproblemsolver.dto.Complaint2Dto;
import org.example.localproblemsolver.dto.ComplaintResponse;
import org.example.localproblemsolver.entity.Complaint;
import org.example.localproblemsolver.entity.Incident;
import org.springframework.stereotype.Component;
@Component
public class ComplaintMapper {
    public ComplaintResponse toResponses(Complaint complaint ) {
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
    public Complaint2Dto toResponse(Complaint complaint ) {
        Incident incident = complaint.getIncident();
        return new Complaint2Dto(
                complaint.getId(),
                incident.getId(),
                complaint.getDescription(),
                complaint.getCreatedAt(),
                incident.getDepartment().getName(),
                incident.getSeverity(),
                incident.getStatus().name() );

    }
    public AdminComplaintResponse toAdminResponse(Incident incident) {

        return new AdminComplaintResponse(

                incident.getId(),
                incident.getDescription(),
                incident.getSeverity().toString(),
                incident.getStatus().toString(),
                incident.getTitle(),
                1
        );


    }

}