package raiseTech.studentManagement.Repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import raiseTech.studentManagement.Data.Student;
import raiseTech.studentManagement.Data.StudentCourse;

/**
 * 受講生テーブルと受講生コース情報テーブルに紐づくリポジトリ
 */

@Mapper
public interface StudentRepository {

  /**
   *受講生一覧検索
   *全件検索するため、条件指定はなし
   * @return 受講生全件検索の一覧
   */
  @Select("SELECT * FROM students WHERE is_delete = 0")
  List<Student> search();

  /**
   * 受講生情報の単一検索
   * @param id　受講生iD
   * @return 受講生
   */
  @Select("SELECT * FROM students WHERE id = #{id}")
  Student  searchStudent(Long id);

  /**
   * 受講生コース情報を全件検索
   * @return 受講生コース情報(全件)
   */
  @Select("SELECT * FROM students_courses")
  List<StudentCourse> searchCourse();

  /**
   * 受講生IDに紐づく受講生コース情報を検索
   * @param studentsId　受講生ID
   * @return 受講生IDに紐づく受講生コース情報
   */
  @Select("SELECT * FROM students_courses WHERE students_id = #{studentsId}")
  List<StudentCourse> searchStudentCourse(Long studentsId);

  @Insert(
      "INSERT INTO students (name, reading, nick_name, mail_address, address, age, gender , remark)"
          +
          "VALUES (#{name} , #{reading} , #{nickName} , #{mailAddress} , #{address} , #{age} , #{gender} , #{remark}) ")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insertStudent(Student student);

  @Insert(
      "INSERT INTO  students_courses (students_id,  courses_name, start_date, expected_end_date)" +
          "VALUES (#{studentsId}, #{coursesName}, #{startDate}, #{expectedEndDate})")
  @Options(useGeneratedKeys = true, keyProperty = "coursesId")
  void insertStudentCourse(StudentCourse studentCourse);

  @Update("UPDATE students SET name = #{name}, reading = #{reading}, nick_name = #{nickName},  mail_address = #{mailAddress}, "
      + "address = #{address}, age = #{age}, gender = #{gender}, remark = #{remark}, is_delete = #{delete} WHERE id = #{id}")
  void updateStudent(Student student);


  @Update("UPDATE students_courses SET courses_name = #{coursesName} WHERE  courses_id = #{coursesId}")
  void updateStudentCourse(StudentCourse studentCourse);
}