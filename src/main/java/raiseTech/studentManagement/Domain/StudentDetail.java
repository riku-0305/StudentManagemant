package raiseTech.studentManagement.Domain;


import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raiseTech.studentManagement.Data.Student;
import raiseTech.studentManagement.Data.StudentCourse;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDetail {

  private Student student;
  private List<StudentCourse> studentCourseList;

}
