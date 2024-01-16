# 스프링 MVC 전체 구조
- Chapter4 까지 진행하며 만든 프론트 컨트롤러 패턴을 적용한 프레임워크 구조는 스프링 MVC 전체 구조와 비슷함
- DispatcherServlet이 스프링에서 Front Controller 패턴을 구현한 것
  - DispatcherServlet이 스프링 MVC의 핵심
  - DispatcherServlet은 부모 클래스에서 HttpServlet을 상속받으며, 루트 경로("/")에 매핑함
    - 더 자세한 경로가 우선 순위가 높기 때문에 기존에 등록한 서블릿도 함께 동작함
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

## 뷰 리졸버
- 뷰 리졸버를 찾는 경우
- 스프링부트는 다양한 뷰 리졸버를 자동으로 등록함
  - BeanNameViewResolver -> 빈 이름으로 뷰를 찾아서 반환
  - InternalResourceViewResolver -> JSP 파일 처리할 수 있는 뷰 반환(InternalResourceView, JSTL 라이브러리 있는 경우 JSTLView를 반환)
  - ThymeleafViewResolver -> 타임리프를 사용할 수 있는 뷰 반환 (JSP 이외의 뷰 템플릿은 forward() 없이 바로 렌더링됨)
      -> ThymeleafViewResolver를 사용하기 위해서는 스프링 빈 등록을 하거나 타임리프 라이브러리를 메이븐이나 그레이들에 등록하면 됨
  - InternalResourceView는 forward()를 호출하여 처리할 수 있는 JSP 등을 렌더링할 때 사용
- 뷰 리졸버 탐색 -> 뷰 반환 -> 화면 렌더링 과정으로 화면 렌더링을 수행함

## 스프링 MVC
- 스프링이 제공하는 컨트롤러는 어노테이션 기반 -> 유연하고 실용적
  - @RequestMapping(요청 정보 (URL)) 
    - 요청 정보 매핑 -> 매핑한 URL로 요청이 들어올 경우 해당 메서드가 호출됨
    - 매핑 -> RequestMappingHandlerMapping
    - 어댑터 -> RequestMappingHandlerAdapter
  - @Controller 
    - 스프링이 자동으로 스프링 빈으로 등록(내부에 @Component가 있기 때문에 컴포넌트 스캔 대상이 되기 때문)
    - -> 스프링 MVC에서 어노테이션 기반 컨트롤러로 인식
- @Controller나 @RequestMapping이 클래스 레벨에 붙어있어야 어노테이션 기반 컨트롤러로 인식함