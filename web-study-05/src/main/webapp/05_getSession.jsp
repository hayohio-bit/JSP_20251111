<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
		String id = (String)session.getAttribute("id");
		String pwd = (String)session.getAttribute("pwd");
		int age = (Integer)session.getAttribute("age");		
%>

id: <%= id %> <br>
pwd: <%= pwd %> <br>
age: <%= age %> <br>

</body>
</html>

<!-- 04_setSession.jsp 실행 후 05_getSession.jsp 실행해야 정상 작동 됨 -->