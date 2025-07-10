package raiseTech.studentManagement.Controller.Converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import raiseTech.studentManagement.Data.Student;
import raiseTech.studentManagement.Data.StudentCourse;
import raiseTech.studentManagement.Domain.StudentDetail;

@Component
public class StudentConverter {


  public List<StudentDetail> convertStudentDetails(List<Student> student,
      List<StudentCourse> studentCourse) {

    List<StudentDetail> studentDetails = new ArrayList<>();
    student.forEach(students -> {
      StudentDetail studentDetail = new StudentDetail();
      studentDetail.setStudent(students);
      List<StudentCourse> convertStudentCourses = studentCourse.stream()
          .filter(studentCourses -> students.getId().equals(studentCourses.getStudentsId()))
          .collect(Collectors.toList());

      studentDetail.setStudentCourses(convertStudentCourses);
      studentDetails.add(studentDetail);
    });
    return studentDetails;

  }
}
