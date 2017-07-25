<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
					编辑用户
				</h4>
			</div>
		</div>
		<form id="saveForm" class="form-horizontal" action="${pageContext.request.contextPath}/user/saveUser" method="post">
			<input type="hidden" id="type" name="type" value="${type}">
			<input type="hidden" id="id" name="id" value="${user.id}">
			<div class="form-group">
				<label class="col-md-2 control-label" for="account">
					账号：
				</label>
				<div class="col-md-3">
					<input type="text" id="account" name="account" class="form-control" placeholder="请输入账号名" value="${user.account}" readonly="readonly">
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="name">姓名：</label>
				<div class="col-md-3" >
					<input type="text" id="name" name="name" class="form-control" placeholder="请输入姓名" value="${user.name}">
				</div>
			</div>	
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="idCardNo">身份证：</label>
				<div class="col-md-3" >
					<input type="text" id="idCardNo" name="idCardNo" class="form-control" placeholder="请输入身份证号" value="${user.idCardNo}">
				</div>
			</div>	
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="mobile">手机号：</label>
				<div class="col-md-3" >
					<input type="mobile" id="mobile" name="mobile" class="form-control" placeholder="请输入手机号" value="${mobile}">
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="gender">性别：</label>
				<div class="col-md-3" >
					<select id="gender" name="gender" class="form-control">
						<option value="0" <c:if test="${user.gender==0}">selected</c:if>>女</option>
						<option value="1" <c:if test="${user.gender==1}">selected</c:if>>男</option>
					</select>
				</div>
			</div>	
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="age">年龄：</label>
				<div class="col-md-3" >
					<input type="text" id="age" name="age" class="form-control" placeholder="请输入年龄" value="${user.age}">
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="email">email：</label>
				<div class="col-md-3" >
					<input type="email" id="email" name="email" class="form-control" placeholder="请输入email" value="${user.email}">
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="address">居住地址：</label>
				<div class="col-md-3" >
					<input type="text" id="address" name="address" class="form-control" placeholder="请输入现在的居住地址" value="${user.address}">
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="homeAddress">家庭住址：</label>
				<div class="col-md-3" >
					<input type="text" id="homeAddress" name="homeAddress" class="form-control" placeholder="请输入户籍所在地址" value="${user.homeAddress}">
				</div>
			</div>
		</form>
		<div class="row">
			<div class="col-md-3 col-md-offset-3">
				<button type="submit" class="btn btn-primary">
					保存
				</button>
			</div>
		</div>
	</div>
</body>
</html>