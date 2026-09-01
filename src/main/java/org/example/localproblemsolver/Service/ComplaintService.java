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
    public ComplaintResponse register(RegisterComplaintDTO registerComplaintDTO){

        {
            //checkforValidation()

            ComplaintResponse analysis =
                    aiComplaintService.analyzeComplaint(
                            registerComplaintDTO.getProblem()
                    );

            return analysis;

        }



    }
    public Complaint2Dto getComplaint(Long id){
        Optional<Complaint2> complaint2 = complaint2Repository.findById(id);
        Complaint2 res = complaint2.get();

        Complaint2Dto complaint2Dto = new
                Complaint2Dto();
        complaint2Dto.setCategory(res.getCategory());
        complaint2Dto.setId(res.getId());
        complaint2Dto.setDate(res.getDate());
        complaint2Dto.setDepartment(res.getDepartment());
        complaint2Dto.setDescription(res.getDescription());
        complaint2Dto.setLocation(res.getLocation());
        complaint2Dto.setPriority(res.getPriority());
        complaint2Dto.setTitle(res.getTitle());

        return complaint2Dto;
    }

        @Transactional
        public ComplaintResponse createComplaint(
                RegisterComplaintDTO request
        ) {

            // 1. Get authenticated user
            User user = currentUserService.getCurrentUser();

            // 2. Analyze complaint using AI
            ComplaintResponse aiResponse =
                    aiComplaintService.analyzeComplaint(
                            request.getProblem()
                    );

            // 3. Reject invalid complaint
            if (!aiResponse.isValid()) {
                throw new IllegalArgumentException(
                        "The submitted complaint is not valid"
                );
            }

            // 4. Find department
            Department department =
                    departmentRepository
                            .findByNameIgnoreCase(
                                    aiResponse.getDepartment()
                            )
                            .orElseThrow(() ->
                                    new IllegalArgumentException(
                                            "Department not found: "
                                                    + aiResponse.getDepartment()
                                    )
                            );

            // 5. Create incident
            Incident incident = new Incident(
                    aiResponse.getDescription(),
                    request.getProblem(),
                    aiResponse.getSeverity(),
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

            // 8. Save complaint



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

