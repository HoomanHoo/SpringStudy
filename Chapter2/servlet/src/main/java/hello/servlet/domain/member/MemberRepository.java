package hello.servlet.domain.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;


// 동시성 문제가 고려되어 있지 않음, 실무에서는 ConcurrentHashMap, AtomicLong 사용을 고려 해야함
public class MemberRepository {
  private static Map<AtomicLong, Member> store = new ConcurrentHashMap<>();
  private static AtomicLong sequence = new AtomicLong(0L);

  private static final MemberRepository instance = new MemberRepository();

  public static MemberRepository getInstance(){
    return instance;
  }
  private MemberRepository(){}

  public Member save(Member member){
    member.setId(new AtomicLong(sequence.incrementAndGet()));
    store.put(member.getId(), member);
    return member;
  }

  public Member findById(AtomicLong id){
    return store.get(id);
  }

  public List<Member> findAll(){
    return new ArrayList<>(store.values());
    //store에 있는 모든 value 값을 새 ArrayList에 담아서 리턴함
    //store에 있는 value값은 건드리지 않고, 복사된 ArrayList의 값만 건드리도록 하기 위해서임 (store 자체를 보호하기 위해서)
  }

  public void clearStore(){
    store.clear();
  }


}
