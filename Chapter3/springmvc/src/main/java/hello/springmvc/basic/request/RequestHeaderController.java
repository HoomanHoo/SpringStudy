package hello.springmvc.basic.request;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Locale;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class RequestHeaderController {
    
    @RequestMapping("/headers") // 어노테이션 기반 컨트롤러는 다양한 파라미터를 매개변수로 받을 수 있다
    public HeaderDto headers(HttpServletRequest request, HttpServletResponse response, HttpMethod httpMethod, Locale locale, @RequestHeader
        MultiValueMap<String, String> headerMap, @RequestHeader("host") String host, @CookieValue(value = "myCookie", required = false) String cookie){
//        @RequestHeader("host") -> 헤더 속성 이름 매핑 String host
//        MultiValueMap 하나의 키에 여러 벨류를 가질 수 있는 컬렉션 객체
//        @CookieValue(value = "myCookie", required = false) -> defaultValue 속성을 지정하여 값이 없을 경우의 기본 값을 지정할 수 있다 String cookie
        log.info("request={}", request);
        log.info("response={}", response);
        log.info("httpMethod={}", httpMethod);
        log.info("locale={}", locale);  // 로컬 로케일이 지정됨 (위치-국가/언어 정보)
        log.info("headerMap={}", headerMap);
        log.info("header host={}", host);
        log.info("myCookie={}", cookie);

        return HeaderDto.builder()
            .request(request.toString())
            .response(response.toString())
            .httpMethod(httpMethod.toString())
            .locale(locale.toString())
            .headerMap(headerMap)
            .host(host)
            .cookie(cookie)
            .build();
    }
    /**
     * 리턴 값
     * {
     *     "request": "org.apache.catalina.connector.RequestFacade@55147a7b",
     *     "response": "org.apache.catalina.connector.ResponseFacade@685d0d0",
     *     "httpMethod": "GET",
     *     "locale": "ko_KR",
     *     "headerMap": {
     *         "content-type": [
     *             "application/json"
     *         ],
     *         "user-agent": [
     *             "PostmanRuntime/7.36.1"
     *         ],
     *         "accept": [
     *             "* / *"
     *          ],
     *          "postman-token":[
     *              "3a93c2da-099a-4be6-bf86-427dd6e27583"
     *          ],
     *          "host":[
     *              "localhost:8080"
     *          ],
     *          "accept-encoding":[
     *              "gzip, deflate, br"
     *          ],
     *          "connection":[
     *              "keep-alive"
     *          ]
     *      },
     *  "host":"localhost:8080",
     *  "cookie":null
     *}
     */

}
