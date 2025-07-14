package raiseTech.studentManagement.Data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Student {

  private Long id;
  private String name;
  private String reading;
  private String nickName;
  private String mailAddress;
  private String address;
  private int age;
  private String gender;
  private String remark;
  private boolean delete;
}
