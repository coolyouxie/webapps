<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8"/>
	<title>权限管理</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<link rel="stylesheet" href="${ctx}/js/common/jquery/jquery-ui-1.12.1/jquery-ui.css" type="text/css"/>
	<link rel="stylesheet" href="${ctx}/js/common/jquery/jqGrid/css/ui.jqgrid.css" type="text/css"/>
	<!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
	<link rel="stylesheet" href="${ctx}/js/common/bootstrap/bootstrap-3.3.7/dist/css/bootstrap.min.css"
	      type="text/css"/>
	<script src="${ctx}/js/jquery/jQuery-1.12.4.0.js"></script>
	<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
	<script src="${ctx}/js/common/bootstrap/bootstrap-3.3.7/dist/js/bootstrap.min.js"></script>
	<script src="${ctx}/js/common/jquery/jqGrid/js/grid.base.js"></script>
	<script src="${ctx}/js/common/jquery/jqGrid/js/grid.common.js"></script>
	<script src="${ctx}/js/common/jquery/jqGrid/js/i18n/grid.locale-cn.js"></script>
	<script src="${ctx}/js/common/jquery/jqGrid/js/jquery.jqGrid.js"></script>

	<script type="text/javascript">
        function deleteById(id) {
            $.ajax({
                url: "${ctx}/permission/deleteById",
                type: "POST",
                dataType: "JSON",
                data: {
                    "id": id
                },
                success: function (response) {
                    alert("删除成功");
                    dataGrid.trigger("reloadGrid");
                }
            });
        }

        //校验名称是否重复

        //校验code是否重复

	</script>
	<style>
		.input-group-sm {
			margin-bottom: 10px;
		}

		.input-group-sm label {
			width: 100%;
		}

		.input-group-sm label span {
			width: 30%;
			text-align: right;
			display: inline-block;
		}

		.input-group-sm label input {
			width: 50%;
			display: inline-block;
		}
	</style>
</head>
<body>
<div class="container-fluid">
	<div class="row">
		<div class="col-md-4">
			<h2>
				权限详情
			</h2>
		</div>
	</div>
	<div class="row" style="width:255px;">
		<label>
			<span>权限名称:</span>
			<span>${permission.name}</span>
		</label>
	</div>
	<div class="row" style="width:275px;">
		<label>
			<span>权限编号:</span>
			<span>${permission.code}</span>
		</label>
	</div>
	<div class="row">
		<label>
			<span>父权限编号:</span>
			<span>${permission.parentCode}</span>
		</label>
	</div>
	<div class="row">
		<label>
			<span>权限层级:</span>
			<span>${permission.level}</span>
		</label>
	</div>
	<div class="row">
		<label>
			<span>权限类型</span>
			<span>${permission.type}</span>
		</label>
	</div>
</div>
<table id="list"></table>
<div id="pager"></div>
</body>
</html>