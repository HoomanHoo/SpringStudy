package hello.servlet.web.frontcontroller.v1.controller;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import hello.servlet.web.frontcontroller.v1.ControllerV1;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class MemberListControllerV1 implements ControllerV1 {
  private MemberRepository memberRepository = MemberRepository.getInstance();
  @Override
  public void process(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    // 비즈니스 로직
    List<Member> members = memberRepository.findAll();

    // Model(HttpServletRequest 객체)에 데이터 적재
    request.setAttribute("members", members);

    // JSP 호출
    String viewPath = "/WEB-INF/views/members.jsp";
    RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
    dispatcher.forward(request, response);
  }
}
