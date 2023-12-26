# Spring 환경에서 Servlet을 시연하는 이유
- Servlet은 WAS를 직접 설치하고 Servlet 코드를 컴파일 후 빌드, 톰캣 실행 을 통해 실행할 수 있음
  - 하지만 번거로움
- Spring Boot가 톰캣을 내장하고 있기 때문에 톰캣을 로컬에 설치하지 않고도 사용할 수 있음

# HttpServletRequest, HttpServletResponse
- 둘 다 인터페이스임
- 인터페이스인 이유는 톰캣, 제티 등에서 두 인터페이스를 구현하도록 하기 위해서임
  - 톰캣에서의 구현체
    - org.apache.catalina.connector.RequestFacade
    - org.apache.catalina.connector.ResponseFacade

# ?username=kim
- 쿼리 파라미터라고 함
- key-value 형식(?key=value)
- HttpServletRequest.getParameter(key값(String)) 으로 값을 꺼낼 수 있다

# 요청 응답
```
HttpServletResponse.setContentType("text/plane"); // ContentType 설정
HttpServletResponse.setCharacterEncoding("utf-8"); // Encoding 설정
HttpServletResponse.getWriter().write("hello " + username); // ResponseBody 작성
```
- 위의 코드를 통해 수많은 Http Request, Response의 항목을 직접 설정하지 않고 Content-Type, Response Payload만 작성하면 된다(나머지는 브라우저, 톰캣(Servlet)이 작성해줌)

# @WebServlet
- attribute
  - name = 서블릿 이름
  - urlPatterns = URL Mapping
- Mapping된 URL이 호출되면 Servlet Container는 해당 Servlet 객체의 service 메서드를 실행한다 (접근 제어자가 protected인)

# Http Logging
- application.properties 파일에 logging.level.org.apache.coyote.http11=debug 를 입력하여 요청에 대한 Http Header 정보를 볼 수 있다

```
2023-12-27T01:34:51.737+09:00 DEBUG 31772 --- [nio-8080-exec-1] o.a.coyote.http11.Http11InputBuffer      : Before fill(): parsingHeader: [true], parsingRequestLine: [true], parsingRequestLinePhase: [0], parsingRequestLineStart: [0], byteBuffer.position(): [0], byteBuffer.limit(): [0], end: [0]
2023-12-27T01:34:51.741+09:00 DEBUG 31772 --- [nio-8080-exec-1] o.a.coyote.http11.Http11InputBuffer      : Received [GET /hello?username=kim HTTP/1.1
Host: localhost:8080
User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:121.0) Gecko/20100101 Firefox/121.0
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8
Accept-Language: ko-KR,ko;q=0.8,en-US;q=0.5,en;q=0.3
Accept-Encoding: gzip, deflate, br
Connection: keep-alive
Cookie: csrftoken=J8D2VIj1lSidpSMDeXhCHIccfv5mMDyE; Idea-48c23a4c=64c5722b-3d22-4c92-a02b-eaa84fc2811a
Upgrade-Insecure-Requests: 1
Sec-Fetch-Dest: document
Sec-Fetch-Mode: navigate
Sec-Fetch-Site: none
Sec-Fetch-User: ?1
Pragma: no-cache
Cache-Control: no-cache
```
- 성능 저하가 생길 수 있기 때문에 개발 환경에서만 사용