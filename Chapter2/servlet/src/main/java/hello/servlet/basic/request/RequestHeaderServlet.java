package hello.servlet.basic.request;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@WebServlet(name = "requestHeaderServlet", urlPatterns = "/request-header")
public class RequestHeaderServlet extends HttpServlet {

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    printStartLine(req);
//    2023-12-27T02:23:29.366+09:00 DEBUG 36304 --- [nio-8080-exec-1] o.a.coyote.http11.Http11InputBuffer      : Received [GET /request-header HTTP/1.1
//    Host: localhost:8080
//    User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:121.0) Gecko/20100101 Firefox/121.0
//    Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8
//  Accept-Language: ko-KR,ko;q=0.8,en-US;q=0.5,en;q=0.3
//  Accept-Encoding: gzip, deflate, br
//  Connection: keep-alive
//  Cookie: csrftoken=J8D2VIj1lSidpSMDeXhCHIccfv5mMDyE; Idea-48c23a4c=64c5722b-3d22-4c92-a02b-eaa84fc2811a
//  Upgrade-Insecure-Requests: 1
//  Sec-Fetch-Dest: document
//  Sec-Fetch-Mode: navigate
//  Sec-Fetch-Site: none
//  Sec-Fetch-User: ?1
//
//  ]
//  --- REQUEST-LINE - start ---
//  request.getMethod() = GET
//  request.getProtocol() = HTTP/1.1
//  request.getScheme() = http
//  request.getRequestURL() = http://localhost:8080/request-header
//  request.getRequestURI() = /request-header
//  request.getQueryString() = null
//  request.isSecure() = false
//  --- REQUEST-LINE - end ---

    printHeaders(req);

//    --- Headers - start ---
//    host: localhost:8080
//    user-agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:121.0) Gecko/20100101 Firefox/121.0
//    accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8
//    accept-language: ko-KR,ko;q=0.8,en-US;q=0.5,en;q=0.3
//    accept-encoding: gzip, deflate, br
//    connection: keep-alive
//    cookie: csrftoken=J8D2VIj1lSidpSMDeXhCHIccfv5mMDyE; Idea-48c23a4c=64c5722b-3d22-4c92-a02b-eaa84fc2811a
//    upgrade-insecure-requests: 1
//    sec-fetch-dest: document
//    sec-fetch-mode: navigate
//    sec-fetch-site: none
//    sec-fetch-user: ?1
//    --- Headers - end ---

    printHeaderUtils(req);

//    --- Header ���� ��ȸ start ---
//    [Host ���� ��ȸ]
//    request.getServerName() = localhost
//    request.getServerPort() = 8080
//
//    [Accept-Language ���� ��ȸ]
//    locale = ko_KR
//    locale = ko
//    locale = en_US
//    locale = en
//    request.getLocale() = ko_KR
//
//    [cookie ���� ��ȸ]
//    csrftoken: J8D2VIj1lSidpSMDeXhCHIccfv5mMDyE
//    Idea-48c23a4c: 64c5722b-3d22-4c92-a02b-eaa84fc2811a
//
//    [Content ���� ��ȸ]
//    request.getContentType() = null
//    request.getContentLength() = -1
//    request.getCharacterEncoding() = UTF-8
//    --- Header ���� ��ȸ end ---

    printEtc(req);

//    --- ��Ÿ ��ȸ start ---
//    [Remote ����]
//    request.getRemoteHost() = 127.0.0.1
//    request.getRemoteAddr() = 127.0.0.1
//    request.getRemotePort() = 3067
//
//    [Local ����]
//    request.getLocalName() = 127.0.0.1
//    request.getLocalAddr() = 127.0.0.1
//    request.getLocalPort() = 8080
//    --- ��Ÿ ��ȸ end ---
  }

  private void printStartLine(HttpServletRequest request) {
    System.out.println("--- REQUEST-LINE - start ---");
    System.out.println("request.getMethod() = " + request.getMethod()); // Http Method
    System.out.println("request.getProtocol() = " + request.getProtocol()); //Http Protocol
    System.out.println("request.getScheme() = " + request.getScheme()); // Scheme
    System.out.println("request.getRequestURL() = " + request.getRequestURL()); // http://localhost:8080/request-header (Full URL)
    System.out.println("request.getRequestURI() = " + request.getRequestURI()); // /request-header  (URI)
    System.out.println("request.getQueryString() = " + request.getQueryString());  //Query String (Query Parameter 라고도 함)
    System.out.println("request.isSecure() = " + request.isSecure()); //https 사용 유무
    System.out.println("--- REQUEST-LINE - end ---");
    System.out.println();
  }

  private void printHeaders(HttpServletRequest request){
    System.out.println("--- Headers - start ---");

//   Enumeration<String> headerNames = request.getHeaderNames();  // Http Request Header의 모든 정보를 가져온다
//   while (headerNames.hasMoreElements()) {
//     String headerName = headerNames.nextElement();
//     System.out.println(headerName + ": " + request.getHeader(headerName));
//   }

    request.getHeaderNames().asIterator() //asIterator는 Enumeration을 Iterator 타입으로 바꿔준다
           .forEachRemaining(headerName -> System.out.println(headerName + ": "
               + request.getHeader(headerName)));
    //request.getHeader("host");  // Header 정보 중 하나만 꺼내고 싶으면 Header 항목의 이름을 변수로 주면 된다
    System.out.println("--- Headers - end ---");
    System.out.println();
  }

  //Header 편리한 조회
  private void printHeaderUtils(HttpServletRequest request) {
    System.out.println("--- Header 편의 조회 start ---");
    System.out.println("[Host 편의 조회]");
    System.out.println("request.getServerName() = " + request.getServerName()); //Host 헤더 서버 이름
    System.out.println("request.getServerPort() = " + request.getServerPort()); //Host 헤더 서버 포트
    System.out.println();
    System.out.println("[Accept-Language 편의 조회]");
    request.getLocales().asIterator()
           .forEachRemaining(locale -> System.out.println("locale = " +
               locale));
    System.out.println("request.getLocale() = " + request.getLocale());
    System.out.println();
    System.out.println("[cookie 편의 조회]");
    if (request.getCookies() != null) {
      for (Cookie cookie : request.getCookies()) {
        System.out.println(cookie.getName() + ": " + cookie.getValue());
      }
    }
    System.out.println();
    System.out.println("[Content 편의 조회]");
    System.out.println("request.getContentType() = " + request.getContentType()); // body에 데이터를 담지 않으면 Content-Type 설정이 안된다
    System.out.println("request.getContentLength() = " + request.getContentLength());
    System.out.println("request.getCharacterEncoding() = " + request.getCharacterEncoding());
    System.out.println("--- Header 편의 조회 end ---");
    System.out.println();
  }

  //기타 정보
  private void printEtc(HttpServletRequest request) {
    System.out.println("--- 기타 조회 start ---");
    System.out.println("[Remote 정보]");
    System.out.println("request.getRemoteHost() = " +
        request.getRemoteHost()); //
    System.out.println("request.getRemoteAddr() = " +
        request.getRemoteAddr()); //
    System.out.println("request.getRemotePort() = " +
        request.getRemotePort()); //
    System.out.println();
    System.out.println("[Local 정보]");
    System.out.println("request.getLocalName() = " + request.getLocalName()); //
    System.out.println("request.getLocalAddr() = " + request.getLocalAddr()); //
    System.out.println("request.getLocalPort() = " + request.getLocalPort()); //
    System.out.println("--- 기타 조회 end ---");
    System.out.println();
  }

}
