package raiseTech.studentManagement.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import raiseTech.studentManagement.Domain.StudentDetail;
import raiseTech.studentManagement.Service.StudentService;

/**
// 受講生の検索、登録、更新処理ができるREST APIとして受け付けるコントローラー
 */
@RestController
public class StudentController {

  private StudentService service;

  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  /**
   *受講生一覧検索
   *全件検索するため、条件指定はなし
   * @return 受講生全件検索の一覧
   */
  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList() {
    return service.searchStudentList();
  }

  /**
   * 単一の受講生検索
   * idに紐づいた受講生を検索
   * @param id　受講生ID
   * @return 受講生
   */
  @GetMapping("/student/{id}")
  public StudentDetail getStudent(@PathVariable Long id) {
    return service.searchStudent(id);
  }

  @PostMapping("/registerStudent")
  public ResponseEntity<StudentDetail> registerStudent (@RequestBody StudentDetail studentDetail) {
    StudentDetail responseStudentDetail = service.newInsetStudent(studentDetail);
    return  ResponseEntity.ok(responseStudentDetail);
  }

 @PostMapping("/updateStudent")
 public ResponseEntity<String>updateStudent(@RequestBody StudentDetail studentDetail) {
   service.updateStudent(studentDetail);
   return ResponseEntity.ok("更新処理に成功しました");
 }
}