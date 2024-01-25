# Chapter2

### HttpServletRequest, HttpServletResponse
- 둘 다 인터페이스임
- 인터페이스인 이유는 톰캣, 제티 등에서 두 인터페이스를 구현하도록 하기 위해서임
  - 톰캣에서의 구현체
    
    - org.apache.catalina.connector.ResponseFacade

# HttpServletRequest
- Servlet에서 HTTP Request 메세지를 파싱한 뒤, 그 결과를 제공해주는 객체
  - 인터페이스로 되어있으며 tomcat등의 WAS에서 구현하여 사용한다
  - 톰캣에서의 구현체
    - org.apache.catalina.connector.RequestFacade
  - HTTP Request 메세지를 개발자가 직접 파싱해서 사용할 수도 있으나 매우 불편함 
- HttpServletRequest, Response 둘 다 HTTP 스펙에 대한 이해가 있어야함
- Http Request Message의 구조
  - START LINE
    - HTTP Method
    - URL
    - Query String
    - Scheme, Protocol
  - Header
    - Host, Content-Type등의 헤더 정보
  - Body 
    - form parameter
    - message body
  -  참고 URL: https://developer.mozilla.org/ko/docs/Web/HTTP/Messages
- HttpServletRequest의 추가 기능
  - 임시 저장소
    - 해당 HTTP 요청이 시작될 때부터 끝날 때 까지 유지되는 임시 저장 기능
    - HttpServletRequest.setAttribute(name(key), value)
    - HttpServletRequest.getAttribute(name(key))
  - Session 관리
    - HttpServletRequest.getSession()
# HTTP Request Data Outline
- 데이터를 클라이언트에서 서버로 전달하는 방법
  - GET - 쿼리 파라미터(쿼리 스트링)
    - 쿼리 파라미터 -> 쿼리 스트링이라고도 함
      - ?파라미터 이름=값
      - key-value 형식(?key=value)
      - HttpServletRequest.getParameter(key값(String)) 으로 값을 꺼낼 수 있다
    - 메세지 바디 없이 URL의 쿼리 파라미터에 데이터를 포함해서 전달
    - 검색, 필터링, 페이징 등에서 자주 사용함
  - POST - HTML Form
    - content-type:application/x-www-form-urlencoded
      - 메세지 바디에 쿼리 파라미터 형식으로 전달 ex)username=hello&age=20
    - 회원 가입, 상품 주문에서 사용되는 방식, HTML Form을 사용
  - HTTP message body에 직접 데이터를 담아서 요청
    - HTTP API (REST API) 에서 주로 사용 (JSON, XML, TEXT)
    - 데이터 형식은 주로 JSON을 사용함
    - POST, PUT, PATCH, DELETE등의 HTTP Method를 사용함
# HTTP Request Data - GET Query Parameter
- GET Parameter 의 경우 URL 뒤에 ? 를 시작으로 데이터를 보낼 수 있음
- 다른 데이터를 추가할 경우 & 를 붙여서 구분한다
- getParameterNames() 메서드를 통해 parameter의 name들을 구할 수 있다
```
Enumeration<String> paramNames = req.getParameterNames();
paramNames.asIterator().forEachRemaining(paramName -> System.out.println(paramName + "=" + req.getParameter(paramName)));

//    http://192.168.0.2:8080/request-param?username=hello&age=20
//    service start
//    username=hello
//    age=20
//    service end
```
- 서버에서는 HttpServletRequest.getParameter("파라미터 이름")를 통해 데이터를 구할 수 있다
```
System.out.println("username =" + req.getParameter("username"));
System.out.println("age =" + req.getParameter("age"));

//    username =hello
//    age =20
```
- getParameter("name") 메서드는 name이 중복일 때는 사용하지 않는다
  - name이 중복일 경우 첫번째 value만 가져온다
  - name이 중복일 경우에는 getParameterValues("name")(return값 String[]) 메서드를 사용한다
  ```
  // http://192.168.0.2:8080/request-param?username=hello&age=20&username=hello2
    String[] usernames = req.getParameterValues("username");  // 같은 이름의 여러 값이 들어올 경우 getParameterValues() 를 사용한다
    for (String username : usernames) {
      System.out.println(username);
    }
  // hello
  // hello2
  ``` 
  # HTTP Request Data POST HTML Form
- content-type:application/x-www-form-urlencoded 가 헤더에 있어야함
- 메세지 바디에 쿼리 파라미터 형식으로 데이터를 전달 (username=hello&age=20)
- Form Data 항목으로 보냄
- 어쨌거나 쿼리 파라미터로 보내기 때문에 getParameter(), getParameterValues() 등으로 값을 꺼낼 수 있음
  - 쿼리 파라미터 조회 메서드를 그대로 사용
- POST의 HTML Form을 사용하면 브라우저는 다음과 같은 HTTP Message를 작성함
  - 요청 URL:Form 데이터가 전송될 URL
  - content-type:application/x-www-form-urlencoded
  - HTTP Message Body:Query Parameter
  - GET URL Query Parameter 방식은 HTTP Message Body 사용 안하기 때문에 content-type 이 없음
  - -> HTTP Message Body를 사용할 때만 content-type을 설정함
  - HTML 스펙 상 Form은 GET/POST만 지원
    - 스프링을 사용했을 때 히든 필드로 타 메서드를 지정해줄 수 있음 하지만 실제 전송은 GET/POST로, 매칭되는 컨트롤러만 해당 메서드 관련 컨트롤러로 지정해줌
# HTTP Request Data API Message Body - Plain Text, JSON
- HTTP Message Body에 데이터를 직접 담아서 요청
  - JSON, XML, TEXT
  - 데이터 형식은 주로 JSON
  - POST, PUT, PATCH 등의 HTTP Method를 사용
  - TEXT를 전송하는 경우 content-type은 text/plain 으로 설정함
  - JSON을 전송하는 경우 content-type은 application/json 으로 설정함
    - Message Body를 JSON 형식으로 작성해야함
    - JSON도 문자임 (형식화된 문자열)
    - application/json 이라는 content-type을 보고 서버에서 JSON Parsing 로직을 넣어주기도 함
    ```
    private ObjectMapper objectMapper = new ObjectMapper(); // Spring 기본 제공 JSON Parsing 라이브러리

    // Message Body의 TEXT, JSON 꺼내는 코드 (Query Parameter와는 다름)
    ServletInputStream inputStream = req.getInputStream();
    String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
    // -> Message Body의 내용을 그대로 가져온다 / getParameter()처럼 name에 맞는 value를 가져오는 것이 아님
    System.out.println("messageBody = " + messageBody);
    // messageBody = {"username":hello, "age":30}
  
    // Json Parsing
    HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
    System.out.println("helloData.username = " + helloData.getUsername());
    System.out.println("helloData.age = " + helloData.getAge());
    ``` 
