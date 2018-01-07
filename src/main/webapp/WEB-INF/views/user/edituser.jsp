<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>新增或修改用户信息</title>
	<!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
	<link rel="stylesheet" href="${ctx}/js/common/bootstrap/bootstrap-3.3.7/dist/css/bootstrap.min.css"
	      type="text/css"/>
	<script src="${ctx}/js/jquery/jQuery-1.12.4.0.js"></script>
	<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
	<script src="${ctx}/js/common/bootstrap/bootstrap-3.3.7/dist/js/bootstrap.min.js"></script>

	<style>
		col-md-2, span {
			display: -moz-inline-box;
			display: inline-block;
			width: 80px;
		}
	</style>

	<script type="text/javascript">
        $(function () {
            if ("${result}") {
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
			<input type="text" id="account" name="account" class="form-group" placeholder="请输入账号名"
			       value="${user.account}" readonly="readonly">
		</div>

		<div class="form-group">
			<label class="col-md-2 control-label" for="name">姓名：</label>
			<input type="text" id="name" name="name" class="form-group" placeholder="请输入姓名" value="${user.name}">
		</div>

		<div class="form-group">
			<label class="col-md-2 control-label" for="idCardNo">身份证：</label>
			<input type="text" id="idCardNo" name="idCardNo" class="form-group" placeholder="请输入身份证号"
			       value="${user.idCardNo}">
		</div>

		<div class="form-group">
			<label class="col-md-2 control-label" for="bankCardNum">银行卡号：</label>
			<input type="text" id="bankCardNum" name="bankCardNum" class="form-group" placeholder="请输入银行卡号"
			       value="${user.bankCardNum}">
		</div>

		<div class="form-group">
			<label class="col-md-2 control-label" for="mobile">手机号：</label>
			<input type="mobile" id="mobile" name="mobile" class="form-group" placeholder="请输入手机号"
			       value="${user.mobile}">
		</div>

		<div class="form-group">
			<label class="col-md-2 control-label" for="gender">性别：</label>
			<select id="gender" name="gender" class="form-group">
				<option value="0" <c:if test="${user.gender==0}">selected</c:if>>女</option>
				<option value="1" <c:if test="${user.gender==1}">selected</c:if>>男</option>
			</select>
		</div>

		<div class="form-group">
			<label class="col-md-2 control-label" for="age">年龄：</label>
			<input type="text" id="age" name="age" class="form-group" placeholder="请输入年龄" value="${user.age}">
		</div>

		<div class="form-group">
			<label class="col-md-2 control-label" for="userType">用户类型：</label>
			<select id="userType" name="userType" class="from-group">
				<option value="1" <c:if test="${user.userType==1}">selected</c:if>>超级管理员</option>
				<option value="2" <c:if test="${user.userType==2}">selected</c:if>>普通管理员</option>
				<option value="3" <c:if test="${user.userType==3}">selected</c:if>>普通会员</option>
				<option value="4" <c:if test="${user.userType==4}">selected</c:if>>企业会员</option>
			</select>
		</div>

		<div class="form-group">
			<div class="col-md-3">
			</div>
			<button type="submit" class="btn btn-primary">
				保存
			</button>
		</div>
	</form>
</div>
</body>
</html>