package hello.springmvc.basic.response;

import hello.springmvc.basic.HelloData;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@Controller
// @ResponseBody를 클래스 단위로 넣으면 하위 메서드들에 자동으로 @ResponseBody 어노테이션이 적용됨 -> @Controller + @ResponseBody = @RestController
public class ResponseBodyController {

    @GetMapping("/response-body-string-v1")
    public void responseBodyV1(HttpServletResponse response) throws IOException {
        response.getWriter().write("response-body-string-v1");
    }

    @GetMapping("/response-body-string-v2")
    public ResponseEntity<String> responseBodyV2() throws IOException {
        return new ResponseEntity<>("response-body-string-v2", HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/response-body-string-v3")
    public String responseBodyV3(){
        return "response-body-string-v3";
    }

    @GetMapping("/response-body-json-v1")
    public ResponseEntity<HelloData> responseBodyJsonV1(){
        HelloData helloData = new HelloData();
        helloData.setUsername("lee");
        helloData.setAge(14);
        return new ResponseEntity<>(helloData, HttpStatus.OK);  // 조건에 따라 상태코드를 동적으로 바꾸기 위해서는 ResponseEntity를 사용하는 것이 낫다
    }

    @ResponseStatus(HttpStatus.OK)// @ResponseBody에서 같이 써서 상태코드를 전달할 수 있다
    @ResponseBody
    @GetMapping("/response-body-json-v2")
    public HelloData responseBodyJsonV2(){
        HelloData helloData = new HelloData();
        helloData.setUsername("lee");
        helloData.setAge(14);
        return helloData;
    }
}
