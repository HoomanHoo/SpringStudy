<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
 <%-- JSTL 사용 안 할 경우 --%>
<%-- <%
 // 프로그램 로직으로 반복문을 돌림
 // out 역시 그대로 쓸 수 있다
 for (Member member : members) {
 out.write(" <tr>");
 out.write(" <td>" + member.getId() + "</td>");
 out.write(" <td>" + member.getUsername() + "</td>");
 out.write(" <td>" + member.getAge() + "</td>");
 out.write(" </tr>");
 }
%> --%>
<%-- JSTL 사용하는 경우 --%>
 <c:forEach var="member" items="${members}">
 <tr>
 <td>${member.id}</td>
 <td>${member.username}</td>
 <td>${member.age}</td>
 </tr>
 </c:forEach>
 </tbody>
</table>
</body>
</html>