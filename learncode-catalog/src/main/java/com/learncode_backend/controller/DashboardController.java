package com.learncode_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learncode_backend.repository.CourseRepository;

@RestController
@RequestMapping("/api/catalog")
public class DashboardController {

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping("/count")
    public long countCourses() {
        return courseRepository.countPublished();
    }
}
