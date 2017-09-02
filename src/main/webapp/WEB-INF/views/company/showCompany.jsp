<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增或修改公司信息</title>
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
	    url:"${ctx}/recruitment/loadRecruitmentList",
	    datatype: "local",
	    mtype : "POST",
	    width : 950,
	    jsonReader : {
							root : "resultList", // json中代表实际模型数据的入口
							page : "page.page", // json中代表当前页码的数据   
							records : "page.records", // json中代表数据行总数的数据   
							total : 'page.total', // json中代表页码总数的数据 
							repeatitems : false // 如果设为false，则jqGrid在解析json时，会根据name来搜索对应的数据元素（即可以json中元素可以不按顺序）；而所使用的name是来自于colModel中的name设定。   
						},
						colNames : [ '标题', '公司', '工种', '薪资范围','联系电话','发布日期', '招工类型','发布单类型','期满天数','状态', '操作'],
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
	    rowNum:10,
	    rowList:[10,20,50],
	    sortname: 'id',
	    viewrecords: true,
	    sortorder: "desc",
	    caption: "公司发布单",
	    gridComplete : function() { //在此事件中循环为每一行添加日志、废保和查看链接
			
		}
	});
	search();
});
	function search(){
		var companyId=$("#companyId").val();
		dataGrid.jqGrid("setGridParam",{
		    postData:{"company.id":$("#companyId").val()},
		    page:1,
		    datatype:'json'
		}).trigger("reloadGrid");
	}
	
	function addRecruitment(){
		var companyId = $("#companyId").val();
		window.location.href="${ctx}/recruitment/toAddRecruitmentPage?companyId="+companyId;
	}
	
	function addMessage(){
		var companyId = $("#companyId").val();
		window.location.href="${ctx}/messageConfig/toAddMessagePage?fkId="+companyId+"&type=3&belongType=1";
	}
</script>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-4 col-md-offset-2">
				<h4>
					查看公司信息
				</h4>
			</div>
			<form id="addRecruitment" method="post">
				<input type="hidden" id="companyId" name="companyId" value="${company.id}">
				<div class="col-md-2">
					<button type="button" onclick="addRecruitment()" class="form-control" >新建发布单</button>
				</div>
				<div class="col-md-2">
					<button type="button" onclick="addMessage()" class="form-control" >新建消息</button>
				</div>
			</form>
		</div>
		
		<div class="row">
			<label class="col-md-2 control-label" for="name">公司名称：</label>
			<div class="col-md-4" >
				${company.name}
			</div>
		</div>
		
		<div class="row">
				<label class="col-md-2 control-label" for="isBanner">Banner展示：</label>
				<div class="col-md-4" >
					<select id="isBanner" name="isBanner" class="form-control">
						<option value="2" <c:if test="${company.isBanner==2}">selected</c:if>>否</option>
						<option value="1" <c:if test="${company.isBanner==1}">selected</c:if>>是</option>
					</select>
					<br/>
					<c:if test="${company.isBanner==1 and company.bannerConfig.picUrl != null}">
						<img src="${company.bannerConfig.picUrl}" style="width:120px;" />
					</c:if>
				</div>
			</div>
			
			<div class="row">
				<label class="col-md-2 control-label" for="isMessage">作为信息推送：</label>
				<div class="col-md-4" >
					<select id="isMessage" name="isMessage" class="form-control" disabled="disabled">
						<option value="0" <c:if test="${company.isMessage==0}">selected</c:if>>否</option>
						<option value="1" <c:if test="${company.isMessage==1}">selected</c:if>>是</option>
					</select>
				</div>
			</div>
			
			<div class="row">
				<label class="col-md-2 control-label" for="messageTitle">消息内容：</label>
				<div class="col-md-4" >
					<text id="messageTitle" name="message.title" value="${recruitment.message.title}" readonly>
				</div>
			</div>
			
			<div class="row">
				<label class="col-md-2 control-label" for="message">消息内容：</label>
				<div class="col-md-4" >
					${recruitment.message.message}
				</div>
			</div>
		
		<div class="row">
			<label class="col-md-2 control-label" >公司性质：</label>
			<div class="col-md-4" >
				${company.type}
			</div>
		</div>
		
		<div class="row">
			<label class="col-md-2 control-label" >公司规模：</label>
			<div class="col-md-4" >
				${company.companySize}
			</div>
		</div>
			
		<div class="row">
			<label class="col-md-2 control-label" for="contactName">联系人：</label>
			<div class="col-md-4" >
				${company.contactName}
			</div>
		</div>
			
		<div class="row">
			<label class="col-md-2 control-label" for="telephone">联系电话：</label>
			<div class="col-md-4" >
				${company.telephone}
			</div>
		</div>
			
		<div class="row">
			<label class="col-md-2 control-label" for="mobile">手机号：</label>
			<div class="col-md-4" >
				${company.mobile}
			</div>
		</div>
			
		<div class="row">
			<label class="col-md-2 control-label" for="briefs">公司简介：</label>
			<div class="col-md-4" >
				${company.briefs}
			</div>
		</div>
			
		<div class="row">
			<label class="col-md-2 control-label" for="environment">公司环境：</label>
			<div class="col-md-4" >
				${company.environment}
			</div>
		</div>
			
		<c:forEach items="${company.pictures}" var="item" varStatus="status">
			<div class="row">
				<label class="col-md-2 control-label" >公司图片：</label>
				<div class="col-md-4" >
					<img src="${item.picUrl}" width="120"/>
				</div>
			</div>
		</c:forEach>
	</div>
	<table id="list"></table>
	<div id="pager"></div>
</body>
</html>