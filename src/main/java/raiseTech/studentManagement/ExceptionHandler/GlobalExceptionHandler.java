package raiseTech.studentManagement.ExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import raiseTech.studentManagement.Exception.TestStudentListException;
import raiseTech.studentManagement.Exception.StudentNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler  {


  /**
   * {@link StudentNotFoundException}を処理
   *
   * @param ex 存在しない生徒のidに対して発生した例外
   * @return HTTPステータスと例外メッセージ
   */
  @ExceptionHandler(StudentNotFoundException.class)
  public ResponseEntity<String> handleStudentNotFoundException(StudentNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }


  //テスト用
  @ExceptionHandler(TestStudentListException.class)
  public ResponseEntity<String> handleTestStudentListException(TestStudentListException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }
}
