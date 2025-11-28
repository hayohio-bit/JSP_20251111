
## 액션 태그

- jsp:include : 동적 페이지 포함
- jsp:forward : 페이지 이동
- jsp:useBean : 자바빈즈 사용
- jsp:setProperty : 빈즈 속성 설정
- jsp:getProperty : 빈즈 속성 가져오기

## EL (Expression Language)

- 문법 : ${expression}
- 내장 객체 : ${param}, ${paramValues}, ${cookie}, ${header}
- 영역 객체 : ${pageScope}, ${requestScope}, ${sessionScope}, ${applicationScope}
- 연산자 : +, -, *, /, %, ==, !=, <, >, <=, >=, &&, ||, !, empty

## JSTL (JSP Standard Tag Library)

- 코어 : <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
- <c:if> : 조건문
- <c:choose>, <c:when>, <c:otherwise> : 다중 조건문
- <c:forEach> : 반복문
- <c:set> : 변수 설정
- <c:out> : 출력

## 세션 관리

- session.setAttribute("key", value) : 세션에 데이터 저장
- session.getAttribute("key") : 세션에서 데이터 가져오기
- session.invalidate() : 세션 무효화
- session.setMaxInactiveInterval(1800) : 타임아웃 설정 (초)

## 쿠키 처리

- Cookie cookie = new Cookie("name", "value")
- cookie.setMaxAge(60*60*24) : 유효기간 설정
- response.addCookie(cookie) : 쿠키 추가
- Cookie[] cookies = request.getCookies() : 쿠키 가져오기

## JSP 기본 개념

- JavaServer Pages : HTML 내에 Java 코드를 삽입하여 동적 웹 페이지 생성
- 서블릿으로 변환되어 실행
- MVC 패턴의 View 계층에 사용

## JSP 태그

- <%@ %> : 지시자 (Directive)
- <% %> : 스크립틀릿 (Scriptlet) - Java 코드
- <%= %> : 표현식 (Expression) - 값 출력
- <%! %> : 선언문 (Declaration) - 메서드/변수 선언

## 지시자 (Directive)

- <%@ page %> : 페이지 설정
    - contentType="text/html; charset=UTF-8"
    - import="java.util.*"
    - errorPage="error.jsp"
- <%@ include %> : 파일 포함
- <%@ taglib %> : 태그 라이브러리 사용

## 내장 객체

- request : 클라이언트 요청 정보
- response : 서버 응답 정보
- out : 출력 스트림
- session : 세션 관리
- application : 애플리케이션 전역 정보
- pageContext : 페이지 컨텍스트
- config : 서블릿 설정 정보
- page : 현재 페이지 인스턴스
- exception : 예외 처리
- 
