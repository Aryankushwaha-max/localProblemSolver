package org.example.localproblemsolver.controller;

import jakarta.validation.Valid;
import org.example.localproblemsolver.Service.UserComplaintService;
import org.example.localproblemsolver.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/complaint")
@CrossOrigin("*")
public class ComplaintController {
    private final UserComplaintService userComplaintService;

        public ComplaintController(UserComplaintService userComplaintService)
        {
            this.userComplaintService = userComplaintService;
        }

        @PostMapping("/registerComplaint")
        public ResponseEntity<ComplaintResponse> Airegister(
                 @RequestBody RegisterComplaintDTO request
        ) {


            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(
                            userComplaintService.createComplaint(request)
                    );
        }

    @GetMapping("/my/{id}")
    public ResponseEntity<Complaint2Dto> register(
            @Valid @PathVariable Long id
    ) {


        return ResponseEntity.ok(
                userComplaintService.getMyComplaint(id)
        );


    }

    @GetMapping("/my")
    public ResponseEntity<List<ComplaintResponse>> getMyComplaints() {

        return ResponseEntity.ok(
                userComplaintService.getMyComplaints()
        );
    }



}
