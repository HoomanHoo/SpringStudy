package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class RequestParamController {

    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response)
        throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        log.info("username={}, age={}", username, age);
        response.getWriter().write("request-param-v1"); // void 타입이어도 response에 값을 쓰게 되면 출력이 된다
    }

    @ResponseBody   // 리턴값을 Http 메세지에 바로 넣어서 반환 -> @RestController와 같은 역할
    @RequestMapping("/request-param-v2")
    public String requestParamV2(@RequestParam("username") String userName, @RequestParam("age") int userAge){

        log.info("userName={}, userAge={}", userName, userAge);
        return "request-param-v2";
    }

    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(@RequestParam String username, @RequestParam int age){
        // @RequestParam의 벨류 속성을 생략 가능 -> 파라미터 이름과 변수 명이 같을 때
        log.info("username={}, age={}", username, age);
        return "request-param-v3";
    }

    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username, int age){
        // @RequestParam 어노테이션 생략 가능 -> String, int, Integer 등의 단순 타입이며 파라미터 이름과 변수 명이 같을 때
        log.info("username={}, age={}", username, age);
        return "request-param-v4";
    }

    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(@RequestParam(value = "username", required = true) String userName, @RequestParam(value = "age", required = false) int userAge){
        // required 속성이 true(디폴트 설정)인 경우 파라미터가 넘어오지 않으면 에러 발생 -> 400 Bad Request -> '파라미터이름=' 으로 넘어올 경우 빈 문자열로 인식하여 값을 받을 수 있게 됨
        // required 속성이 false인 경우 파라미터 값이 넘어오지 않아도 됨 -> null이 넘어오기 때문에 기본 타입으로 받을 경우 500 에러 발생 -> 래퍼 클래스로 받아줘야함
        log.info("userName={}, userAge={}", userName, userAge);
        return "request-param-required";
    }

    @ResponseBody   // 리턴값을 Http 메세지에 바로 넣어서 반환 -> @RestController와 같은 역할
    @RequestMapping("/request-param-default")
    public String requestParamDefault(@RequestParam(value = "username", required = true, defaultValue = "guest") String userName, @RequestParam(value = "age", required = false, defaultValue = "-1") int userAge){
//        2024-01-21T05:51:53.149+09:00  INFO 15568 --- [nio-8080-exec-2] h.s.b.request.RequestParamController     : userName=guest, userAge=-1
        // defaultValue 속성을 설정하여 파라미터를 넘기지 않았을 때의 기본 값을 설정할 수 있다 -> required 옵션 여부와 상관 없이 설정됨
        // 'username=' 등 빈 문자가 들어와도 기본 값을 적용한다
        log.info("userName={}, userAge={}", userName, userAge);
        return "request-param-default";
    }

    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam Map<String, Object> paramMap){
//        http://localhost:8080/request-param-map
//        userName=null, userAge=null
//        http://localhost:8080/request-param-map?username=kin&age=13
//        userName=kin, userAge=13
//        파라미터 값이 1개가 확실하면 Map을 사용하고, 1개 이상이 될 수도 있다면 MultiValueMap을 사용한다
        log.info("userName={}, userAge={}", paramMap.get("username"), paramMap.get("age"));
        return "request-param-map";
    }

    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@RequestParam String username, @RequestParam int age){
        HelloData helloData = new HelloData();
        helloData.setUsername(username);
        helloData.setAge(age);

        log.info("userName={}, userAge={}", helloData.getUsername(), helloData.getAge());
        log.info("helloData={}", helloData);
        return "model-attribute-v1";
    }

    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(@ModelAttribute HelloData helloData){
        // @ModelAttribute 가 있으면 어노테이션이 붙은 객체 생성 후 요청 파라미터 이름으로 getter, setter를 호출하여 파라미터 값을 바인딩함
        // 값 변경은 setter, 조회는 getter를 호출함
        // 요청 파라미터 타입과 객체 프로퍼티 필드의 타입이 맞지 않으면 BindingException이 발생함
//        HelloData helloData = new HelloData();
//        helloData.setUsername(username);
//        helloData.setAge(age);

        log.info("userName={}, userAge={}", helloData.getUsername(), helloData.getAge());
        log.info("helloData={}", helloData);
        return "model-attribute-v2";
    }

    @ResponseBody
    @RequestMapping("/model-attribute-v3")
    public String modelAttributeV3(HelloData helloData){
    // @ModelAttribute 어노테이션 생략하고 객체를 바로 매개변수에 넣을 수도 있음 -> 바인딩 객체
    // 객체 필드 이름이 파라미터 이름과 같아야 한다    
    // @RequestParam, @ModelAttribute 둘 다 생략할 경우 단순 타입은 RequestParam, 그 이외는 ModelAttribute를 스프링에서 자동으로 붙여줌
    // argument resolver로 지정해둔 타입에는 ModelAttribute 가 붙지 않음    

        log.info("userName={}, userAge={}", helloData.getUsername(), helloData.getAge());
        log.info("helloData={}", helloData);
        return "model-attribute-v3";
    }



}
