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
<script src="${ctx}/js/jquery/jQuery-1.12.4.0.js"></script>
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
		    url:"${ctx}/bannerConfig/loadBannerConfigList",
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
		    colNames : [ '标题','图片', '公司名称', '发布单', '类型', '新建时间', '操作'],
		    colModel : [ {
								label : 'title',
								name : 'title',
								align : 'center',
								sortable : false,
								formatter:function(cellValue,options,rowObject){
									return '<a href="${ctx}/bannerConfig/getById?id='+rowObject.id+'" style="color:blue">'+cellValue+'</a>';
								}
							}, {
								label : 'picUrl',
								name : 'picUrl',
								align : 'center',
								sortable : false,
								formatter:function(cellValue,options,rowObject){
									return '<img src="'+cellValue+'" width="100"></img>';
								}
							}, {
								label : 'companyName',
								name : 'companyName',
								align : 'center',
								sortable : false,
								formatter:function(cellValue,options,rowObject){
									if(rowObject!=null){
										if(rowObject.company){
											return '<a href="${ctx}/company/getById?id='+rowObject.company.id+'" style="color:blue;">'+cellValue+'</a>';
										}
										return "";
									}
									return "";
								}
							}, {
								label : 'reruitmentTitle',
								name : 'reruitmentTitle',
								align : 'center',
								sortable : false,
								formatter:function(cellValue,options,rowObject){
									if(rowObject){
										if(rowObject.recruitment){
											return '<a href="${ctx}/recruitment/getById?id='+rowObject.recruitment.id+'" style="color:blue">'+cellValue+'</a>';
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
										result = "公司详情";
									}else if(cellValue==4){
										result = "发布单详情";									}
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
		    rowNum:50,
		    rowList:[50,100,200],
		    sortname: 'id',
		    viewrecords: true,
		    sortorder: "desc",
		    caption: "Banner列表",
		    gridComplete : function() { //在此事件中循环为每一行添加日志、废保和查看链接
				var ids = jQuery("#list").jqGrid('getDataIDs');
				for ( var i = 0; i < ids.length; i++) {
				var id = ids[i];
				var rowData = $('#list').jqGrid('getRowData', id);
					operateClick = '<a href="${ctx}/bannerConfig/toAddBannerPicturePage?id='+id+'" style="color:blue">编辑</a> <a href="#" style="color:blue" onclick="deleteById('+ id + ')" >删除</a>';
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
			url:"${ctx}/bannerConfig/deleteBannerConfigById",
			type:"POST",
			dataType:"JSON",
			data:{
				"id":id
			},
			success:function(response){
				if(response.result=="success"){
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
				<!-- <div class="col-md-4">
					<label>
						<span>公司名称:</span>
						<input type="text" id="keyWords" name="keyWords" value="">
					</label>
				</div> -->
				<!-- <div class="col-md-2">
					<button type='button' class="btn btn-primary btn-sm" data-toggle="modal" onclick="search()">
						查询
					</button>
				</div> -->
				<div class="col-md-2">
					<a type='button' class="btn btn-primary btn-sm" href="${ctx}/bannerConfig/toBannerConfigAddPage">
						添加banner
					</a>
				</div>
			</div>
		</form>
	</div>
	<table id="list"></table>
	<div id="pager"></div>
</body>
</html>