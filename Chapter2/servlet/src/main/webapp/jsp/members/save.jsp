<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="hello.servlet.domain.member.Member" %>
<%@ page import="hello.servlet.domain.member.MemberRepository" %>
<%
        // request, response는 그대로 사용 가능 -> JSP는 Servlet으로 변환 되기 때문에
        // 대신 객체 이름은 request, response로 고정해야한다
        MemberRepository memberRepository = MemberRepository.getInstance();

        // 요청 메세지 파싱
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        // 비즈니스 로직
        Member member = new Member(username, age);
        memberRepository.save(member);

%>
<!-- 주로 로직(자바 코드)를 위에 두고 HTML을 아래에 둔다 -->
<html>
<head>
 <title>Title</title>
</head>
<body>
성공
<ul>
  <li>id=<%=member.getId()%></li>
  <li>username=<%=member.getUsername()%></li>
  <li>age=<%=member.getAge()%></li>
</ul>
<a href="/index.html">메인</a>
</body>
</html>