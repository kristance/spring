<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>파일 업로드 결과보기</title>
</head>
<body>

<h1> fileUploadResult.jsp <-- ###</h1>

<c:forEach var="filename" items="${list }">
	${filename } 

</c:forEach>

</body>
</html>