package org.example.localproblemsolver.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletOutputStream;
import org.example.localproblemsolver.dto.*;
import org.example.localproblemsolver.entity.*;
import org.example.localproblemsolver.repository.Complaint2Repository;
import org.example.localproblemsolver.repository.ComplaintRepository;
import org.example.localproblemsolver.repository.DepartmentRepository;
import org.example.localproblemsolver.repository.IncidentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class ComplaintService {
    private final  ComplaintRepository complaintRepository;
    private final CurrentUserService currentUserService;
    private final IncidentRepository incidentRepository;
    private  final DepartmentRepository departmentRepository;
    private final  Complaint2Repository complaint2Repository;
    private final AiComplaintService aiComplaintService;

    private final ComplaintMapper complaintMapper;

    public ComplaintService(ComplaintRepository complaintRepository,
                            IncidentRepository incidentRepository,
                            DepartmentRepository departmentRepository,
                            AiComplaintService aiComplaintService,
                            Complaint2Repository complaint2Repository,
                            CurrentUserService currentUserService,
                            ComplaintMapper complaintMapper
                            ){
        this.complaintRepository = complaintRepository;
        this.departmentRepository = departmentRepository;
        this.incidentRepository = incidentRepository;
        this.aiComplaintService = aiComplaintService;
        this.complaint2Repository = complaint2Repository;
        this.currentUserService = currentUserService;
        this.complaintMapper = complaintMapper;
    }


        @Transactional
        public ComplaintResponse createComplaint(
                RegisterComplaintDTO request
        ) {

            // 1. Get authenticated user
            User user = currentUserService.getCurrentUser();

            // 2. Analyze complaint using AI
            AiApiResponse aiApiResponse = aiComplaintService.analyzeComplaint(
                    request.getProblem()
            );



            // 3. Reject invalid complaint
            if (!aiApiResponse.isValid()) {
                throw new IllegalArgumentException(
                        "The submitted complaint is not valid"
                );
            }

            // 4. Find department
            Department department =
                    departmentRepository
                            .findByNameIgnoreCase(
                                    aiApiResponse.getDepartment()
                            )
                            .orElseThrow(() ->
                                    new IllegalArgumentException(
                                            "Department not found: "
                                                    + aiApiResponse.getDepartment()
                                    )
                            );

            // 5. Create incident
            Incident incident = new Incident(
                    aiApiResponse.getProblem(),
                    request.getProblem(),
                    aiApiResponse.getSeverity(),
                    IncidentStatus.REPORTED,
                    department
            );


            // 6. Save incident
            incident = incidentRepository.save(incident);

            // 7. Create complaint
            Complaint complaint = new Complaint(
                    user,
                    incident,
                    request.getProblem()
            );





            Complaint savedComplaint =
                    complaintRepository.save(complaint);

            return complaintMapper.toResponse(savedComplaint);








        }
    @Transactional(readOnly = true)
    public List<ComplaintResponse> getMyComplaints() {
        User user = currentUserService.getCurrentUser();

        return complaintRepository
                .findByUserId(user.getId())
                .stream()
                .map(complaintMapper::toResponse)
                .toList();


    }


}

