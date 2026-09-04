package org.example.localproblemsolver.Service;

import org.example.localproblemsolver.dto.AdminComplaintResponse;
import org.example.localproblemsolver.dto.AdminPageDetails;
import org.example.localproblemsolver.dto.ComplaintResponse;
import org.example.localproblemsolver.entity.Incident;
import org.example.localproblemsolver.entity.User;
import org.example.localproblemsolver.repository.Complaint2Repository;
import org.example.localproblemsolver.repository.ComplaintRepository;
import org.example.localproblemsolver.repository.DepartmentRepository;
import org.example.localproblemsolver.repository.IncidentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminComplaintService {

    private final IncidentRepository incidentRepository;
    private  final DepartmentRepository departmentRepository;
    private final ComplaintMapper complaintMapper;

    public AdminComplaintService(
                                IncidentRepository incidentRepository,
                                DepartmentRepository departmentRepository,



                                ComplaintMapper complaintMapper
    ){

        this.departmentRepository = departmentRepository;
        this.incidentRepository = incidentRepository;


        this.complaintMapper = complaintMapper;
    }
    @Transactional(readOnly = true)
    public AdminPageDetails getAdminComplaints(Long depthId) {
        AdminPageDetails adminPageDetails = new AdminPageDetails();
        String department = "WATER";
        adminPageDetails.setDepartmentName(department);



        List<AdminComplaintResponse> adminComplaintResponse =   incidentRepository
                .findByDepartmentId(depthId)
                .stream()
                .map(incident -> complaintMapper.toAdminResponse(incident))
                .toList();
        adminPageDetails.setComplaintResponses(adminComplaintResponse);

        return adminPageDetails;


    }
}
