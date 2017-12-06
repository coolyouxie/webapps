<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>团队用户列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="${ctx}/js/common/jquery/jquery-ui-1.12.1/jquery-ui.css" type="text/css" />
<link rel="stylesheet" href="${ctx}/js/common/jquery/jqGrid/css/ui.jqgrid.css" type="text/css" />
<!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="${ctx}/js/common/bootstrap/bootstrap-3.3.7/dist/css/bootstrap.min.css" type="text/css" />
<link rel="stylesheet" href="${ctx}/js/common/jquery/My97DatePicker/skin/WdatePicker.css" type="text/css" />

<script src="${ctx}/js/jquery/jQuery-1.12.4.0.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="${ctx}/js/common/bootstrap/bootstrap-3.3.7/dist/js/bootstrap.min.js"></script>
<script src="${ctx}/js/common/jquery/jqGrid/js/grid.base.js"></script>
<script src="${ctx}/js/common/jquery/jqGrid/js/grid.common.js"></script>
<script src="${ctx}/js/common/jquery/jqGrid/js/i18n/grid.locale-cn.js"></script>
<script src="${ctx}/js/common/jquery/jqGrid/js/jquery.jqGrid.js"></script>
<!-- 日期时间控件 -->
<script src="${ctx}/js/common/jquery/My97DatePicker/WdatePicker.js"></script>
<script src="${ctx}/js/common/jquery/My97DatePicker/lang/zh-cn.js"></script>


<script type="text/javascript">
	var dataGrid = null;
	jQuery(document).ready(function(){
		dataGrid = jQuery("#list").jqGrid({
		    url:"${ctx}/groupUser/loadGroupUserPage",
		    datatype: "json",
		    mtype : "POST",
		    height : "auto",
		    width : "auto",
		    jsonReader : {
								root : "resultList", // json中代表实际模型数据的入口
								page : "page", // json中代表当前页码的数据   
								records : "records", // json中代表数据行总数的数据   
								total : 'total', // json中代表页码总数的数据 
								repeatitems : false // 如果设为false，则jqGrid在解析json时，会根据name来搜索对应的数据元素（即可以json中元素可以不按顺序）；而所使用的name是来自于colModel中的name设定。   
							},
		    colNames : [ '用户姓名', '用户手机号','创建时间'],
		    colModel : [ {
								label : 'leaderName',
								name : 'leaderName',
								align : 'center',
								sortable : false
							}, {
								label : 'leaderMobile',
								name : 'leaderMobile',
								align : 'center',
								sortable : false
							}, {
								label : 'createTimeFullStr',
								name : 'createTimeFullStr',
								align : 'center',
								sortable : false
							}],
		    pager: '#pager',
		    rowNum:15,
		    rowList:[15,30,50],
		    sortname: 'id',
		    viewrecords: true,
		    sortorder: "desc",
		    caption: "团队用户列表"
		});
	});
	
	function search(){
		dataGrid.jqGrid("setGridParam",{
            url:"${ctx}/groupUser/loadGroupUserPage?"+encodeURI($("#searchForm").serialize())
		}).trigger("reloadGrid");
	}
</script>
<style>
	.input-group-sm {
		margin-bottom: 10px;
	}
	.input-group-sm label{
		width:100%;
	}
	.input-group-sm label span{
		width:30%;
		text-align:right;
		display:inline-block;
	}
	.input-group-sm label input{
		width:50%;
		display:inline-block;
	}
</style>
</head>
<body>
	<div class="container-fluid">
		<form id="searchForm">
			<div class="row" style="margin-bottom:8px">
				<div class="col-sm-4" >
					<label>
						<span>用户姓名:</span>
						<input type="text" id="leaderName" name="leaderName" value="">
					</label>
				</div>
				<div class="col-sm-4" >
					<label>
						<span>用户手机号:</span>
						<input type="text" id="leaderMobile" name="leaderMobile" value="">
					</label>
				</div>
				<div class="col-sm-1" style="text-align:right;width:200px">
					<button type='button' class="btn btn-primary btn-sm" data-toggle="modal" onclick="search()">
						查询
					</button>
				</div>
			</div>
		</form>
	</div>
	<table id="list"></table>
	<div id="pager"></div>
</body>
</html>