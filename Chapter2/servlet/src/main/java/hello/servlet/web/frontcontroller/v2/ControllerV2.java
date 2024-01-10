package hello.servlet.web.frontcontroller.v2;

import hello.servlet.web.frontcontroller.MyView;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ControllerV2 {
  // 리턴 타입을 렌더링 코드인 MyView 를 리턴하는 것으로 변경
  // v1에서는 controller가 알아서 forward() 메서드를 호출했었음
  MyView process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
