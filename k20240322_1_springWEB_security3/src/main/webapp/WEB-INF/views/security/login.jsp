<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
</head>
<body>

<h2> login.jsp </h2>

<!-- userPrincipal -> 사용자 권한 -> 사용자가 비어있지 않다면 -> 누군가 로그인을 했다면 -->
<c:if test="${not empty pageContext.request.userPrincipal}">
	<h3>로그인 상태</h3>
</c:if>
<c:if test="${empty pageContext.request.userPrincipal}">
	<h3>로그아웃 상태 </h3>
</c:if>

접속자 ID : ${pageContext.request.userPrincipal.name}<br />
<a href="${pageContext.request.contextPath}/j_spring_security_logout" >로그아웃</a>


</body>
</html>