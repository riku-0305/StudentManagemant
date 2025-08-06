package raiseTech.studentManagement.Controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import raiseTech.studentManagement.Data.Student;
import raiseTech.studentManagement.Data.StudentCourse;
import raiseTech.studentManagement.Domain.StudentDetail;
import raiseTech.studentManagement.Service.StudentService;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private StudentService service;

  private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  @Test
  void 受講生詳細の一覧検索が可能でsearchStudentListが呼び出せていること() throws Exception {
    mockMvc.perform(get("/studentList"))
        .andExpect(status().isOk());

    Mockito.verify(service, Mockito.times(1)).searchStudentList();
  }

  @Test
  void 受講生単一検索が可能で入浴されたidの受講生情報とコースリストが帰ってくること()
      throws Exception {
    Long id = 1L;

    mockMvc.perform(get("/student/{id}", id))
        .andExpect(status().isOk());

    Mockito.verify(service, Mockito.times(1)).searchStudent(id);
  }

  @Test
  void idを用いた受講生単一検索機能のリクエストでバリデーションエラーが機能すること() throws Exception {
    mockMvc.perform(get("/student/{id}", "abc"))
        .andExpect(status().isBadRequest());

    Mockito.verify(service, Mockito.never()).searchStudent(Mockito.anyLong());
  }

  @Test
  void 受講生新規登録が可能でその登録内容がユーザーに帰ってくること() throws Exception {

    Student responseStudent = new Student(1L, "テスト", "てすと", "tesuto@gmail.com");

    List<StudentCourse> responseStudentCourseList = new ArrayList<>();
    responseStudentCourseList.add(new StudentCourse(1L, 1L, "java"));

    StudentDetail actualResponseDetail = new StudentDetail(responseStudent,
        responseStudentCourseList);

    Mockito.when(service.newInsetStudent(Mockito.any(StudentDetail.class)))
        .thenReturn(actualResponseDetail);

    String jsonExpectedRequest = objectMapper.writeValueAsString(actualResponseDetail);

    mockMvc.perform(post("/registerStudent")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonExpectedRequest))
        .andExpect(status().isOk());

    Mockito.verify(service, Mockito.times(1)).newInsetStudent(Mockito.any(StudentDetail.class));
  }

  @Test
  void 受講生情報及び受講生コース情報が更新が可能で更新時にメッセージをユーザーに返す()
      throws Exception {

    String expectedMessage = "更新処理に成功しました";

    Student responseStudent = new Student(1L, "テスト", "てすと", "tesuto@gmail.com");

    List<StudentCourse> responseStudentCourseList = new ArrayList<>();
    responseStudentCourseList.add(new StudentCourse(1L, 1L, "java"));

    StudentDetail actualStudent = new StudentDetail(responseStudent, responseStudentCourseList);

    Mockito.doNothing().when(service).updateStudent(actualStudent);

    String jsonExpectedRequest = objectMapper.writeValueAsString(actualStudent);

    mockMvc.perform(put("/updateStudent")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonExpectedRequest))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN_VALUE))
        .andExpect(content().string(expectedMessage));

    Mockito.verify(service, Mockito.times(1)).updateStudent(Mockito.any(StudentDetail.class));
  }

  @Test
  void 使われていないAPIがエラーレスポンスをユーザーに返す() throws Exception {

    String expectedErrorMessage = "現在このAPIは使われておりません。生徒一覧の検索のURLはStudentListをお使いください。";

    mockMvc.perform(get("/testStudentList"))
        .andExpect(status().isBadRequest())
        .andExpect(content().string(expectedErrorMessage));
  }

  @Test
  void Studentオブジェクトのバリデーション機能のテスト() {
    Student errorrStudent = new Student(0L, " ", " ", " ");
    errorrStudent.setAge(200);

    Set<ConstraintViolation<Student>> violations = validator.validate(errorrStudent);

    assertThat(violations.size()).isEqualTo(6);
    assertThat(violations).extracting("message")
        .containsExactlyInAnyOrder(
            "IDは1以上の整数値を入力してください",
            "名前入力は必須です",
            "ふりがなは必須です",
            "メールアドレスは必須です",
            "メールアドレスの形式が正しくありません",
            "年齢は130以下で入力してください");
  }

  @Test
  void StudentCourseオブジェクトのバリデーション機能テスト() {
    StudentCourse errorStudentCourse = new StudentCourse(0L,0L," ");

    Set<ConstraintViolation<StudentCourse>> violations = validator.validate(errorStudentCourse);

    assertThat(violations.size()).isEqualTo(3);
    assertThat(violations).extracting("message")
        .containsExactlyInAnyOrder(
            "コースIDは1以上を入力してください",
            "生徒IDは1以上を入力してください",
            "コース名の登録は必須です"
        );
  }
}