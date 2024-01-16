# Front Controller Pattern
- 프론트 컨트롤러 패턴 적용 이전
  - 공통 로직을 각 클래스가 다 가져야 함(메서드로 분리하더라도 이를 호출하는 코드를 가져야함)
- 프론트 컨트롤러 패턴 적용 이후
  - 공통 관심사를 별도로 모은 뒤 실행한 뒤, 실행하고자 하는 클래스를 실행시킬 수 있게 됨
  - 웹 어플리케이션 프레임워크에서는 HTTP 요청을 맨 앞에서 받아주는 클래스가 프론트 컨트롤러 역할을 하게 됨
- 웹 어플리케이션에서의 프론트 컨트롤러 패턴
  - 서블릿 클래스 하나로 클라이언트의 요청을 받음
  - 요청에 맞는 컨트롤러를 프론트 컨트롤러가 찾아서 호출함
  - 프론트 컨트롤러 이외의 컨트롤러는 서블릿을 사용하지 않아도 됨
- 스프링 웹 MVC 역시 프론트 컨트롤러 패턴으로 구현되어있음
  - DispatcherServlet 과 프론트 컨트롤러 패턴으로 구현되어 있음

## 어댑터 패턴
- 호환되지 않는 인터페이스들을 연결하는 디자인 패턴
- 각 객체를 직접 호출하지 않고 어댑터 객체를 통해서 호출하도록 하는 방식
- 해당 강의에서는 다양한 방식의 컨트롤러를 호출할 수 있도록 핸들러 어댑터를 두는 것을 의미함


### etc
- 무언가를 개선하고자 할 때는 비슷한 레벨의 것만 개선해야함
  - 구조를 개선하고자 할 때는 구조만 개선
  - 코드를 개선하고자 할 때는 코드만 개선
- HTML 내의 URL 대부분은 절대 경로를 쓰나 패턴이 비슷한 경우 상대 경로를 사용해도 됨
- 어떠한 것과 관련된 변경이 한 부분에서만 일어나는 것이 좋은 설계, 이곳저곳에서 일어나면 별로 좋지 않음
- 서버의 응답이 있는 404 에러의 경우 gradle task - bootRun 실행
  - ServletApplication.java 실행 만으로는 응답이 제대로 안됨
  - 
  ```
     bootRun 실행시
     오후 10:31:01: Executing 'bootRun'...

    > Task :compileJava UP-TO-DATE
    > Task :processResources UP-TO-DATE
    > Task :classes UP-TO-DATE
    > Task :resolveMainClassName UP-TO-DATE

    > Task :bootRun
  
    ServletApplication 실행시
    오후 10:33:51: Executing ':ServletApplication.main()'...

    > Task :compileJava UP-TO-DATE
    > Task :processResources UP-TO-DATE
    > Task :classes UP-TO-DATE

    > Task :ServletApplication.main()
  
  -> resolveMainClassName 테스크에 대해 알아보기
  ```
- if-else 를 사용할지 다형성을 사용할지는 코드의 크기에 따라 선택할 수 있음
  - 간단한 분기 처리의 경우 if-else를 사용할 수 있음
  - 로직이 많아질 경우 다형성을 사용할 수 있음

## 기타
- 스프링은 다양한 어댑터를 가지고 다양한 형식의 구현을 지원한다
  - 컨트롤러의 구현에 있어서 어노테이션, xml 방식등을 지원
  - 요청에 대한 응답 방식에 있어서 ModelAndView 리턴, 텍스트 리턴, 객체 리턴 등의 방식을 지원함
  - 이는 어댑터 패턴과 인터페이스 사용에 의해 가능하다