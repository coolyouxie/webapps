<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户信息</title>
<link rel="stylesheet" href="${ctx}/js/common/jquery/jquery-ui-1.12.1/jquery-ui.css" type="text/css" />
<link rel="stylesheet" href="${ctx}/js/common/jquery/jqGrid/css/ui.jqgrid.css" type="text/css" />
<!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="${ctx}/js/common/bootstrap/bootstrap-3.3.7/dist/css/bootstrap.min.css" type="text/css" />
<script src="${ctx}/js/jquery/jQuery-1.12.4.0.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="${ctx}/js/common/bootstrap/bootstrap-3.3.7/dist/js/bootstrap.min.js"></script>
<script src="${ctx}/js/common/jquery/jqGrid/js/grid.base.js"></script>
<script src="${ctx}/js/common/jquery/jqGrid/js/grid.common.js"></script>
<script src="${ctx}/js/common/jquery/jqGrid/js/i18n/grid.locale-cn.js"></script>
<script src="${ctx}/js/common/jquery/jqGrid/js/jquery.jqGrid.js"></script>
<style>
	 col-md-2, span{
		display:-moz-inline-box;
		display:inline-block;
		width:80px;
	}
</style>

<script type="text/javascript">
	jQuery(document).ready(function(){

	});
</script>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-3 col-md-offset-2">
				<h4>
					用户信息
				</h4>
			</div>
		</div>
		<input type="hidden" id="id" name="id" value="${user.id}">
		<div class="row">
			<label class="col-md-2 control-label" >
				账号：
			</label>
			<div class="col-md-3">
				${user.account}
			</div>
		</div>
	
		<div class="row">
			<label class="col-md-2 control-label" >姓名：</label>
			<div class="col-md-3" >
				${user.name}
			</div>
		</div>	
			
		<div class="row">
			<label class="col-md-2 control-label" >身份证：</label>
			<div class="col-md-3" >
				${user.idCardNo}
			</div>
		</div>

		<div class="row">
			<label class="col-md-2 control-label" >银行卡号：</label>
			<div class="col-md-3" >
				${user.bankCardNum}
			</div>
		</div>
			
		<div class="row">
			<label class="col-md-2 control-label" >手机号：</label>
			<div class="col-md-3" >
				${user.mobile}
			</div>
		</div>

		<div class="row">
			<label class="col-md-2 control-label" >证件图片：</label>
			<div class="col-md-3" >
				<c:forEach var="item" items="${user.pictures}" varStatus="status">
					<img src="${item.picUrl}" style="width:240px;" />
				</c:forEach>
			</div>
		</div>
		
		<div class="row">
			<div class="col-md-9">
				<table id="list"></table>
				<div id="pager"></div>
			</div>
		</div>
	</div>
	
</body>
</html>