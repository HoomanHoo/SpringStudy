# Servlet

## 웹 어플리케이션 서버에서 처리하는 업무

```
- 서버 TCP/IP 연결 대기, 소켓 연결
- HTTP 요청 메세지 파싱해서 읽기
- HTTP Method, URL 판독
- Content-Type 확인
- HTTP 메세지 바디 내용 파싱
- 저장 프로세스 실행
- 비즈니스 로직 실행
    - DB 저장 요청 수행
- HTTP 응답 메세지 생성
    - HTTP 시작 라인 생성
    - Header 생성
    - 메시지 바디에 HTML 생성해서 입력
- TCP/IP 에 응답 전달, 소켓 종료
```

- Servlet은 이 중 비즈니스 로직 실행 이외의 과정들을 대신 처리해준다

- 기본적인 HTTP 스펙은 알아야 한다

- HttpServletRequest, HttpServletResponse 객체를 이용하여 HTTP Request, Response 정보에 접근할 수 있다

- HttpServelt 클래스 상속 필요

## Servlet의 HTTP 요청, 응답 흐름

  - HTTP Request를 기반으로 Request, Response 객체 새로 생성
  - WAS 내 서블릿 컨테이너에서 Request, Response 객체를 매개변수로 하는 서블릿 객체 호출
  - Request 객체에서 HTTP Request 정보를 꺼내서 사용
  - Response 객체에 HTTP Response 정보 입력
  - WAS는 Response 객체에 담겨있는 내용으로 HTTP응답 정보를 생성

## Servlet Container

- HttpServlet을 상속한 서블릿 객체를 관리(생성, 호출, 삭제 등의 라이프 사이클 관리)
- 톰캣처럼 서블릿을 지원하는 WAS를 서블릿 컨테이너라고 함
- 서블릿 객체는 싱글톤으로 관리함
    - HttpServletRequest, HttpServletResponse 객체는 싱글톤 아님
    - 요청이 생길 때마다 계속 객체를 생성하는것이 비효율적이기 때문이다
    - 최초 로딩 시점에 서블릿 객체를 미리 만들어두고 재활용
    - 동일한 URL로의 요청은 동일한 서블릿 객체 인스턴스에 접근함
    - 서블릿 컨테이너 종료시 함께 종료됨
    - 공유 변수 사용에 주의해야함
- JSP도 Servlet으로 변환되어서 사용한다
- 동시 요청을 위한 멀티 스레드 처리를 지원해줌