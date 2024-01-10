package hello.servlet.web.frontcontroller.v1;

import hello.servlet.web.frontcontroller.v1.controller.MemberFormControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberListControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberSaveControllerV1;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV1", urlPatterns = "/front-controller/v1/*") // *는 모든 인자를 다 받는다는 의미이다
public class FrontControllerServletV1 extends HttpServlet {

  private Map<String, ControllerV1> controllerMap = new HashMap<>();

  // 실행하고자 하는 컨트롤러를 Map 형식으로 URL 과 함께 저장해둔다
  public FrontControllerServletV1(){
    controllerMap.put("/front-controller/v1/members/new-form", new MemberFormControllerV1());
    controllerMap.put("/front-controller/v1/members/save", new MemberSaveControllerV1());
    controllerMap.put("/front-controller/v1/members", new MemberListControllerV1());
  }

  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    System.out.println("FrontControllerServletV1.service");

    // 요청된 URI 획득
    String requestURI = request.getRequestURI();
    
    // 요청된 URI와 매칭되는 URL-Controller 쌍이 있는지 탐색
    ControllerV1 controller = controllerMap.get(requestURI);
    
    // 매칭되는 URL이 없을 경우 404 상태코드 리턴
    if(controller == null){
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return;
    }
    
    // 매칭되는 URL이 있을 경우 해당 URL과 쌍을 이루는 Controller의 process 메서드 실행
    controller.process(request, response);
  }
}
