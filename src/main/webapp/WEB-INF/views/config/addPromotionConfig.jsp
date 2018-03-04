<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>新增活动信息</title>
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

	</script>
</head>
<body>
<div class="container-fluid">
	<div class="row">
		<div class="col-md-4 col-md-offset-2">
			<h4>
				添加活动信息
			</h4>
		</div>
	</div>
	<form id="saveForm" class="form-horizontal" action="${ctx}/promotionConfig/savePromotionConfig" method="post">
		<div class="form-group">
			<label class="col-md-2 control-label" for="name">活动名称：</label>
			<div class="col-md-4">
				<input type="text" id="name" name="name" class="form-control" placeholder="请输入活动名称">
			</div>
		</div>
		<div class="form-group">
			<label class="col-md-2 control-label" for="brief">中奖率：</label>
			<div class="col-md-4">
				<textarea name="brief" id="brief" cols="30" rows="10"></textarea>
			</div>
		</div>
		<div class="form-group">
			<label class="col-md-2 control-label" for="remark">备注：</label>
			<div class="col-md-4">
				<input type="text" id="remark" name="remark" class="form-control" placeholder="请输入备注信息">
			</div>
		</div>
		<div class="">
			<label class="col-md-4 control-label"></label>
			<div class="col-md-4">
				<button type="submit" class="btn btn-primary">
					下一步
				</button>
			</div>
		</div>
	</form>
</div>
</body>
</html>