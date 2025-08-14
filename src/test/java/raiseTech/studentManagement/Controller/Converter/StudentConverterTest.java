package raiseTech.studentManagement.Controller.Converter;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import net.minidev.json.JSONUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import raiseTech.studentManagement.Data.Student;
import raiseTech.studentManagement.Data.StudentCourse;
import raiseTech.studentManagement.Data.StudentEnrollmentStatus;
import raiseTech.studentManagement.Domain.StudentDetail;

class StudentConverterTest {

  private StudentConverter sut;

  @BeforeEach
  void before() {
    sut = new StudentConverter();
  }

  @Test
  void 生徒情報リストの中でその生徒が持つコース情報とその申し込み状況がマッピングできているか() {

    List<Student> expectedStudentList = new ArrayList<>();
    expectedStudentList.add(new Student(1L, "テスト1", "てすといち", "test@gmail.com"));
    expectedStudentList.add(new Student(2L, "テスト2", "てすとに", "test2@gmail.com"));

    List<StudentCourse> expectedStudentCourseList = new ArrayList<>();
    expectedStudentCourseList.add(new StudentCourse(1L, 1L, "java"));
    expectedStudentCourseList.add(new StudentCourse(2L, 1L, "AWS"));
    expectedStudentCourseList.add(new StudentCourse(3L, 2L, "デザイン"));
    
    List<StudentEnrollmentStatus> expectedStudentEnrollmentStatusList = new ArrayList<>();
    expectedStudentEnrollmentStatusList.add(new StudentEnrollmentStatus(1L,1L,1L,"仮申し込み"));
    expectedStudentEnrollmentStatusList.add(new StudentEnrollmentStatus(2L,1L,2L,"本申し込み"));
    expectedStudentEnrollmentStatusList.add(new StudentEnrollmentStatus(3L,2L,3L,"仮申し込み"));
    
    List<StudentDetail> actualStudentDetails = sut.convertStudentDetails(expectedStudentList,expectedStudentCourseList,expectedStudentEnrollmentStatusList);

    Assertions.assertEquals(2,actualStudentDetails.size());

    //1人目
    StudentDetail fastStudentDetail = actualStudentDetails.getFirst();
    Assertions.assertEquals(1L,fastStudentDetail.getStudent().getId());
    Assertions.assertEquals("テスト1",fastStudentDetail.getStudent().getName());
    
    Assertions.assertEquals(2,fastStudentDetail.getStudentCourseList().size());
    Assertions.assertEquals("java",fastStudentDetail.getStudentCourseList().getFirst().getCoursesName());
    Assertions.assertEquals("AWS",fastStudentDetail.getStudentCourseList().get(1).getCoursesName());
    
    Assertions.assertEquals(2,fastStudentDetail.getStudentEnrollmentStatusList().size());
    Assertions.assertEquals("仮申し込み",fastStudentDetail.getStudentEnrollmentStatusList().getFirst().getStatus());
    Assertions.assertEquals("本申し込み",fastStudentDetail.getStudentEnrollmentStatusList().get(1).getStatus());

    StudentDetail secondStudentDetail = actualStudentDetails.get(1);

    //2人目
    Assertions.assertEquals(2L,secondStudentDetail.getStudent().getId());
    Assertions.assertEquals("テスト2",secondStudentDetail.getStudent().getName());
    
    Assertions.assertEquals(1,secondStudentDetail.getStudentCourseList().size());
    Assertions.assertEquals("デザイン",secondStudentDetail.getStudentCourseList().getFirst().getCoursesName());
    
    Assertions.assertEquals(1,secondStudentDetail.getStudentEnrollmentStatusList().size());
    Assertions.assertEquals("仮申し込み",secondStudentDetail.getStudentEnrollmentStatusList().getFirst().getStatus());
  }
}