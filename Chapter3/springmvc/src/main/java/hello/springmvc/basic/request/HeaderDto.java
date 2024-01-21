package hello.springmvc.basic.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Locale;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;

@Setter
@Getter // 객체 타입 리턴을 위해서는 Getter 메서드들이 필요하다
@Builder
public class HeaderDto {
    @JsonProperty
    private String request;
    @JsonProperty
    private String response;
    @JsonProperty
    private String httpMethod ;
    @JsonProperty
    private String locale;
    @JsonProperty
    private MultiValueMap<String, String> headerMap;
    @JsonProperty
    private String host;
    @JsonProperty
    private String cookie;

}
