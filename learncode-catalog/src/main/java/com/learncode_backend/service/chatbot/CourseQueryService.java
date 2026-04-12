package com.learncode_backend.service.chatbot;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class CourseQueryService {

    private final JdbcTemplate jdbcTemplate;

    public CourseQueryService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String, Object>> executeSafeQuery(String sql) {
        String normalized = sql.trim().toLowerCase();

        if (!normalized.startsWith("select") || !normalized.contains("catalog_schema.courses")) {
            throw new IllegalArgumentException("Solo SELECT en catalog_schema.courses");
        }
        if (normalized.contains("insert") || normalized.contains("update")
                || normalized.contains("delete") || normalized.contains("drop")) {
            throw new IllegalArgumentException("Operación no permitida");
        }

        return jdbcTemplate.queryForList(sql);
    }
}