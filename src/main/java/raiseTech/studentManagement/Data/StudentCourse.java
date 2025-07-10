package raiseTech.studentManagement.Data;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentCourse {

  private Long coursesId;
  private Long studentsId;
  private String coursesName;
  private LocalDate startDate;
  private LocalDate expectedEndDate;

}
