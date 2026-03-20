package com.learncode_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "module_files", schema = "catalog_schema")
public class ModuleFile {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id", nullable = false)
    @JsonIgnore // Evita bucles infinitos al serializar
    private CourseModule module;

    @Column(name = "file_name", length = 200)
    private String fileName;

    // Aquí guardaremos el Base64. Usamos 'text' para soportar cadenas largas.
    @Column(name = "file_url", columnDefinition = "TEXT", nullable = false)
    private String base64Content; 

    @Column(name = "mime_type", length = 50)
    private String mimeType;

    @Column(name = "uploaded_at")
    private LocalDateTime uploadedAt;

    @PrePersist
    public void prePersist() {
        this.uploadedAt = LocalDateTime.now();
    }

    // Getters y Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public CourseModule getModule() { return module; }
    public void setModule(CourseModule module) { this.module = module; }
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    public String getBase64Content() { return base64Content; }
    public void setBase64Content(String base64Content) { this.base64Content = base64Content; }
    public String getMimeType() { return mimeType; }
    public void setMimeType(String mimeType) { this.mimeType = mimeType; }
}