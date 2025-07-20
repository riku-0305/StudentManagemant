package raiseTech.studentManagement.Service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raiseTech.studentManagement.Controller.Converter.StudentConverter;
import raiseTech.studentManagement.Data.Student;
import raiseTech.studentManagement.Data.StudentCourse;
import raiseTech.studentManagement.Domain.StudentDetail;
import raiseTech.studentManagement.Repository.StudentRepository;

/**
 * 受講生情報を取り扱うサービス
 * 受講生の検索、登録、更新を行う。
 */
@Service
public class StudentService {

  private StudentRepository repository;
  private StudentConverter studentConverter;

  @Autowired
  public StudentService(StudentRepository repository, StudentConverter studentConverter) {
    this.repository = repository;
    this.studentConverter = studentConverter;
  }

  /**
   *受講生一覧検索
   *全件検索するため、条件指定はなし
   * @return 受講生全件検索の一覧
   */
  public List<StudentDetail> searchStudentList() {
    List<Student> studentList = repository.search();
    List<StudentCourse> studentCourseList = repository.searchCourse();
    return studentConverter.convertStudentDetails(studentList,studentCourseList);
  }

  /**
   *単一の受講生検索
   *idに紐づいた受講生を検索した後に、その受講生に紐づくコース情報を取得
   *
   * @param id　受講生ID
   * @return 受講生
   */


  public StudentDetail searchStudent(Long id) {
    Student student = repository.searchStudent(id);
    List<StudentCourse> studentCourses = repository.searchStudentCourse(student.getId());
    return new StudentDetail(student,studentCourses);
  }

  @Transactional
  public StudentDetail newInsetStudent(StudentDetail studentDetail) {
    repository.insertStudent(studentDetail.getStudent());
    for (StudentCourse studentCourse : studentDetail.getStudentCourses()) {
      studentCourse.setStudentsId(studentDetail.getStudent().getId());
      studentCourse.setStartDate(LocalDate.now());
      studentCourse.setExpectedEndDate(LocalDate.now().plusMonths(6));
      repository.insertStudentCourse(studentCourse);
    }
    return studentDetail;
  }

  @Transactional
  public void updateStudent(StudentDetail studentDetail) {
    repository.updateStudent(studentDetail.getStudent());
    for(StudentCourse studentCourse : studentDetail.getStudentCourses()) {
      repository.updateStudentCourse(studentCourse);
    }
  }
}
