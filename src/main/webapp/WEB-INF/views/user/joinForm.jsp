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
// 정규표현식
//공백
var regExp = /\s/g;	
//이름 체크 정규표현식: 한글과 영어 대/소문자만 가능
var acceptName = /^[가-힣a-zA-Z]+$/;
//비밀번호 체크: 소문자/숫자/특수문자를 사용,  8~16자리로 제한
var acceptPassword = /^(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,16}$/;
var success="";
$(function () {
	$('#btn-checkid').click(function() {
		idDuplicateCheck();
	});
});
function idDuplicateCheck(){
	var id = $('#id').val();
	if(id == null || regExp.test(id)){
		alert("아이디에 공백은 허용하지 않습니다.");
		$('#id').focus();
		$('#id').val("");
		result = false;
		return;
	}else{
		// contentType: "application/json" 꼭 써주기
		$.ajax({
			url : "${pageContext.servletContext.contextPath}/user/checkId",
			type : "POST",
			dataType: "json",
			async: true,
			contentType: "application/json",
		    data: JSON.stringify({
		    	"id":id
		    }),
			success : function(result) {
				if (result == 1) {
					$('#id').focus();
					$('#id').val("");
					$('#checkid-msg').text("다른 아이디로 가입해 주세요.").css("color", "red");
					success = false;
					console.log("1. checkId success결과 : "+success);
					return false;
				}else{
					$('#checkid-msg').text("사용할 수 있는 아이디 입니다.").css("color", "green");
					success = true;
					console.log("1. checkId success결과 : "+success);
				}
				$('#check-button').hide();
				
			},
			error : function(xhr, error) { //xmlHttpRequest?
				console.error("error : " + error);
			}
		});
	}
}

function joinCheck(){
	idDuplicateCheck();
	if(success == false){
		console.log("fasle뜸");
		return;
	}else{
		console.log("dmdkdkdkdkdkd");
	}
	
	var userName = $('#userName').val();
	var password = $('#password').val();
	//이름
	if(userName == null || regExp.test(userName) || !acceptName.test(userName)){
		alert("이름에 공백 또는 특수문자는 허용되지 않습니다.");
		$('#userName').focus();
		$('#userName').val("");
		success = false;
		console.log("2. userName success결과 : "+success);
	}else{
		success = true;
		console.log("2. userName success결과 : "+success);
	}
	//패스워드
	if(password == null || regExp.test(password)){
		alert("비밀번호에 공백은 허용되지 않습니다.");
		$('#password').focus();
		$('#password').val("");
		success = false;
		console.log("2. password success결과 : "+success);
	}else{
		success = true;
		console.log("2. password success결과 : "+success);
	}
	console.log("3. 최종 success결과 : "+success);
	
	if(success == false){
		return;
	}else{
		$('#join-form').submit();
	}
}

</script>
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

				<!-- 로그인 후 메뉴 -->
				<!-- 
				<li><a href="">로그아웃</a></li>
				<li><a href="">내블로그</a></li> 
				-->
 		</ul>
		
		<form class="join-form" id="join-form" method="post" action="${pageContext.servletContext.contextPath}/user/doJoin">
			<label class="block-label" for="name">이름</label>
			<input type="text" id="userName" name="userName"  value="" required="required"/>
			
			<label class="block-label" for="id">아이디</label>
			<input type="text" name="id"  value="" id="id" required="required"/>
			
			<input id="btn-checkid" type="button" value="id 중복체크">
			<p id="checkid-msg" class="form-error">
			&nbsp;
			</p>
			
			<label class="block-label" for="password">패스워드</label>
			<input type="password" id="password" name="password"  value="" required="required"/>

			<fieldset>
				<legend>약관동의</legend>
				<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
				<label class="l-float">서비스 약관에 동의합니다.</label>
			</fieldset>

			<input type="button" id="btn-join" onclick="joinCheck()" value="가입하기">

		</form>
	</div>

</body>



</html>