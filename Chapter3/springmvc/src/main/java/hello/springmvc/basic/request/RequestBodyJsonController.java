package hello.springmvc.basic.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class RequestBodyJsonController {
    private ObjectMapper objectMapper;

    @Autowired
    public RequestBodyJsonController(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}", messageBody);
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        response.getWriter().write("request-body-json-v1");
    }

    @ResponseBody
    @PostMapping("/request-body-json-v2")   // 중복되는 URL 매핑시(Http 메서드도 동일) IllegalStateException 발생 -> to {POST [/request-body-json-v1]}: There is already 'requestBodyJsonController' bean method
    public String requestBodyJsonV2(@RequestBody String messageBody)
        throws IOException {

        log.info("messageBody={}", messageBody);
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        // ObjectMapper 클래스를 이용해서 객체에 JSON 타입 메세지 바인딩 가능
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "request-body-json-v2";
    }

    @ResponseBody
    @PostMapping("/request-body-json-v3")
    public String requestBodyJsonV3(@RequestBody HelloData helloData) {
    // @RequestBody에 JSON 데이터를 바인딩할 객체를 바로 지정해줄 수도 있음
    // Http Header에 Content-Type application/json이라는 헤더가 있을 경우에 해당
    // Content-Type 헤더가 application/json이 아닐 경우 "status": 415, "error": "Unsupported Media Type", 에러 메세지 리턴
    // @RequestBody는 생략해줄 수 없음 -> 생략할 경우 ModelAttribute를 적용
        log.info("messageBody={}", helloData);
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "request-body-json-v3";
    }

    @ResponseBody
    @PostMapping("/request-body-json-v4")
    public String requestBodyJsonV4(HttpEntity<HelloData> helloData) {
        // HttpEntity를 사용해서도 메세지 바디의 JSON 데이터를 객체에 바인딩하여 사용할 수 있다
        log.info("HttpEntity={}", helloData);
        HelloData data = helloData.getBody();
        log.info("data={}", data);
        log.info("username={}, age={}", data.getUsername(), data.getAge());
        return "request-body-json-v4";
    }

    @ResponseBody
    @PostMapping("/request-body-json-v5")
    public HelloData requestBodyJsonV5(@RequestBody HelloData helloData) {
    // JSON 타입으로 응답하는 경우에는 메서드에 @ResponseBody 어노테이션을 붙이고 리턴 타입을 객체로 한 뒤, 객체를 리턴시키면 된다
    // @RequestBody와 마찬가지로 @ResponseBody에서도 Http 메세지 컨버터가 동작한다 JSON -> 객체 / 객체 -> JSON
    // 요청 헤더에 Accept 속성이 application/json, 혹은 */* 이어야 한다
        log.info("messageBody={}", helloData);
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return helloData;
    }
}
