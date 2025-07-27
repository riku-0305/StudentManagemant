package raiseTech.studentManagement.ExceptionHandler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import raiseTech.studentManagement.Exception.TestStudentListException;
import raiseTech.studentManagement.Exception.StudentNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler  {


  /**
   * {@link StudentNotFoundException}を処理
   * @param ex 存在しない生徒のidに対して発生した例外
   * @return HTTPステータスと例外メッセージ
   */
  @ExceptionHandler(StudentNotFoundException.class)
  public ResponseEntity<String> handleStudentNotFoundException(StudentNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }

  /**
   * コントローラーのid検索で文字列や少数が入力された際の処理
   * @param ex 文字列や少数が入力された際の例外
   * @return HTTPステータスと例外メッセージ
   */
  @ExceptionHandler(NumberFormatException.class)
  public ResponseEntity<String> handleNumberFormatException(NumberFormatException ex) {
    String message = "IDは1以上の整数値を入力してください";
    return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
  }

  /**
   * バリデーションエラー(@PathVariable,@RequestParam)があった際の処理
   * コントローラーのid検索時に－の値が入力された際の処理
   * @param ex バリデーションエラーがあったオブジェクトのフィールド
   * @return HTTPステータスとバリデーションエラーの変数名,例外メッセージ
   */
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Map<String,String>> handleConstraintViolationException(ConstraintViolationException ex) {
    Map<String,String> errors = new HashMap<>();

    //オブジェクトを指定しない(ワイルドカード)ConstraintViolation型のviolationに１つ以上のバリデーションエラーのリストを代入してループ
    for(ConstraintViolation<?> violation : ex.getConstraintViolations()) {

      //String型の変数fieldNameにバリデーションエラーのフィールドパスを文字列化して代入
      String fieldName = violation.getPropertyPath().toString();

      //String型の変数paramNameに文字列化したバリデーションエラーのフィールドパスの.以降(変数名)を代入
      String paramName = fieldName.substring(fieldName.lastIndexOf('.') +1);

      //Map型の変数errorにバリデーションエラー箇所の変数名とエラーメッセージを追加
      errors.put(paramName, violation.getMessage());
    }
    return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
  }

  /**
   * バリデーションエラー(@RequestBody)があった際の処理
   * コントローラーの生徒情報更新の際に想定外の値が入力された際の処理
   * @param ex　バリデーションエラーがあったオブジェクトのフィールド
   * @param request　
   * @return HTTPステータスとバリデーションエラーの変数名,例外メッセージ
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String,String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
    Map<String,String> errors = new HashMap<>();

    //FieldErrorはMethodArgumentNotValidExceptionクラスが持つリスト型
    //MethodArgumentNotValidExceptionはバインディングリザルトを持っていてそこからgetFieldErrors(エラーフィールドを取得)
    for(FieldError error : ex.getBindingResult().getFieldErrors()) {

      //String型の変数fieldNameにバリデーションエラーのフィールドパスを代入
      String fieldName = error.getField();

      //String型の変数paramNameに文字列化したバリデーションエラーのフィールドパスの.以降(変数名)を代入
      String paramName = fieldName.substring(fieldName.lastIndexOf('.') +1);

      //Map型の変数errorにバリデーションエラー箇所の変数名とエラーメッセージを追加
      errors.put(paramName,error.getDefaultMessage());
    }
    return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
  }

  //テスト用
  @ExceptionHandler(TestStudentListException.class)
  public ResponseEntity<String> handleTestStudentListException(TestStudentListException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }
}