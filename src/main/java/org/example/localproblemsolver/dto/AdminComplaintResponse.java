package org.example.localproblemsolver.dto;

import java.time.LocalDateTime;

public class AdminComplaintResponse {

    private Long incidentId;
    private String description;
    private String severity;

    public Long getIncidentId() {
        return incidentId;
    }

    public void setIncidentId(Long incidentId) {
        this.incidentId = incidentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public AdminComplaintResponse(Long incidentId, String description, String severity, String status, String title, int totalComplaint) {
        this.incidentId = incidentId;
        this.description = description;
        this.severity = severity;
        this.status = status;
        this.title = title;
        this.totalComplaint = totalComplaint;
    }
    public AdminComplaintResponse(){

    }


    public void setTitle(String title) {
        this.title = title;
    }



    private String status;
    private String title;

    public int getTotalComplaint() {
        return totalComplaint;
    }

    public void setTotalComplaint(int totalComplaint) {
        this.totalComplaint = totalComplaint;
    }

    private int totalComplaint;
}
