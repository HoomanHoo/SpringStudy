package hello.springmvc.basic.requestmapping;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.messageresolver.IMessageResolver;

@Slf4j
@Controller
public class RequestBodyStringController {

    @PostMapping("/request-body-string-v1")
    public void requestBodyString(HttpServletRequest request, HttpServletResponse response)
        throws IOException {
        ServletInputStream inutStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inutStream, StandardCharsets.UTF_8);

        log.info("messageBody={}", messageBody);

        response.getWriter().write("request-body-string-v1");
    }

    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer writer)
        throws IOException {
        // HttpServletRequest, Response를 받는 대신 InputStream, OutputStream(Writer)를 바로 매개변수로 받아서 사용할 수 있음
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        
        log.info("messageBody={}", messageBody);
        writer.write("request-body-string-v2");
    }

    @PostMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) {
        // HttpEntity 객체를 매개변수로 받는 것으로 바이트 코드를 문자열로 변환하는 작업을 스프링에게 위임할 수 있다
        // HttpEntity는 헤더와 바디 데이터를 조회할 수 있는 객체 -> 요청 파라미터 조회와는 관계 없음
        // 응답에도 사용할 수 있음 -> 메세지 바디 정보 직접 반환, 헤더 정보 포함 가능
        // 이를 상속받은 RequestEntity, ResponseEntity 객체를 통해 세분화된 기능 사용 가능
        // 리턴 타입을 HttpEntity로 두고 새 HttpEntity 객체를 생성하여 리턴할 때 생성자 매개변수로 데이터를 넣어서 반환할 수 있다
        String messageBody = httpEntity.getBody();

        log.info("messageBody={}", messageBody);
        
        return new HttpEntity<>("request-body-string-v3");

    }

    @ResponseBody
    @PostMapping("/request-body-string-v4")
    public String requestBodyStringV4(@RequestBody String messageBody) {
    // 메세지 바디만 받고자 할 경우 @RequestBody 어노테이션을 붙여서 매개변수로 받을 수 있다
    // 헤더 정보가 필요할 경우 @RequestHeader 어노테이션을 사용할 수 있다    
    // 메서드에 @responseBody 어노테이션을 붙여서 응답 메세지 바디에 데이터를 바로 넣을 수 있다    
    // HttpMessageConverter가 실행됨    
        log.info("messageBody={}", messageBody);

        return "request-body-string-v4";

    }
}
