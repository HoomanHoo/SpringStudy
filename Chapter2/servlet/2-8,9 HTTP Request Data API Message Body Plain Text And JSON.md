# HTTP Request Data API Message Body - Plain Text, JSON
- HTTP Message Body에 데이터를 직접 담아서 요청
  - JSON, XML, TEXT
  - 데이터 형식은 주로 JSON
  - POST, PUT, PATCH 등의 HTTP Method를 사용
  - TEXT를 전송하는 경우 content-type은 text/plain 으로 설정함
  - JSON을 전송하는 경우 content-type은 application/json 으로 설정함
    - Message Body를 JSON 형식으로 작성해야함
    - JSON도 문자임
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