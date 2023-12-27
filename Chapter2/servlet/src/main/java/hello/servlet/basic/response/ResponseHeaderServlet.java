package hello.servlet.basic.response;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "responseHeaderServlet", urlPatterns = "/response-header")
public class ResponseHeaderServlet extends HttpServlet {

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    //HTTP Status Code 생성
    //resp.setStatus(HttpServletResponse.SC_OK); // Http 응답 코드 생성, SC_OK는 응답 코드 200을 의미한다
    //resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); Http 응답 코드 400
    resp.setStatus(HttpServletResponse.SC_FOUND); //Http 응답 코드 302

    // 응답 코드 200 일 때는 Connection: keep-alive, 응답 코드 400일 때는 Connection: close 으로 Response Header가 변경된다

    // HTTP Response Message Header 생성
    resp.setHeader("Content-Type", "text/plain;charset=utf-8"); // Content-Type 지정
    resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // 캐시 무효화를 의미
    resp.setHeader("Pragma", "no-cache"); // 과거의 캐시까지 무효화함
    resp.setHeader("my-header", "hello"); // Custom Header 작성 가능

    // Header의 편의 메서드
    content(resp);

    //Cookie 설정 메서드
    cookie(resp);
    // Set-Cookie:myCookie=good; Max-Age=600; Expires=Wed, 27 Dec 2023 16:56:01 GMT
    // Expires는 WAS에서 추가해준 항목이다

    redirect(resp);
    // sendRedirect()에 설정된 주소로 리다이렉트함

    // Http Message Body 설정
    resp.getWriter().write("Header Setting OK : 헤더 세팅 완료");

    // Postman으로 Request를 날리면 Http Status Code 200 과 함께 세팅한 Header의 내용을 확인할 수 있다

  }

  private void content(HttpServletResponse response) {
    //Content-Type: text/plain;charset=utf-8
    //Content-Length: 2
    
    // 편의 메서드 미사용
    //response.setHeader("Content-Type", "text/plain;charset=utf-8");
    
    //편의 메서드 사용
    response.setContentType("text/plain");
    response.setCharacterEncoding("utf-8");
    // serContentType(), setCharacterEncoding() 두 메서드를 통해 Content-Type과 인코딩을 따로 설정할 수 있다
    //response.setContentLength(2); //(생략시 자동 생성)
  }

  private void cookie(HttpServletResponse response) {
    //Set-Cookie: myCookie=good; Max-Age=600;
    
    //편의 메서드 미사용
    //response.setHeader("Set-Cookie", "myCookie=good; Max-Age=600");
    
    //편의 메서드 사용
    Cookie cookie = new Cookie("myCookie", "good");
    cookie.setMaxAge(600); //600초
    response.addCookie(cookie);
  }

  private void redirect(HttpServletResponse response) throws IOException {
    //Status Code 302
    //Location: /basic/hello-form.html
    
    //편의 메서드 안 쓰는 방식
    //response.setStatus(HttpServletResponse.SC_FOUND); //302
    //response.setHeader("Location", "/basic/hello-form.html");
    
    //편의 메서드 사용
    //response.setStatus(HttpServletResponse.SC_FOUND); //302
    response.sendRedirect("/basic/hello-form.html");
  }
}
