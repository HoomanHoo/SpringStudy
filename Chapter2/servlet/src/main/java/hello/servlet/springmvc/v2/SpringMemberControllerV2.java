package hello.servlet.springmvc.v2;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/springmvc/v2/members")
public class SpringMemberControllerV2 {
    private MemberRepository memberRepository = MemberRepository.getInstance();

    //http://localhost:8080/springmvc/v2/members/new-form
    @RequestMapping("/new-form")   // 요청 정보 매핑 -> 매핑한 URL로 요청이 들어올 경우 해당 메서드가 호출됨
    public ModelAndView newForm() {
        System.out.println("newForm Controller V2");
        return new ModelAndView("new-form");
    }

    //http://localhost:8080/springmvc/v2/members/save
    @RequestMapping("/save")
    public ModelAndView save(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("Save Controller V2");
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        Member member = new Member(username, age);
        memberRepository.save(member);

        ModelAndView modelAndView = new ModelAndView("save-result");
        // modelAndView.getModel().put("member", member);
        modelAndView.addObject("member", member);
        return modelAndView;
    }

    //http://localhost:8080/springmvc/v2/members
    @RequestMapping // 인자를 아무것도 설정하지 않으면 클래스 레벨에 설정된 URL이 그대로 매핑된다
    public ModelAndView list(Map<String, String> paraMap) {
        System.out.println("List Controller V2");
        List<Member> members = memberRepository.findAll();
        ModelAndView modelAndView = new ModelAndView("members");
        // modelAndView.getModel().put("members", members);
        modelAndView.addObject("members", members);
        return modelAndView;
    }
}
