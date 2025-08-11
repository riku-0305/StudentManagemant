package raiseTech.studentManagement.Repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import raiseTech.studentManagement.Data.Student;
import raiseTech.studentManagement.Data.StudentCourse;

@MybatisTest
class StudentRepositoryTest {

  @Autowired
  private StudentRepository sut;

  @Test
  void 受講生の全件検索が行えること() {
    List<Student> actualSearch = sut.search();
    assertThat(actualSearch.size()).isEqualTo(3);
  }

  @Test
  void 受講生のコース情報の全件検索が行えること() {
    List<StudentCourse> actualStudentCourseList = sut.searchCourseList();
    assertThat(actualStudentCourseList.size()).isEqualTo(3);
  }

  @Test
  void 受講生の単一検索が行えること() {
    Student expectedStudent = new Student();
    expectedStudent.setId(1L);
    expectedStudent.setName("うずまきナルト");

    Student actualStudent = sut.searchStudent(1L);
    assertThat(expectedStudent.getId()).isEqualTo(actualStudent.getId());
    assertThat(expectedStudent.getName()).isEqualTo(actualStudent.getName());
  }

  @Test
  void 受講生の単一コース情報検索が行えること () {
    StudentCourse expectedStudentCourse = new StudentCourse();
    expectedStudentCourse.setStudentsId(2L);
    expectedStudentCourse.setCoursesName("Aws");

    List<StudentCourse> studentCourseList = sut.searchStudentCourse(2L);
    assertThat(expectedStudentCourse.getStudentsId()).isEqualTo(studentCourseList.getFirst().getStudentsId());
    assertThat(expectedStudentCourse.getCoursesName()).isEqualTo(studentCourseList.getFirst().getCoursesName());
  }

  @Test
  void 受講生の登録が行えること() {
    Student student = new Student();
    student.setName("riku");
    student.setReading("りく");
    student.setMailAddress("riku@gmail.com");
    student.setAge(26);

    sut.insertStudent(student);

    List<Student> actualStudent = sut.search();

    assertThat(actualStudent.size()).isEqualTo(4);
  }

  @Test
  void 受講生コース情報の登録が行えること () {
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setStudentsId(1L);
    studentCourse.setCoursesName("PHP");
    studentCourse.setStartDate(LocalDate.of(2025,8,10));
    studentCourse.setExpectedEndDate(LocalDate.of(2026,4,10));

    sut.insertStudentCourse(studentCourse);

    List<StudentCourse> actualStudentCourseList = sut.searchCourseList();

    assertThat(actualStudentCourseList.size()).isEqualTo(4);
  }

  @Test
  void 受講生情報の更新が行えること () {

    Student updateStudent = new Student();
    updateStudent.setId(1L);
    updateStudent.setName("riku");
    updateStudent.setMailAddress("riku0305@gmail.com");

    List<Student> oldStudentList = sut.search();

    sut.updateStudent(updateStudent);

    List<Student> newStudentList = sut.search();

    assertThat(updateStudent.getMailAddress()).isEqualTo(newStudentList.getFirst().getMailAddress());
    assertThat(newStudentList.size()).isEqualTo(oldStudentList.size());

  }

  @Test
  void 受講生コース情報の更新が行えること() {

    StudentCourse updateStudentCourse = new StudentCourse();
    updateStudentCourse.setCoursesId(1L);
    updateStudentCourse.setCoursesName("Ruby");

    List<StudentCourse> oldStudentCourse = sut.searchCourseList();

    sut.updateStudentCourse(updateStudentCourse);

    List<StudentCourse> newStudentCourse = sut.searchCourseList();

    assertThat(updateStudentCourse.getCoursesName()).isEqualTo(newStudentCourse.getFirst().getCoursesName());
    assertThat(oldStudentCourse.size()).isEqualTo(newStudentCourse.size());
  }
}