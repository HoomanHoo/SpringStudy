package hello.servlet.web.frontcontroller.v4;

import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "frontControllerServletV4", urlPatterns = "/front-controller/v4/*") // *는 모든 인자를 다 받는다는 의미이다
public class FrontControllerServletV4 extends HttpServlet {

  private Map<String, ControllerV4> controllerMap = new HashMap<>();

  // 실행하고자 하는 컨트롤러를 Map 형식으로 URL 과 함께 저장해둔다
  public FrontControllerServletV4(){
    controllerMap.put("/front-controller/v4/members/new-form", new MemberFormControllerV4());
    controllerMap.put("/front-controller/v4/members/save", new MemberSaveControllerV4());
    controllerMap.put("/front-controller/v4/members", new MemberListControllerV4());
  }

  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    System.out.println("FrontControllerServletV4.service");

    // 요청된 URI 획득
    String requestURI = request.getRequestURI();
    
    // 요청된 URI와 매칭되는 URL-Controller 쌍이 있는지 탐색
    ControllerV4 controller = controllerMap.get(requestURI);
    
    // 매칭되는 URL이 없을 경우 404 상태코드 리턴
    if(controller == null){
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      System.out.println("URL 없음");
      return;
    }
    System.out.println("URL 있음");
    // paramMap에 요청 정보 모두 담기 -> 메서드를 따로 뽑는 이유는 호출하는 객체들의 레벨을 맞춰준다?
    Map<String, String> paramMap = createParamMap(request);
    Map<String, Object> model = new HashMap<>();
    // 매칭되는 URL이 있을 경우 해당 URL과 쌍을 이루는 Controller의 process 메서드 실행
    // 공통 로직 실행 (Controller 내의 비즈니스 로직&화면 렌더링)
    // ModelView 객체는 viewName(String)과 model(Map) 으로 구성되어있음
    // MyView view = controller.process(request, response);
    // view.render(request, response);

    String viewName = controller.process(paramMap, model);
    // model은 매개변수로 전달되기 때문에 컨트롤러 구현체에서 접근하는 model은 front controller에서 생성된 model과 동일한 메모리 주소값으로 접근함
    System.out.println(viewName);
    System.out.println(model.toString());
    List<Integer> x = new ArrayList<>();
    x.add(1);
    test(x);
    System.out.println(x.get(0));
    // jsp 파일 이름을 얻고 이를 물리적인 이름으로 변환함
//    String viewName = modelView.getViewName();
    MyView view = viewResolver(viewName);
    
    // 화면 렌더링, 클라이언트로의 응답
    view.render(model, request, response);

  }

  private MyView viewResolver(String viewName){
    return new MyView("/WEB-INF/views/" + viewName + ".jsp");
  }

  private Map<String, String> createParamMap(HttpServletRequest request){
    Map<String, String> paramMap = new HashMap<>();
    request.getParameterNames().asIterator()
        .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));

    return paramMap;
  }

  public String test(List<Integer> x){
    int y = x.get(0);
    y += 10;
    x.add(0, y);
    return "test";
  }
}
