<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
</head>
<body>

<h2> login.jsp </h2>

<!-- userPrincipal -> 사용자 권한 -> 사용자가 비어있지 않다면 -> 누군가 로그인을 했다면 -->
	<c:if test="${not empty pageContext.request.userPrincipal}"> <!-- 로그인 상태인가? -->
		<h3>로그인 상태</h3>
		접속자 ID : ${pageContext.request.userPrincipal.name} 로그인<br />
		<a href="${pageContext.request.contextPath}/j_spring_security_logout" >로그아웃</a>
	</c:if>
 
<c:if test="${empty pageContext.request.userPrincipal}">
	<h3>로그아웃 상태 </h3>
</c:if>
 
 
 <!-- spring-security-taglibs를 사용해 ROLE_USER 그룹으로 들어온 사용자가 있나 확인한다. -->
 <s:authorize ifAnyGranted="ROLE_USER"> <!-- 로그인 상태인가 -->
		<h3>ROLE_USER 권한으로 로그인 상태</h3>
		<!-- spring-security-taglibs를 사용해서 시큐리티 기능으로 로그인한 아이디를 얻어온다. -->
		접속자 ID : <s:authentication property="name"/> 로그인<br />
		<a href="${pageContext.request.contextPath}/j_spring_security_logout" >로그아웃</a>
 </s:authorize>

 
 <s:authorize ifNotGranted="ROLE_USER"> <!-- 로그 아웃 상태인가? -->
	<h3>로그아웃 상태 </h3>
 </s:authorize>


</body>
</html>