<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-colpatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
	<link rel="stylesheet" href="${ctx}/js/common/bootstrap/bootstrap-3.3.7/dist/css/bootstrap.min.css" type="text/css" />
	<script src="${ctx}/js/jquery/jQuery-1.12.4.0.js"></script>
	<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
	<script src="${ctx}/js/common/bootstrap/bootstrap-3.3.7/dist/js/bootstrap.min.js"></script>
	
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
			
			$.ajax({
	            url: "${ctx}/login/getCopyright.json",
	            type: 'GET',
	            dataType: "json",
	            data: null,
	            success: function(data) {
	                if(data && data.result == "S"){
	                	$("#sys_verion").html(data.resultVo.version);
	                	$("#sys_copyright").html(data.resultVo.copyright);
	                }else{
	                }
	            },
	            error: function() {
	            }
	        });
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
	    	/* var checkCode = $("#checkCodeId").val();
	    	if(checkCode==null||checkCode==""||$.trim(checkCode)==""){
	    		alert("请输入验证码！");
	    		return;
	    	} */
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
<body onload="Code(loginForm.yzImg);">
	<div class="center">
		<form name="loginForm">
			<div class="">
				<p>后台管理系统</p>
			</div>
			<div class="form-group">
				<div class="input-group input-group-lg">
					<span class="input-group-addon" id="basic-addon1" style="width:48px">账号</span>
					<input name="account" id="nameId" type="text" class="form-control" id="" placeholder="请输入用户名">
				</div>
				<br/>
				<div class="input-group input-group-lg">
					<span class="input-group-addon" id="basic-addon1" style="width:48px">密码</span>
					<input name="password" id="pwdId" type="password" class="form-control" id="" placeholder="请输入密码">
				</div>
			</div>
			<div class="form-group">
				<div class="input-group input-group-lg" style="float:left">
					<span class="input-group-addon" id="basic-addon1" style="width:48px">验证码</span>
					<input  name="checkCode" id="checkCodeId"  type="text" class="form-control" id="" placeholder="请输入验证码">
				</div>
				<div class="login_form_testcode">
					<img id="yzImg" src="${ctx}/login/getValidatorImage" alt="看不清?点击图片换一张" onclick="Code(this)">
				</div>
			</div>
			<div class="checkbox">
				<label class="pull-left">
					<input type="checkbox" id="ck_rmbUser" class="login_form_checkbox"/>记住用户名和密码
				</label>
				<label class="f12">
					<span id="state" style="color:red;display:inline-block">${loginResult}</span></br>
				</label>
			</div>
			<div class="btn-group btn-group-justified" role="group" aria-label="...">
				<div class="btn-group" role="group">
					<button type="submit" class="btn btn-default" onclick="login(); return false">登录</button>
				</div>
			</div>
		</form>
	</div>
			
	<script language="javascript">
		window.onload = function(){
			if(window.top!=null && window.top.document.URL!=document.URL){
				top.location.href = window.href;
				window.top.location= document.URL;
			}
			var w = document.documentElement.clientHeight;
			var w_top = (w-540)/2;
			var login = document.getElementById('login');
			login.setAttribute('style','margin-top:'+w_top+'px');
		}
	</script>
</body>
</html>