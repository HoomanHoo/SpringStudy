package hello.springmvc.basic.response;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ResponseViewController {

    @RequestMapping("/response-view-v1")
    public ModelAndView responseViewV1(){
        ModelAndView modelAndView = new ModelAndView("response/hello")
            .addObject("data", "hello");
        return modelAndView;
    }

    @RequestMapping("/response-view-v2")
    public String responseViewV2(Model model){
        // ModelAndView를 생성하여 리턴하는 대신 Model 객체를 매개변수로 받고 뷰 템플릿의 논리 이름을 반환할 수 있다 -> 뷰 리졸버 실행
        // @ResponseBody 를 메서드에 붙이면 리턴한 문자열을 뷰 템플릿 논리 이름으로 인식하지 않고 문자열 그대로 브라우저에 출력한다 -> 뷰 리졸버 미실행
        model.addAttribute("data", "response-view-v2");
        return "response/hello";
    }

    @RequestMapping("/response/hello")
    public void responseViewV3(Model model){
        // @RequestMapping에 매핑한 경로가 뷰 템플릿의 논리적 경로와 같을 경우 뷰 템플릿을 자동으로 매핑해준다
        // Http 메세지 바디를 처리하는 매개변수가 없을 경우에 동작
        // -> 코드가 불명확해지기 때문에 권장하지는 않음
        model.addAttribute("data", "response-view-v3 -> response/hello");
    }

}
