package raiseTech.studentManagement.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raiseTech.studentManagement.Controller.Converter.StudentConverter;
import raiseTech.studentManagement.Data.Student;
import raiseTech.studentManagement.Data.StudentCourse;
import raiseTech.studentManagement.Data.StudentEnrollmentStatus;
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
    List<StudentEnrollmentStatus> studentEnrollmentStatusList = repository.searchStudentEnrollmentStatusList();
    return studentConverter.convertStudentDetails(studentList, studentCourseList,
        studentEnrollmentStatusList);
  }

  /**
   * 指定された名前と合致する受講生情報リストの検索
   * @param name 指定された受講生の名前
   * @return 指定された名前に合致する受講生のリスト
   */
  public List<StudentDetail> nameSearchStudentList(String name) {
    String normalizationName = name.replaceAll("[ 　]", "");

    List<Student> studentList = repository.search();

    List<Student> selectStudentList = studentList.stream()
        .filter(student -> student.getName().equals(normalizationName))
        .collect(Collectors.toList());

    if(selectStudentList.isEmpty()) {
      throw new StudentNotFoundException(name);
    }

    List<StudentCourse> studentCourseList = repository.searchCourseList();
    List<StudentEnrollmentStatus> studentEnrollmentStatusList = repository.searchStudentEnrollmentStatusList();
    return studentConverter.convertStudentDetails(selectStudentList,studentCourseList,studentEnrollmentStatusList);
  }

  /**
   * 指定された受講コース申し込み状況に合致するする受講生情報リストの検索
   * @param status　指定された受講コース申し込み状況
   * @return 指定された受講コース申し込み状況に合致するする受講生情報リスト
   */
  public List<StudentDetail> statusSearchStudentList(String status) {
    String normalizationStatus = status.replaceAll("[ 　]", "");

    List<StudentEnrollmentStatus> selectEnrollmentStatus = repository.searchStudentEnrollmentStatusName(normalizationStatus);

    if(selectEnrollmentStatus.isEmpty()) {
      throw new StudentNotFoundException(status + "に該当する受講生はみつかりませんでした");
    }

    List<Student> studentList = repository.search();
    List<StudentCourse> studentCourseList = repository.searchCourseList();

    Set<Long> enrollmentStudentId = selectEnrollmentStatus.stream()
        .map(StudentEnrollmentStatus::getStudentsId)
        .collect(Collectors.toSet());

    List<Student> selectStudent = studentList.stream()
        .filter(students -> enrollmentStudentId.contains(students.getId()))
        .collect(Collectors.toList());

    return studentConverter.convertStudentDetails(selectStudent,studentCourseList,selectEnrollmentStatus);
  }

  /**
   *単一の受講生詳細の検索
   *idに紐づいた受講生を検索した後に、その受講生に紐づくコース情報とそのコース情報に紐づく申し込み状況を取得
   * @param id　受講生ID
   * @return 受講生
   */
  public StudentDetail searchStudent(Long id) {
    Student student = repository.searchStudent(id);

    if (student == null) {
      throw new StudentNotFoundException(id);
    }

    List<StudentCourse> studentCourses = repository.searchStudentCourse(student.getId());

    List<Long> courseId = studentCourses.stream()
        .map(StudentCourse::getCoursesId)
        .toList();

    List<StudentEnrollmentStatus> studentEnrollmentStatus = new ArrayList<>();

    for(Long matchId : courseId) {
     List<StudentEnrollmentStatus> status = repository.searchStudentEnrollmentStatus(matchId);
     studentEnrollmentStatus.addAll(status);
    }

    return new StudentDetail(student, studentCourses, studentEnrollmentStatus);
  }

  /**　
   * 受講生詳細の登録
   * 受講生詳細登録と受講生コース情報,受講生コース情報の申し込み状況の登録を個別で行い、
   * 登録された受講生に紐づく受講生コース情報の値や日付を設定。
   * その後、登録された受講生、受講生コース情報に紐づくコース申し込み状況の値を設定。
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

       studentDetail.getStudentEnrollmentStatusList().forEach(StudentEnrollmentStatus -> {
         initStudentStudentEnrollmentStatus(StudentEnrollmentStatus, studentCourse, student);
         repository.insertStudentEnrollmentStatus(StudentEnrollmentStatus);
    });
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
   * 受講生コース情報の申し込み状況の初期設定
   * @param studentEnrollmentStatus 受講コース申し込み状況
   * @param studentCourse 受講コース情報
   * @param student　受講生
   */
  private void initStudentStudentEnrollmentStatus(StudentEnrollmentStatus studentEnrollmentStatus, StudentCourse studentCourse, Student student) {
    studentEnrollmentStatus.setStudentsId(student.getId());
    studentEnrollmentStatus.setStudentsCoursesId(studentCourse.getCoursesId());
  }

  /**
   * 受講生詳細の更新を行う
   * 受講生情報と受講生コース情報,受講生コース情報の申し込み状況をそれぞれを更新
   * @param studentDetail 受講生詳細
   */
  @Transactional
  public void updateStudent(StudentDetail studentDetail) {

    Student student = repository.searchStudent(studentDetail.getStudent().getId());
    if (student == null) {
      throw new StudentNotFoundException(studentDetail.getStudent().getId());
    }

    repository.updateStudent(studentDetail.getStudent());
    studentDetail.getStudentCourseList()
        .forEach(studentCourse -> repository.updateStudentCourse(studentCourse));

    studentDetail.getStudentEnrollmentStatusList()
        .forEach(studentEnrollmentStatus -> repository.updateStudentEnrollmentStatus(studentEnrollmentStatus));
  }
}
