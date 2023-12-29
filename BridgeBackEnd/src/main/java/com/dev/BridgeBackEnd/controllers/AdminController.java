package com.dev.BridgeBackEnd.controllers;

import com.dev.BridgeBackEnd.models.Course;
import com.dev.BridgeBackEnd.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;
@RestController
@RequestMapping("/admin/courses")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4201"})

public class AdminController {

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping
    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

    @GetMapping("/{id}")
    public Course getCourseById(@PathVariable long id) {
        return courseRepository.findById(id).orElse(null);
    }

    @PostMapping
    public ResponseEntity<Course> saveCourse(@RequestBody Course newCourse) {
        try {
            String imagePath = "images" + File.separator + newCourse.getImage();
            Course savedCourse = courseRepository.save(newCourse);
            System.out.println("Course saved successfully: " + savedCourse);
            return ResponseEntity.ok(savedCourse);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable Long id) {
        courseRepository.deleteById(id);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@RequestBody Course updatedCourse, @PathVariable long id) {
        Course existingCourse = courseRepository.findById(id).orElse(null);
        if (existingCourse != null) {
            existingCourse.setTitle(updatedCourse.getTitle());
            existingCourse.setPrice(updatedCourse.getPrice());
            existingCourse.setImage(updatedCourse.getImage());
            Course savedCourse = courseRepository.save(existingCourse);
            System.out.println("Course updated successfully: " + savedCourse);
            return ResponseEntity.ok(savedCourse);
        }
        return ResponseEntity.notFound().build();
    }
}
