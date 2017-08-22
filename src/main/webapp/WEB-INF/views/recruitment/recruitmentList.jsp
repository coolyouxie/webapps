<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>发布单管理</title>	
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

<!-- 日期时间控件 -->
<script src="${ctx}/js/common/jquery/My97DatePicker/WdatePicker.js"></script>
<script src="${ctx}/js/common/jquery/My97DatePicker/lang/zh-cn.js"></script>

<script type="text/javascript">
	var dataGrid = null;
	jQuery(document).ready(function(){
		dataGrid = jQuery("#list").jqGrid({
		    url:"${ctx}/recruitment/loadRecruitmentList",
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
		    colNames : [ '标题', '公司', '工种', '薪资范围','联系电话','发布日期', '招工类型','发布单类型','期满天数','状态','Banner展示','Banner图片', '操作'],
		    colModel : [ {
								label : 'title',
								name : 'title',
								align : 'center',
								sortable : false,
								formatter:function(cellvalue,options,rowObject){
									return '<a href="${ctx}/recruitment/getById?id='+rowObject.id+'" style="color:blue">'+cellvalue+'</a>';
								}
							}, {
								label : 'company.name',
								name : 'company.name',
								align : 'center',
								sortable : false,
								formatter:function(cellValue,options,rowObject){
									var result = "";
									if(rowObject.company){
										result = '<a href="${ctx}/company/getById?id='+rowObject.company.id+'" style="color:blue">'+cellValue+'</a>';
									}
									return result;
								}
							}, {
								label : 'workType',
								name : 'workType',
								align : 'center',
								sortable : false
							}, {
								label : 'salaryLow',
								name : 'salaryLow',
								align : 'center',
								sortable : false,
								formatter:function(cellValue,options,rowObject){
									var result =  cellValue+'元~'+rowObject.salaryHigh+'元';
									return result;
								}
							}, {
								label : 'mobile',
								name : 'mobile',
								align : 'center',
								sortable : false
							}, {
								label : 'createTimeStr',
								name : 'createTimeStr',
								align : 'center',
								sortable : false
							}, {
								label : 'publishType',
								name : 'publishType',
								align : 'center',
								sortable : false,
								formatter:function(cellValue,options,rowObject){
									var result = null;
									if(cellValue==1){
										result = "普招";
									}else if(cellValue==2){
										result = "直招";
									}else if(cellValue==3){
										result = "兼职";
									}
									return result;
								}
							}, {
								label : 'type',
								name : 'type',
								align : 'center',
								sortable : false,
								formatter:function(cellValue,options,rowObject){
									var result = null;
									if(cellValue==1){
										result = "热招";
									}else if(cellValue==2){
										result = "高返费";
									}else if(cellValue==3){
										result = "工作轻松";
									}else if(cellValue==4){
										result = "高工资";
									}
									return result;
								}
							}, {
								label : 'cashbackDays',
								name : 'cashbackDays',
								align : 'center',
								sortable : false
							}, {
								label : 'dataState',
								name : 'dataState',
								align : 'center',
								sortable : false,
								formatter:function(cellValue,options,rowObject){
									var result = "";
									if(cellValue==0){
										result = "无效";
									}else{
										result = "有效";
									}
									return result;
								}
							}, {
								label : 'isBanner',
								name : 'isBanner',
								align : 'center',
								sortable : false,
								formatter:function(cellValue,options,rowObject){
									var result = "";
									if(cellValue==1){
										result = "是";
									}else{
										result = "否";
									}
									return result;
								}
							}, {
								label : 'picture.picUrl',
								name : 'picture.picUrl',
								align : 'center',
								sortable : false,
								formatter:function(cellValue,options,rowObject){
									var result = "";
									if(rowObject.isBanner==1){
										result = "<img src='"+cellValue+"' style='width:120px;'>";
									}else{
										result = "无";
									}
									return result;
								}
							}, {
								label : 'operate',
								name : 'operate',
								align : 'center',
								sortable : false,
								formatter:function(cellValue,options,rowObject){
									var result = null;
									if(rowObject.dataState==0){
										return "";
									}
									result = '<a href="${ctx}/recruitment/toRecruitmentInfoPage?type=editFromList&id='+rowObject.id+'" style="color:blue">编辑</a>'+
										' <a href="#" style="color:blue" onclick="deleteById('+ rowObject.id + ')" >删除</a>'
									return result;
								}
							} ],
		    pager: '#pager',
		    rowNum:50,
		    rowList:[50,100,200],
		    sortname: 'id',
		    viewrecords: true,
		    sortorder: "desc",
		    caption: "报名列表",
		    gridComplete : function() { //在此事件中循环为每一行添加日志、废保和查看链接
				
			}
		});
	});
	
	function search(){
		dataGrid.jqGrid("setGridParam",{
		    postData:$("#searchForm").serialize(),
		    page:1
		}).trigger("reloadGrid");
	}
	
	function deleteById(id){
		$.ajax({
			url:"${ctx}/recruitment/deleteRecruitmentById",
			type:"POST",
			dataType:"JSON",
			data:{
				"id":id
			},
			success:function(response){
				if("S"==response.resutl){
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
				<div class="col-sm-3" style="width:275px;">
					<label>
						<span>公司:</span>
						<input type="text" id="company.name" name="company.name">
					</label>
				</div>
				<div class="col-sm-4" style="width:275px;">
					<label>
						<span>发布单:</span>
						<input type="text" id="title" name="title">
					</label>
				</div>
				<div class="col-md-3" >
					<label>
						<span>招工类型:</span>
						<select id="publishType" name="publishType" >
							<option value="">-请选择-</option>
							<option value="1">普招</option>
							<option value="2">直招</option>
							<option value="3">兼职</option>
						</select>
					</label>
					<label>
						<span>发布单类型:</span>
						<select id="type" name="type" >
							<option value="">-请选择-</option>
							<option value="1">热招</option>
							<option value="2">高返费</option>
							<option value="3">工作轻松</option>
							<option value="4">工资高</option>
						</select>
					</label>
				</div>
			</div>
			<div class="row">
				<div class="col-md-6" style="width:435px">
					<label>
						<span>发布时间:</span>
						<input type="text" id="publishTimeStart" name="publishTimeStart" onclick="WdatePicker({isShowWeek:true})" style="widht:100px">
						-
						<input type="text" id="publishTimeEnd" name="publishTimeEnd" onclick="WdatePicker({isShowWeek:true})" style="widht:100px">
					</label>
				</div>
				<div class="col-md-2">
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