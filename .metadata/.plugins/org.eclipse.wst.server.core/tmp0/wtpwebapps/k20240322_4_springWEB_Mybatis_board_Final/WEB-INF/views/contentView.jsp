<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 보기</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
</head>
<body>

<form class="m-3" action="update" method="post">
	<table class="table table-hover" style="width: 600px; margin-left: auto; margin-right: auto;">
	
		<tr class="table-primary">
			<th class="align-middle text-center" colspan="4" style="font-size: 30px;">
				게시글 보기
			</th>
		</tr>
	
		<tr class="table-success">
			<td class="align-middle text-center" style="width: 100px;">글번호</td>
			<td class="align-middle text-center" style="width: 240px;">이름</td>
			<td class="align-middle text-center" style="width: 160px;">작성일</td>
			<td class="align-middle text-center" style="width: 100px;">조회수</td>
		</tr>
		
		<tr>
			<td class="align-middle text-center">${vo.idx}</td>
			<td class="align-middle text-center">
				<c:set var="name" value="${fn:replace(vo.name, '<', '&lt;')}"/>
				<c:set var="name" value="${fn:replace(name, '>', '&gt;')}"/>
				${name}
			</td>
			<td class="align-middle text-center">
				<jsp:useBean id="date" class="java.util.Date"/>
				<fmt:formatDate var="today" value="${date}" pattern="yyyy.MM.dd"/>
				<fmt:formatDate var="writeDate" value="${vo.writeDate}" pattern="yyyy.MM.dd"/>
				<c:if test="${today == writeDate}">
					<fmt:formatDate value="${vo.writeDate}" pattern="HH:mm:ss"/>
				</c:if>
				<c:if test="${today != writeDate}">
					<fmt:formatDate value="${vo.writeDate}" pattern="yyyy.MM.dd(E)"/>
				</c:if>
			</td>
			<td class="align-middle text-center">${vo.hit}</td>
		</tr>
		
		<tr>
			<th class="align-middle table-warning">
				<label for="subject">제목</label>
			</th>
			<td colspan="3">
				<input 
					id="subject" 
					class="form-control form-control-sm" 
					type="text" 
					name="subject"
					value="${vo.subject}"/>
			</td>
		</tr>

		<tr>
			<th class="align-middle table-warning">
				<label for="content">내용</label>
			</th>
			<td colspan="3">
				<textarea 
					id="content"
					class="form-control form-control-sm" 
					rows="10"
					name="content"
					style="resize: none;">${vo.content}</textarea>
			</td>
		</tr>
		
		<tr class="table-secondary">
			<th colspan="4" class="align-middle text-center">
				<input 
					class="btn btn-outline-primary btn-sm" 
					type="submit" 
					value="수정하기" 
					style="font-size: 13px;"/>
				<input 
					class="btn btn-outline-danger btn-sm" 
					type="button" 
					value="삭제하기" 
					style="font-size: 13px;"
					onclick="location.href='delete?idx=${vo.idx}&currentPage=${currentPage}'"/>
				<input 
					class="btn btn-outline-info btn-sm" 
					type="button" 
					value="답변하기" 
					style="font-size: 13px;"
					onclick="location.href='reply?idx=${vo.idx}&currentPage=${currentPage}'"/>
				<input 
					class="btn btn-outline-success btn-sm" 
					type="button" 
					value="돌아가기" 
					style="font-size: 13px;"
					onclick="location.href='list?currentPage=${currentPage}'"/>
			</th>
		</tr>
		
	</table>
	
	<input type="hidden" name="idx" value="${vo.idx}"/>
	<input type="hidden" name="currentPage" value="${currentPage}"/>
	
</form>

</body>
</html>
















