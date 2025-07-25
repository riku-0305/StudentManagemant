package raiseTech.studentManagement.Controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import raiseTech.studentManagement.Domain.StudentDetail;
import raiseTech.studentManagement.Exception.TestStudentListException;
import raiseTech.studentManagement.Service.StudentService;

/**
// 受講生の検索、登録、更新処理ができるREST APIとして受け付けるコントローラー
 */
@Validated
@RestController
public class StudentController {

  private StudentService service;

  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  /**
   *受講生詳細一覧検索
   *全件検索するため、条件指定はなし
   * @return 受講生全件検索の一覧
   */
  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList() {
    return service.searchStudentList();
  }

  /**
   * 単一の受講生詳細検索
   * idに紐づいた受講生を検索
   * @param id　受講生ID
   * @return 受講生
   */
  @GetMapping("/student/{id}")
  public StudentDetail getStudent(@PathVariable @Min(value = 1) Long id) {
    return service.searchStudent(id);
  }

  /**
   * 新規の受講生詳細の登録
   * @param studentDetail 受講生詳細
   * @return 実行結果
   */
  @PostMapping("/registerStudent")
  public ResponseEntity<StudentDetail> registerStudent (@RequestBody @Valid StudentDetail studentDetail) {
    StudentDetail responseStudentDetail = service.newInsetStudent(studentDetail);
    return  ResponseEntity.ok(responseStudentDetail);
  }

  /**
   * 受講生詳細の更新を行う。論理削除の更新もここで行う。
   *
    * @param studentDetail 受講生詳細
   * @return 実行結果
   */
 @PutMapping("/updateStudent")
 public ResponseEntity<String>updateStudent(@RequestBody @Valid StudentDetail studentDetail) {
   service.updateStudent(studentDetail);
   return ResponseEntity.ok("更新処理に成功しました");
 }

 //(例外処理テスト用)
 @GetMapping("/testStudentList")
  public List<StudentDetail> getTestStudentList() throws TestStudentListException {
   throw new TestStudentListException("現在このAPIは使われておりません。生徒一覧の検索のURLはStudentListをお使いください。");
 }
}