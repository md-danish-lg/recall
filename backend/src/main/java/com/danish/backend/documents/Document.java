package com.danish.backend.documents;

import com.danish.backend.user.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "document")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String filename;

    @Column(nullable = false)
    private String filePath;

    private LocalDateTime uploadedAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DocumentStatus status;


    private String chromaCollectionRef;


    @PrePersist
    void onCreate(){
        this.uploadedAt = LocalDateTime.now();
        this.status = DocumentStatus.PENDING;
    }

    public Document() {
    }

    public Document(UUID id, String filename, String filePath, LocalDateTime uploadedAt, User user, DocumentStatus status, String chromaCollectionRef) {
        this.id = id;
        this.filename = filename;
        this.filePath = filePath;
        this.uploadedAt = uploadedAt;
        this.user = user;
        this.status = status;
        this.chromaCollectionRef = chromaCollectionRef;
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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public DocumentStatus getStatus() {
        return status;
    }

    public void setStatus(DocumentStatus status) {
        this.status = status;
    }

    public String getChromaCollectionRef() {
        return chromaCollectionRef;
    }

    public void setChromaCollectionRef(String chromaCollectionRef) {
        this.chromaCollectionRef = chromaCollectionRef;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Document document = (Document) o;
        return Objects.equals(id, document.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
