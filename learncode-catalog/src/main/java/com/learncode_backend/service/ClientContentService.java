package com.learncode_backend.service;

import com.learncode_backend.client.AuthClient;
import com.learncode_backend.dto.FileDTO;
import com.learncode_backend.dto.ModuleDTO;
import com.learncode_backend.model.CourseModule;
import com.learncode_backend.model.ModuleFile;
import com.learncode_backend.model.StudentProgress;
import com.learncode_backend.repository.CourseModuleRepository;
import com.learncode_backend.repository.ModuleFileRepository;
import com.learncode_backend.repository.StudentProgressRepository;
import com.learncode_backend.utils.ModeloNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ClientContentService {

    private final CourseModuleRepository moduleRepo;
    private final StudentProgressRepository progressRepo;
    private final ModuleFileRepository fileRepo;
    private final AuthClient authClient;

    public ClientContentService(CourseModuleRepository moduleRepo, 
                                StudentProgressRepository progressRepo, 
                                ModuleFileRepository fileRepo,
                                AuthClient authClient) {
        this.moduleRepo = moduleRepo;
        this.progressRepo = progressRepo;
        this.fileRepo = fileRepo;
        this.authClient = authClient;
    }

    public List<ModuleDTO> getModulesForStudent(UUID courseId) {
        return moduleRepo.findByCourseIdOrderByModuleOrderAsc(courseId).stream()
            .map(m -> new ModuleDTO(
                m.getId(),
                m.getModuleOrder(),
                m.getTitle(),
                m.getFiles().stream()
                    .map(f -> new FileDTO(f.getId(), f.getFileName(), f.getMimeType()))
                    .collect(Collectors.toList())
            ))
            .collect(Collectors.toList());
    }

    public List<UUID> getCompletedModuleIds(String email, UUID courseId) {
        // SE AGREGA .getData() PARA EXTRAER EL UUID DEL APIRESPONSE
        UUID userId = authClient.getUserIdByEmail(email).getData();
        return progressRepo.findByUserIdAndCourseId(userId, courseId).stream()
                .map(StudentProgress::getModuleId)
                .collect(Collectors.toList());
    }

    @Transactional
    public void markModuleAsCompleted(String email, UUID moduleId) {
        // SE AGREGA .getData() PARA EXTRAER EL UUID DEL APIRESPONSE
        UUID userId = authClient.getUserIdByEmail(email).getData();
        
        if (progressRepo.existsByUserIdAndModuleId(userId, moduleId)) {
            return;
        }

        CourseModule module = moduleRepo.findById(moduleId)
            .orElseThrow(() -> new ModeloNotFoundException("Módulo no encontrado"));

        StudentProgress progress = new StudentProgress();
        progress.setUserId(userId);
        progress.setModuleId(moduleId);
        progress.setCourseId(module.getCourseId());

        progressRepo.save(progress);
    }
    
    public Map<String, String> getFileContent(UUID fileId) {
        ModuleFile file = fileRepo.findById(fileId)
            .orElseThrow(() -> new ModeloNotFoundException("Archivo no encontrado"));
            
        return Map.of(
            "fileName", file.getFileName(),
            "mimeType", file.getMimeType(),
            "base64", file.getBase64Content()
        );
    }
}