<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8"/>
	<title>新增权限</title>
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
                    alert("添加成功");
                    window.location.href = "${ctx}/manage/toPermissionListPage";
                }
            });
        }

        function add() {
            if(!$("#name").val()||!$("#name").val().trim()){
                alert("请输入权限名称");
                return;
            }
            if(!$("#code").val()||!$("#code").val().trim()){
                alert("请输入权限编号");
                return;
            }
            if(!$("#parentCode").val()||!$("#parentCode").val().trim()){
                alert("请输入父权限编号");
                return;
            }
            if(!$("#type").val()||!$("#type").val().trim()){
                alert("请选择权限类型");
                return;
            }
            if(!$("#level").val()||!$("#level").val().trim()){
                alert("请输入权限层级");
                return;
            }
            $.ajax({
                url: "${ctx}/permission/savePermission",
                type: "POST",
                dataType: "JSON",
                data: {
                    "name": $("#name").val(),
                    "code": $("#code").val(),
                    "parentCode": $("#parentCode").val(),
                    "type": $("#type").val(),
                    "level": $("#level").val()
                },
                success: function (response) {
                    alert("添加成功");
                    window.location.href = "${ctx}/permission/toPermissionListPage";
                }
            });
        }

        //校验权限是否已存在
        function validate() {
            if(!$("#name").val()||!$("#name").val().trim()){
                alert("请输入权限名称");
                return;
            }
            if(!$("#code").val()||!$("#code").val().trim()){
                alert("请输入权限编号");
                return;
            }
            $.ajax({
                url: "${ctx}/permission/validatePermission",
                type: "POST",
                dataType: "JSON",
                data: {
                    "name": $("#name").val().trim(),
                    "code": $("#code").val()
                },
                success: function (response) {
                    if(response.result=="S"){
                        add();
                    }else{
                        alert(response.errorMsg);
                        return;
                    }
                }
            });
        }

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
	<form id="addForm">
		<div class="row" style="width:255px;">
			<label>
				<span>权限名称:</span>
				<input id="name" name="name" value="">
			</label>
		</div>
		<div class="row" style="width:275px;">
			<label>
				<span>权限编号:</span>
				<input id="code" name="code" value="">
			</label>
		</div>
		<div class="row">
			<label>
				<span>父权限编号:</span>
				<input id="parentCode" name="parentCode" value="">
			</label>
		</div>
		<div class="row">
			<label>
				<span>权限层级:</span>
				<input id="level" name="level" value="">
			</label>
		</div>
		<div class="row">
			<label>
				<span>权限类型</span>
				<input id="type" name="type" value="">
			</label>
		</div>
		<a href="###" onclick="validate()">添加</a>
	</form>
</div>
<table id="list"></table>
<div id="pager"></div>
</body>
</html>