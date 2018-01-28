<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>用户申请提现审核信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="${ctx}/js/common/jquery/jquery-ui-1.12.1/jquery-ui.css" type="text/css" />
<link rel="stylesheet" href="${ctx}/js/common/jquery/jqGrid/css/ui.jqgrid.css" type="text/css" />
<!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="${ctx}/js/common/bootstrap/bootstrap-3.3.7/dist/css/bootstrap.min.css" type="text/css" />
<link rel="stylesheet" href="${ctx}/js/common/jquery/My97DatePicker/skin/WdatePicker.css" type="text/css" />
<script src="${ctx}/js/jquery/jQuery-1.12.4.0.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script
	src="${ctx}/js/common/bootstrap/bootstrap-3.3.7/dist/js/bootstrap.min.js"></script>
<script src="${ctx}/js/common/jquery/jqGrid/js/grid.base.js"></script>
<script src="${ctx}/js/common/jquery/jqGrid/js/grid.common.js"></script>
<script src="${ctx}/js/common/jquery/jqGrid/js/i18n/grid.locale-cn.js"></script>
<script src="${ctx}/js/common/jquery/jqGrid/js/jquery.jqGrid.js"></script>
<!-- 日期时间控件 -->
<script src="${ctx}/js/common/jquery/My97DatePicker/WdatePicker.js"></script>
<script src="${ctx}/js/common/jquery/My97DatePicker/lang/zh-cn.js"></script>
<script src="${ctx}/js/common/common.js"></script>

<script type="text/javascript">
	function submit(state){
		var r=null;
		if(state==1){
			r=confirm("确认审核通过！");
		}else{
			r=confirm("确认审核不通过！");
		}
		if (r==true){
			if(state==1){
				agree();
			}else{
				$('#remarkModal').modal('show');
			}
		}else{
			return;
		}
	}

	function agree(){
		$.ajax({
			url:"${ctx}/applyExpenditure/expenditureApprovalById",
			type:"POST",
			dataType:"JSON",
	        traditional: true,//必须指定为true
			data:{
				"id":$("#applyExpenditureId").val(),
				"state":1
			},
			success:function(response){
				if(response.result=='S'){
					window.location.href="${ctx}/applyExpenditure/toExpenditureApproveListPage";
				}else{
					alert(response.errorMsg);
				}
			}
		});
	}
	
	function disagree(){
		var remark = $("#remark").val().trim();
		if(!remark){
			alert("请输入不通过原因");
			return;
		}
		$('#remarkModal').modal('hide');
		$.ajax({
			url:"${ctx}/applyExpenditure/expenditureApprovalById",
			type:"POST",
			dataType:"JSON",
	        traditional: true,//必须指定为true
			data:{
				"id":$("#applyExpenditureId").val(),
				"state":2,
				"reason":remark
			},
			success:function(response){
				if(response.result=='S'){
					window.location.href="${ctx}/applyExpenditure/toExpenditureApproveListPage";
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
.row div {
	margin-bottom:10px;
}
</style>
</head>
<body>
	<input type="hidden" id="applyExpenditureId" value="${dto.id}">
	<!-- 不通过原因模态框 -->
	<div class="modal fade" id="remarkModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">不通过原因</h4>
				</div>
				<div class="modal-body">
					<textarea id="remark" class="form-control" ></textarea>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" onclick="disagree()">提交</button>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal -->
	</div>
	<div class="container-fluid">
		<h3>用户申请提现审核信息</h3>
		<br/>
		<div class="row">
			<label class="col-md-2 control-label">用户姓名：</label>
			<div class="col-md-3">
				<span>${dto.user.name}</span>
			</div>
		</div>
		<div class="row">
			<label class="col-md-2 control-label">用户手机号：</label>
			<div class="col-md-3">
				<span>${dto.user.mobile}</span>
			</div>
		</div>
		<div class="row">
			<label class="col-md-2 control-label">用户身份证号：</label>
			<div class="col-md-3">
				<span>${dto.user.idCardNo}</span>
			</div>
		</div>
		<div class="row">
			<label class="col-md-2 control-label">用户银行卡号：</label>
			<div  class="col-md-3">
				<span>${dto.user.bankCardNum}</span>
			</div>
		</div>
		<div class="row">
			<label class="col-md-2 control-label">提现金额：</label>
			<div class="col-md-3" style="color:red">
				<span>${dto.fee}</span>
			</div>
		</div>
		<div class="row">
			<label class="col-md-2 control-label">申请时间：</label>
			<div class="col-md-3" style="color:red">
				<span>${dto.createTimeStr}</span>
			</div>
		</div>
		<div class="row">
			<div class="col-md-3"></div>
			<div class="col-md-3">
				<a class="btn btn-primary btn-sm" onclick="submit(1)">通过</a>
				<a class="btn btn-primary btn-sm" onclick="submit(2)">不通过</a>
				<a class="btn btn-primary btn-sm" onclick="goBack()">返回</a>
			</div>
		</div>
	</div>
</body>
</html>