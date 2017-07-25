<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增或修改用户信息</title>
<!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

<style>
	 col-md-2, span{
		display:-moz-inline-box;
		display:inline-block;
		width:80px;
	}
</style>

<script type="text/javascript">
	
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
		<form id="saveForm" class="form-horizontal" action="${pageContext.request.contextPath}/user/addOrEdit?type=${type}">
			<div class="form-group">
				<label class="col-md-2 control-label" for="account">
					账号：
				</label>
				<div class="col-md-3">
					<input type="text" id="account" name="account" class="form-control" placeholder="请输入账号名" >
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
					<input type="text" id="gender" name="gender" class="form-control" placeholder="请输入性别" >
				</div>
			</div>	
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="age">年龄：</label>
				<div class="col-md-3" >
					<input type="text" id="age" name="age" class="form-control" placeholder="请输入年龄" >
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="email">email：</label>
				<div class="col-md-3" >
					<input type="email" id="email" name="email" class="form-control" placeholder="请输入email" >
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="address">居住地址：</label>
				<div class="col-md-3" >
					<input type="text" id="address" name="address" class="form-control" placeholder="请输入现在的居住地址" >
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="homeAddress">家庭住址：</label>
				<div class="col-md-3" >
					<input type="text" id="homeAddress" name="homeAddress" class="form-control" placeholder="请输入户籍所在地址" >
				</div>
			</div>
		</form>
		<div class="row">
			<div class="col-md-3 col-md-offset-3">
				<button type="button" class="btn btn-primary" onclick="save()">
					保存
				</button>
			</div>
		</div>
	</div>
</body>
</html>