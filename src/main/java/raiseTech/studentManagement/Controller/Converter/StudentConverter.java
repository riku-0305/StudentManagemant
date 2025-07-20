package raiseTech.studentManagement.Controller.Converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import raiseTech.studentManagement.Data.Student;
import raiseTech.studentManagement.Data.StudentCourse;
import raiseTech.studentManagement.Domain.StudentDetail;

/**
 * 受講生詳細を受講生や受講生コース情報、もしくはその逆を変換するコンバーター
 */
@Component
public class StudentConverter {

  /**
   * 受講生とそれに紐づくコース情報をマッピングする。
   * 受講生とそれに紐づくコースは複数存在するので、受講生とその受講生が持つコース情報をループさせ
   *受講生詳細を組み立て。
   * @param student 受講生情報
   * @param studentCourse 受講生コース情報リスト
   * @return 受講生コース情報詳細
   */
  public List<StudentDetail> convertStudentDetails(List<Student> student,
      List<StudentCourse> studentCourse) {

    List<StudentDetail> studentDetails = new ArrayList<>();
    student.forEach(students -> {
      StudentDetail studentDetail = new StudentDetail();
      studentDetail.setStudent(students);
      List<StudentCourse> convertStudentCourses = studentCourse.stream()
          .filter(studentCourses -> students.getId().equals(studentCourses.getStudentsId()))
          .collect(Collectors.toList());

      studentDetail.setStudentCourses(convertStudentCourses);
      studentDetails.add(studentDetail);
    });
    return studentDetails;

  }
}
