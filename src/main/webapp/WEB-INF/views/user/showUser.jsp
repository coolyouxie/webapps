<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户信息</title>
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
<style>
	 col-md-2, span{
		display:-moz-inline-box;
		display:inline-block;
		width:80px;
	}
</style>

<script type="text/javascript">
	jQuery(document).ready(function(){
		/* var dataGrid = jQuery("#list").jqGrid({
		    url:"${ctx}/billRecord/loadBillRecordByUserId",
		    datatype: "json",
		    mtype : "POST",
		    height:650,
		    width:950,
		    jsonReader : {
								root : "resultList", // json中代表实际模型数据的入口
								page : "page.page", // json中代表当前页码的数据   
								records : "page.records", // json中代表数据行总数的数据   
								total : 'page.total', // json中代表页码总数的数据 
								repeatitems : false // 如果设为false，则jqGrid在解析json时，会根据name来搜索对应的数据元素（即可以json中元素可以不按顺序）；而所使用的name是来自于colModel中的name设定。   
							},
		    colNames : [ '金额', '类型', '时间' ],
		    colModel : [ {
								label : 'fee',
								name : 'fee',
								align : 'center',
								sortable : false
							}, {
								label : 'type',
								name : 'type',
								align : 'center',
								sortable : false,
								formatter:function(cellValue,options,rowObject){
									if(cellValue==-1){
										return '提现';
									}else if(cellValue==1){
										return '推荐奖励';
									}else if(cellValue==2){
										return '红包奖励';
									}
								}
							}, {
								label : 'createTime',
								name : 'createTime',
								align : 'center',
								sortable : false,
								formatter:function(cellValue,options,rowObject){
									if(cellValue==1){
										return '男';
									}else{
										return '女';
									}
								}
							}],
		    pager: '#pager',
		    rowNum:50,
		    rowList:[50,100,200],
		    sortname: 'id',
		    viewrecords: true,
		    sortorder: "desc",
		    caption: "金额列表",
		    gridComplete : function() { //在此事件中循环为每一行添加日志、废保和查看链接
								
			}
		}); */
	});
</script>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-3 col-md-offset-2">
				<h4>
					用户信息
				</h4>
			</div>
		</div>
		<input type="hidden" id="id" name="id" value="${user.id}">
		<div class="row">
			<label class="col-md-2 control-label" for="account">
				账号：
			</label>
			<div class="col-md-3">
				${user.account}
			</div>
		</div>
	
		<div class="row">
			<label class="col-md-2 control-label" for="name">姓名：</label>
			<div class="col-md-3" >
				${user.name}
			</div>
		</div>	
			
		<div class="row">
			<label class="col-md-2 control-label" for="idCardNo">身份证：</label>
			<div class="col-md-3" >
				${user.idCardNo}
			</div>
		</div>	
			
		<div class="row">
			<label class="col-md-2 control-label" for="mobile">手机号：</label>
			<div class="col-md-3" >
				${user.mobile}
			</div>
		</div>
			
		<div class="row">
			<label class="col-md-2 control-label" for="gender">性别：</label>
			<div class="col-md-3" >
				<select id="gender" name="gender" class="form-control" disabled="disabled">
					<option value="0" <c:if test="${user.gender==0}">selected</c:if>>女</option>
					<option value="1" <c:if test="${user.gender==1}">selected</c:if>>男</option>
				</select>
			</div>
		</div>
			
		<div class="row">
			<label class="col-md-2 control-label" for="age">年龄：</label>
			<div class="col-md-3" >
				${user.age}
			</div>
		</div>
		
		<div class="row">
			<div class="col-md-9">
				<table id="list"></table>
				<div id="pager"></div>
			</div>
		</div>
	</div>
	
</body>
</html>