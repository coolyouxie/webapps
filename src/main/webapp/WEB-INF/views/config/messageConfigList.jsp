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
<link rel="stylesheet" href="${ctx}/js/common/bootstrap/bootstrap-3.3.7/dist/css/bootstrap.min.css" type="text/css" />
<script src="${ctx}/js/jquery/jQuery-1.12.4.0.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="${ctx}/js/common/bootstrap/bootstrap-3.3.7/dist/js/bootstrap.min.js"></script>
<script src="${ctx}/js/common/jquery/jqGrid/js/grid.base.js"></script>
<script src="${ctx}/js/common/jquery/jqGrid/js/grid.common.js"></script>
<script src="${ctx}/js/common/jquery/jqGrid/js/i18n/grid.locale-cn.js"></script>
<script src="${ctx}/js/common/jquery/jqGrid/js/jquery.jqGrid.js"></script>

<script type="text/javascript">
	var dataGrid = null;
	jQuery(document).ready(function(){
		dataGrid = jQuery("#list").jqGrid({
		    url:"${ctx}/messageConfig/loadMessageConfigList",
		    datatype: "json",
		    mtype : "POST",
		    height : "auto",
		    width : "auto",
            rownumbers: true,
		    jsonReader : {
								root : "resultList", // json中代表实际模型数据的入口
								page : "page", // json中代表当前页码的数据   
								records : "records", // json中代表数据行总数的数据   
								total : 'total', // json中代表页码总数的数据 
								repeatitems : false // 如果设为false，则jqGrid在解析json时，会根据name来搜索对应的数据元素（即可以json中元素可以不按顺序）；而所使用的name是来自于colModel中的name设定。   
							},
		    colNames : [ '标题', '发布单', '类型', '新建时间', '操作'],
		    colModel : [ {
								label : 'title',
								name : 'title',
								align : 'center',
								sortable : false
								<%--formatter:function(cellValue,options,rowObject){--%>
									<%--return '<a href="${ctx}/bannerConfig/getById?id='+rowObject.id+'" style="color:blue">'+cellValue+'</a>';--%>
								<%--}--%>
							}, {
								label : 'reruitmentTitle',
								name : 'reruitmentTitle',
								align : 'center',
								sortable : false,
								formatter:function(cellValue,options,rowObject){
									if(rowObject){
										if(rowObject.belongType==2&&rowObject.recruitment!=null){
											return '<a href="${ctx}/recruitment/getById?id='+rowObject.fkId+'" style="color:blue">'+rowObject.recruitment.title+'</a>';
										}
									}
									return "";
								}
							}, {
								label : 'type',
								name : 'type',
								align : 'center',
								sortable : false,
								formatter:function(cellValue,options,rowObject){
									var result = null;
									if(cellValue==1){
										result = "推荐";
									}else if(cellValue==2){
										result = "分享";
									}else if(cellValue==3){
										result = "详情";
									}else if(cellValue==4){
                                        result = "补全信息";
									}
									return result;
								}
							}, {
								label : 'createTimeStr',
								name : 'createTimeStr',
								align : 'center',
								sortable : false
							}, {
								label : 'operate',
								name : 'operate',
								align : 'center',
								sortable : false
							} ],
		    pager: '#pager',
		    rowNum:15,
		    rowList:[15,30,50],
		    sortname: 'id',
		    viewrecords: true,
		    sortorder: "desc",
		    caption: "消息列表",
		    gridComplete : function() { //在此事件中循环为每一行添加日志、废保和查看链接
				var ids = jQuery("#list").jqGrid('getDataIDs');
				for ( var i = 0; i < ids.length; i++) {
				var id = ids[i];
				var rowData = $('#list').jqGrid('getRowData', id);
					operateClick = '<a href="${ctx}/messageConfig/toMessageConfigEditPage?id='+id+'" style="color:blue">编辑</a> <a href="#" style="color:blue" onclick="deleteById('+ id + ')" >删除</a>';
					jQuery("#list").jqGrid('setRowData', id, {
						operate : operateClick
					});
				}
			}
		});
	});
	
	function search(){
		dataGrid.jqGrid("setGridParam",{
            url:"${ctx}/messageConfig/loadMessageConfigList?"+encodeURI($("searchForm").serialize())
		}).trigger("reloadGrid");
	}
	
	function deleteById(id){
		$.ajax({
			url:"${ctx}/messageConfig/deleteMessageConfigById",
			type:"POST",
			dataType:"JSON",
			data:{
				"id":id
			},
			success:function(response){
				if(response.result=="S"){
					alert("删除成功");
				}else{
					alert(response.errorMsg);
				}
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
				<div class="col-md-2">
					<a type='button' class="btn btn-primary btn-sm" href="${ctx}/messageConfig/toMessageConfigAddPage">
						添加消息
					</a>
				</div>
			</div>
		</form>
	</div>
	<table id="list"></table>
	<div id="pager"></div>
</body>
</html>