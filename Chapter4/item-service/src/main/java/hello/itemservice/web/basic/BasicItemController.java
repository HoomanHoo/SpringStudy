package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/basic/items")
//@RequiredArgsConstructor -> final인 필드에 대한 생성자를 생성해줌
public class BasicItemController {

    private final ItemRepository itemRepository;

    @Autowired  // Spring에 등록된 Bean을 주입해줌 -> 생성자의 요소가 하나일 경우에는 생략 가능
    public BasicItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping
    public String items(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm(){
        return "basic/addForm";
    }

//    @PostMapping("/add")
//    public String addItemV1(@RequestParam String itemName, @RequestParam int price, @RequestParam int quantity, Model model){
//        Item item = new Item();
//        item.setItemName(itemName);
//        item.setPrice(price);
//        item.setQuantity(quantity);
//        itemRepository.save(item);
//        model.addAttribute("item", item);
//        return "basic/item";
//    }
//    @PostMapping("/add")
//    public String addItemV2(@ModelAttribute(name = "item") Item item, Model model){
//        // @ModelAttribute 어노테이션을 붙일 경우 해당 객체의 setter를 호출하여 데이터를 바인딩 한다
//        // @ModelAttribute는 Model 객체에 값을 담는 것까지 처리해준다
//        //  -> name 속성에 지정한 이름을 key로 하여 model 객체에 들어감
//        itemRepository.save(item);
//        //model.addAttribute("item", item);
//        return "basic/item";
//    }

//    @PostMapping("/add")
//    public String addItemV3(@ModelAttribute Item item){
//        // @ModelAttribute의 name값을 지정하지 않을 경우 클래스명의 카멜스페이스 형태가 기본 name값이 된다
//        // Model에 데이터를 담는 것은 여전히 자동으로 처리됨(매개변수에 Model을 선언해주지 않아도 됨)
//        itemRepository.save(item);
//
//        return "basic/item";
//    }
//    @PostMapping("/add")
//    public String addItemV4(Item item){
//        // @ModelAttribute의 name값을 지정하지 않을 경우 클래스명의 카멜스페이스 형태가 기본 name값이 된다
//        // Model에 데이터를 담는 것은 여전히 자동으로 처리됨(매개변수에 Model을 선언해주지 않아도 됨)
//        // ModelAttribute를 생략해도 동일하게 동작함
//        itemRepository.save(item);
//
//        return "basic/item";
//    }

//    @PostMapping("/add")
//    public String addItemV5(Item item){
//        itemRepository.save(item);
//
//        return "redirect:/basic/items/" + item.getId();
//        // URL에 바로 변수를 더하는 것은 인코딩이 안되기 때문에 위험함(한글 등)
//    }

    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes){
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/items/{itemId}";
        // RedirectAttributes에 attribute를 추가한 뒤 redirect URL에 바인딩을 걸어줄 수 있으며, 
        // URL에 선언하지 않은 attribute는 쿼리 파라미터 형식으로 치환된다

    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item){
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
        // PathVariable의 데이터를 Redirect에서도 사용할 수 있음(RedirectAttributes 없이도)
    }
    /**
     * POST 요청에서 HTML을 리턴할 경우, 새로고침 했을 때 POST 요청이 다시 전송됨
     * -> 웹 브라우저는 새로 고침을 하면 마지막으로 서버에 전송한 데이터를 다시 전송하기 때문
     * -> 따라서 POST 요청에 - 리다이렉트 - GET 요청으로 처리를 하면 POST 요청이 다시 전송되는 것을 막을수 있음
     * (PRG 패턴)
     */

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init(){
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }
}
