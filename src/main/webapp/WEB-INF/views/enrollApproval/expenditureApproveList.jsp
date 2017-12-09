<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>提现审核</title>	
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
		    url:"${ctx}/applyExpenditure/loadPageList",
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
		    colNames : [ '姓名', '手机号','身份证号','银行卡号','提现金额','状态',"申请时间",'备注','操作'],
		    colModel : [ {
								label : 'user.name',
								name : 'user.name',
								align : 'center',
								sortable : false,
								formatter:function(cellValue,options,rowObject){
									return '<a href="${ctx}/user/getById?id='+rowObject.user.id+'" style="color:blue">'+cellValue+'</a>';
								}
							}, {
								label : 'user.mobile',
								name : 'user.mobile',
								align : 'center',
								sortable : false
							}, {
								label : 'user.idCardNo',
								name : 'user.idCardNo',
								align : 'center',
								sortable : false
							}, {
								label : 'user.bankCardNum',
								name : 'user.bankCardNum',
								align : 'center',
								sortable : false
							}, {
								label : 'fee',
								name : 'fee',
								align : 'center',
								sortable : false,
								formatter:function(cellValue,options,rowObject){
									return cellValue;
								}
							}, {
								label : 'state',
								name : 'state',
								align : 'center',
								sortable : false,
								formatter:function(cellValue,options,rowObject){
									var result = null;
									if(cellValue==0){
										result = '待审核';
									}else if(cellValue==1){
										result = "审核通过";
									}else if(cellValue==2){
										result = "审核不通过";
									}
									return result;
								}
							}, {
								label : 'createTimeFullStr',
								name : 'createTimeFullStr',
								align : 'center',
								sortable : false
							}, {
								label : 'reason',
								name : 'reason',
								align : 'center',
								sortable : false
							}, {
								label : 'operate',
								name : 'operate',
								align : 'center',
								sortable : false,
								formatter:function(cellValue,options,rowObject){
									var result = null;
									if(rowObject.state==0){
                                        result = '<a href="${ctx}/applyExpenditure/toApproveExpenditurePage?id='+rowObject.id+'" class="btn btn-primary btn-sm">审核</a>';
									}else{
                                        result = '<a href="${ctx}/applyExpenditure/toShowExpenditurePage?id='+rowObject.id+'" class="btn btn-primary btn-sm">审核信息</a>';
									}
									return result;
								}
							} ],
		    pager: '#pager',
		    rowNum:15,
		    rowList:[15,30,50],
		    sortname: 'id',
		    viewrecords: true,
		    sortorder: "desc",
		    caption: "审核列表"
		});
	});
	
	function search(){
		dataGrid.jqGrid("setGridParam",{
		    url:"${ctx}/applyExpenditure/loadPageList?"+decodeURI($("#searchForm").serialize())
		}).trigger("reloadGrid");
	}
	
	function approvalById(id,state,remark){
		$.ajax({
			url:"${ctx}/applyExpenditure/approvalById",
			type:"POST",
			dataType:"JSON",
			data:{
				"id":id,
				"state":state,
				"reason":remark
			},
			success:function(response){
				$('#remarkModal').modal('hide');
				if(response.result=='S'){
					alert("审核信息更新完成");
					dataGrid.trigger("reloadGrid");
				}else{
					alert(response.errorMsg);
				}
			}
		});
	}
	
	function showModal(id,state){
		$("#applyExpenditureId").val(id);
		$("#approvalState").val(state);
		$('#remarkModal').modal('show');
	}
	
	function approvalByIdWithRemark(){
		var id = $("#applyExpenditureId").val();
		var state = $("#approvalState").val();
		var remark = $("#remark").val().trim();
		if(state==2){
			if(!remark){
				alert("请填写审核不通过原因");
				return ;
			}
		}
		approvalById(id,state,remark);
	}
	
</script>
<style>
	.ui-jqgrid tr.jqgrow td {
		white-space: normal !important;
		height:auto;
		vertical-align:text-top;
		padding-top:2px;
		word-break:break-all;
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
	<input type="hidden" id="applyExpenditureId" >
	<input type="hidden" id="approvalState" >
	<div class="modal fade" id="remarkModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">拒绝原因</h4>
				</div>
				<div class="modal-body">
					<textarea id="remark" class="form-control" ></textarea>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" onclick="approvalByIdWithRemark()">提交</button>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal -->
	</div>
	<div class="container-fluid">
		<form id="searchForm">
			<div class="row" style="margin-bottom:8px">
				<div class="col-sm-3" style="width:255px">
					<label>
						<span>会员姓名:</span>
						<input type="text" id="userName" name="user.name" value="">
					</label>
				</div>
				<div class="col-sm-3" style="width:255px">
					<label>
						<span>手机号:</span>
						<input type="text" id="userMobile" name="user.mobile" value="">
					</label>
				</div>
				<div class="col-sm-1" style="text-align:right;width:200px">
					<button type='button' class="btn btn-primary btn-sm" data-toggle="modal" onclick="search()">
						查询
					</button>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-4">
					<label>
						<span>申请时间:</span>
						<input type="text" id="applyTimeStart" name="applyTimeStart" onClick="WdatePicker({isShowWeek:true})" style="width: 120px;">
						-
						<input type="text" id="applyTimeEnd" name="applyTimeEnd" onClick="WdatePicker({isShowWeek:true})" style="width: 120px;">
					</label>
				</div>
			</div>
		</form>
	</div>
	<table id="list"></table>
	<div id="pager"></div>
</body>
</html>