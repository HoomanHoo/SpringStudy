package hello.servlet.web.servletmvc;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "mvcMemberFormServlet", urlPatterns = "/servlet-mvc/members/new-form")
public class MvcMemberFormServlet extends HttpServlet {

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    //JSP 호출
    String viewPath = "/WEB-INF/views/new-form.jsp";
    RequestDispatcher dispatcher = req.getRequestDispatcher(viewPath);
    dispatcher.forward(req, resp);
    //RequestDispatcher -> 컨트롤러에서 JSP등을 호출하기 위해 사용하는 객체
    // RequestDispatcher.forward(ServletRequest, ServletResponse)
    // -> 다른 서블릿이나 JSP로 이동시키는 메서드로, 서버 내부에서 다시 호출이 발생함
    // 서버 내부에서 다시 호출을 한다는 말은 forward 메서드로 JSP를 호출, JSP에서 응답 메세지를 만들어서 클라이언트로 반환하는 과정을 말함
    // WEB-INF는 다른 디렉토리와는 다르게 웹 브라우저에서 파일 경로 입력을 통해 바로 접근할 수 없는 경로이다
    // redirect() 메서드는 클라이언트에 응답이 간 뒤 redirect 경로를 클라이언트가 요청하는 과정을 거침
    // -> 클라이언트가 이동을 인지할 수 있고 URL 경로도 변경됨
//    Defines an object that receives requests from the client and sends them to any resource
//    (such as a servlet, HTML file, or JSP file) on the server.
//    The servlet container creates the RequestDispatcher object,
//    which is used as a wrapper around a server resource located at a particular path or given by a particular name.
//    This interface is intended to wrap servlets, but a servlet container can create RequestDispatcher objects to wrap any type of resource.
  }

}
