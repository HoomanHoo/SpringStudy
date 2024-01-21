package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j  // 코드를 통해서 로거를 불러오는 대신 어노테이션으로도 로거를 불러올 수 있다(lombok 사용시)
@RestController // 문자열 리턴 시 뷰 이름이 아닌 텍스트 자체를 반환함
public class LogTestController {
//    private final Logger log = LoggerFactory.getLogger(LogTestController.class); // getClass() 를 매개변수로 넣어도 됨

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
