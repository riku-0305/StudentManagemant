package raiseTech.studentManagement.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raiseTech.studentManagement.Data.Student;
import raiseTech.studentManagement.Data.StudentCourse;
import raiseTech.studentManagement.Domain.StudentDetail;
import raiseTech.studentManagement.Repository.StudentRepository;

@Service
public class StudentService {

  private StudentRepository repository;

 @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  public List<Student> searchStudentList() {
   return repository.search();
  }

  public List<StudentCourse> searchStudentCourseList() {
   return repository.searchCourse();
  }

  @Transactional
  public void newInsetStudent(StudentDetail studentDetail) {
   repository.insertStudent(studentDetail.getStudent());
  }
}
