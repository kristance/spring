<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>model 페이지</title>
</head>
<body>

<h2> model.jsp <-###</h2>

<!-- 컨트롤러에서 Model 인터페이스 객체에 저장되어 넣어오는 데이터는 EL을 사용해서 받는다. -->
id : ${id } <br /> 
pw : ${password } <br />
name : ${name } <br />
hobbies : ${hobbies } <br />

</body>
</html>