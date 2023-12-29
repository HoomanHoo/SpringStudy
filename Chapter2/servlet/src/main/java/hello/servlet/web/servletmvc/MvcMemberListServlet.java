package hello.servlet.web.servletmvc;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "mvcMemberListServlet", urlPatterns = "/servlet-mvc/members")
public class MvcMemberListServlet extends HttpServlet {

  private MemberRepository memberRepository = MemberRepository.getInstance();
  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    // 비즈니스 로직
    List<Member> members = memberRepository.findAll();
    
    // Model(HttpServletRequest 객체)에 데이터 적재
    req.setAttribute("members", members);
    
    // JSP 호출
    String viewPath = "/WEB-INF/views/members.jsp";
    RequestDispatcher dispatcher = req.getRequestDispatcher(viewPath);
    dispatcher.forward(req, resp);
  }
}
