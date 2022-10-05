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
var sel_file = [];
$(document).ready(function(){
	//------------- 이미지 미리보기 시작 ------------------
	$("#input_img").on("change", handleImgFileSelect);
	
	//e : change 이벤트 객체
	// change 이벤트 설정하면  e는 이벤트가 된다. handleImgFileSelect에 파라미터 주면 e가 이벤트가 아니라 그냥 파라미터가 됨.
	function handleImgFileSelect(e){
		
		console.log("여길봐라: "+ JSON.stringify(e));
		//e.target : 파일객체
		//e.target.files : 파일객체 안의 파일들
		var files = e.target.files;
		var filesArr = Array.prototype.slice.call(files);
		
		//f : 파일 객체
		filesArr.forEach(function(f){
			//미리보기는 이미지만 가능함
			if(!f.type.match("image.*")){
				alert("이미지만 가능합니다");
				return;
			}
			
			// 파일객체 복사
			 sel_file.push(f);
			
			//파일을 읽어주는 객체 생성
			var reader = new FileReader();
			reader.onload = function(e){
				//forEach 반복 하면서 img 객체 생성
				var img_html = "<img src=\"" + e.target.result + "\" />";
				$(".img_wrap").append(img_html);
			}
			reader.readAsDataURL(f);
		});
	}
	
	//------------- 이미지 미리보기 끝 ------------------
	
	//첨부파일의 확장자가 exe, sh, zip, alz 경우 업로드를 제한
	var regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
	//최대 5MB까지만 업로드 가능
	var maxSize = 5242880; //5MB
	//확장자, 크기 체크
	function checkExtension(fileName, fileSize){
		if(fileSize >= maxSize){
			alert("파일 사이즈 초과");
			return false;
		}
		
		if(regex.test(fileName)){
			alert("해당 종류의 파일은 업로드할 수 없습니다.");
			return false;
		}
		//체크 통과
		return true;
	}
	
	//Upload 버튼 클릭 시 수행
	/* $("#uploadBtn").on("click",function(e){
		//FormData : 가상의 <form> 태그
		//Ajax를 이용하는 파일 업로드는 FormData를 이용
		var formData = new FormData();
		//<input type="file" 요소
		var inputFile = $("input[name='uploadFile']");
		//<input type="file" 요소 내의 이미지들
		
		console.log("inputFile[0]:::::::::::::" );
		var files = inputFile[0].files;
		
		console.log("files : " + files);
		
		for(var i=0;i<files.length;i++){
			console.log(files[i]);
			//확장자, 크기 체크
			//function checkExtension(fileName, fileSize){
			if(!checkExtension(files[i].name, files[i].size)){//!true라면 실패
				return false;
			}
			
			formData.append("uploadFile",files[i]);
		}
		
		//없어?카드가?또?
		//processData,contentType은 반드시 false여야 전송됨
		var id = $("#authUserId").val();
		$.ajax({
			url:"${pageContext.servletContext.contextPath}/"+id+"/admin/uploadAjaxAction",
			processData:false,
			contentType:false,
			data:formData,
			type:'POST',
			success:function(result){
				console.log("result : " + JSON.stringify(result));
			}
		});
	}); */
});
	
</script>
</head>
<body>
	<input type="hidden" id="authUserId" value="${authUser.id}">
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
					<li class="selected"><a href="/jblog/${authUser.id}/admin">기본설정</a></li>
					<li><a href="/jblog/${authUser.id}/admin/category">카테고리</a></li>
					<li><a href="/jblog/${authUser.id}/admin/write">글작성</a></li>
				</ul>
				
				<form action="/jblog/${authUser.id}/admin/blogSetting" method="post" enctype="multipart/form-data">
	 		      	<table class="admin-config">
			      		<tr>
			      			<td class="t">블로그 제목</td>
			      			<td><input type="text" size="40" name="blogTitle" value="${basic.blogTitle}"></td>
			      		</tr>
			      		<tr>
			      			<td class="t">로고이미지</td>
			      			<td>
			      				<c:choose>
								    <c:when test="${basic.logoFile eq 'notExist' || empty basic.logoFile}">
								    	<img src="${pageContext.request.contextPath}/assets/images/spring-logo.jpg">	
								    </c:when>
								    <c:otherwise>
										 <img src="${pageContext.request.contextPath}/assets/upload/${basic.logoFile}">
								    </c:otherwise>
								</c:choose>
			      			</td>
			      		</tr>  		
			      		<tr>
			      			<td class="t">&nbsp;</td>
			      			<td>
			      				<!-- <input type="file" name="file"> -->
				      			<div class="uploadDiv">
									<input type="file" id="input_img" name="file" />
								</div>
							</td>  			
			      		</tr>           		
			      		<tr>
			      			<td class="t">&nbsp;</td>
			      			<td class="s">
			      				<input type="submit" value="기본설정 변경">
			      			</td>      			
			      		</tr>           		
			      	</table>
			      	
			      	<!-- 
			      	<h3>ajax test</h3>
			      	<div class="uploadDiv">
						<input type="file" id="input_img" name="uploadFile" />
					</div>
					
					<button id="uploadBtn">Upload</button>
					<div class="img_wrap"></div> -->
					
					
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
