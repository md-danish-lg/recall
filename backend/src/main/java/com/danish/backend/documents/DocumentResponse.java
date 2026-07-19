package com.danish.backend.documents;

import java.time.LocalDateTime;
import java.util.UUID;


public class DocumentResponse {

    private UUID id;

    private String filename;

    private LocalDateTime uploadedAt;

    private DocumentStatus status;


    public DocumentResponse(Document document){
        this.id = document.getId();
        this.filename = document.getFilename();
        this.status = document.getStatus();
        this.uploadedAt = document.getUploadedAt();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public DocumentStatus getStatus() {
        return status;
    }

    public void setStatus(DocumentStatus status) {
        this.status = status;
    }
}
