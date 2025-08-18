package raiseTech.studentManagement.Controller;

import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.web.bind.annotation.RequestParam;
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
   * 受講生詳細一覧検索,様々な条件を指定して受講生詳細の検索が可能。
   * @return 指定された名前の受講生の受講生情報,指定された申し込み状況の受講生詳細
   * リクエストパラメータでの受講生検索が行われない場合に受講生詳細一覧を返す
   */
  @Operation(summary = "一覧検索", description = "受講生一覧の検索")
  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList(
      @RequestParam(name = "name", required = false) String name,
      @RequestParam(name = "status", required = false) String status) {
    if (name != null) {
       return service.nameSearchStudentList(name);
    } else if (status != null) {
       return service.statusSearchStudentList(status);
    } else {
       return service.searchStudentList();
    }
  }

  /**
   * 単一の受講生詳細検索
   * idに紐づいた受講生を検索
   * @param id　受講生ID
   * @return 受講生
   */
  @Operation(summary = "単一検索", description = "受講生単一検索")
  @GetMapping("/student/{id}")
  public StudentDetail getStudent(@PathVariable @Min(value = 1) Long id) {
    return service.searchStudent(id);
  }

  /**
   * 新規の受講生詳細の登録
   * @param studentDetail 受講生詳細
   * @return 実行結果
   */
  @Operation(summary = "受講生新規登録", description = "新規受講生登録には受講生id,削除フラグとコースidの入力フォームは自動生成のため不要")
  @PostMapping("/registerStudent")
  public ResponseEntity<StudentDetail> registerStudent(
      @RequestBody @Valid StudentDetail studentDetail) {
    StudentDetail responseStudentDetail = service.newInsetStudent(studentDetail);
    return ResponseEntity.ok(responseStudentDetail);
  }

  /**
   * 受講生詳細の更新を行う。論理削除の更新もここで行う。
   *
   * @param studentDetail 受講生詳細
   * @return 実行結果
   */
  @Operation(summary = "受講生情報更新", description = "受講生詳細,受講生コース情報の更新の際に受講生コース情報の生徒idの入力フォームは不要です。")
  @PutMapping("/updateStudent")
  public ResponseEntity<String> updateStudent(@RequestBody @Valid StudentDetail studentDetail) {
    service.updateStudent(studentDetail);
    return ResponseEntity.ok("更新処理に成功しました");
  }

  //(例外処理テスト用)
  @GetMapping("/testStudentList")
  public List<StudentDetail> getTestStudentList() throws TestStudentListException {
    throw new TestStudentListException(
        "現在このAPIは使われておりません。生徒一覧の検索のURLはStudentListをお使いください。");
  }
}