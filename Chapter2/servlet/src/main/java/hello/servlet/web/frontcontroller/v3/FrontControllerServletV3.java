package hello.servlet.web.frontcontroller.v3;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV3", urlPatterns = "/front-controller/v3/*") // *는 모든 인자를 다 받는다는 의미이다
public class FrontControllerServletV3 extends HttpServlet {

  private Map<String, ControllerV3> controllerMap = new HashMap<>();

  // 실행하고자 하는 컨트롤러를 Map 형식으로 URL 과 함께 저장해둔다
  public FrontControllerServletV3(){
    controllerMap.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
    controllerMap.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
    controllerMap.put("/front-controller/v3/members", new MemberListControllerV3());
  }

  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    System.out.println("FrontControllerServletV3.service");

    // 요청된 URI 획득
    String requestURI = request.getRequestURI();
    
    // 요청된 URI와 매칭되는 URL-Controller 쌍이 있는지 탐색
    ControllerV3 controller = controllerMap.get(requestURI);
    
    // 매칭되는 URL이 없을 경우 404 상태코드 리턴
    if(controller == null){
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return;
    }

    // paramMap에 요청 정보 모두 담기 -> 메서드를 따로 뽑는 이유는 호출하는 객체들의 레벨을 맞춰준다?
//    Map<String, String> paramMap = new HashMap<>();
//    request.getParameterNames().asIterator()
//        .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
    Map<String, String> paramMap = createParamMap(request);
    
    // 매칭되는 URL이 있을 경우 해당 URL과 쌍을 이루는 Controller의 process 메서드 실행
    // 공통 로직 실행 (Controller 내의 비즈니스 로직&화면 렌더링)
    // ModelView 객체는 viewName(String)과 model(Map) 으로 구성되어있음
    // MyView view = controller.process(request, response);
    // view.render(request, response);

    ModelView modelView = controller.process(paramMap);
    
    // jsp 파일 이름을 얻고 이를 물리적인 이름으로 변환함
    String viewName = modelView.getViewName();
    MyView view = viewResolver(viewName);
    
    // 화면 렌더링, 클라이언트로의 응답
    view.render(modelView.getModel(), request, response);

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
}
