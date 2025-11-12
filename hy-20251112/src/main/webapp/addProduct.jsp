<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <% %>
<!DOCTYPE html>
<%
	//세션 유효성 검사
	String loggedInUser = (String) session.getAttribute("loggedInUser");
	if(loggedInUser == null){
		response.sendRedirect("login.jsp"); //로그인되어 있지 않으면 로그인 페이지로 리다이렉트
		return;
	}
	
	//장바구니 (세션에 저장될 리스트)
	List<String> cart = (List<String>) session.getAttribute("cart");
	if(cart == null){
		car = new ArrayList<>();
		session.setAttribute("cart")
	}
%>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>