<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
 <%
    // 세션이 없으면 새로 생성 (기본 동작)
    // request.getSession(true); 와 동일
    String username = request.getParameter("username");
    String password = request.getParameter("password");

    // 간단한 인증 로직 (실제 애플리케이션에서는 데이터베이스와 연동)
	if (username != null && username.equals("user") && password !=null && password.equals("pass")){
		session.setAttribute("loggedInUser", username); // 세션에 사용자 이름 저장
	%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>환영합니다!</title>
</head>
<body>
	<h2><%= username %>님, 환영입니다!</h2>
	<p><a href="addProduct.jsp">상품 추가하기</a></p>
	<p><a href="logout.jsp">로그아웃</a></p>
</body>
</html>
<%
	} else{
%>
<!DOCTYPE html>
<html>
<head>
<title>로그인 실패</title>
</head>
<body>
	<h2>로그인 실패</h2>
	<p>아이디 또는 비밀번호를 확인해주세요.</p>
	<p><a href="logout.jsp">다시 로그인</a></p>
</body>
</html>
<%
	}
%>