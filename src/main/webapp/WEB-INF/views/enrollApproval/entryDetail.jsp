<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>期满返费详情</title>
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


<script type="text/javascript">
	
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
	<input type="hidden" id="enrollApprovalId">
	<input type="hidden" id="approvalState">
	<input type="hidden" id="approvalType">

	<div class="modal fade" id="rewardModal" tabindex="-1" role="dialog" aria-labelledby="rewardModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="rewardModalLabel">入职当天返费金额及期满天数</h4>
				</div>
				<div class="modal-body">
					<label style="width: 205px;"> 
						期满天数：
						<select id="cashbackDays0" class="form-control" style="display: inline-block; width: 60%;">
							<option>请选择天数</option>
							<%
								for (int i = 1; i <= 120; i++) {
							%>
							<option value="<%=i%>"><%=i%></option>
							<%
								}
							%>
						</select>
					</label>
					<label>
						返费金额：
						<input type="text" id="reward0" class="form-control" style="display: inline-block; width: 60%;">
					</label>
					<label style="width: 205px;">
						期满天数： 
						<select id="cashbackDays1" class="form-control" style="display: inline-block; width: 60%;">
							<option>请选择天数</option>
							<%
								for (int i = 1; i <= 120; i++) {
							%>
							<option value="<%=i%>"><%=i%></option>
							<%
								}
							%>
						</select>
					</label>
					<label>
						返费金额：
						<input type="text" id="reward1" class="form-control" style="display: inline-block; width: 60%;">
					</label>
					<label style="width: 205px;">
						期满天数： 
						<select id="cashbackDays2" class="form-control" style="display: inline-block; width: 60%;">
							<option>请选择天数</option>
							<%
								for (int i = 1; i <= 120; i++) {
							%>
							<option value="<%=i%>"><%=i%></option>
							<%
								}
							%>
						</select>
					</label>
					<label>
						返费金额：
						<input type="text" id="reward2" class="form-control" style="display: inline-block; width: 60%;">
					</label>
					<label style="width: 205px;">
						期满天数：
						<select id="cashbackDays3" class="form-control" style="display: inline-block; width: 60%;">
							<option>请选择天数</option>
							<%
								for (int i = 1; i <= 120; i++) {
							%>
							<option value="<%=i%>"><%=i%></option>
							<%
								}
							%>
						</select>
					</label>
					<label>
						返费金额：
						<input type="text" id="reward3" class="form-control" style="display: inline-block; width: 60%;">
					</label>
					<label style="width: 205px;">
						期满天数：
						<select id="cashbackDays4" class="form-control" style="display: inline-block; width: 60%;">
							<option>请选择天数</option>
							<%
								for (int i = 1; i <= 120; i++) {
							%>
							<option value="<%=i%>"><%=i%></option>
							<%
								}
							%>
						</select>
					</label>
					<label>
						返费金额：
						<input type="text" id="reward4" class="form-control" style="display: inline-block; width: 60%;">
					</label>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" onclick="enrollApprovalByIdWithReward()">提交</button>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal -->
	</div>

	<div class="container-fluid">
	<h3>用户分阶段期满返费信息</h3>
	<br/>
	<form class="form-inline">
		<div class="row">
			<label class="col-md-2 control-label" for="companyName">公司名称：</label>
			<div class="col-md-3">
				<span>${dto.company.name}</span>
			</div>
		</div>
		<div class="row">
			<label class="col-md-2 control-label" for="recruitmentTitle">发布单：</label>
			<div class="col-md-3">
				<span>${dto.recruitment.title}</span>
			</div>
		</div>
		<div class="row">
			<label class="col-md-2 control-label" for="userName">用户：</label>
			<div class="col-md-3">
				<span>${dto.user.name}</span>
			</div>
		</div>
		<div class="row">
			<label class="col-md-2 control-label" for="enrollmentCreateTime">报名时间：</label>
			<div  class="col-md-3">
				<span>${dto.enrollment.createTimeStr}</span>
			</div>
		</div>
		<div class="row">
			<label class="col-md-2 control-label" for="enrollApprovalEntryDate">入职时间：</label>
			<div class="col-md-3">
				<span>${dto.enrollApproval.entryDateStr}</span>
			</div>
		</div>
		<div class="row">
			<label class="col-md-2 control-label" for="userMobile">用户手机号：</label>
			<div class="col-md-3">
				<span>${dto.user.mobile}</span>
			</div>
		</div>
		<div class="row">
			<c:forEach items="${dto.extraList}" var="item" varStatus="status">
				<div class="row" style="margin-left:0px;margin-right:0px;">
					<label class="col-md-2 control-label" for="days${status}">期满天数：</label>
					<div class="col-md-2 " id="days${status}">
						<input type="text" class="form-control" value="${item.cashbackDays}">
					</div>
					
					<label class="col-md-2 control-label" for="fee${status}">期满天数：</label>
					<div class="col-md-2" id="fee${status}">
						<input type="text" class="form-control" value="${item.fee}">
					</div>
				</div>
			</c:forEach>
		</div>
	</form>
	</div>
</body>
</html>