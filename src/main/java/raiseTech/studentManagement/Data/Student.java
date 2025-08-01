package raiseTech.studentManagement.Data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生")
@Getter
@Setter

public class Student {

  @Min(value = 1, message = "IDは1以上の整数値を入力してください")
  private Long id;

  @NotBlank(message = "名前入力は必須です")
  @Size(min = 1, max = 100, message = "名前は1文字以上100文字以下で入力してください")
  private String name;

  @NotBlank(message = "ふりがなは必須です")
  @Size(min = 1, max = 100, message = "ふりがなは1文字以上100文字以下で入力してください")
  private String reading;

  @Size(max = 100, message = "ニックネームは100文字以内で入力してください")
  private String nickName;

  @NotBlank(message = "メールアドレスは必須です")
  @Email(message = "メールアドレスの形式が正しくありません")
  private String mailAddress;

  @Size(max = 100, message ="住所は100文字以下で入力してください")
  private String address;

  @Min(value = 0, message = "年齢は0歳以上を入力してください")
  @Max(value = 130, message = "年齢は130以下で入力してください")
  private int age;

  @Size(max = 20, message = "性別は20字以内で入力してください")
  private String gender;

  @Size(max = 300, message = "備考は300字以内で書いてください")
  private String remark;

  private boolean delete;
}
