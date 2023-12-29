// course.component.ts
import { Component, OnInit } from '@angular/core';
import { CourseService } from '../course.service';

@Component({
  selector: 'app-course',
  templateUrl: './courses.component.html',
  styleUrls: ['./courses.component.css']
})
export class CourseComponent implements OnInit {
  courses: any[] = [];

  constructor(private courseService: CourseService) { }

  ngOnInit(): void {
    this.loadCourses();
  }

  loadCourses(): void {
    this.courseService.getCourses().subscribe(
      data => {
        this.courses = data;
      },
      error => {
        console.log(error);
      }
    );
  }

  // Implement methods to create, update, and delete courses
}
