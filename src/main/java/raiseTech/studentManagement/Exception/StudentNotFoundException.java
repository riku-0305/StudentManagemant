package raiseTech.studentManagement.Exception;

public class StudentNotFoundException extends RuntimeException {

  public StudentNotFoundException(Long id) {
   super(id + "番の生徒はみつかりませんでした");
  }
}
