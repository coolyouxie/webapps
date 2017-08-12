<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>企业信息管理</title>	
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="${ctx}/js/common/jquery/jquery-ui-1.12.1/jquery-ui.css" type="text/css" />
<link rel="stylesheet" href="${ctx}/js/common/jquery/jqGrid/css/ui.jqgrid.css" type="text/css" />
<!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
<script src="${ctx}/js/common/jquery/jqGrid/js/grid.base.js"></script>
<script src="${ctx}/js/common/jquery/jqGrid/js/grid.common.js"></script>
<script src="${ctx}/js/common/jquery/jqGrid/js/i18n/grid.locale-cn.js"></script>
<script src="${ctx}/js/common/jquery/jqGrid/js/jquery.jqGrid.js"></script>

<script type="text/javascript">
	var dataGrid = null;
	jQuery(document).ready(function(){
		dataGrid = jQuery("#list").jqGrid({
		    url:"${ctx}/company/loadCompanyList",
		    datatype: "json",
		    mtype : "POST",
		    height : 650,
		    width : 950,
		    jsonReader : {
								root : "resultList", // json中代表实际模型数据的入口
								page : "page.page", // json中代表当前页码的数据   
								records : "page.records", // json中代表数据行总数的数据   
								total : 'page.total', // json中代表页码总数的数据 
								repeatitems : false // 如果设为false，则jqGrid在解析json时，会根据name来搜索对应的数据元素（即可以json中元素可以不按顺序）；而所使用的name是来自于colModel中的name设定。   
							},
		    colNames : [ '公司名称', '法人代表', '地址', '固定电话', '手机号', 'email', '操作'],
		    colModel : [ {
								label : 'name',
								name : 'name',
								align : 'center',
								sortable : false,
								formatter:function(cellvalue,options,rowObject){
									return '<a href="${ctx}/company/getById?id='+rowObject.id+'" style="color:blue">'+cellvalue+'</a>';
								}
							}, {
								label : 'enterpriseLegalPerson',
								name : 'enterpriseLegalPerson',
								align : 'center',
								sortable : false
							}, {
								label : 'address',
								name : 'address',
								align : 'center',
								sortable : false
							}, {
								label : 'telephone',
								name : 'telephone',
								align : 'center',
								sortable : false
							}, {
								label : 'mobile',
								name : 'mobile',
								align : 'center',
								sortable : false
							}, {
								label : 'email',
								name : 'email',
								align : 'center',
								sortable : false
							},{
								label : 'operate',
								name : 'operate',
								align : 'center',
								sortable : false
							} ],
		    pager: '#pager',
		    rowNum:50,
		    rowList:[50,100,200],
		    sortname: 'id',
		    viewrecords: true,
		    sortorder: "desc",
		    caption: "公司列表",
		    gridComplete : function() { //在此事件中循环为每一行添加日志、废保和查看链接
								var ids = jQuery("#list").jqGrid('getDataIDs');
								for ( var i = 0; i < ids.length; i++) {
									var id = ids[i];
									var rowData = $('#list').jqGrid('getRowData', id);
									operateClick = '<a href="${ctx}/company/toCompanyInfoPage?type=edit&id='+id+'" style="color:blue">编辑</a> <a href="#" style="color:blue" onclick="deleteById('+ id + ')" >删除</a>';
									jQuery("#list").jqGrid('setRowData', id, {
										operate : operateClick
									});
								}
							}
		});
	});
	
	function search(){
		var params = {};
		var keyWords = $.trim($("#keyWords").val());
		if(keyWords){
			params.keyWords=keyWords;
		}
		dataGrid.jqGrid("setGridParam",{
		    postData:{"keyWords":$("#keyWords").val()},
		    page:1
		}).trigger("reloadGrid");
	}
	
	function deleteById(id){
		$.ajax({
			url:"${ctx}/company/deleteCompanyById",
			type:"POST",
			dataType:"JSON",
			data:{
				"id":id
			},
			success:function(response){
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
			<div class="row" style="margin-bottom:10px">
				<div class="col-md-4">
					<label>
						<span>公司名称:</span>
						<input type="text" id="keyWords" name="keyWords" value="">
					</label>
				</div>
				<div class="col-md-2">
					<button type='button' class="btn btn-primary btn-sm" data-toggle="modal" onclick="search()">
						查询
					</button>
				</div>
				<div class="col-md-2">
					<a type='button' class="btn btn-primary btn-sm" href="${ctx}/company/toCompanyInfoPage?type=add">
						添加公司
					</a>
				</div>
			</div>
		</form>
	</div>
	<table id="list"></table>
	<div id="pager"></div>
</body>
</html>