<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
    <title>权限管理</title>
</head>
<body>
<div>
    对不起，你没有登录该系统的权限。<a href="${ctx}/login/toLoginPage">返回</a>
</div>
</body>
</html>
