package hello.servlet.springmvc.old;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

@Component("/springmvc/old-controller") // spring bean의 이름을 url 형식으로 지정 -> 호출 가능함
public class OldController implements Controller {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
        throws Exception {
        System.out.println("OldController.handleRequest");
        // return null;
        return new ModelAndView("new-form");    // 논리 이름으로 뷰 리졸버를 호출함
    }
    /*
    스프링 빈의 이름으로 핸들러를 찾을 수 있는 핸들러 매핑이 필요함
    핸들러 매핑을 통해 찾은 핸들러를 실행할 수 있는 핸들러 어댑터가 필요
    Controller 인터페이스를 실행할 핸들러 어댑터를 찾고 실행해야함
    스프링 부트 사용시 핸들러 매핑과 어댑터를 자동 등록해줌
    RequestMappingHandlerMapping은 어노테이션 기반 컨트롤러인 @RequestMapping에서 사용 -> 최우선순위 등록
    BeanNameUrlHandlerMapping 은 스프링 빈의 이름으로 핸들러를 찾음 -> 스프링 빈의 이름을 url 형식으로 해두었으며 어노테이션 기반 컨트롤러가 아니기 때문에 이 매핑을 사용
    RequestMappingHandlerAdapter -> @requestMapping에서 사용 -> 최우선순위 등록
    HttpRequestHandlerAdapter -> HttpRequestHandler 처리
    SimpleControllerHandlerAdapter -> Controller 인터페이스 처리
    핸들러 매핑으로 핸들러 조회 -> 핸들러 어댑터 조회 -> 핸들러 어댑터 실행 의 과정으로 컨트롤러 실행을 함
     */
    /*
    뷰 리졸버를 찾는 경우 
    스프링부트는 다양한 뷰 리졸버를 자동으로 등록함
    BeanNameViewResolver -> 빈 이름으로 뷰를 찾아서 반환
    InternalResourceViewResolver -> JSP 파일 처리할 수 있는 뷰 반환(InternalResourceView, JSTL 라이브러리 있는 경우 JSTLView를 반환)
    ThymeleafViewResolver -> 타임리프를 사용할 수 있는 뷰 반환 (JSP 이외의 뷰 템플릿은 forward() 없이 바로 렌더링됨)
        -> ThymeleafViewResolver를 사용하기 위해서는 스프링 빈 등록을 하거나 타임리프 라이브러리를 메이븐이나 그레이들에 등록하면 됨
    InternalResourceView는 forward()를 호출하여 처리할 수 있는 JSP 등을 렌더링할 때 사용
    뷰 리졸버 탐색 -> 뷰 반환 -> 화면 렌더링 과정으로 화면 렌더링을 수행함
     */
}
