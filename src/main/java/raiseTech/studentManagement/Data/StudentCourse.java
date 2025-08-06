package raiseTech.studentManagement.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "受講生コース情報")
@Getter
@Setter
@NoArgsConstructor
public class StudentCourse {

  @Min(value = 1, message = "コースIDは1以上を入力してください")
  private Long coursesId;

  @Min(value = 1, message = "生徒IDは1以上を入力してください")
  private Long studentsId;

  @NotBlank(message = "コース名の登録は必須です")
  @Size(max = 30, message = "コース名は30字以内で入力してください")
  private String coursesName;

  private LocalDate startDate;
  private LocalDate expectedEndDate;

  public StudentCourse(Long coursesId, Long studentsId, String coursesName) {
    this.coursesId = coursesId;
    this.studentsId = studentsId;
    this.coursesName = coursesName;
  }

}
