<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.12.4.js"></script>
<script type="text/javascript">
$(function () {
	$('#showPost').hide();
	
	var lastPostNo = $('#liiiiiiiiii').children('input').attr('value');
	console.log("lastPostNo:  "+lastPostNo);
	
	var postNo;
	console.log("postNo:  "+postNo);
	
	$(document).on("click", "a[id='postTitle']", function () {
		//var parentID = $(this).closest('li').attr('id')
	    postNo = $(this).closest('li').children('input').attr('value');
	    console.log("clicked postNo: "+postNo);
	  	$('#lastPost').hide();
		$('#showPost').show();
		
		// contentType: "application/json" 꼭 써주기
		$.ajax({
			url : "${pageContext.servletContext.contextPath}/showPost",
			type : "POST",
			dataType: "json",
			contentType: "application/json",
		    data: JSON.stringify({
		    	"postNo":postNo
		    }),
			success : function(res) {
				var result = res;
				console.log("값 : " + result);
				$("#resultPostTitle").text(result.postTitle);
				$("#resultPostContent").text(result.postContent);
			},
			error : function(xhr, error) { //xmlHttpRequest?
				console.error("error : " + error);
			}
		});
	});
});

</script>
</head>
<body>
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
			<div id="content">
				<!-- 최신의 게시글 표출 -->
				<div class="blog-content" id="lastPost">
					<h4>${lastPostVo.postTitle}</h4>
					<p>
						${lastPostVo.postContent}<br>
					</p>
				</div>
				<!-- 선택한 게시글의 내용 표출 -->
					<div class="blog-content" id="showPost">
						<h4 id="resultPostTitle"></h4>
						<p id="resultPostContent"></p>
					</div>
				
				
				
				<!-- 작성된 게시글리스트 표출 -->
				<ul class="blog-list" style="border-color: #eeeeee; border-style: solid;">
					<c:choose>
						<c:when test='${empty postVo}'>
							<h4>등록된 글이 없습니다.</h4>
						</c:when>
						<c:otherwise>
							<c:forEach items="${postVo}" var="postVo" varStatus="status">	
								<li id="liiiiiiiiii">
									<input type="hidden" name="postNo" value="${postVo.postNo}">
 									<a id="postTitle">${postVo.postTitle}</a>
									<span>${postVo.regDate}</span>
								</li>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</ul>
			</div>
		</div>

		<div id="extra">
			<div class="blog-logo">
				<img src="${pageContext.request.contextPath}/assets/images/spring-logo.jpg">				
			</div>
		</div>

		<div id="navigation">
			<h2>카테고리</h2>
			<ul>
				<c:forEach items="${categoryVo}" var="categoryVo" varStatus="status">	
					<li><a href="">${categoryVo.cateName}</a></li>
				</c:forEach>
			</ul>
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