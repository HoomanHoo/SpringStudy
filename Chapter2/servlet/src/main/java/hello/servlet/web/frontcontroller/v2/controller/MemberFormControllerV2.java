package hello.servlet.web.frontcontroller.v2.controller;

import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v2.ControllerV2;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MemberFormControllerV2 implements ControllerV2 {

  @Override
  public MyView process(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    //JSP 호출
//    String viewPath = "/WEB-INF/views/new-form.jsp";
//    RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
//    dispatcher.forward(request, response);
  // JSP 호출을 MyView에서 담당하기 때문에 Controller는 MyView 객체 생성 시 JSP 파일 경로만 넣어주면 된다
    return new MyView("/WEB-INF/views/new-form.jsp");
  }
}
