package hello.itemservice.domain.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class ItemRepository {

    private static final Map<Long, Item> store = new HashMap<>();   // 데이터를 저장할 용도로 HashMap을 사용하지 않은 ConcurrentHashMap 사용해야함 -> 멀티 스레드 환경에서 사용하기 때문
    private static long sequence = 0L;  // long 역시 동시성 문제가 발생하기 때문에 AtomicLong 등을 사용해야함 -> HashMap과 같이 여러 스레드에서 접근할 경우 값이 꼬일 수 있음

    public Item save(Item item){
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id){
        return store.get(id);
    }

    public List<Item> findAll(){
        return new ArrayList<>(store.values()); // store.values() 바로 리턴 가능하나 컬렉션 객체로 반환하는 이유는 원본 데이터에 접근하지 못하도록 하기 위해서
    }

    public void update(Long itemId, Item updateParam){
        Item findItem = findById(itemId);
        findItem.setItemName(updateParam.getItemName());  // Item의 id 필드는 사용하지 않기 때문에 정석대로라면 name price quantity만 가지고 있는 DTO를 만드어야함
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    public void clearStore(){
        store.clear();
    }

}
