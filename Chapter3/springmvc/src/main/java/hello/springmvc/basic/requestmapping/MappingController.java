package hello.springmvc.basic.requestmapping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MappingController {

    @RequestMapping({"/hello-basic", "/hello-go"}) // URL을 통한 매핑, 대부분의 속성을 배열로 제공하기 때문에 두개 이상의 URL의 매핑이 가능하다
    public String helloBasic(){
        log.info("hello basic");
        return "ok";
    }
    @GetMapping("/mapping-get-v2")
    public String mappingGetV2(){
        log.info("mapping-get-v2");
        return "get-v2";
    }

    /**
     * Path Variable 스타일
     * 변수명이 같으면 생략 가능
     * @PathVariable("userId") String userId -> @PathVariable String userId
     * /mapping/userA
     */
    @GetMapping("/mapping/{userId}")
    public String mappingPath(@PathVariable String userId){
        log.info("mappingPath userId={}", userId);
        return userId;
    }
    /**
     * 최근 HTTP API는 리소스 경로에 식별자 넣는 스타일을 선호
     * /mapping/userA
     * /users/1
     */

    // 두개 이상의 Path Variable도 매핑 가능
    @GetMapping("/mapping/users/{userId}/orders/{orderId}")
    public String mappingMultifulPath(@PathVariable String userId, @PathVariable Long orderId){
        log.info("mapping path userId={}, orderId={}", userId, orderId);
        return "userId= " + userId + " orderId= " + orderId;
    }

    /**
     * 
     * params 속성을 통해 컨트롤러를 호출할 쿼리 파라미터의 조건을 설정할 수 있음
     * 잘 사용하지는 않음
     * param="mode"
     * param="!mode"
     * param="mode!=info"
     */
    @GetMapping(value = "/mapping-param", params = "mode=debug")
    public String mappingParams(){
        log.info("mappingParam");
        return "mappingparam";
    }

    // headers 속성을 통해 컨트롤러를 호출할 HTTP 메세지 헤더의 조건을 설정할 수 있음
    // 조건은 params 속성과 같음 / 잘 사용하지는 않음
    @GetMapping(value = "/mapping-param", headers = "mode=debug")
    public String mappingHeaders(){
        log.info("mappingHeaders");
        return "mappingheaders";
    }

    // headers 옵션에서 ContentType을 지정할 수도 있으나 ContentType(MediaType)에 따라 호출될 컨트롤러를 결정하고자 하면 consumes 옵션을 쓰는 것이 더 좋음
    @PostMapping(value = "/mapping-consume", consumes = "application/json")
    public String mappingConsumes(){
        log.info("mappingConsume");
        return "mappingConsume";
    }

    /**
     * Accept 헤더 기반 Media Type (Content Type)
     * produces 속성을 이용하여 설정
     * Accept 헤더가 * / * 인 경우 호출 가능(모든 Accept 헤더 속성을 포함하기 때문)
     */
    @PostMapping(value = "/mapping-produce", produces = "text/html")
    public String mappingProduces(){
        log.info("mappingProduces");
        return "mappingProduces";
    }

}
