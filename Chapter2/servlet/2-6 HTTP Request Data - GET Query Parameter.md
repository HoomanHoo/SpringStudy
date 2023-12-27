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
- 서버에서는 HttpServlet.getParameter("name")를 통해 데이터를 구할 수 있다
```
System.out.println("username =" + req.getParameter("username"));
System.out.println("age =" + req.getParameter("age"));

//    username =hello
//    age =20
```
- getParameter("name")는 name이 중복일 때는 사용하지 않는다
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