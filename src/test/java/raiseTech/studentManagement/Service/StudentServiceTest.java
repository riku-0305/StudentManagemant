package raiseTech.studentManagement.Service;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import raiseTech.studentManagement.Controller.Converter.StudentConverter;
import raiseTech.studentManagement.Data.Student;
import raiseTech.studentManagement.Data.StudentCourse;
import raiseTech.studentManagement.Domain.StudentDetail;
import raiseTech.studentManagement.Exception.StudentNotFoundException;
import raiseTech.studentManagement.Repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

  @Mock
  private StudentRepository repository;

  @Mock
  private StudentConverter converter;

  private StudentService sut;

  @BeforeEach
  void before() {
    sut = new StudentService(repository, converter);
  }

  @Test
  void 受講生詳細の全件検索機能でリポジトリとコンバーターが適切に呼び出せていること() {

    List<Student> studentList = new ArrayList<>();
    List<StudentCourse> studentCourseList = new ArrayList<>();
    Mockito.when(repository.search()).thenReturn(studentList);
    Mockito.when(repository.searchCourseList()).thenReturn(studentCourseList);

    sut.searchStudentList();

    Mockito.verify(repository, Mockito.times(1)).search();
    Mockito.verify(repository, Mockito.times(1)).searchCourseList();
    Mockito.verify(converter, Mockito.times(1))
        .convertStudentDetails(studentList, studentCourseList);
  }

  @Test
  void 受講生単一の検索でidで指定された受講生情報及び受講コース情報がリポジトリから適切に呼び出せているか() {

    Student expectedStudent = new Student();
    expectedStudent.setId(1L);
    Mockito.when(repository.searchStudent(expectedStudent.getId())).thenReturn(expectedStudent);

    StudentCourse expectedStudentCourse = new StudentCourse();
    expectedStudentCourse.setStudentsId(1L);
    expectedStudentCourse.setCoursesName("テストコース");
    List<StudentCourse> expectedStudentCourseList = new ArrayList<>();
    expectedStudentCourseList.add(expectedStudentCourse);
    Mockito.when(repository.searchStudentCourse(expectedStudent.getId()))
        .thenReturn(expectedStudentCourseList);

    sut.searchStudent(expectedStudent.getId());

    Mockito.verify(repository, Mockito.times(1)).searchStudent(expectedStudent.getId());
    Mockito.verify(repository, Mockito.times(1)).searchStudentCourse(expectedStudentCourse.getStudentsId());
  }

  @Test
  void 指定したidの受講生が見つからない場合にStudentNotFoundExceptionがスローされているか() {

    Student expectedStudent = new Student();
    expectedStudent.setId(99L);
    Mockito.when(repository.searchStudent(expectedStudent.getId())).thenReturn(null);

    StudentNotFoundException actualException = Assertions.assertThrows(
        StudentNotFoundException.class,
        () -> sut.searchStudent(expectedStudent.getId()));

    Assertions.assertEquals(expectedStudent.getId(), actualException.getId());

    Mockito.verify(repository, Mockito.times(1)).searchStudent(expectedStudent.getId());
    Mockito.verify(repository, Mockito.never()).searchStudentCourse(Mockito.anyLong());
  }

  @Test
  void 新規登録時に入力された受講生情報と生徒コース情報をリポジトリに渡せているか() {
    Student expectedRegisterStudent = new Student();
    expectedRegisterStudent.setName("テスト学生");

    StudentCourse expectedRegisterStudentCourse = new StudentCourse();
    expectedRegisterStudentCourse.setCoursesName("テストコース");

    List<StudentCourse> expectedStudentCourseList = new ArrayList<>();
    expectedStudentCourseList.add(expectedRegisterStudentCourse);

    StudentDetail actualStudent = new StudentDetail(expectedRegisterStudent, expectedStudentCourseList);

    Mockito.doNothing().when(repository).insertStudent(expectedRegisterStudent);
    Mockito.doNothing().when(repository).insertStudentCourse(expectedRegisterStudentCourse);

    sut.newInsetStudent(actualStudent);

    Mockito.verify(repository, Mockito.times(1)).insertStudent(expectedRegisterStudent);
    Mockito.verify(repository, Mockito.times(1)).insertStudentCourse(expectedRegisterStudentCourse);
  }

  @Test
  void 受講生更新時に更新された受講生情報と受講生コース情報をリポジトリに渡せているか() {
   Student expectedUpdateStudent = new Student();
   expectedUpdateStudent.setName("テスト");

   StudentCourse expectedUpdateStudentCourse = new StudentCourse();
   expectedUpdateStudentCourse.setCoursesName("テストコース");

   List<StudentCourse> expectedUpdateStudentCourseList = new ArrayList<>();
   expectedUpdateStudentCourseList.add(expectedUpdateStudentCourse);

   StudentDetail actualUpdateStudent = new StudentDetail(expectedUpdateStudent,expectedUpdateStudentCourseList);

   Mockito.doNothing().when(repository).updateStudent(expectedUpdateStudent);
   Mockito.doNothing().when(repository).updateStudentCourse(expectedUpdateStudentCourse);

   sut.updateStudent(actualUpdateStudent);


   Mockito.verify(repository,Mockito.times(1)).updateStudent(expectedUpdateStudent);
   Mockito.verify(repository,Mockito.times(1)).updateStudentCourse(expectedUpdateStudentCourse);
  }

  @Test
  void 存在しない受講生のidがリクエストされた時に例外を返す () {
    Student notFoundStudent = new Student();
    notFoundStudent.setId(99L);

    Mockito.when(repository.searchStudent(notFoundStudent.getId())).thenReturn(null);

    StudentNotFoundException actualException = Assertions.assertThrows(StudentNotFoundException.class,
    ()-> sut.searchStudent(notFoundStudent.getId()));

    Assertions.assertEquals(actualException.getId(),notFoundStudent.getId());

    Mockito.verify(repository,Mockito.never()).updateStudent(Mockito.any());
  }
}