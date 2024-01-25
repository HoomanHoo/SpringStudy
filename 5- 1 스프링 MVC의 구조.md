# Chapter 5
## 스프링 MVC 전체 구조
- DispatcherServlet이 컨트롤러의 공통 로직 처리를 전담 -> 프론트 컨트롤러
  - DispatcherServlet은 부모 클래스에서 HttpServlet을 상속받으며, 루트 경로("/")에 매핑함
    - 더 자세한 경로가 우선 순위가 높기 때문에 따로 서블릿을 등록하더라도 루트 경로만 아니면 함께 동작함
- DispatcherServlet을 중심으로 어댑터와 뷰 리졸버가 존재, DispatcherServlet에서 요청에 맞는 컨트롤러를 찾기 위해 핸들러 어댑터(어댑터)를 탐색, 어댑터에서 핸들러를 실행, 리턴 방식에 맞는 뷰 리졸버를 탐색하여 실행
- 핵심 동작 방식을 알아두어야 문제 발생 시 어떤 부분에서 발생했는지 쉽게 파악하고 해결할 수 있음
- 확장 포인트 필오시 어떤 부분을 확장해야할지 감을 잡을 수 있음

## 핸들러 매핑과 핸들러 어댑터
- Controller 인터페이스를 구현해서 사용하는 방식
  - ModelAndView 리턴하는 handlerRequest() 메서드를 선언해야했음
- HttpRequestHandler 인터페이스를 구현하여 사용하는 방식
  - 서블릿과 가장 유사한 방식
- @RequestMapping 어노테이션을 사용하는 방식
  - 현재 가장 많이 쓰이는 방식
    스프링 빈의 이름으로 핸들러를 찾을 수 있는 핸들러 매핑이 필요함
- 핸들러 매핑을 통해 찾은 핸들러를 실행할 수 있는 핸들러 어댑터가 필요 
- 각각의 사용 방식에 맞게 구현한 구현체를 실행할 핸들러 어댑터를 찾고 실행해야함
- 스프링 부트 사용시 핸들러 매핑과 어댑터를 자동 등록해줌
  - RequestMappingHandlerMapping은 어노테이션 기반 컨트롤러인 @RequestMapping에서 사용 -> 최우선순위 등록
  - BeanNameUrlHandlerMapping 은 스프링 빈의 이름으로 핸들러를 찾음 -> 스프링 빈의 이름을 url 형식으로 해두었으며 어노테이션 기반 컨트롤러가 아니기 때문에 이 매핑을 사용
  - RequestMappingHandlerAdapter -> @requestMapping에서 사용 -> 최우선순위 등록
  - HttpRequestHandlerAdapter -> HttpRequestHandler 처리
  - SimpleControllerHandlerAdapter -> Controller 인터페이스 처리
- 핸들러 매핑으로 핸들러 조회 -> 핸들러 어댑터 조회 -> 핸들러 어댑터 실행 의 과정으로 컨트롤러 실행을 함
- 다른 다양한 핸들러 어댑터 리스트와 사용 방법: https://www.baeldung.com/spring-mvc-handler-adapters

## 뷰 리졸버
- 뷰 리졸버를 찾는 경우
- 스프링부트는 다양한 뷰 리졸버를 자동으로 등록함
  - BeanNameViewResolver -> 빈 이름으로 뷰를 찾아서 반환
  - InternalResourceViewResolver -> JSP 파일 처리할 수 있는 뷰 반환(InternalResourceView, JSTL 라이브러리 있는 경우 JSTLView를 반환)
  - ThymeleafViewResolver -> 타임리프를 사용할 수 있는 뷰 반환 (JSP 이외의 뷰 템플릿은 forward() 없이 바로 렌더링됨)
      -> ThymeleafViewResolver를 사용하기 위해서는 스프링 빈 등록을 하거나 타임리프 라이브러리를 메이븐이나 그레이들에 등록하면 됨
  - InternalResourceView는 forward()를 호출하여 처리할 수 있는 JSP 등을 렌더링할 때 사용
- 뷰 리졸버 탐색 -> 뷰 반환 -> 화면 렌더링 과정으로 화면 렌더링을 수행함
- 다른 다양한 뷰 리졸버 리스트: https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-servlet/viewresolver.html

## 스프링 MVC에서의 컨트롤러(핸들러)
- 스프링이 제공하는 컨트롤러는 어노테이션 기반 -> 유연하고 실용적
  - @RequestMapping(요청 정보 (URL)) 
    - 요청 정보 매핑 -> 매핑한 URL로 요청이 들어올 경우 해당 메서드가 호출됨
    - 매핑 -> RequestMappingHandlerMapping
    - 어댑터 -> RequestMappingHandlerAdapter
  - @Controller 
    - 스프링이 자동으로 스프링 빈으로 등록(내부에 @Component가 있기 때문에 컴포넌트 스캔 대상이 되기 때문)
    - -> 스프링 MVC에서 어노테이션 기반 컨트롤러로 인식
- @Controller이 클래스 레벨에 붙어있어야 어노테이션 기반 컨트롤러로 인식함
  - 스프링 부트 3.0(스프링 6.0) 부터는 클래스 레벨에 @RequestMapping만 붙어있으면 스프링 컨트롤러로 인식하지 않음

## 컨트롤러 통합
- @Controller 어노테이션을 클래스 레벨에 부착한 뒤 메서드 단위로 @RequestMapping을 부착하여 하나의 클래스에서 여러 URL을 처리할 수 있음
- @Controller 어노테이션 + @RequestMapping(공통 URL) 을 클래스 레벨에 부착 후 세부 URL을 메서드 단위 @RequestMapping 어노테이션에 설정해서 사용할 수도 있음

## 실용적인 방식
- HttpServletRequest 대신 @RequestParam("파라미터 이름")을 통해 컨트롤러 메서드에서 파라미터 데이터를 바로 받을 수 있음
- ModelAndView를 생성해서 리턴하는 대신 Model 객체를 매개변수로 받고, jsp, html 파일명을 문자열로 리턴하는 방식으로 사용할 수 있음
- @RequestMapping의 method 속성값으로 RequestMethod.GET, POST등을 설정하여 요청 메서드 타입을 제한할 수 있으나 대신 @GetMapping, @PostMapping을 사용할 수도 있음
  - 매핑되는 URL에서 받고자 하는 HTTP 메서드를 제한해줘야 URL 직접 입력을 통한 접속 등의 예기치 못한 상황에 대한 사이드 이펙트가 발생하는 것을 줄일 수 있음