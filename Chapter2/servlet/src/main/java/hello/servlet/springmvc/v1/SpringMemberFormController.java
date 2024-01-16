package hello.servlet.springmvc.v1;

import java.lang.reflect.Method;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller // 스프링이 자동으로 스프링 빈으로 등록(내부에 @Component가 있기 때문에 컴포넌트 스캔 대상이 되기 때문) -> 스프링 MVC에서 어노테이션 기반 컨트롤러로 인식
public class SpringMemberFormController {
    @RequestMapping("/springmvc/v1/members/new-form")   // 요청 정보 매핑 -> 매핑한 URL로 요청이 들어올 경우 해당 메서드가 호출됨
    public ModelAndView process() throws NoSuchMethodException {
        CustomAnnotationUse use = new CustomAnnotationUse();
        final Method method = use.getClass().getMethod("test");
        CustomAnnotation anno = method.getAnnotation(CustomAnnotation.class);
        String test = anno.test();
        System.out.println(test);
        return new ModelAndView("new-form");
    }
    
    // @Controller나 @RequestMapping이 클래스 레벨에 붙어있어야 어노테이션 기반 컨트롤러로 인식함

}
