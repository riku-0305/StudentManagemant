package raiseTech.studentManagement.Exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StudentNotFoundException extends RuntimeException {

  private Long id;

  public StudentNotFoundException(Long id) {
    super(id + "番の生徒はみつかりませんでした");
    this.id = id;
  }
}

