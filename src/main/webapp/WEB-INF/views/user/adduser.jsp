<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增或修改用户信息</title>
<!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="${ctx}/js/common/bootstrap/bootstrap-3.3.7/dist/css/bootstrap.min.css" type="text/css" />
<script src="${ctx}/js/jquery/jQuery-1.12.4.0.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="${ctx}/js/common/bootstrap/bootstrap-3.3.7/dist/js/bootstrap.min.js"></script>

<style>
	 col-md-2, span{
		display:-moz-inline-box;
		display:inline-block;
		width:80px;
	}
</style>

<script type="text/javascript">
	$(function(){
		if("${result}"){
			alert("${result}");
		}
	});
	function saveUser(){
		$.ajax({
			url:"${pageContext.request.contextPath}/user/saveUser",
			type:"POST",
			dataType:"JSON",
			data:$("#saveForm").serialize(),
			success:function(response){
				alert(response);
			}
		})
	}
	
</script>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-3 col-md-offset-2">
				<h4>
					添加新用户
				</h4>
			</div>
		</div>
		<form id="saveForm" class="form-horizontal" action="${pageContext.request.contextPath}/user/saveUser"  method="post">
			<div class="form-group">
				<label class="col-md-2 control-label" for="account">
					账号：
				</label>
				<div class="col-md-3">
					<input type="text" id="account" name="account" class="form-control" value="${user.account}" placeholder="请输入账号名" >
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="password">密码：</label>
				<div class="col-md-3" >
					<input type="password" id="password" name="password" class="form-control" placeholder="请输入密码" >
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="confirmPassword">确认密码：</label>
				<div class="col-md-3" >
					<input type="password" id="confirmPassword" name="confirmPassword" class="form-control" placeholder="请输入密码" >
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="name">姓名：</label>
				<div class="col-md-3" >
					<input type="text" id="name" name="name" class="form-control" placeholder="请输入姓名" >
				</div>
			</div>	
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="idCardNo">身份证：</label>
				<div class="col-md-3" >
					<input type="text" id="idCardNo" name="idCardNo" class="form-control" placeholder="请输入身份证号" >
				</div>
			</div>	
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="mobile">手机号：</label>
				<div class="col-md-3" >
					<input type="mobile" id="mobile" name="mobile" class="form-control" placeholder="请输入手机号" >
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="gender">性别：</label>
				<div class="col-md-3" >
					<select id="gender" name="gender" class="form-control">
						<option value="0">女</option>
						<option value="1">男</option>
					</select>
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="userType">用户类型：</label>
				<div class="col-md-3" >
					<jsp:include page="../common/usertype.jsp" flush="true" />
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-3 control-label"></label>
				<div class="col-md-3">
					<button type="submit" class="btn btn-primary">
						保存
					</button>
				</div>
			</div>
		</form>
	</div>
</body>
</html>