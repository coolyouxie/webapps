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
        var dataGrid = null;
        jQuery(document).ready(function () {
            dataGrid = jQuery("#list").jqGrid({
                url: "${ctx}/permission/loadMenuPermissionList",
                datatype: "json",
                mtype: "POST",
                height: 'auto',
                width: 'auto',
                jsonReader: {
                    root: "resultList", // json中代表实际模型数据的入口
                    page: "page", // json中代表当前页码的数据
                    records: "records", // json中代表数据行总数的数据
                    total: 'total', // json中代表页码总数的数据
                    repeatitems: false // 如果设为false，则jqGrid在解析json时，会根据name来搜索对应的数据元素（即可以json中元素可以不按顺序）；而所使用的name是来自于colModel中的name设定。
                },
                colNames: ['权限名称', '权限编号', '上级权限编号','权限层级','类型', '创建时间', '操作'],
                colModel: [{
                    label: 'name',
                    name: 'name',
                    align: 'center',
                    sortable: false,
                    formatter: function (cellValue, options, rowObject) {
                        return '<a href="${ctx}/permission/getById?type=show&id=' + rowObject.id + '" style="color:blue">' + cellValue + '</a>';
                    }
                }, {
                    label: 'code',
                    name: 'code',
                    align: 'center',
                    sortable: false
                },{
                    label: 'parentCode',
                    name: 'parentCode',
                    align: 'center',
                    sortable: false
                }, {
                    label: 'level',
                    name: 'level',
                    align: 'center',
                    sortable: false
                }, {
                    label: 'type',
                    name: 'type',
                    align: 'center',
                    sortable: false
                }, {
                    label: 'createTimeFullStr',
                    name: 'createTimeFullStr',
                    align: 'center',
                    sortable: false
                }, {
                    label: 'operate',
                    name: 'operate',
                    align: 'center',
                    sortable: false,
                    formatter: function (cellValue, options, rowObject) {
						return '<a href="${ctx}/permission/getById?type=edit&id='+rowObject.id+'" class="btn btn-primary btn-sm">修改</a>';
                    }
                }],
                pager: '#pager',
                rowNum: 15,
                rowList: [15, 30, 50],
                sortname: 'id',
                viewrecords: true,
                sortorder: "desc",
                caption: "权限列表"
            });
        });

        function search() {
            dataGrid.jqGrid("setGridParam", {
                url: "${ctx}/permission/loadPermissionList?" + encodeURI($("#searchForm").serialize())
            }).trigger("reloadGrid");
        }

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
		<div class="col-md-3">
			<a href="${ctx}/permission/toAddPermissionPage" class="btn btn-primary btn-sm">添加权限</a>
		</div>
	</div>
	<div class="row">
		<div class="col-md-3">&nbsp;</div>
	</div>
</div>
<table id="list"></table>
<div id="pager"></div>
</body>
</html>