<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<select id="userType" name="userType">
		<option value="1">超级管理员</option>
		<option value="2">普通管理员</option>
		<option value="3" selected>普通会员</option>
		<option value="4">招聘员</option>
		<option value="5">入职与期满审核员</option>
		<%--<option value="6">期满审核专员</option>--%>
		<option value="7">提现审核员</option>
	</select>
</body>
</html>