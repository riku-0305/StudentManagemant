package raiseTech.studentManagement.Controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import raiseTech.studentManagement.Data.Student;
import raiseTech.studentManagement.Data.StudentCourse;
import raiseTech.studentManagement.Domain.StudentDetail;
import raiseTech.studentManagement.Service.StudentService;

@RestController
public class StudentController {

  private StudentService service;

  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList() {
    List<Student> student = service.searchStudentList();
    List<StudentCourse> studentCourse = service.searchStudentCourseList();

    List<StudentDetail> studentDetails = new ArrayList<>();
    for (Student students : student) {
      StudentDetail studentDetail = new StudentDetail();
      studentDetail.setStudent(students);

      List<StudentCourse> convertStudentCourses = new ArrayList<>();
      for (StudentCourse studentCourses : studentCourse) {
        if (students.getId().equals(studentCourses.getStudentsId())) {
          convertStudentCourses.add(studentCourses);
        }
      }
      studentDetail.setStudentCourses(convertStudentCourses);
      studentDetails.add(studentDetail);
    }
    return studentDetails;
  }

  @GetMapping("/studentCourseList")
  public List<StudentCourse> getStudentCourseList() {
    return service.searchStudentCourseList();
  }
}