// Import necessary modules
import { Component, OnInit } from '@angular/core';
import { CourseService } from '../course.service';

@Component({
  selector: 'app-courses',
  templateUrl: './courses.component.html',
  styleUrls: ['./courses.component.css']
})


export class CoursesComponent implements OnInit {
  courses: any[] = [];
  courseObject: any = {};
  showNewCourseForm: boolean = false;
  selectedCourse: any = null;

  constructor(private courseService: CourseService) { }

  ngOnInit(): void {
    this.getAllCourses();
  }

  toggleNewCourseForm(): void {
    this.showNewCourseForm = !this.showNewCourseForm;
    this.selectedCourse = null;
    this.clearForm();
  }

  getAllCourses(): void {
    this.courseService.getAllCourses()
      .subscribe(
        (data) => {
          this.courses = data;
        },
        (error) => {
          console.error('Error fetching courses:', error);
        }
      );
  }

  addNewCourse(): void {
    this.courseService.addNewCourse(this.courseObject)
      .subscribe(
        (response) => {
          console.log('Course added successfully:', response);
          this.getAllCourses();
          this.toggleNewCourseForm();
        },
        (error) => {
          console.error('Error adding new course:', error);
        }
      );
  }

  updateExistingCourse(): void {
    console.log('Update method called');

    if (this.selectedCourse) {
        console.log('Updating course:', this.selectedCourse);

        const updatedCourse = {
            title: this.courseObject.title,  // Use courseObject for title
            price: this.courseObject.price,  // Use courseObject for price
            image: this.courseObject.image
        };

        this.courseService.updateCourse(updatedCourse, this.selectedCourse.id)
            .subscribe(
                (response) => {
                    console.log('Course updated successfully:', response);
                    this.getAllCourses();
                    this.showNewCourseForm = false;
                    this.clearForm();
                    this.selectedCourse = null;
                },
                (error) => {
                    console.error('Error updating course:', error);
                }
            );
    }
}




  updateCourse(course: any): void {
    this.selectedCourse = { ...course };
    this.showNewCourseForm = true;
  }

  private clearForm(): void {
    this.courseObject = {};
  }

  deleteCourse(id: number): void {
    this.courseService.deleteCourse(id)
      .subscribe(
        () => {
          console.log('Course deleted successfully');
          this.getAllCourses();
        },
        (error) => {
          console.error('Error deleting course:', error);
        }
      );
  }

  getFormHeader(): string {
    return this.selectedCourse ? 'Update Course' : 'Add new Course';
  }

  getButtonLabel(): string {
    return this.selectedCourse ? 'Update' : 'Add new';
  }

  onImageChange(event: any): void {
    const fileInput = event.target;

    if (fileInput.files && fileInput.files[0]) {
      const reader = new FileReader();

      reader.onload = (e: any) => {
        const previewImage = document.getElementById('previewImage');

        if (previewImage) {
          previewImage.setAttribute('src', e.target.result);
        }

        this.courseObject.image = e.target.result;
      };

      reader.readAsDataURL(fileInput.files[0]);
    }
  }
}