package hello.servlet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@ServletComponentScan	// Servlet Auto Scan
@SpringBootApplication
public class ServletApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServletApplication.class, args);
	}
	// Spring Boot에서의 View Resolver 동작
	// 내부에서 하단의 코드를 실행한다 -> prefix와 suffix 정보는 application.properties에서 가져옴
//	@Bean
//	ViewResolver internalResourceViewResover(){
//		return new InternalResourceViewResolver("WEB-INF/views/", ".jsp");
//	}

}
