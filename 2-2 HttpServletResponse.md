# Chapter2
## HttpServletResponse
- HTTP Response Message 생성 역할
  - 요청 응답
  ```
  HttpServletResponse.setContentType("text/plane"); // ContentType 설정
  HttpServletResponse.setCharacterEncoding("utf-8"); // Encoding 설정
  HttpServletResponse.getWriter().write("hello " + username); // ResponseBody 작성
  ```
    - 위의 코드를 통해 수많은 Http Response Header를 직접 설정하지 않고 Content-Type, Response Message Body 등만 작성하면 된다(나머지는 브라우저, 톰캣(Servlet)이 작성해줌)
  - HTTP 응답 코드 지정
  ```
    //HTTP Status Code 생성
    //resp.setStatus(HttpServletResponse.SC_OK); // Http 응답 코드 생성, SC_OK는 응답 코드 200을 의미한다
    //resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); Http 응답 코드 400
    resp.setStatus(HttpServletResponse.SC_FOUND); //Http 응답 코드 302

    // 응답 코드 200 일 때는 Connection: keep-alive, 응답 코드 400일 때는 Connection: close 으로 Response Header가 변경된다
  ``` 
  - Header 생성
  ```
    // HTTP Response Message Header 생성
    resp.setHeader("Content-Type", "text/plain;charset=utf-8"); // Content-Type 지정
    resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // 캐시 무효화를 의미
    resp.setHeader("Pragma", "no-cache"); // 과거의 캐시까지 무효화함
    resp.setHeader("my-header", "hello"); // Custom Header 작성 가능
    resp.setHeader("Set-Cookie", "myCookie=good; Max-Age=600"); // 쿠키 설정
    resp.setHeader("Location", "/basic/hello-form.html"); // 해당 주소로 리다이렉트
  ``` 
    - content-type, cookie, Redirect 기능 제공
    ```
    // Content-Type
    resp.setContentType("text/plain");
    resp.setCharacterEncoding("utf-8");

    // Cookie
    Cookie cookie = new Cookie("myCookie", "good");
    cookie.setMaxAge(600); //600초
    resp.addCookie(cookie);

    // Redirect
    resp.sendRedirect("/basic/hello-form.html");
    ``` 
  - Body 생성
    - HTML 응답
      - Content-Type:text/html
      ```
      //Content-Type:text/html;charset=utf-8
      resp.setContentType("text/html");
      resp.setCharacterEncoding("utf-8");

      PrintWriter writer = resp.getWriter();
      writer.println("<html>");
      writer.println("<a href='https://www.naver.com'>naver</a>");
      writer.println("</html>");
      ``` 
    - TEXT 응답
      - Content-Type:text/plain
      ```
      resp.getWriter().write("Header Setting OK : 헤더 세팅 완료");
      ``` 
    - JSON, XML등의 데이터 응답
      - JSON의 경우 Content-Type:application/json
        - 스펙 상 UTF-8을 사용하도록 정의되어있기 때문에 인코딩 파라미터를 추가하지 않음
        - HttpServletResponse.getWriter()을 사용할 경우 인코딩 파라미터를 자동으로 추가함 따라서 getOutputStream()을 사용함
        ```
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
        ```
      - XML은 복잡하고 어렵기 때문에 최근에는 데이터 형식으로 JSON을 사용함