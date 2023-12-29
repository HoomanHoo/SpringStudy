<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="hello.servlet.domain.member.Member" %>
<%@ page import="hello.servlet.domain.member.MemberRepository" %>
<%@ page import="java.util.List" %>
<%
        // request, response는 그대로 사용 가능 -> JSP는 Servlet으로 변환 되기 때문에
        // 대신 객체 이름은 request, response로 고정해야한다
        MemberRepository memberRepository = MemberRepository.getInstance();

        // 비즈니스 로직
        List<Member> members = memberRepository.findAll();

%>
<html>
<head>
 <meta charset="UTF-8">
 <title>Title</title>
</head>
<body>
<a href="/index.html">메인</a>
<table>
 <thead>
 <th>id</th>
 <th>username</th>
 <th>age</th>
 </thead>
 <tbody>
<%
 // 프로그램 로직으로 반복문을 돌림
 // out 역시 그대로 쓸 수 있다
 for (Member member : members) {
 out.write(" <tr>");
 out.write(" <td>" + member.getId() + "</td>");
 out.write(" <td>" + member.getUsername() + "</td>");
 out.write(" <td>" + member.getAge() + "</td>");
 out.write(" </tr>");
 }
%>
 </tbody>
</table>
</body>
</html>