package raiseTech.studentManagement.Repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import raiseTech.studentManagement.Data.Student;
import raiseTech.studentManagement.Data.StudentCourse;

@Mapper
public interface StudentRepository {

  @Select("SELECT * FROM students")
  List<Student> search();

  @Select("SELECT * FROM students_courses")
  List<StudentCourse> searchCourse();

  @Insert("INSERT INTO students (name,reading, nick_name,mail_address,address,age,gender ,remark)" +
  "VALUES (#{name} , #{reading} , #{nickName} , #{mailAddress} , #{address} , #{age} , #{gender} , #{remark}) " )
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insertStudent(Student student);

  @Insert("INSERT INTO  students_courses (students_id,  courses_name, start_date, expected_end_date)" +
  "VALUES (#{studentsId}, #{coursesName}, #{startDate}, #{expectedEndDate})")
  @Options(useGeneratedKeys = true, keyProperty = "coursesId")
  void insertStudentCourse(StudentCourse studentCourse);
}
