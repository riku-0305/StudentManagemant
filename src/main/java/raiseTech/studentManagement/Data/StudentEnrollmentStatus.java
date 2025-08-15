package raiseTech.studentManagement.Data;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StudentEnrollmentStatus {

  @Min(value = 1, message = "申し込み状況のIDは1以上を入力してください")
  private Long statusId;

  @Min(value = 1, message = "生徒IDは1以上を入力してください")
  private Long studentsId;

  @Min(value = 1, message = "コースIDは1以上を入力してください")
  private Long studentsCoursesId;

  @NotBlank(message = "申し込み状況の登録は必須です")
  @Size(max = 50, message = "50字以内で入力してください")
  private String status;

  public StudentEnrollmentStatus(Long statusId, Long studentsId, Long studentsCoursesId, String status) {
    this.statusId = statusId;
    this.studentsId = studentsId;
    this.studentsCoursesId = studentsCoursesId;
    this.status = status;
  }
}

