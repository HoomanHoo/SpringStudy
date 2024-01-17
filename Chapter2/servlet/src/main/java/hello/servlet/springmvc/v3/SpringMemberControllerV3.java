package hello.servlet.springmvc.v3;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/springmvc/v3/members")
public class SpringMemberControllerV3 {
    private MemberRepository memberRepository = MemberRepository.getInstance();

    //http://localhost:8080/springmvc/v3/members/new-form
//    @RequestMapping(value = "/new-form", method = RequestMethod.GET)   // 요청 정보 매핑 -> 매핑한 URL로 요청이 들어올 경우 해당 메서드가 호출됨
    // method 속성 인자로 RequestMethod.GET 등을 설정하여 받고자 하는 요청 메서드만 받도록 할 수 있음
    @GetMapping("new-form") // GET 방식 요청만 받도록 함
    public String newForm() {
        System.out.println("newForm Controller V2");
        return "new-form";
        //return new ModelAndView("new-form");
        // ModelAndView를 반환해도 되고 String을 반환해도 된다
    }

    //http://localhost:8080/springmvc/v3/members/save
//    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @PostMapping("/save")
    // 사이드 이펙트가 발생하지 않도록 받고자 하는 메서드 타입만 받을 수 있도록 설계를 하는 것이 좋은 설계다
    public String save(@RequestParam("username") String username, @RequestParam("age") int age, Model model) {
        // @RequestParam("파라미터 이름") 을 이용하여 HttpServletRequest 객체 없이 요청 파라미터를 바로 받을 수 있다
        System.out.println("Save Controller V2");
//        String username = request.getParameter("username");
//        int age = Integer.parseInt(request.getParameter("age"));

        Member member = new Member(username, age);
        memberRepository.save(member);

//        ModelAndView modelAndView = new ModelAndView("save-result");
//        // modelAndView.getModel().put("member", member);
//        modelAndView.addObject("member", member);
        model.addAttribute("member", member);
        // Model 객체를 이용하여 템플릿 엔진에 데이터를 전달할 수 있다
//        return modelAndView;
        return "save-result";
    }

    //http://localhost:8080/springmvc/v3/members
    //@RequestMapping // 인자를 아무것도 설정하지 않으면 클래스 레벨에 설정된 URL이 그대로 매핑된다
    @GetMapping
    public String list(Model model) {
        System.out.println("List Controller V2");
        List<Member> members = memberRepository.findAll();
        model.addAttribute("members", members);
        return "members";
    }
}
