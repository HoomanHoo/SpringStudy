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

@WebServlet(name = "mvcMemberSaveServlet", urlPatterns = "/servlet-mvc/members/save")
public class MvcMemberSaveServlet extends HttpServlet {
  
  private MemberRepository memberRepository = MemberRepository.getInstance();
  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    // 요청 메세지 파싱
    String username = req.getParameter("username");
    int age = Integer.parseInt(req.getParameter("age"));

    // 비즈니스 로직
    Member member = new Member(username, age);
    memberRepository.save(member);
    
    // Model에 데이터 보관
    req.setAttribute("member", member);
    // HttpServletRequest 객체는 Map 형태의 저장소를 가지고 있음

    // JSP 호출
    String viewPath = "/WEB-INF/views/save-result.jsp";
    RequestDispatcher dispatcher = req.getRequestDispatcher(viewPath);
    dispatcher.forward(req, resp);
  }
}
