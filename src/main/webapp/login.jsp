<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-colpatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
	
	<link rel="icon" type="image/png" href="${ctx}/js/common/amazeUI/assets/i/favicon.png">
	<link rel="apple-touch-icon-precomposed" href="${ctx}/js/common/amazeUI/assets/i/app-icon72x72@2x.png">
	<meta name="apple-mobile-web-app-title" content="Amaze UI" />
	<link rel="stylesheet" href="${ctx}/js/common/amazeUI/assets/css/amazeui.min.css" />
	<link rel="stylesheet" href="${ctx}/js/common/amazeUI/assets/css/admin.css">
	<link rel="stylesheet" href="${ctx}/js/common/amazeUI/assets/css/app.css">
	<script src="${ctx}/js/jquery/jQuery-1.12.4.0.js"></script>
	<script src="${ctx}/js/jquery/jquery.cookie.js"></script>
	<script src="${ctx}/js/common/amazeUI/assets/js/amazeui.min.js"></script>
	<script src="${ctx}/js/common/amazeUI/assets/js/app.js"></script>
	
	<title>后台管理系统</title>
	<script type="text/javascript">
		$(function(){
			$(document).keydown(function(event){
				if(event.keyCode==13){
					login();
				}
			});
			
			if ($.cookie("rmbUser") == "true") {
		        $("#ck_rmbUser").attr("checked", true);
		        $("#nameId").val($.cookie("username"));
		        $("#pwdId").val($.cookie("password"));
	        }
			
		});
		
		function saveUser(){
			if ($("#ck_rmbUser").is(":checked")) {
	            var str_username = $("#nameId").val();
	            var str_password = $("#pwdId").val();
	            $.cookie("rmbUser", "true", { expires: 7 }); //存储一个带7天期限的cookie
	            $.cookie("username", str_username, { expires: 7 });
	            $.cookie("password", str_password, { expires: 7 });
	        }
	        else {
	            $.cookie("rmbUser", "false", { expire: -1 });
	            $.cookie("username", "", { expires: -1 });
	            $.cookie("password", "", { expires: -1 });
	        }
		}
		
	    function Code(obj){
	        obj.src= "${ctx}/login" + "/getValidatorImage?d=" + new Date().getTime();     
	    }
	    function login(){
	    	var fm = document.loginForm;
	    	fm.action="${ctx}/login/userLogin";	
	    	fm.method="POST";
	    	saveUser();
	    	fm.submit();
	    }
	</script>
	
	<style>
		.center {
			position: fixed;
			top: 50%;
			left: 50%;
			background-color: #fff;
			width:30%;
			height: 50%;
			-webkit-transform: translateX(-50%) translateY(-50%);
			-moz-transform: translateX(-50%) translateY(-50%);
			-ms-transform: translateX(-50%) translateY(-50%);
			transform: translateX(-50%) translateY(-50%);
		}
    </style>
	
</head>


<body data-type="login" onload="Code(loginForm.yzImg);">
	<div class="am-g myapp-login">
		<div class="myapp-login-logo-block  tpl-login-max">
			<div class="myapp-login-logo-text">
				<div class="myapp-login-logo-text">
					后台管理<span> 登录</span> <i class="am-icon-skyatlas"></i>
				</div>
			</div>
	
			<div class="login-font">
				<i> </i>  <span> </span>
			</div>
			<div class="am-u-sm-10 login-am-center">
				<form class="am-form" name="loginForm" method="post">
					<fieldset>
						<div class="am-form-group">
							<input type="text" class="" name="account" id="nameId" placeholder="输入账号">
						</div>
						<div class="am-form-group">
							<input type="password" class="" name="password" id="pwdId" placeholder="输入密码">
						</div>
						<div class="" style="float:left;width:350px;">
							<input  name="checkCode" id="checkCodeId"  type="text" id="" placeholder="请输入验证码">
						</div>
						<div class="" style="float:right;width:120px;">
							<img id="yzImg" src="${ctx}/login/getValidatorImage" alt="看不清?点击图片换一张" onclick="Code(this)">
						</div>
						<p><button type="submit" class="am-btn am-btn-default" onclick="login(); return false">登录</button></p>
					</fieldset>
				</form>
			</div>
		</div>
	</div>
	<script>
		window.onload = function(){
			//如果是iframe内部session过期时需要将登录页面强制弹出显示到顶层
			if(window.top!=null && window.top.document.URL!=document.URL){
				top.location.href = window.href;
				window.top.location= document.URL;
			}
		}
	</script>
</body>
</html>