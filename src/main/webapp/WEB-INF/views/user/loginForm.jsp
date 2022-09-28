<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
</head>
<body>
	<div class="center-content">
		
		<!-- 메인해더 -->
	 	<a href="/jblog/main">
			<img class="logo" src="${pageContext.request.contextPath}/assets/images/logo.jpg">
		</a>
		<ul class="menu">
			<!-- 로그인 전 메뉴 -->
			<li><a href="/jblog/user/loginForm">로그인</a></li>
			<li><a href="/jblog/user/joinForm">회원가입</a></li>
 		</ul>
		
		<form class="login-form" method="post" action="${pageContext.request.contextPath}/user/doLogin">
      		<label>아이디</label> 
      		<input type="text" name="id" id="id" required="required">
      		
      		<label>패스워드</label> 
      		<input type="password" name="password" id="password" required="required" style="width: 240px;height: 27px;">
      		
      		<c:if test="${fail == 0}">
	      		<p class="form-error">
					로그인 실패<br>
					아이디/패스워드를 확인해 주세요
				</p>
      		</c:if>
      		
      		
      		<input type="submit" value="로그인" style="margin-top: 10px;">
		</form>
		
	</div>
</body>

</html>