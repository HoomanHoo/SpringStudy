package hello.servlet.springmvc.old;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestHandler;

@Component("/springmvc/request-handler")
public class MyHttpRequestHandler implements HttpRequestHandler {
// Servlet과 가장 유사한 방식 -> 구현체 내에서 응답/JSP or Tymeleaf에 전달까지 끝내야함
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        System.out.println("MyHttpRequestHandler.handlerRequest");
    }
    /*
    BeanNameUrlHandlerMapping -> HttpRequestHandlerAdapter -> MyHttpRequestHandler 순으로 실행이 된다
    핸들러 매핑 탐색 -> 핸들러 어댑터 -> 핸들러(컨트롤러) 실행
     */
}
