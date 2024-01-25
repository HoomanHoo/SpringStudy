# Chapter 6
# 스프링 MVC 기본 기능
## 로깅
- System.out.println()은 로깅용으로 적합하지 않음
- 로깅 레벨 설정 등의 불가능하기 때문
- 모든 상황에서 로그가 남기 때문에 꼭 필요한 정보를 찾기가 힘듬
- 스프링 부트 사용 시 로깅 라이브러리 제공
- org.springframework.boot:spring-boot-starter -> spring-boot-starter-logging
    - slf4j 라이브러리의 구현체인 Logback을 많이 사용함
- application.properties 파일에서 로그 레벨 설정 가능
  - logging.level.root=debug  -> 프로젝트 전체 로그 레벨 설정
  - logging.level.hello.springmvc=debug -> 패키지별 로그 레벨 설정
- 운영 서버에서는 info 레벨, 개발 서버에서는 debug 레벨로 설정하여 로그를 남김
  - 개발 서버에서는 디버그 레벨까지의 정보들이 필요하지만 운영 서버에서는 운영에 꼭 필요한 정보들만 볼 수 있어야 하기 때문
- 소스단에서 설정한 레벨보다 낮은 로그를 출력하는 코드를 넣어도 설정한 레벨 이상의 로그만 출력할 수 있음
- 콘솔단에 출력하는 것 이외에도 파일로 따로 로그를 남길 수도 있다
- 예시 코드
```
@Slf4j  // 코드를 통해서 로거를 불러오는 대신 어노테이션으로도 로거를 불러올 수 있다(lombok 사용시)
@RestController // 문자열 리턴 시 뷰 이름이 아닌 텍스트 자체를 반환함
public class LogTestController {
//  private final Logger log = LoggerFactory.getLogger(LogTestController.class); // getClass() 를 매개변수로 넣어도 됨

    @RequestMapping("/log-test")
    public String logTest(){
        String name = "Spring";

        System.out.println("name = " + name);
        log.trace("trace log={}", name);
        log.debug("debug log={}", name);
        log.info("info log={}", name);  // 시각, 디버그 레벨, 프로세스 번호, 스레드 이름, 현재 컨트롤러 이름, 텍스트 등을 보여줄 수 있음
        log.warn("warn log={}", name);
        log.error("error log={}", name);
        return "ok";
        //        name = Spring
        //        2024-01-19T23:37:34.303+09:00  INFO 9416 --- [nio-8080-exec-1] h.springmvc.basic.LogTestController      : info log=Spring
        //        2024-01-19T23:41:20.171+09:00  WARN 6480 --- [nio-8080-exec-3] h.springmvc.basic.LogTestController      : warn log=Spring
        //        2024-01-19T23:41:20.171+09:00 ERROR 6480 --- [nio-8080-exec-3] h.springmvc.basic.LogTestController      : error log=Spring
        // 디버그 레벨에 따른 출력 로그 수준을 조정할 수 있음 -> 기본 레벨은 info
        // 로깅시 문자열 더하기를 하지 않는 이유는 메서드 실행 전 매개변수 단계에서 문자열 더하기를 실행해버림
        // -> 자원을 잡아먹는것에 더해서 출력하지 않는 레벨의 로그에서도 연산이 발생해버림
        // 매개변수 두 개를 사용해서 로그를 출력할 경우에는 로그 레벨이 맞지 않으면 문자열 더하기 연산 자체를 실행하지 않고 로깅 메서드를 종료해버림
    }

}
```
## HttpEntity< T >
- Http 헤더, 메세지 바디 데이터를 조회, 입력할 수 있는 객체
- 요청과 응답 둘 다 사용 가능함
- 요청에서는 헤더와 바디 데이터를 조회할 수 있음
- 응답에서는 메세지 바디에 직접 데이터를 쓸 수 있으며 헤더 정보도 포함할 수 있음
- 세분화 된 기능 사용을 위해 RequestEntity, ResponseEntity 객체를 사용할 수 있음
  - ResponseEntity<객체> 로 지정할 경우, 객체 내의 데이터를 JSON 형태로 포매팅하여 Http 응답 메세지 바디에 입력함
  
## @RequestBody 
- 핸들러 메서드 매개변수 단위에 부착하여 사용
- Http 요청에서 메세지 바디만 받고자 할 경우 사용할 수 있음
- Http 요청 헤더 정보가 필요할 경우 다른 매개변수에 @RequestHeader 어노테이션을 붙여 받을 수 있음
- primitive type 변수 외에 객체 타입 변수에도 붙일 수 있음
  - 객체 타입 변수에 @RequestBody 어노테이션이 붙을 경우 JSON 타입 메세지 바디 데이터를 바로 바인딩 할 수 있음
    - Content-Type 헤더가 application/json 이어야만 동작, 이외의 값일 경우 415 에러 코드 리턴
  
## @ResponseBody
- 클래스, 메서드 단위에 부착하여 사용
- 뷰 리졸버를 사용하는 대신 Http 메세지 바디에 String 반환 값을 그대로 넣음
- 리턴 타입이 객체일 시, 객체의 데이터를 JSON 포맷으로 변환하여 Http 메세지 바디에 넣음
- @ResponseStatus(HttpStatus.OK) 와 같이 @ResponseStatus 어노테이션을 이용하여 상태 코드를 같이 전달할 수 있음
  
## @RestController
- @Controller는 String 반환 시 뷰 이름을 탐색함
- @RestController는 HTTP 메세지 바디에 String 반환 값을 그대로 태움（乗せる）
- 에러 메세지 스타일을 JSON 형식으로 보냄
- @Controller + @ResponseBody 어노테이션의 역할을 한다
  - @RestController 대신 위의 두 어노테이션을 같이 쓰는 것도 가능함  
  
## @RequestMapping
- 클래스, 메서드 단위에 부착하여 사용
- 클래스 단위에 부착할 시, 하위 핸들러 메서드들의 공통 URL을 분리할 수 있음
- 속성을 배열로 제공 -> 두개 이상의 URL 매핑 가능 
  - @RequestMapping(value = {URL1, URL2})
- /url 과 /url/ 을 같은 컨트롤러에 매핑해줌
- 동일한 URL을 다른 두 핸들러 메서드에 매핑할 경우 IllegalStateException 발생
- method 속성을 지정하지 않으면 모든 HTTP Method 속성에 매핑된다
  - method = ReqhestMethod.GET 등의 메서드 속성 설정 가능
  - @GetMapping 등으로 메서드 속성 설정 대신 어노테이션을 따로 설정할 수 있음
- @Controller와 같이 클래스 레벨에 사용하면 클래스 내 컨트롤러 메서드들의 공통 URL을 분리할 수 있음
- 속성 (@RequestMapping을 내부에 가지고 있는 ~Mapping 계열 어노테이션도 동일)
  - value = "/mapping-param" -> 매핑할 URL
  - params = "mode=debug" -> 컨트롤러를 호출할 쿼리 파라미터의 조건
  - headers = "mode=debug" -> 컨트롤러를 홏출할 Http 메세지 헤더 조건
  - consumes = "application/json" -> 컨트롤러를 호출할 Content-Type 헤더 조건
  - produces = "text/plain" -> 컨트롤러를 호출할 Accept 헤더 조건
  - method = RequestMethod.GET -> 컨트롤러를 호출할 Http 메서드 조건
- ~Mapping 계열 어노테이션
  - @RequestMapping의 method 속성을 지정하는 대신 사용할 수 있음
  - 종류
    - @GetMapping
    - @PostMapping
    - @PatchMapping
    - @PutMapping
    - @DeleteMapping

## @PathVariable
- URL의 경로 일부분을 핸들러 내부 변수로 사용하는 것
- ~Mapping 계열 어노테이션의 value 속성에서 사용하고자 하는 경로 일부분을 {}로 감싼 뒤 매개변수에 @PathVariable 어노테이션을 부착하여 사용
- 두개 이상의 Path Variable도 매핑 가능하다
- 예시 코드
```
    @GetMapping("/mapping/{userId}")
    public String mappingPath(@PathVariable("userId") String userId){
        log.info("mappingPath userId={}", userId);
        return userId;
    }
    // @PathVariable String userId 와 같이 {}로 감싼 파라미터 이름과 변수명이 같을 경우 어노테이션의 value 값을 생략할 수 있다
``` 

## @RequestParam
- 쿼리 파라미터 형식으로 넘어오는 데이터를 받을 때 사용함
- HttpServletRequest.getParameter()와 달리 형 변환까지 해서 넘겨줌
- 파라미터 이름과 변수 명이 같을 경우 value 옵션을 생략할 수 있음
- primitive type 뿐만 아니라 컬렉션 타입도 매핑이 가능하다 (Map, MultivalueMap)
  - 파라미터의 값이 하나일 경우는 Map, 두개 이상이 된다면 MultiValueMap을 사용하여 매핑할 수도 있음
- 파라미터 이름과 변수명이 같을 때, 해당 어노테이션을 생략할 경우 단순 타입(Integer, int, String등)에 한해서 스프링이 어노테이션을 붙여줌
- 속성
  - value -> 받고자 하는 파라미터 이름
  - required -> 필수로 받아야 하는 파라미터인지 설정함
    - true가 기본값으로 파라미터가 넘어오지 않을 경우 400 Bad Request 에러 코드 반환
      - '파라미터 이름=' 으로 넘어올 경우 빈 문자열로 인식하여 값을 받는다
    - false로 설정할 경우 파라미터 값을 안 받아도 Bad Request 에러 코드를 반환하지 않음
      - 해당 변수에는 null이 들어오기 때문에 래퍼 클래스나 String으로 값을 받아야 함
  - defaultValue -> 파라미터를 넘기지 않았을 때 가질 기본 값을 설정함
    - required 옵션 여부와는 상관 없이 설정됨
    - '파라미터 이름=' 으로 넘어오더라도 설정된 기본 값이 적용됨

## @ModelAttribute
- 핸들러 메서드의 매개변수에 부착하여 사용
- 해당 어노테이션이 붙은 객체를 Argument Resolver에서 생성하여 핸들러로 넘겨줌
- 요청 파라미터 이름으로 getter, setter를 호출하여 파라미터 값을 바인딩함
  - 파라미터 이름과 객체 필드의 이름이 같아야 바인딩 됨
  - 파라미터의 타입과 객체 필드의 타입이 다를 경우 BindingException이 발생함
- 해당 어노테이션을 생략할 수 있음
  - Http 요청 메세지 바디 데이터가 필요할 경우 @RequestBody 어노테이션을 반드시 부착해야함
  - 어떠한 어노테이션 없이 핸들러 메서드 매개변수로 객체를 선언하는 경우 스프링에서는 @ModelAttribute 어노테이션을 부착해주기 때문임

## HTTP 메세지 컨버터
- JSON, 텍스트를 HTTP 메세지 바디에서 직접 읽거나 쓰는 경우 사용
- 인터페이스로 되어있으며 MappingJackson2HttpMessageConverter, StringHttpMessageConverter 등의 구현체들이 존재
- 응답의 경우 HTTP Accept 헤더의 값과 컨트롤러 메서드의 리턴 타입 정보들을 조합하여 컨버터를 선택함
- 요청과 응답 두 경우에 모두 사용함 
- 컨버터를 결정하기 위해 요청에서는 Content-Type, 응답에서는 Accept 헤더의 값을 확인
- 요청 매핑 핸들러 어댑터
- DispatchServlet에서 요청 매핑 핸들러 어댑터 호출 -> argument resolver 호출(컨트롤러에 넘길 매개변수 생성해서 넘겨줌) -> 핸들러 어댑터가 생성된 매개변수를 컨트롤러에 넘김 -> 리턴값 있을 경우 Return Value 핸들러 동작

## HandlerMethodArgumentResolver
- 줄여서 Argument Resolver
- 인터페이스로 이를 구현한 구현체들을 사용
- 컨트롤러 메서드에 넘길 매개변수(객체)를 생성해주는 역할
- 컨트롤러의 매개변수를 생성해서 RequestMappingHandlerAdapter에 넘겨줌

## HadlerMethodReturnValueHandler
- 줄여서 ReturnValueHandler
- 인터페이스로 이를 구현한 구현체들을 사용
- Http Message Converter는 Argument Resolver, Return Value Handler 두 곳에서 모두 사용됨
- @RequestBody, @RespnseBody는 하나의 프로세서에서 처리함
  
- HandlerMethodArgumentResolver, HandlerMethodReturnValueHandler, HttpMessageConverter 모두 인터페이스로 제공되기 때문에 확장가능
- 실제로 확장할 일은 별로 없으나 WebMvcConfigure 상속받아 구현하고 스프링 빈으로 등록하면 됨


