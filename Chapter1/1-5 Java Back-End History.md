## 과거 기술

- 서블릿 - 1997년
    - HTML 생성이 어려움 (out.print();)
- JSP - 1999
    - HTML 생성은 편리하나 비즈니스 로직까지 너무 많은 역할을 담당
- 서블릿 + JSP 조합 MVC 패턴 사용
    - Model, View, Controller 로 역할을 나누어 개발
    - 화면 렌더링과 비즈니스 로직을 분리했음에 의의
- MVC 프레임워크 다양화  - 2000년대 초 ~ 2010년 초
    - MVC 패턴 자동화, 복잡한 웹 기술을 편리하게 사용할 수 있는 다양한 기능 지원
    - 스트럿츠, 웹워크, 스프링 MVC(Spring Boot 아님)

## 현재 사용 기술

- 어노테이션 기반의 스프링 MVC 등장
    - 어노테이션 기반이기 때문에 매우 유연하고 코드를 깔끔하게 작성할 수 있음
    - @Controller
    - 예시 코드
    
    ```jsx
    @Controller
    public class AutoOrderSettingProHandler implements CommandHandler{
    
    	@Resource(name="orderDao")
    	OrderDao orderDao;
    	
    	@RequestMapping("/autoordersettingpro")
    	@Override
    	public ModelAndView process(HttpServletRequest request, HttpServletResponse response) throws Exception {
    
    		HttpSession session = request.getSession();
    		
    		if(session.getAttribute("mem_code") == null) {
    			return new ModelAndView("user/loginForm");
    		}
    		else {
    			int mem_code = (int) session.getAttribute("mem_code");
    			String[] mediCode = request.getParameterValues("mediCode");
    			String[] triggerQuan = request.getParameterValues("triggerQuan");
    			String[] aoQuan = request.getParameterValues("aoQuan");
    			int result = 0;
    			
    			for(int i = 0; i < mediCode.length; i++) {
    				int medi_code = Integer.parseInt(mediCode[i]);
    				int trigger_quan = Integer.parseInt(triggerQuan[i]);
    				int ao_quan = Integer.parseInt(aoQuan[i]);
    				
    				AutoOrderSettingDataBean dto = new AutoOrderSettingDataBean();
    				dto.setMem_code(mem_code);
    				dto.setMedi_code(medi_code);
    				dto.setTrigger_quan(trigger_quan);
    				dto.setAo_quan(ao_quan);
    				result += orderDao.insertAutoOrderSetting(dto);
    			}
    			request.setAttribute("result", result);
    			return new ModelAndView("order/autoOrderSettingPro");
    		}
    	}
    
    }
    ```
    
    - 기존 MVC 프레임워크들을 밀어내고 점유율 확장
- 스프링 부트 등장
    - 기존 스프링을 편리하게 사용할 수 있도록 도와줌
        - 서버 내장(WAS)
        - 과거에는 서버에 WAS를 직접 설치하고 소스를 war 파일로 만들어서 설치한 WAS에 배포하였음
        - 스프링 부트는 빌드 결과로 jar파일을 생성하는데, 이 파일 내부에 WAS 서버를 포함하고 있음 → 빌드 및 배포를 단순화 할 수 있음
- 스프링 웹 기술의 분화
    - Web Servlet - Spring MVC
        - Servlet 기반 기술
        - 멀티 스레딩 가능
        - 일반적으로 사용하는 것
    - Web Reactive - Spring WebFlux
        - 특징
            - 비동기 Non-Blocking 처리
            - 최소 쓰레드로 최대 성능 - 쓰레드 컨텍스트 스위칭 비용 효율화 → 거의 CPU 코어 갯수만큼의 쓰레드를 사용
            - 함수형 스타일로 개발 → 동시 처리 코드 효율화
            - 서블릿 기술 사용하지 않음
                - Netty라는 프레임워크를 사용하여 구현되어있음
            - 기술적 난이도가 매우 높음
            - 아직 RDB에 대한 지원이 부족함
                - Redis, Elastic Search, mongoDB 등에서 사용 가능
            - 일반 MVC의 쓰레드 모델도 충분히 빠름
                - 성능이 중요하면서도 매우 복잡한 시스템을 가지고 있을 때 WebFlux를 사용
            - 실무에서 아직 많이 사용하지는 않음

## 자바 뷰 템플릿 기술 역사

- HTML을 편리하게 생성하는 뷰 기능
- JSP
    - 속도 느림, 기능 부족
- Freemarker, Velocity
    - 속도 문제 해결, 다양한 기능
- Thymeleaf
    - 내추럴 템플릿 → HTML 모양 유지하면서도 th:태그, sec:태그 등으로 뷰 템플릿 적용 가능
    - 스프링 MVC와의 강력한 기능 통합
    - 스프링에서 권장하는 템플릿 엔진
        - 성능은 Freemarker, Velocity가 더 빠름