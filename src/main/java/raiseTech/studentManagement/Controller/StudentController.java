package raiseTech.studentManagement.Controller;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import raiseTech.studentManagement.Controller.Converter.StudentConverter;
import raiseTech.studentManagement.Data.Student;
import raiseTech.studentManagement.Data.StudentCourse;
import raiseTech.studentManagement.Domain.StudentDetail;
import raiseTech.studentManagement.Service.StudentService;

@RestController
public class StudentController {

  private StudentService service;
  private StudentConverter converter;

  @Autowired
  public StudentController(StudentService service, StudentConverter converter) {
    this.service = service;
    this.converter = converter;
  }

  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList() {
    List<Student> student = service.searchStudentList();
    List<StudentCourse> studentCourse = service.searchStudentCourseList();

    return converter.convertStudentDetails(student, studentCourse);
  }

  @GetMapping("/student/{id}")
  public String getStudent(@PathVariable Long id, Model model) {
   StudentDetail studentDetail = service.searchStudent(id);
   model.addAttribute("studentDetail",studentDetail);
   return "updateStudent";
  }

  @GetMapping("/newStudent")
  public String newStudent(Model model) {
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudentCourses(Arrays.asList(new StudentCourse()));
    model.addAttribute("studentDetail", studentDetail);
    return "registerStudent";
  }

  @PostMapping("/registerStudent")
  public String registerStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result) {
    if (result.hasErrors()) {
      return "registerStudent";
    }
    service.newInsetStudent(studentDetail);
    return "redirect:/studentList";
  }

 @PostMapping("/updateStudent")
 public ResponseEntity<String>updateStudent(@RequestBody StudentDetail studentDetail) {
   service.updateStudent(studentDetail);
   return ResponseEntity.ok("更新処理に成功しました");
 }
}