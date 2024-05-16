<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">

<style type="text/css">

	a {
		color: black;
		text-decoration: none;
	}
	
	a:hover {
		color: dodgerblue;
		text-decoration: none;
	}

	.button {
		background-color: #04AA6D; /* Green */
		border: none;
		color: white;
		padding: 2px;
		text-align: center;
		text-decoration: none;
		display: inline-block;
		font-size: 12px;
		margin: 4px 2px;
		transition-duration: 0.4s;
		cursor: pointer;
		width: 55px;
		height: 30px;
	}
	
	.button1 {
		background-color: white;
		color: black;
		border: 1px solid #04AA6D;
	}
	
	.button1:hover {
		background-color: #04AA6D;
		color: white;
	}
	
	.button2 {
		background-color: dodgerblue;
		color: white;
		border: none;
		cursor: default;
	}

</style>

</head>
<body>

<div class="m-3">
	<table class="table table-hover" style="width: 1000px; margin-left: auto; margin-right: auto;">
	
		<tr class="table-primary">
			<th class="align-middle text-center" colspan="5" style="font-size: 30px;">
				게시판 보기
			</th>
		</tr>
	
		<tr>
			<td class="align-middle text-end" colspan="5">
				${mvcboardList.totalCount}(${mvcboardList.currentPage} / ${mvcboardList.totalPage})
			</td>
		</tr>
	
		<tr class="table-success">
			<td class="align-middle text-center" style="width: 70px;">글번호</td>
			<td class="align-middle text-center" style="width: 100px;">이름</td>
			<td class="align-middle text-center" style="width: 600px;">제목</td>
			<td class="align-middle text-center" style="width: 160px;">작성일</td>
			<td class="align-middle text-center" style="width: 70px;">조회수</td>
		</tr>
		
		<!-- mvcboardList에서 1페이지 분량의 글 목록만 꺼낸다. -->
		<c:set var="list" value="${mvcboardList.list}"/>
		
		<!-- 글이 없으면 없다고 출력한다. -->
		<c:if test="${list.size() == 0}">
		<tr class="table-danger">
			<td colspan="5" class="align-middle">
				<marquee>테이블에 저장된 글이 없습니다.</marquee>
			</td>
		</tr>
		</c:if>
		
		<!-- 글이 있으면 글 목록을 출력한다. -->
		<c:if test="${list.size() != 0}">
		
		<!-- 컴퓨터 시스템의 현재 날짜를 얻어온다. -->
		<jsp:useBean id="date" class="java.util.Date"/>
		<!-- 현재 날짜와 작성일의 날짜를 비교하기 위해 현재 날짜의 서식을 지정한다. -->
		<fmt:formatDate var="today" value="${date}" pattern="yyyy.MM.dd"/>
		
		<c:forEach var="vo" items="${list}">
		<!-- 현재 날짜와 작성일의 날짜를 비교하기 위해 작성일의 서식을 지정한다. -->
		<fmt:formatDate var="writeDate" value="${vo.writeDate}" pattern="yyyy.MM.dd"/>
		<tr>
			<td class="align-middle text-center">${vo.idx}</td>
			<td class="align-middle text-center">
				<c:set var="name" value="${fn:replace(vo.name, '<', '&lt;')}"/>
				<c:set var="name" value="${fn:replace(name, '>', '&gt;')}"/>
				${name}
			</td>
			<td class="align-middle text-start">
			
				<c:if test="${vo.lev > 0}">
				<c:forEach var="i" begin="1" end="${vo.lev}" step="1">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</c:forEach>
				<i class="bi bi-arrow-return-right"></i>
				</c:if>
			
				<c:if test="${today == writeDate}">
					<span class="badge bg-success">NEW</span>
				</c:if>
				<c:set var="subject" value="${fn:replace(vo.subject, '<', '&lt;')}"/>
				<c:set var="subject" value="${fn:replace(subject, '>', '&gt;')}"/>
				<a href="increment?idx=${vo.idx}&currentPage=${mvcboardList.currentPage}">
					${subject}
				</a>
				<c:if test="${vo.hit > 10}">
					<span class="badge bg-danger">HOT</span>
				</c:if>
			</td>
			<td class="align-middle text-center">
				<c:if test="${today == writeDate}">
					<fmt:formatDate value="${vo.writeDate}" pattern="HH:mm:ss"/>
				</c:if>
				<c:if test="${today != writeDate}">
					<fmt:formatDate value="${vo.writeDate}" pattern="yyyy.MM.dd(E)"/>
				</c:if>
			</td>
			<td class="align-middle text-center">${vo.hit}</td>
		</tr>
		</c:forEach>
		</c:if>
		
		<!-- 페이지 이동 버튼 -->
		<tr>
			<td class="align-middle text-center" colspan="5">
				<!-- 처음으로 -->
				<c:if test="${mvcboardList.currentPage > 1}">
					<button 
						class="button button1"
						type="button"
						title="첫 페이지로 이동합니다."
						onclick="location.href='?currentPage=1'"
					>처음</button>
				</c:if>
				
				<c:if test="${mvcboardList.currentPage <= 1}">
					<button 
						class="button button2"
						type="button"
						title="이미 첫 페이지 입니다."
						disabled="disabled"
					>처음</button>
				</c:if>
				
				<!-- 10페이지 앞으로 -->
				<c:if test="${mvcboardList.startPage > 1}">
					<button 
						class="button button1" 
						type="button" 
						title="이전 10 페이지로 이동합니다." 
						onclick="location.href='?currentPage=${mvcboardList.startPage - 1}'"
					>이전</button>
				</c:if>
				
				<c:if test="${mvcboardList.startPage <= 1}">
					<button 
						class="button button2" 
						type="button" 
						disabled="disabled" 
						title="이미 첫 10 페이지 입니다."
					>이전</button>
				</c:if>
				
				<!-- 페이지 이동 버튼 -->
				<c:forEach var="i" begin="${mvcboardList.startPage}" end="${mvcboardList.endPage}" step="1">
				
					<c:if test="${mvcboardList.currentPage == i}">
						<button 
							class="button button2" 
							type="button" 
							disabled="disabled"
						>${i}</button>
					</c:if>
				
					<c:if test="${mvcboardList.currentPage != i}">
						<button 
							class="button button1" 
							type="button" 
							onclick="location.href='?currentPage=${i}'"
						>${i}</button>
					</c:if>
				
				</c:forEach>
				
				<!-- 10페이지 뒤로 -->
				<c:if test="${mvcboardList.endPage < mvcboardList.totalPage}">
					<button 
						class="button button1" 
						type="button" 
						title="다음 10 페이지로 이동합니다." 
						onclick="location.href='?currentPage=${mvcboardList.endPage + 1}'"
					>다음</button>
				</c:if>
				
				<c:if test="${mvcboardList.endPage >= mvcboardList.totalPage}">
					<button 
						class="button button2" 
						type="button" 
						disabled="disabled" 
						title="이미 마지막 10 페이지 입니다."
					>다음</button>
				</c:if>
				
				<!-- 마지막으로 -->
				<c:if test="${mvcboardList.currentPage < mvcboardList.totalPage}">
					<button 
						class="button button1" 
						type="button" 
						title="마지막 페이지로 이동합니다." 
						onclick="location.href='?currentPage=${mvcboardList.totalPage}'"
					>마지막</button>
				</c:if>
	
				<c:if test="${mvcboardList.currentPage >= mvcboardList.totalPage}">
					<button 
						class="button button2" 
						type="button" 
						disabled="disabled" 
						title="이미 마지막 페이지 입니다."
					>마지막</button>
				</c:if>
			</td>
		</tr>
		
		<!-- 글쓰기 버튼 -->
		<tr class="table-info">
			<td class="align-middle text-end" colspan="5">
				<input
					class="btn btn-outline-primary btm-sm"
					type="button" 
					value="글쓰기" 
					style="font-size: 13px;" 
					onclick="location.href='insert'"/>
			</td>
		</tr>		
	
	</table>
</div>

</body>
</html>





















