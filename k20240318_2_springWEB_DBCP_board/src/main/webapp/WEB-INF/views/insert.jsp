<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>답변형 게시판 글 입력</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
</head>
<body>

<form class="m-3" action="insertOK" method="post">
	<table class="table table-hover" style="width: 600px; margin-left: auto; margin-right: auto;">

		<tr class="table-primary">
			<th class="align-middle text-center" colspan="2" style="font-size: 30px;">
				답변형 게시판 글 입력하기
			</th>
		</tr>

		<tr>
			<th class="align-middle table-warning" width="100">
				<label for="name">이름</label>
			</th>
			<td width="500">
				<input 
					id="name" 
					class="form-control form-control-sm" 
					type="text" 
					name="name" 
					style="width: 200px;"/>
			</td>
		</tr>

		<tr>
			<th class="align-middle table-warning">
				<label for="subject">제목</label>
			</th>
			<td>
				<input 
					id="subject" 
					class="form-control form-control-sm" 
					type="text" 
					name="subject"/>
			</td>
		</tr>

		<tr>
			<th class="align-middle table-warning">
				<label for="content">내용</label>
			</th>
			<td>
				<textarea 
					id="content"
					class="form-control form-control-sm" 
					rows="10"
					name="content"
					style="resize: none;"></textarea>
			</td>
		</tr>

		<tr class="table-secondary">
			<th colspan="2" class="align-middle text-center">
				<input 
					class="btn btn-outline-primary btn-sm" 
					type="submit" 
					value="저장하기" 
					style="font-size: 13px;"/>
				<input 
					class="btn btn-outline-danger btn-sm" 
					type="reset" 
					value="다시쓰기" 
					style="font-size: 13px;"/>
				<input 
					class="btn btn-outline-success btn-sm" 
					type="button" 
					value="돌아가기" 
					style="font-size: 13px;"
					onclick="history.back()"/>
			</th>
		</tr>

	</table>
</form>

</body>
</html>















