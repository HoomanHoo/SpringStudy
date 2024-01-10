package hello.servlet.web.frontcontroller.v2.controller;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v2.ControllerV2;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MemberSaveControllerV2 implements ControllerV2 {

  private MemberRepository memberRepository = MemberRepository.getInstance();

  @Override
  public MyView process(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
      // 요청 메세지 파싱
      String username = request.getParameter("username");
      int age = Integer.parseInt(request.getParameter("age"));

      // 비즈니스 로직
      Member member = new Member(username, age);
      memberRepository.save(member);

      // Model에 데이터 보관
      request.setAttribute("member", member);
      // HttpServletRequest 객체는 Map 형태의 저장소를 가지고 있음

      // JSP 호출
//      String viewPath = "/WEB-INF/views/save-result.jsp";
//      RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
//      dispatcher.forward(request, response);

        return new MyView("/WEB-INF/views/save-result.jsp");
    }

}



