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
<style>
	 col-md-2, span{
		display:-moz-inline-box;
		display:inline-block;
		width:80px;
	}
</style>

<script type="text/javascript">
	var dataGrid = null;
	jQuery(document).ready(function(){
		dataGrid = jQuery("#list").jqGrid({
	    url:"${ctx}/recruitment/loadRecruitmentList",
	    datatype: "local",
	    mtype : "POST",
	    jsonReader : {
							root : "resultList", // json中代表实际模型数据的入口
							page : "page.page", // json中代表当前页码的数据   
							records : "page.records", // json中代表数据行总数的数据   
							total : 'page.total', // json中代表页码总数的数据 
							repeatitems : false // 如果设为false，则jqGrid在解析json时，会根据name来搜索对应的数据元素（即可以json中元素可以不按顺序）；而所使用的name是来自于colModel中的name设定。   
						},
	    colNames : [ '标题', '招聘人数', '招聘佣金', '工种', '薪资范围', '返现金额','推荐人佣金','期满天数', '操作'],
	    colModel : [ {
							label : 'title',
							name : 'title',
							align : 'center',
							sortable : false,
							formatter:function(cellvalue,options,rowObject){
								return '<a href="${ctx}/recruitment/getById?id='+rowObject.id+'" style="color:blue">'+cellvalue+'</a>';
							}
						}, {
							label : 'recruitmentNumber',
							name : 'recruitmentNumber',
							align : 'center',
							sortable : false
						}, {
							label : 'commision',
							name : 'commision',
							align : 'center',
							sortable : false
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
							formatter:function(cellvalue,options,rowObject){
								return rowObject.salaryLow+"元-"+rowObject.salaryHigh+"元";
							}
						}, {
							label : 'cashback',
							name : 'cashback',
							align : 'center',
							sortable : false
						},{
							label : 'cashbackForBroker',
							name : 'cashbackForBroker',
							align : 'center',
							sortable : false
						},{
							label : 'cashbackDays',
							name : 'cashbackDays',
							align : 'center',
							sortable : false
						},{
							label : 'operate',
							name : 'operate',
							align : 'center',
							sortable : false
						} ],
	    pager: '#pager',
	    rowNum:10,
	    rowList:[10,20,50],
	    sortname: 'id',
	    viewrecords: true,
	    sortorder: "desc",
	    //caption: "公司发布单",
	    gridComplete : function() { //在此事件中循环为每一行添加日志、废保和查看链接
			var ids = jQuery("#list").jqGrid('getDataIDs');
			for ( var i = 0; i < ids.length; i++) {
				var id = ids[i];
				var rowData = $('#list').jqGrid('getRowData', id);
				operateClick = '<a href="${ctx}/recruitment/toRecruitmentInfoPage?type=edit&id='+id+'" style="color:blue">编辑</a> <a href="#" style="color:blue" onclick="deleteRecruitmentById('+ id + ')" >删除</a>';
				jQuery("#list").jqGrid('setRowData', id, {
					operate : operateClick
				});
			}
		}
	});
	search();
});
	function search(){
		var companyId=$("#companyId").val();
		dataGrid.jqGrid("setGridParam",{
		    postData:{"companyId":$("#companyId").val()},
		    page:1,
		    datatype:'json'
		}).trigger("reloadGrid");
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
			<form id="addRecruitment" action="${ctx}/recruitment/toAddRecruitmentPage" method="post">
				<input type="hidden" id="companyId" name="companyId" value="${company.id}">
				<div class="col-md-2">
					<button type="submit" onclick="">新建发布单</button>
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
			<label class="col-md-2 control-label" for="contactName">联系人：</label>
			<div class="col-md-4" >
				${company.contactName }
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
				${company.mobile }
			</div>
		</div>
			
		<div class="row">
			<label class="col-md-2 control-label" for="email">email：</label>
			<div class="col-md-4" >
				${company.email }
			</div>
		</div>
			
		<div class="row">
			<label class="col-md-2 control-label" for="enterpriseLegalPerson" >法人代表：</label>
			<div class="col-md-4" >
				${company.enterpriseLegalPerson }
			</div>
		</div>
			
		<div class="row">
			<label class="col-md-2 control-label" for="address">地址：</label>
			<div class="col-md-4" >
				${company.address }
			</div>
		</div>
			
		<div class="row">
			<label class="col-md-2 control-label" for="briefs">公司简介：</label>
			<div class="col-md-4" >
				${company.briefs }
			</div>
		</div>
			
		<div class="row">
			<label class="col-md-2 control-label" for="environment">公司环境：</label>
			<div class="col-md-4" >
				${company.environment }
			</div>
		</div>
			
		<c:forEach items="${company.pictures}" var="item" varStatus="status">
			<div class="row">
				<label class="col-md-2 control-label" for="">公司图片：</label>
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