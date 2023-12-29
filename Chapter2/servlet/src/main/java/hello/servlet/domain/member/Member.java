package hello.servlet.domain.member;

import java.util.concurrent.atomic.AtomicLong;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Member {
  private AtomicLong id;
  private String username;
  private int age;

  public Member(){}
  public Member (String username, int age){
    this.username = username;
    this.age = age;
  }
}
