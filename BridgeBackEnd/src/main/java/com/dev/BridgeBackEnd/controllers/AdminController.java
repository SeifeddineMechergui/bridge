package com.dev.BridgeBackEnd.controllers;

import com.dev.BridgeBackEnd.models.Course;
import com.dev.BridgeBackEnd.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/courses")
public class AdminController {

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping
    public String listCourses(Model model) {
        model.addAttribute("courses", courseRepository.findAll());
        return "admin/courses/list";
    }

    @GetMapping("/create")
    public String createCourseForm(Model model) {
        model.addAttribute("course", new Course());
        return "admin/courses/create";
    }

    @PostMapping("/create")
    public String createCourse(@ModelAttribute Course course) {
        courseRepository.save(course);
        return "redirect:/admin/courses";
    }

    @GetMapping("/edit/{id}")
    public String editCourseForm(@PathVariable Long id, Model model) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid course ID"));
        model.addAttribute("course", course);
        return "admin/courses/edit";
    }

    @PostMapping("/edit/{id}")
    public String editCourse(@PathVariable Long id, @ModelAttribute Course updatedCourse) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid course ID"));
        course.setTitle(updatedCourse.getTitle());
        course.setPrice(updatedCourse.getPrice());
        course.setImageUrl(updatedCourse.getImageUrl());
        courseRepository.save(course);
        return "redirect:/admin/courses";
    }

    @GetMapping("/delete/{id}")
    public String deleteCourse(@PathVariable Long id) {
        courseRepository.deleteById(id);
        return "redirect:/admin/courses";
    }
}
