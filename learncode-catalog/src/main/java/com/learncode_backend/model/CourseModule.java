package com.learncode_backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "course_modules", schema = "catalog_schema")
public class CourseModule {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "course_id", nullable = false)
    private UUID courseId;

    @Column(name = "module_order", nullable = false)
    private Integer moduleOrder;

    @Column(nullable = false)
    private String title;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ModuleFile> files;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getCourseId() { return courseId; }
    public void setCourseId(UUID courseId) { this.courseId = courseId; }
    public Integer getModuleOrder() { return moduleOrder; }
    public void setModuleOrder(Integer moduleOrder) { this.moduleOrder = moduleOrder; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public List<ModuleFile> getFiles() { return files; }
    public void setFiles(List<ModuleFile> files) { this.files = files; }
}