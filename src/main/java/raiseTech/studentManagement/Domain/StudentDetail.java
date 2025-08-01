package raiseTech.studentManagement.Domain;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raiseTech.studentManagement.Data.Student;
import raiseTech.studentManagement.Data.StudentCourse;

@Schema(description = "受講生詳細")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDetail {

  @Valid
  private Student student;

  @Valid
  private List<StudentCourse> studentCourseList;

}
