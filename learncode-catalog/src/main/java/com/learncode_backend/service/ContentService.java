package com.learncode_backend.service;

import com.learncode_backend.dto.CreateFileDTO;
import com.learncode_backend.dto.FileDTO;
import com.learncode_backend.dto.ModuleDTO;
import com.learncode_backend.model.CourseModule;
import com.learncode_backend.model.ModuleFile;
import com.learncode_backend.repository.CourseModuleRepository;
import com.learncode_backend.repository.ModuleFileRepository;
import com.learncode_backend.utils.ModeloNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ContentService {

    private final CourseModuleRepository moduleRepo;
    private final ModuleFileRepository fileRepo;

    public ContentService(CourseModuleRepository moduleRepo, ModuleFileRepository fileRepo) {
        this.moduleRepo = moduleRepo;
        this.fileRepo = fileRepo;
    }

    public List<ModuleDTO> getModulesByCourse(UUID courseId) {
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

    @Transactional
    public CourseModule createModule(UUID courseId, String title, Integer order) {
        CourseModule module = new CourseModule();
        module.setCourseId(courseId);
        module.setTitle(title);
        module.setModuleOrder(order);
        return moduleRepo.save(module);
    }

    @Transactional
    public CourseModule updateModule(UUID moduleId, String newTitle) {
        CourseModule module = moduleRepo.findById(moduleId)
            .orElseThrow(() -> new ModeloNotFoundException("Modulo no encontrado"));
        module.setTitle(newTitle);
        return moduleRepo.save(module);
    }

    @Transactional
    public void deleteModule(UUID moduleId) {
        moduleRepo.deleteById(moduleId);
    }

    @Transactional
    public void uploadFile(CreateFileDTO dto) {
        CourseModule module = moduleRepo.findById(dto.moduleId())
            .orElseThrow(() -> new ModeloNotFoundException("Modulo no encontrado"));

        if (module.getFiles() != null && !module.getFiles().isEmpty()) {
            fileRepo.deleteAll(module.getFiles());
            module.getFiles().clear();
        }

        ModuleFile file = new ModuleFile();
        file.setModule(module);
        file.setFileName(dto.fileName());
        file.setBase64Content(dto.base64());
        file.setMimeType(dto.mimeType());

        fileRepo.save(file);
    }

    // AHORA DEVUELVE EL MAP DIRECTAMENTE, SIN RESPONSE_ENTITY
    public Map<String, String> getFileContent(UUID fileId) {
        ModuleFile file = fileRepo.findById(fileId)
            .orElseThrow(() -> new ModeloNotFoundException("Archivo no encontrado"));
            
        return Map.of(
            "fileName", file.getFileName(),
            "mimeType", file.getMimeType(),
            "base64", file.getBase64Content()
        );
    }

    @Transactional
    public void deleteFile(UUID fileId) {
        fileRepo.deleteById(fileId);
    }
}