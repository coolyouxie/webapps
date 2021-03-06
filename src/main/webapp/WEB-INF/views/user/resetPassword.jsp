<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>重置密码</title>
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
        });

        function resetPwd() {
			var oldPwd = $("#oldPwd").val().trim();
			if(!oldPwd){
			    alert("请输入原密码！");
			    return
			}
			var newPwd = $("#newPwd").val();
			if(!newPwd){
			    alert("请输入新密码");
			    return;
			}
            var confirmNewPwd = $("#confirmNewPwd").val();
            if(!confirmNewPwd){
                alert("请输入确认密码");
                return;
            }
            if(newPwd!=confirmNewPwd){
                alert("两次输入的新密码不一致");
                return;
            }
            $.ajax({
	            url:"${ctx}/user/resetPassword",
	            type:"POST",
	            dataType:"JSON",
	            data:{
	                id:$("#id").val(),
		            oldPwd:$("#oldPwd").val(),
		            newPwd:$("#newPwd").val(),
		            confirmNewPwd:$("#confirmNewPwd").val()
                },
	            success:function (response) {
		            if(response.result=="S"){
		                alert("密码更新成功");
                        $("#saveForm").submit();
                    }else{
		                alert(response.errorMsg);
		                return;
                    }
                }
            });
        }
	</script>
</head>
<body>
<div class="container-fluid">
	<div class="row">
		<div class="col-md-3 col-md-offset-2">
			<h4>
				重置密码
			</h4>
		</div>
	</div>
	<form id="saveForm" class="form-horizontal" action="${ctx}/login/logout" method="get">
		<input type="hidden" id="id" name="id" value="${user.id}">
		<input type="hidden" id="userId" name="userId" value="${user.id}">
		<div class="form-group">
			<label class="col-md-2 control-label" for="account">
				账号：
			</label>
			<input type="text" id="account" name="account" class="form-group" placeholder="请输入账号名"
			       value="${user.account}" readonly="readonly">
		</div>

		<div class="form-group">
			<label class="col-md-2 control-label" for="name">姓名：</label>
			<input type="text" id="name" name="name" class="form-group" placeholder="请输入姓名" value="${user.name}" readonly="readonly">
		</div>

		<div class="form-group">
			<label class="col-md-2 control-label" for="idCardNo">身份证：</label>
			<input type="text" id="idCardNo" name="idCardNo" class="form-group" placeholder="请输入身份证号"
			       value="${user.idCardNo}" readonly="readonly">
		</div>

		<div class="form-group">
			<label class="col-md-2 control-label" for="bankCardNum">银行卡号：</label>
			<input type="text" id="bankCardNum" name="bankCardNum" class="form-group" placeholder="请输入银行卡号"
			       value="${user.bankCardNum}" readonly="readonly">
		</div>

		<div class="form-group">
			<label class="col-md-2 control-label" for="mobile">手机号：</label>
			<input type="mobile" id="mobile" name="mobile" class="form-group" placeholder="请输入手机号"
			       value="${user.mobile}" readonly="readonly">
		</div>

		<div class="form-group">
			<label class="col-md-2 control-label" for="gender">性别：</label>
			<select id="gender" name="gender" class="form-group" disabled="disabled">
				<option value="0" <c:if test="${user.gender==0}">selected</c:if>>女</option>
				<option value="1" <c:if test="${user.gender==1}">selected</c:if>>男</option>
			</select>
		</div>

		<div class="form-group">
			<label class="col-md-2 control-label" for="age">年龄：</label>
			<input type="text" id="age" name="age" class="form-group" placeholder="请输入年龄" value="${user.age}" readonly="readonly">
		</div>

		<div class="form-group">
			<label class="col-md-2 control-label" for="userType">用户类型：</label>
			<select id="userType" name="userType" class="from-group" disabled="disabled">
				<option value="1" <c:if test="${user.userType==1}">selected</c:if>>超级管理员</option>
				<option value="2" <c:if test="${user.userType==2}">selected</c:if>>普通管理员</option>
				<option value="3" <c:if test="${user.userType==3}">selected</c:if>>普通会员</option>
				<option value="4" <c:if test="${user.userType==4}">selected</c:if>>招聘专员</option>
				<option value="5" <c:if test="${user.userType==5}">selected</c:if>>入职与期满审核专员</option>
				<%--<option value="6" <c:if test="${user.userType==6}">selected</c:if>>期满审核专员</option>--%>
				<option value="7" <c:if test="${user.userType==7}">selected</c:if>>提现审核专员</option>
			</select>
		</div>

		<div class="form-group">
			<label class="col-md-2 control-label" for="userType">输入原密码：</label>
			<input type="password" id="oldPwd" name="oldPwd">
		</div>

		<div class="form-group">
			<label class="col-md-2 control-label" for="userType">输入新密码：</label>
			<input type="password" id="newPwd" name="newPwd">
		</div>

		<div class="form-group">
			<label class="col-md-2 control-label" for="userType">确认新密码：</label>
			<input type="password" id="confirmNewPwd" name="confirmNewPwd">
		</div>

		<div class="form-group">
			<div class="col-md-3">
			</div>
			<a class="btn btn-primary" onclick="resetPwd()">
				保存
			</a>
		</div>
	</form>
</div>
</body>
</html>