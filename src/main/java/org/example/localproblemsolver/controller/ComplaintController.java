package org.example.localproblemsolver.controller;

import jakarta.validation.Valid;
import org.example.localproblemsolver.Service.ComplaintService;
import org.example.localproblemsolver.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/complaint")
@CrossOrigin("*")
public class ComplaintController {
    private final ComplaintService complaintService;

        public ComplaintController(ComplaintService complaintService)
        {
            this.complaintService = complaintService;
        }

        @PostMapping("/registerComplaint")
        public ResponseEntity<ComplaintResponse> Airegister(
                 @RequestBody RegisterComplaintDTO request
        ) {


            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(
                            complaintService.createComplaint(request)
                    );
        }

//    @GetMapping("/getComplaint2/{id}")
//    public ResponseEntity<Complaint2Dto> register(
//            @Valid @PathVariable Long id
//    ) {
//
//       Complaint2Dto complaint2Dto = complaintService.getComplaint(id);
//       return ResponseEntity.ok(complaint2Dto);
//
//
//    }

    @GetMapping("/my")
    public ResponseEntity<List<ComplaintResponse>> getMyComplaints() {

        return ResponseEntity.ok(
                complaintService.getMyComplaints()
        );
    }



}
