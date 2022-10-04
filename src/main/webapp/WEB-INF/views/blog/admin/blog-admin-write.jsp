<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.12.4.js"></script>
<script type="text/javascript">
$(function(){
	var message = $("#msg").val();
	if(message == "success"){
		alert("게시글이 등록되었습니다.");
	}else if(message == "fail"){
		alert("게시글 등록에 실패했습니다.");
	}
	
});
</script>
</head>
<body>
	<input type="hidden" id="msg" value="${msg}">
	<div id="container">
		
		<!-- 블로그 해더 -->
		<div id="header">
			<h1><a href="/jblog/main">${basic.blogTitle}</a></h1>
			<ul class="menu">
				<c:choose>
					<c:when test='${empty authUser}'>
						<!-- 로그인 전 메뉴 -->
						<li><a href="/jblog/user/loginForm">로그인</a></li>
						<li><a href="/jblog/user/joinForm">회원가입</a></li>
					</c:when>
					<c:otherwise>
						<!-- 로그인 후 메뉴 -->
						<%--<li>로그인한 사용자: ${authUser.id}</li>
				  			<li>현재 방문한 페이지: ${userVo.id}</li> --%>
						<c:choose>
							<c:when test="${authUser.id eq userVo.id}">
								<li><a href="/jblog/${authUser.id}/admin/basic">내블로그 관리</a></li> 
							</c:when>
						</c:choose>
						<li><a href="/jblog/user/logout">로그아웃</a></li>
					</c:otherwise>
				</c:choose>
			</ul>
		</div>

		
		<div id="wrapper">
			<div id="content" class="full-screen">
				<ul class="admin-menu">
					<li><a href="/jblog/${authUser.id}/admin">기본설정</a></li>
					<li><a href="/jblog/${authUser.id}/admin/category">카테고리</a></li>
					<li class="selected"><a href="/jblog/${authUser.id}/admin/write">글작성</a></li>
				</ul>
				
				
				<form action="/jblog/${authUser.id}/admin/writePost" method="post">
			      	<table class="admin-cat-write">
			      		<tr>
			      			<td class="t" id="title">제목</td>
			      			<td>
			      				<input type="text" size="60" name="postTitle">
				      			<select name="cateNo" id="cateNo">
				      				<c:forEach items="${cateVo}" var="cateVo" varStatus="status">
				      					<option value="${cateVo.cateNo}">${cateVo.cateName}</option>
		      						</c:forEach>
				      			</select>
				      		</td>
			      		</tr>
			      		<tr>
			      			<td class="t">내용</td>
			      			<td><textarea name="postContent"></textarea></td>
			      		</tr>
			      		<tr>
			      			<td>&nbsp;</td>
			      			<td class="s"><input id="btnWrite" type="submit" value="포스트하기"></td>
			      		</tr>
			      	</table>
				</form>
			</div>
		</div>
		
		<!-- 푸터-->
		<div id="footer">
			<p>
				<strong>Spring 이야기</strong> is powered by JBlog (c)2018
			</p>
		</div>
	</div>
</body>
</html>