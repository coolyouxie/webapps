<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8"/>
	<title>企业信息管理</title>
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
        $(document).ready(function () {
            dataGrid = $("#list").jqGrid({
                url: "${ctx}/awardConfig/loadAwardConfigList",
                datatype: "json",
                mtype: "POST",
                height: "auto",
                width: "auto",
                rownumbers: true,
                jsonReader: {
                    root: "resultList", // json中代表实际模型数据的入口
                    page: "page", // json中代表当前页码的数据
                    records: "records", // json中代表数据行总数的数据
                    total: 'total', // json中代表页码总数的数据
                    repeatitems: false // 如果设为false，则jqGrid在解析json时，会根据name来搜索对应的数据元素（即可以json中元素可以不按顺序）；而所使用的name是来自于colModel中的name设定。
                },
                colNames: ['奖品名称', '中奖率', '操作'],
                colModel: [{
                    label: 'name',
                    name: 'name',
                    align: 'center',
                    sortable: false
                }, {
                    label: 'pr',
                    name: 'pr',
                    align: 'center',
                    sortable: false
                }, {
                    label: 'operate',
                    name: 'operate',
                    align: 'center',
                    sortable: false,
                    formatter: function (cellValue, options, rowObject) {
                        return "<a class='btn btn-sm' onclick='deleteById(" + rowObject.id + ")' style='color:blue'>删除</a>";
                    }
                }],
                pager: '#pager',
                rowNum: 15,
                rowList: [15, 30, 50],
                sortname: 'id',
                viewrecords: true,
                sortorder: "desc",
                caption: "奖品配置列表"
            });
        });

        function search() {
            dataGrid.jqGrid("setGridParam", {
                url: "${ctx}/awardConfig/loadAwardConfigList?" + encodeURI($("#searchForm").serialize())
            }).trigger("reloadGrid");
        }

        function deleteById(id) {
            $.ajax({
                url: "${ctx}/awardConfig/deleteById",
                type: "POST",
                dataType: "JSON",
                data: {
                    "id": id
                },
                success: function (response) {
                    if(response.result=="S"){
                        alert("删除成功");
                        dataGrid.trigger("reloadGrid");
                    }else{
                        alert(response.errorMsg);
                    }
                }
            });
        }

	</script>
	<style>
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
	<form id="searchForm">
		<div class="row" style="margin-bottom:10px">
			<div class="col-md-2">
				<button type='button' class="btn btn-primary btn-sm" data-toggle="modal" onclick="search()">
					查询
				</button>
			</div>
			<div class="col-md-2">
				<a type='button' class="btn btn-primary btn-sm" href="${ctx}/awardConfig/toAddAwardConfig">
					添加
				</a>
			</div>
		</div>
	</form>
</div>
<table id="list"></table>
<div id="pager"></div>
</body>
</html>