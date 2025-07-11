package raiseTech.studentManagement.Repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
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
  //INSERT,UPDATE,DELETEはこの操作によって何件のレコードが影響を受けたかを示すために戻り値はint型を使う。
  int insertStudent(Student student);

}
