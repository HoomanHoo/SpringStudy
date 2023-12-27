package hello.servlet.basic.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.servlet.basic.HelloData;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "responseJsonServlet", urlPatterns = "/response-json")
public class ResponseJsonServlet extends HttpServlet {
  private ObjectMapper objectMapper = new ObjectMapper();
  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    //Content-Type:application/json
    resp.setContentType("application/json");
    resp.setCharacterEncoding("utf-8");

    HelloData helloData = new HelloData();
    helloData.setUsername("엄");
    helloData.setAge(22);

    //{"username":"엄", "age":22}
    String result = objectMapper.writeValueAsString(helloData); // JSON 포멧으로 객체를 바꿔줌
//    resp.getWriter().write(result);
    resp.getOutputStream().write(result.getBytes(StandardCharsets.UTF_8)); // json 응답시 charset 파라미터 추가 안 하기 위해 사용

//    Response Header
//    Connection:keep-alive
//    Content-Length:27
//    Content-Type:application/json;charset=utf-8
//    Date:Wed, 27 Dec 2023 17:35:21 GMT
//    Keep-Alive:timeout=60
    
//    Response Body
//    {"username":"엄","age":22}


  }
}
