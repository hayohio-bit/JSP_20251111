# JSP (JavaServer Pages) 학습 정리

## 목차
1. [JSP 기본 개념](#jsp-기본-개념)
2. [JSP 생명주기 (Life Cycle)](#jsp-생명주기-life-cycle)
3. [JSP 태그](#jsp-태그)
4. [지시자 (Directive)](#지시자-directive)
5. [내장 객체 (Implicit Objects)](#내장-객체-implicit-objects)
6. [액션 태그 (Action Tags)](#액션-태그-action-tags)
7. [EL (Expression Language)](#el-expression-language)
8. [JSTL (JSP Standard Tag Library)](#jstl-jsp-standard-tag-library)
9. [세션 관리](#세션-관리)
10. [쿠키 처리](#쿠키-처리)
11. [MVC 패턴](#mvc-패턴)

---

## JSP 기본 개념

- **JavaServer Pages** : HTML 내에 Java 코드를 삽입하여 동적 웹 페이지를 생성하는 기술
- 웹 컨테이너가 자동으로 JSP 페이지를 서블릿으로 변환하여 실행
- MVC 패턴의 **View** 계층에 주로 사용
- 확장자: `.jsp`

---

## JSP 생명주기 (Life Cycle)

JSP는 다음 단계를 거쳐 실행됨:
1) Translation (번역) : JSP 파일 → .java 서블릿 소스 파일로 변환
2) Compilation (컴파일) : .java 파일 → .class 파일로 컴파일
3) Class Loading (클래스 로딩) : 컴파일된 클래스 로드
4) Instantiation (인스턴스화) : 서블릿 인스턴스 생성
5) Initialization (초기화) : jspInit() 메서드 호출
6) Request Processing (요청 처리) : _jspService() 메서드 호출
7) Destruction (소멸) : jspDestroy() 메서드 호출

### 생명주기 메서드
| 메서드 | 설명 | 오버라이드 가능 여부 |
|--------|------|---------------------|
| `jspInit()` | 초기화 시 1회 호출 (DB 연결, 파일 열기 등) | O |
| `_jspService(request, response)` | 요청마다 호출, 응답 생성 담당 | X (자동 생성) |
| `jspDestroy()` | 소멸 시 1회 호출 (리소스 정리) | O |

> jspInit(), jspDestroy()는 오버라이드 가능하지만, _jspService() 메서드는 오버라이드할 수 없다.

---

## JSP 태그

| 태그 | 이름 | 설명 | 예시 |
|------|------|------|------|
| `<%@ %>` | 지시자 (Directive) | 페이지 설정, 파일 포함 등 | `<%@ page contentType="text/html" %>` |
| `<% %>` | 스크립틀릿 (Scriptlet) | Java 코드 블록 작성 | `<% int x = 10; %>` |
| `<%= %>` | 표현식 (Expression) | 값을 출력 | `<%= x %>` |
| `<%! %>` | 선언문 (Declaration) | 메서드/변수 선언 | `<%! int count = 0; %>` |
| `<%-- --%>` | 주석 (Comment) | JSP 주석 (클라이언트에 전송되지 않음) | `<%-- 주석 내용 --%>` |

---

## 지시자 (Directive)

### 1. page 지시자 (`<%@ page %>`)
페이지 전반의 설정을 정의

```jsp
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*, java.io.*" %>
<%@ page errorPage="error.jsp" %>
<%@ page isErrorPage="true" %>
<%@ page session="true" %>
<%@ page isELIgnored="false" %>
```

| 속성 | 설명 | 기본값 |
|------|------|--------|
| `contentType` | 응답의 MIME 타입 및 인코딩 | `text/html` |
| `import` | 사용할 Java 패키지/클래스 | 없음 |
| `errorPage` | 에러 발생 시 이동할 페이지 | 없음 |
| `isErrorPage` | 현재 페이지가 에러 페이지인지 여부 | `false` |
| `session` | 세션 사용 여부 | `true` |
| `isELIgnored` | EL 표현식 무시 여부 | `false` (JSP 2.0+) |

### 2. include 지시자 (`<%@ include %>`)
정적 파일 포함 (컴파일 시점에 포함)

```jsp
<%@ include file="header.jsp" %>
```

### 3. taglib 지시자 (`<%@ taglib %>`)
태그 라이브러리 사용 선언

```jsp
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
```

---

## 내장 객체 (Implicit Objects)

JSP에서 지원하는 내장 객체는 총 9개이다. 이 객체들은 JSP 컨테이너가 각 페이지에서 개발자에게 자동으로 제공하는 Java 객체로, 명시적 선언 없이 바로 사용할 수 있다.

| 객체 | 타입 | 설명 |
|------|------|------|
| `request` | `HttpServletRequest` | 클라이언트 요청 정보 (파라미터, 헤더, 쿠키 등) |
| `response` | `HttpServletResponse` | 서버 응답 정보 (리다이렉트, 헤더 설정 등) |
| `out` | `JspWriter` | 출력 스트림 (클라이언트에 데이터 전송) |
| `session` | `HttpSession` | 세션 관리 (사용자별 데이터 저장) |
| `application` | `ServletContext` | 애플리케이션 전역 정보 (모든 사용자 공유) |
| `pageContext` | `PageContext` | 페이지 컨텍스트 (모든 스코프 접근 가능) |
| `config` | `ServletConfig` | 서블릿 설정 정보 (초기화 파라미터 등) |
| `page` | `Object (this)` | 현재 페이지의 서블릿 인스턴스 |
| `exception` | `Throwable` | 예외 처리 (isErrorPage="true"인 페이지에서만 사용) |

### 주요 메서드 예시

```jsp
<%-- request --%>
request.getParameter("name")          // 요청 파라미터 가져오기
request.setAttribute("key", value)     // 속성 설정
request.getRequestDispatcher("page.jsp").forward(request, response)  // 포워딩

<%-- response --%>
response.sendRedirect("page.jsp")     // 리다이렉트
response.setContentType("text/html")  // 콘텐츠 타입 설정

<%-- out --%>
out.print("텍스트")                    // 출력
out.println("텍스트")                  // 줄바꿈 포함 출력

<%-- pageContext --%>
pageContext.setAttribute("key", value, PageContext.SESSION_SCOPE)   // 특정 스코프에 속성 설정
pageContext.findAttribute("key")       // 모든 스코프에서 속성 검색
```

### 4대 스코프 (Scope)
| 스코프 | 객체 | 범위 |
|--------|------|------|
| Page | `pageContext` | 현재 페이지 내에서만 유효 |
| Request | `request` | 하나의 요청 내에서 유효 (forward 포함) |
| Session | `session` | 사용자 세션 동안 유효 |
| Application | `application` | 웹 애플리케이션 전체에서 유효 |

> exception 내장 객체는 isErrorPage 값이 "True"로 설정된 JSP 페이지에서만 사용할 수 있다.

---

## 액션 태그 (Action Tags)

| 태그 | 설명 |
|------|------|
| `<jsp:include page="file.jsp" />` | 동적 페이지 포함 (실행 시점에 포함, include 지시자와 차이점) |
| `<jsp:forward page="page.jsp" />` | 다른 페이지로 요청 전달 (포워딩) |
| `<jsp:useBean id="bean" class="패키지.클래스" scope="스코프" />` | 자바빈즈 객체 생성/조회 |
| `<jsp:setProperty name="bean" property="속성명" value="값" />` | 빈즈 속성 값 설정 |
| `<jsp:getProperty name="bean" property="속성명" />` | 빈즈 속성 값 출력 |
| `<jsp:param name="key" value="value" />` | include, forward 시 파라미터 전달 |

### include 지시자 vs include 액션 태그

| 구분 | `<%@ include %>` | `<jsp:include />` |
|------|-------------------|---------------------|
| 포함 시점 | 컴파일(번역) 시점 | 실행(요청) 시점 |
| 포함 방식 | 소스 코드 자체를 합침 | 실행 결과를 합침 |
| 변경 반영 | 원본 수정 시 재컴파일 필요 | 매 요청마다 최신 내용 반영 |

---

## EL (Expression Language)

Expression Language(EL)은 JSP 2.0에서 도입되었으며, 빈 프로퍼티와 암시적 객체로부터 데이터에 접근하는 과정을 간소화하는 것이 주된 목적이다.

### 기본 문법
```jsp
${expression}
```
expression은 런타임에 평가되어 출력 스트림으로 전달되는 값이다.

### EL 내장 객체

| 객체 | 설명 |
|------|------|
| `${param.이름}` | 요청 파라미터 (단일 값) |
| `${paramValues.이름}` | 요청 파라미터 (배열, 다중 값) |
| `${header["이름"]}` | HTTP 요청 헤더 (단일 값) |
| `${headerValues["이름"]}` | HTTP 요청 헤더 (배열) |
| `${cookie.이름.value}` | 쿠키 값 |
| `${pageContext.request.contextPath}` | 컨텍스트 경로 등 pageContext 접근 |
| `${initParam.이름}` | 컨텍스트 초기화 파라미터 (web.xml) |

### EL 영역 객체 (Scope)

| 객체 | 설명 |
|------|------|
| `${pageScope.key}` | Page 스코프 속성 |
| `${requestScope.key}` | Request 스코프 속성 |
| `${sessionScope.key}` | Session 스코프 속성 |
| `${applicationScope.key}` | Application 스코프 속성 |

> 스코프를 명시하지 않으면 page → request → session → application 순서로 검색

### EL 연산자

| 종류 | 연산자 |
|------|--------|
| 산술 | `+`, `-`, `*`, `/`(div), `%`(mod) |
| 비교 | `==`(eq), `!=`(ne), `<`(lt), `>`(gt), `<=`(le), `>=`(ge) |
| 논리 | `&&`(and), `\|\|`(or), `!`(not) |
| 삼항 | `조건 ? 참 : 거짓` |
| 빈 값 체크 | `empty` |
| 접근 | `.` (프로퍼티), `[]` (배열/리스트/맵) |

### EL NULL 처리
산술 연산에서 EL은 NULL을 0으로 처리하고, 논리 연산에서는 false로 처리하며, 문자열이 NULL이면 빈 문자열을 반환한다.

---

## JSTL (JSP Standard Tag Library)

JSTL은 많은 JSP 애플리케이션에서 공통으로 사용되는 핵심 기능을 캡슐화한 커스텀 태그 모음이며, JSP 페이지 내에서 Java 코드 작성의 필요성을 줄여 더 깔끔하고 읽기 쉬운 코드 작성을 돕는다.

### JSTL 라이브러리 종류

| 라이브러리 | 접두어 | URI | 설명 |
|-----------|--------|-----|------|
| **Core** | `c` | `http://java.sun.com/jsp/jstl/core` | 조건문, 반복문, 변수, URL 등 |
| **Formatting** | `fmt` | `http://java.sun.com/jsp/jstl/fmt` | 날짜, 숫자 포맷, 국제화(i18n) |
| **SQL** | `sql` | `http://java.sun.com/jsp/jstl/sql` | 데이터베이스 연동 |
| **XML** | `x` | `http://java.sun.com/jsp/jstl/xml` | XML 파싱, 변환 |
| **Functions** | `fn` | `http://java.sun.com/jsp/jstl/functions` | 문자열 처리 함수 |

### 1. Core 태그 (주요)

```jsp
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
```

| 태그 | 설명 | 예시 |
|------|------|------|
| `<c:set>` | 변수 설정 | `<c:set var="name" value="홍길동" />` |
| `<c:out>` | 값 출력 (XSS 방지) | `<c:out value="${name}" />` |
| `<c:remove>` | 변수 제거 | `<c:remove var="name" scope="session" />` |
| `<c:if>` | 단일 조건문 | `<c:if test="${조건}">...</c:if>` |
| `<c:choose>` | 다중 조건문 (switch와 유사) | 아래 참조 |
| `<c:forEach>` | 반복문 | `<c:forEach var="item" items="${list}">` |
| `<c:forTokens>` | 구분자 기반 반복 | `<c:forTokens items="a,b,c" delims=",">` |
| `<c:url>` | URL 생성 | `<c:url value="/page.jsp" />` |
| `<c:redirect>` | 리다이렉트 | `<c:redirect url="/login.jsp" />` |
| `<c:import>` | 외부 리소스 포함 | `<c:import url="http://..." />` |
| `<c:catch>` | 예외 처리 | `<c:catch var="error">...</c:catch>` |
| `<c:param>` | 파라미터 전달 | url, redirect, import 내에서 사용 |

#### 다중 조건문 예시
```jsp
<c:choose>
    <c:when test="${score >= 90}">A</c:when>
    <c:when test="${score >= 80}">B</c:when>
    <c:when test="${score >= 70}">C</c:when>
    <c:otherwise>F</c:otherwise>
</c:choose>
```

#### forEach 속성
| 속성 | 설명 |
|------|------|
| `var` | 현재 항목을 참조할 변수명 |
| `items` | 반복할 컬렉션 |
| `begin` | 시작 인덱스 |
| `end` | 종료 인덱스 |
| `step` | 증가값 |
| `varStatus` | 반복 상태 객체 (index, count, first, last) |

### 2. Formatting 태그 (주요)

```jsp
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
```

| 태그 | 설명 |
|------|------|
| `<fmt:formatDate>` | 날짜/시간 포맷팅 |
| `<fmt:parseDate>` | 문자열을 날짜로 파싱 |
| `<fmt:formatNumber>` | 숫자 포맷팅 (통화, 퍼센트 등) |
| `<fmt:parseNumber>` | 문자열을 숫자로 파싱 |
| `<fmt:setLocale>` | 로케일 설정 |
| `<fmt:bundle>` / `<fmt:setBundle>` | 리소스 번들 (다국어 지원) |
| `<fmt:message>` | 국제화 메시지 출력 |
| `<fmt:timeZone>` / `<fmt:setTimeZone>` | 타임존 설정 |

### 3. SQL 태그 (주요)

```jsp
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
```

| 태그 | 설명 |
|------|------|
| `<sql:setDataSource>` | 데이터 소스 설정 |
| `<sql:query>` | SELECT 쿼리 실행 |
| `<sql:update>` | INSERT, UPDATE, DELETE 실행 |
| `<sql:param>` | 쿼리 파라미터 바인딩 (SQL 인젝션 방지) |
| `<sql:dateParam>` | 날짜 파라미터 바인딩 |
| `<sql:transaction>` | 트랜잭션 처리 |

> SQL 태그는 보안 및 설계상의 이유로 실제 운영 애플리케이션에서는 사용을 권장하지 않는다.

### 4. Functions 태그 (주요)

```jsp
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
```

| 함수 | 설명 |
|------|------|
| `${fn:length(obj)}` | 컬렉션/문자열 길이 |
| `${fn:contains(str, substr)}` | 문자열 포함 여부 |
| `${fn:containsIgnoreCase(str, substr)}` | 대소문자 무시 포함 여부 |
| `${fn:startsWith(str, prefix)}` | 접두어 확인 |
| `${fn:endsWith(str, suffix)}` | 접미어 확인 |
| `${fn:indexOf(str, substr)}` | 위치 반환 |
| `${fn:substring(str, begin, end)}` | 부분 문자열 |
| `${fn:substringBefore(str, substr)}` | 특정 문자열 이전 부분 |
| `${fn:substringAfter(str, substr)}` | 특정 문자열 이후 부분 |
| `${fn:replace(str, before, after)}` | 문자열 치환 |
| `${fn:split(str, delim)}` | 문자열 분리 |
| `${fn:trim(str)}` | 앞뒤 공백 제거 |
| `${fn:toLowerCase(str)}` | 소문자 변환 |
| `${fn:toUpperCase(str)}` | 대문자 변환 |
| `${fn:escapeXml(str)}` | XML 특수문자 이스케이프 |

### Maven 의존성 (JSTL)
```xml
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>jstl</artifactId>
    <version>1.2</version>
</dependency>
```

---

## 세션 관리

### 주요 메서드

```java
// 세션에 데이터 저장
session.setAttribute("key", value);

// 세션에서 데이터 가져오기
Object value = session.getAttribute("key");

// 특정 속성 제거
session.removeAttribute("key");

// 세션 전체 무효화 (로그아웃 시 사용)
session.invalidate();

// 타임아웃 설정 (초 단위, 30분 = 1800초)
session.setMaxInactiveInterval(1800);

// 세션 ID 가져오기
String id = session.getId();

// 세션 생성 시간
long creationTime = session.getCreationTime();

// 마지막 접근 시간
long lastAccessed = session.getLastAccessedTime();

// 새로운 세션 여부 확인
boolean isNew = session.isNew();
```

### web.xml에서 세션 타임아웃 설정 (분 단위)
```xml
<session-config>
    <session-timeout>30</session-timeout>
</session-config>
```

---

## 쿠키 처리

### 쿠키 생성 및 설정

```java
// 쿠키 생성
Cookie cookie = new Cookie("name", "value");

// 유효기간 설정 (초 단위, 1일 = 86400초)
cookie.setMaxAge(60 * 60 * 24);

// 쿠키 경로 설정
cookie.setPath("/");

// 보안 쿠키 설정 (HTTPS에서만 전송)
cookie.setSecure(true);

// HttpOnly 설정 (JavaScript에서 접근 불가)
cookie.setHttpOnly(true);

// 응답에 쿠키 추가
response.addCookie(cookie);
```

### 쿠키 읽기

```java
Cookie[] cookies = request.getCookies();
if (cookies != null) {
    for (Cookie c : cookies) {
        if (c.getName().equals("name")) {
            String value = c.getValue();
        }
    }
}
```

### 쿠키 삭제

```java
Cookie cookie = new Cookie("name", "");
cookie.setMaxAge(0);  // 유효기간을 0으로 설정하면 삭제
cookie.setPath("/");
response.addCookie(cookie);
```

### EL에서 쿠키 접근

```jsp
${cookie.name.value}
```

---

## MVC 패턴

MVC(Model-View-Controller)는 애플리케이션을 모델, 뷰, 컨트롤러 세 가지 주요 논리적 컴포넌트로 분리하는 소프트웨어 아키텍처 패턴이다.

### 구성 요소

| 구성요소 | 역할 | JSP에서의 구현 |
|----------|------|---------------|
| **Model** | 비즈니스 로직, 데이터 처리 | JavaBean, DAO, Service 클래스 |
| **View** | 사용자 인터페이스 (화면 표시) | JSP 페이지 |
| **Controller** | 요청 처리 및 흐름 제어 | Servlet |

### MVC 흐름

```
[클라이언트] → [Controller(Servlet)] → [Model(JavaBean/Service)] → [DB]
                      ↓
              [View(JSP)] → [클라이언트에게 응답]
```

웹 브라우저가 HTTP 요청을 컨트롤러(Servlet)에 보내면, 컨트롤러가 데이터베이스 또는 기타 소스에서 데이터를 가진 모델 객체를 가져온다. JSP는 애플리케이션의 뷰로서 컨트롤러로부터 필요한 모든 정보를 받으며, 비즈니스 레이어와 직접 상호작용할 필요가 없다.

### 간단한 예시 구조

```
src/
├── controller/
│   └── StudentServlet.java       // Controller (Servlet)
├── model/
│   ├── Student.java              // Model (JavaBean)
│   └── StudentService.java       // Model (Service/DAO)
└── webapp/
    └── WEB-INF/
        └── jsp/
            └── student-list.jsp  // View (JSP)
```

### MVC 패턴의 장점
- MVC 패턴은 애플리케이션의 각 부분에 대한 관심사를 분리하여 개발, 유지보수, 테스트를 더 쉽게 만든다.
- 역할 분리로 인해 팀 개발 시 분업이 용이
- View를 독립적으로 변경 가능 (비즈니스 로직에 영향 없음)

---

## 참고: JSP vs Servlet 비교

| 항목 | JSP | Servlet |
|------|-----|---------|
| 기반 | HTML 중심 (Java 삽입) | Java 중심 (HTML 삽입) |
| 용도 | View (화면 표시) | Controller (로직 처리) |
| 변환 | 컨테이너가 자동으로 Servlet으로 변환 | 직접 Java 클래스 작성 |
| 수정 반영 | JSP 수정 시 자동 재컴파일 | 수정 시 재컴파일 및 재배포 필요 |
| 출력 객체 | `JspWriter (out)` | `PrintWriter` |

---
