package raiseTech.studentManagement.Repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import raiseTech.studentManagement.Data.Student;
import raiseTech.studentManagement.Data.StudentCourse;
import raiseTech.studentManagement.Data.StudentEnrollmentStatus;

/**
 * 受講生テーブルと受講生コース情報テーブルに紐づくリポジトリ
 */

@Mapper
public interface StudentRepository {

  /**
   *受講生詳細一覧検索
   *全件検索するため、条件指定はなし
   * @return 受講生全件検索の一覧
   */
  List<Student> search();

  /**
   * 受講生詳細情報の単一検索
   * @param id　受講生iD
   * @return 受講生情報
   */
  Student  searchStudent(Long id);

  /**
   * 受講生コース情報を全件検索
   * @return 受講生コース情報(全件)
   */
  List<StudentCourse> searchCourseList();

  /**
   * 受講生IDに紐づく受講生コース情報を検索
   * @param studentsId　受講生ID
   * @return 受講生IDに紐づく受講生コース情報
   */
  List<StudentCourse> searchStudentCourse(Long studentsId);

  /**
   * 受講生コース情報の申し込み状況の全件検索
   * @return 受講生コース情報の申し込み状況(全件)
   */
  List<StudentEnrollmentStatus> searchStudentEnrollmentStatusList();

  /**
   * 受講生IDに紐づく受講生コース情報の申し込み状況の単一検索
   * @param studentsCoursesId　
   * @return 受講生IDに紐づく受講生コース情報の申し込み状況
   */
  List<StudentEnrollmentStatus> searchStudentEnrollmentStatus(Long studentsCoursesId);

  /**
   * 新規受講生登録、IDにはDBの自動採番を設定
   * @param student 新規受講生情報
   */
  void insertStudent(Student student);

  /**
   * 新規受講生コース情報登録、コースIDにはDBの自動採番を設定
   * @param studentCourse 新規受講生コース情報
   */
  void insertStudentCourse(StudentCourse studentCourse);

  /**
   * 新規受講生コース情報の申し込み状況の登録
   * @param studentEnrollmentStatus　新規受講生コース情報の申し込み状況
   */
  void insertStudentEnrollmentStatus (StudentEnrollmentStatus studentEnrollmentStatus);

  /**
   *受講生情報の更新
   * @param student 受講生情報
   */
  void updateStudent(Student student);

  /**
   * 受講生コース情報のコース名更新
   * @param studentCourse 受講生コース情報
   */
  void updateStudentCourse(StudentCourse studentCourse);


  void updateStudentEnrollmentStatus(StudentEnrollmentStatus studentEnrollmentStatus);
}