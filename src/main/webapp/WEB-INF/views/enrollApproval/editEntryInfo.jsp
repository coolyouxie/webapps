<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>用户申请入职审核信息</title>
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
	
	function openRemarkModal(){
		$('#remarkModal').modal('show');
	}

	function agree(){
		var cashbackData = getCashbackData();
		if(!cashbackData||cashbackData.length==0){
	        alert("请输入期满返额金额和天数");
	        return;
		}
		$.ajax({
			url:"${ctx}/enrollApproval/entryApprovalById",
			type:"POST",
			dataType:"JSON",
            traditional: true,//必须指定为true
			data:{
				"id":$("#enrollApprovalId").val(),
				"state":1,
				"cashbackData":cashbackData
			},
			success:function(response){
				if(response.result=='S'){
					window.location.href="${ctx}/enrollApproval/toEntryApprovalListPage";
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
			url:"${ctx}/enrollApproval/entryApprovalById",
			type:"POST",
			dataType:"JSON",
            traditional: true,//必须指定为true
			data:{
				"id":$("#enrollApprovalId").val(),
				"state":2,
				"remark":remark,
				"approvalType":1
			},
			success:function(response){
				if(response.result=='S'){
					window.location.href="${ctx}/enrollApproval/toEntryApprovalListPage";
				}else{
					alert(response.errorMsg);
				}
			}
		});
	}
	
	function getCashbackData() {
	    var cashbackData = [];
		for(var i=1;i<=5;i++){
			var reward = $("#reward"+i).val();
			var cashbackDays = $("#cashbackDays"+i).val();
			if(reward!=null&&cashbackDays!=null){
			    if(reward&&cashbackDays){
                    cashbackData.push(reward+":"+cashbackDays);
				}
			}
		}
		return cashbackData;
    }
</script>
<style>
.input-group-sm {
	margin-bottom: 10px;
}

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
	<input type="hidden" id="enrollApprovalId" value="${enrollApprovalId}">
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
		<h3>用户申请入职审核信息</h3>
		<br/>
		<form class="form-inline">
			<div class="row">
				<label class="col-md-2 control-label">公司名称：</label>
				<div class="col-md-3">
					<span>${enrollment.company.name}</span>
				</div>
			</div>
			<div class="row">
				<label class="col-md-2 control-label">发布单：</label>
				<div class="col-md-3">
					<span>${enrollment.recruitment.title}</span>
				</div>
			</div>
			<div class="row">
				<label class="col-md-2 control-label">用户：</label>
				<div class="col-md-3">
					<span>${enrollment.user.name}</span>
				</div>
			</div>
			<div class="row">
				<label class="col-md-2 control-label">报名时间：</label>
				<div  class="col-md-3">
					<span>${enrollment.createTimeStr}</span>
				</div>
			</div>
			<div class="row">
				<label class="col-md-2 control-label">入职时间：</label>
				<div class="col-md-3">
					<span>${enrollment.entryDateStr}</span>
				</div>
			</div>
			<div class="row">
				<label class="col-md-2 control-label">用户手机号：</label>
				<div class="col-md-3">
					<span>${enrollment.user.mobile}</span>
				</div>
			</div>
			<div class="row">
				<% for(int i=1;i<=5;i++){%>
					<div class="row" style="margin-left:0px;margin-right:0px;">
						<label class="col-md-2 control-label" for="divDays<%=i%>">期满天数：</label>
						<div class="col-md-2 " id="divDays<%=i%>">
							<select id="cashbackDays<%=i%>">
								<option>请选择天数</option>
								<% for(int days=1;days<=120;days++){%>
									<option value="<%=days%>"><%=days%></option>
								<%}%>
							</select>
						</div>
						<label class="col-md-2 control-label" for="divFee<%=i%>">期满返费：</label>
						<div class="col-md-2" id="divFee<%=i%>">
							<input type="text" id="reward<%=i%>" class="form-control" value="">
						</div>
					</div>
				<%}%>
			</div>
			<div class="row">
				<div class="col-md-3">
				</div>
				<div class="col-md-2">
					<a class="btn btn-primary btn-sm" onclick="submit(1)">通过</a>
					<a class="btn btn-primary btn-sm" onclick="submit(2)">不通过</a>
					<a class="btn btn-primary btn-sm" onclick="goBack()">返回</a>
				</div>
			</div>
		</form>
	</div>
</body>
</html>