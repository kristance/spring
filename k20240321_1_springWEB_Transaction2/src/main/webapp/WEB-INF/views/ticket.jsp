<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>카드 결제</title>
</head>
<body>

<h2>카드 결제</h2>

<form action="ticketCard" method="POST">
	고객 아이디 : <input type="text" name="consumerId" /> <br />
	구매 티켓수 : <input type="text" name="amount" /><br />
	<input type="submit" value="구매" />
</form>


</body>
</html>