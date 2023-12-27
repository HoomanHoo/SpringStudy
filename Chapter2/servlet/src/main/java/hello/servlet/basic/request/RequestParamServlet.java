package hello.servlet.basic.request;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@WebServlet(name = "RequestParamServlet", urlPatterns = "/request-param")
public class RequestParamServlet extends HttpServlet {
  /*
  parameter 전송 기능
  username = hello, age = 20
   */
  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    System.out.println("service start");
  Enumeration<String> paramNames = req.getParameterNames();
  paramNames.asIterator().forEachRemaining(paramName -> System.out.println(paramName + "=" + req.getParameter(paramName)));
    System.out.println("service end");

//    http://192.168.0.2:8080/request-param
//    service start
//    service end

//    http://192.168.0.2:8080/request-param?username=hello&age=20
//    service start
//    username=hello
//    age=20
//    service end

//    http://192.168.0.2:8080/request-param?username=hello&age=20
    System.out.println("username =" + req.getParameter("username"));
    System.out.println("age =" + req.getParameter("age"));
//    username =hello
//    age =20

//    http://192.168.0.2:8080/request-param?username=hello&age=20&username=hello2
    String[] usernames = req.getParameterValues("username");  // 같은 이름의 여러 값이 들어올 경우 getParameterValues() 를 사용한다
    for (String username : usernames) {
      System.out.println(username);
    }
//    hello
//    hello2


    resp.getWriter().write("ok");
  }
}
