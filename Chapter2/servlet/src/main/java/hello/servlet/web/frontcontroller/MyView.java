package hello.servlet.web.frontcontroller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class MyView {
  private String viewPath;

  public MyView(String viewPath) {
    this.viewPath = viewPath;
  }

  // 렌더링 로직
  // JSP 이외의 템플릿 엔진도 렌더링 하고자 할 때는 MyView를 인터페이스로 만든 뒤 이를 각 템플린 엔진에 맞게 구현할 수 있음
  public void render(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
    dispatcher.forward(request, response);
  }

  // 템플릿 엔진에 전달해야할 데이터가 있을 경우 Map에 데이터를 key-value 형식으로 담은 뒤 이를 HttpServletRequest 객체에 담아줘야함
  public void render(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
//    model.forEach((key, value) -> request.setAttribute(key, value));
    modelToRequestAttribute(model, request);
    RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
    System.out.println(viewPath);
    dispatcher.forward(request, response);
  }

  // model의 데이터를 request 객체에 전부 집어넣는 메서드
  private void modelToRequestAttribute(Map<String, Object> model, HttpServletRequest request){
    //    model.forEach((key, value) -> request.setAttribute(key, value));
    model.forEach(request::setAttribute);
  }
}
