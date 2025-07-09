package raiseTech.studentManagement.Data;

import java.time.LocalDateTime;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentCourse {

  private Long coursesId;
  private Long studentId;
  private String coursesName;
  private Date startData;
  private Date expected_end_date;

}
