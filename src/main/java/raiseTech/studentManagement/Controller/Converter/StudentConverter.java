package raiseTech.studentManagement.Controller.Converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import raiseTech.studentManagement.Data.Student;
import raiseTech.studentManagement.Data.StudentCourse;
import raiseTech.studentManagement.Data.StudentEnrollmentStatus;
import raiseTech.studentManagement.Domain.StudentDetail;

/**
 * 受講生詳細を受講生や受講生コース情報、もしくはその逆を変換するコンバーター
 */
@Component
public class StudentConverter {

  /**
   * 受講生とそれに紐づくコース情報とそのコース情報に紐づく申し込み状況をマッピングする。
   * 受講生とそれに紐づくコースは複数存在するので、受講生とその受講生が持つコース情報をループさせ
   * 受講生詳細を組み立て。
   * 受講生コース情報と申し込み状況は1:1なので受講生に紐づいたコース情報のリストと
   * そのリストに紐づく申し込み状況をループ
   * * @param student 受講生情報
   * @param studentCourseList 受講生コース情報リスト
   * @return 受講生コース情報詳細
   */
  public List<StudentDetail> convertStudentDetails(
      List<Student> student,
      List<StudentCourse> studentCourseList,
      List<StudentEnrollmentStatus> studentEnrollmentStatusList) {

      List<StudentDetail> studentDetails = new ArrayList<>();
      student.forEach(students -> {
       StudentDetail studentDetail = new StudentDetail();
       studentDetail.setStudent(students);

      List<StudentCourse> convertStudentCourse = studentCourseList.stream()
          .filter(studentCourse -> students.getId().equals(studentCourse.getStudentsId()))
          .collect(Collectors.toList());

      List<Long> coursesId = convertStudentCourse.stream()
          .map(StudentCourse::getCoursesId)
          .toList();

      List<StudentEnrollmentStatus> convertStudentEnrollmentStatus = studentEnrollmentStatusList.stream()
          .filter(studentEnrollmentStatus -> coursesId.contains(
              studentEnrollmentStatus.getStudentsCoursesId())).collect(Collectors.toList());

      studentDetail.setStudentEnrollmentStatusList(convertStudentEnrollmentStatus);
      studentDetail.setStudentCourseList(convertStudentCourse);
      studentDetails.add(studentDetail);
    });
    return studentDetails;
  }
}
