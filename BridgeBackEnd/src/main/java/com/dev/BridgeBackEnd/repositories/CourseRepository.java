package com.dev.BridgeBackEnd.repositories;

import com.dev.BridgeBackEnd.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
