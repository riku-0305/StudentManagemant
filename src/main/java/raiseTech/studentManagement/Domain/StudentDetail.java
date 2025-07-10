package raiseTech.studentManagement.Domain;


import java.util.List;
import lombok.Getter;
import lombok.Setter;
import raiseTech.studentManagement.Data.Student;
import raiseTech.studentManagement.Data.StudentCourse;

@Getter
@Setter
public class StudentDetail {

  private Student student;
  private List<StudentCourse> studentCourses;

}
