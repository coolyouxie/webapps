<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html">
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
			alert("result");
		}
	})
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
					<input type="mobile" id="mobile" name="mobile" class="form-control" placeholder="请输入手机号" value="${user.mobile}">
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
				<label class="col-md-2 control-label" for="age">学历：</label>
				<div class="col-md-3" >
					<select id="eductionId" name="educationId" >
						<option value="" selected>-请选择-</option>
						<option value="1" <c:if test="${user.educationId==1}">selected</c:if>>小学</option>
						<option value="2" <c:if test="${user.educationId==2}">selected</c:if>>初中</option>
						<option value="3" <c:if test="${user.educationId==3}">selected</c:if>>高中</option>
						<option value="4" <c:if test="${user.educationId==4}">selected</c:if>>中专</option>
						<option value="5" <c:if test="${user.educationId==5}">selected</c:if>>职高</option>
						<option value="6" <c:if test="${user.educationId==6}">selected</c:if>>大专</option>
						<option value="7" <c:if test="${user.educationId==7}">selected</c:if>>本科</option>
						<option value="8" <c:if test="${user.educationId==8}">selected</c:if>>硕士</option>
						<option value="9" <c:if test="${user.educationId==9}">selected</c:if>>博士</option>
						<option value="10" <c:if test="${user.educationId==10}">selected</c:if>>博士后</option>
					</select>
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
			<div class="form-group">
				<div class="col-md-3" >
				</div>
				<button type="submit" class="btn btn-primary">
					保存
				</button>
			</div>
		</form>
	</div>
</body>
</html>