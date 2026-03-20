package com.learncode_backend.repository;

import com.learncode_backend.model.ModuleFile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ModuleFileRepository extends JpaRepository<ModuleFile, UUID> {
}