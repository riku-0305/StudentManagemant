package raiseTech.studentManagement.Data;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

}
