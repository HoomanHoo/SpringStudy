package hello.servlet.basic.response;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "responseHtmlServlet", urlPatterns = "/response-html")
public class ResponseHtmlServlet extends HttpServlet {

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    //Content-Type:text/html;charset=utf-8
    resp.setContentType("text/html");
    resp.setCharacterEncoding("utf-8");

    PrintWriter writer = resp.getWriter();
    writer.println("<html>");
    writer.println("<a href='https://www.naver.com'>naver</a>");
    writer.println("</html>");

    // 응답 메세지
    // Header
//    Connection:keep-alive
//    Content-Length:60
//    Content-Type:text/html;charset=utf-8
//    Date:Wed, 27 Dec 2023 17:27:25 GMT
//    Keep-Alive:timeout=60
//
    // Body
//    <html>
//    <a href='https://www.naver.com'>naver</a>
//    </html>
  }
}
