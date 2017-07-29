<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<select id="eductionId" name="educationId">
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
</body>
</html>