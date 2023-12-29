<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="hello.servlet.domain.member.Member" %>
<html>
<head>
 <meta charset="UTF-8">
</head>
<body>
성공
<ul>
<%-- ${}(표현식) 문법 사용 안 할 경우 --%>
 <li>id=<%=((Member)request.getAttribute("member")).getId()%></li>
<%-- 표현식을 사용할 경우 --%>
 <li>id=${member.id}</li><!-- member.id와 같은 형식을 프로퍼티 접근법이라고 한다 -->
 <li>username=${member.username}</li>
 <li>age=${member.age}</li>
</ul>
<a href="/index.html">메인</a>
</body>
</html>