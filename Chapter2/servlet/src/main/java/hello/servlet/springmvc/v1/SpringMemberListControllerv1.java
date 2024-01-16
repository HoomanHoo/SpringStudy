package hello.servlet.springmvc.v1;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import hello.servlet.web.frontcontroller.ModelView;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SpringMemberListControllerv1 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @RequestMapping("/springmvc/v1/members")
    public ModelAndView process(Map<String, String> paraMap) {
        System.out.println("List Controller V3");
        List<Member> members = memberRepository.findAll();
        ModelAndView modelAndView = new ModelAndView("members");
        // modelAndView.getModel().put("members", members);
        modelAndView.addObject("members", members);
        return modelAndView;
    }
}
