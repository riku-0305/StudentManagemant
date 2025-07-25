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
import raiseTech.studentManagement.Exception.StudentNotFoundException;
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
   *受講生詳細一覧検索
   *全件検索するため、条件指定はなし
   * @return 受講生全件検索の一覧
   */
  public List<StudentDetail> searchStudentList() {
    List<Student> studentList = repository.search();
    List<StudentCourse> studentCourseList = repository.searchCourseList();
    return studentConverter.convertStudentDetails(studentList,studentCourseList);
  }

  /**
   *単一の受講生詳細の検索
   *idに紐づいた受講生を検索した後に、その受講生に紐づくコース情報を取得
   *
   * @param id　受講生ID
   * @return 受講生
   */
  public StudentDetail searchStudent(Long id) {
    Student student = repository.searchStudent(id);

    if (student == null) {
      throw new StudentNotFoundException(id);
    }

    List<StudentCourse> studentCourses = repository.searchStudentCourse(student.getId());
    return new StudentDetail(student,studentCourses);
  }

  /**　
   * 受講生詳細の登録
   * 受講生詳細登録と受講生コース情報の登録を個別で行い、登録された受講生に紐づく受講生コース情報の
   * 値や日付を設定。
   * @param studentDetail 受講生詳細　
   * @return 登録された受講生詳細
   */
  @Transactional
  public StudentDetail newInsetStudent(StudentDetail studentDetail) {
    Student student = studentDetail.getStudent();

    repository.insertStudent(student);
    studentDetail.getStudentCourseList().forEach(studentCourse -> {
      initStudentCourse(studentCourse, student);
      repository.insertStudentCourse(studentCourse);
    });
    return studentDetail;
  }

  /**
   * 受講生コース情報を登録する際の初期値設定
   * @param studentCourse 受講生コース情報
   * @param student 受講生
   */
  private void initStudentCourse(StudentCourse studentCourse, Student student) {
    LocalDate now = LocalDate.now();

    studentCourse.setStudentsId(student.getId());
    studentCourse.setStartDate(now);
    studentCourse.setExpectedEndDate(now.plusMonths(6));
  }

  /**
   * 受講生詳細の更新を行う
   * 受講生情報と受講生コース情報のそれぞれを更新
   * @param studentDetail 受講生詳細
   */
  @Transactional
  public void updateStudent(StudentDetail studentDetail) {
    repository.updateStudent(studentDetail.getStudent());
    studentDetail.getStudentCourseList()
        .forEach(studentCourse -> repository.updateStudentCourse(studentCourse));
  }
}
