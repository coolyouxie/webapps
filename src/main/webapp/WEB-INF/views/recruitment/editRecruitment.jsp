<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改发布信息</title>
<link rel="stylesheet" href="${ctx}/js/common/bootstrap/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.css" type="text/css" />
<!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
<script src="${ctx}/js/common/bootstrap/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script src="${ctx}/js/common/bootstrap/bootstrap-datetimepicker-master/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<style>
	 col-md-2, span{
		display:-moz-inline-box;
		display:inline-block;
		width:80px;
	}
</style>

<script type="text/javascript">
	$(function(){
		$('.form_datetime').datetimepicker({
			language: 'zh-CN',
			weekStart: 1,
	        todayBtn:  1,
			autoclose: 1,
			todayHighlight: 1,
			startView: 1,
			minView: 0,
			maxView: 1,
			forceParse: 0,
			minuteStep:1,
			format:'yyyy-mm-dd hh:ii:ss'
		});
	});
</script>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-4 col-md-offset-2">
				<h4>
					修改发布单
				</h4>
			</div>
		</div>
		<form id="saveForm" class="form-horizontal" action="${ctx}/recruitment/saveRecruitment"  method="post">
			<input type="hidden" id="handleType" name="handleType" value="edit">
			<input type="hidden" id="id" name="id" value="${recruitment.id}">
			<input type="hidden" id="companyId" name="companyId" value="${recruitment.company.id}" >
			<div class="form-group">
				<label class="col-md-2 control-label" for="">公司名称：</label>
				<div class="col-md-4" >
					<label class="col-md-3 control-label" >${recruitment.company.name}</label>
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="title">标题：</label>
				<div class="col-md-4" >
					<input type="text" id="title" name="title" class="form-control" value="${recruitment.title }" >
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="salaryBriefs">薪酬福利：</label>
				<div class="col-md-4" >
					<textarea id="salaryBriefs" name="salaryBriefs" class="form-control" row="3" >${recruitment.salaryBriefs}</textarea>
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="briefInfo">其他福利：</label>
				<div class="col-md-4" >
					<textarea id="briefInfo" name="briefInfo" class="form-control" row="3" >${recruitment.briefInfo}</textarea>
				</div>
			</div>
			
			<%-- <div class="form-group">
				<label class="col-md-2 control-label" for="recruitmentNumber">招聘人数：</label>
				<div class="col-md-4" >
					<input type="text" id="recruitmentNumber" name="recruitmentNumber" class="form-control" value="${recruitment.recruitmentNumber}" placeholder="请输入招聘人数" >
				</div>
			</div> --%>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="mobile" >手机号：</label>
				<div class="col-md-4" >
					<input type="text" id="mobile" name="mobile" class="form-control" value="${recruitment.mobile}" placeholder="请输入发布人手机号" >
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="mobile" >QQ：</label>
				<div class="col-md-4" >
					<input type="text" id="QQ" name="QQ" class="form-control" value="${recruitment.QQ}" placeholder="请输入发布人QQ号" >
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="weiXin" >微信：</label>
				<div class="col-md-4" >
					<input type="text" id="weiXin" name="weiXin" class="form-control" value="${recruitment.weiXin}" placeholder="请输入发布人微信号" >
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="workType">工种：</label>
				<div class="col-md-4" >
					<input type="text" id="workType" name="workType" class="form-control" value="${recruitment.workType}" placeholder="工种" >
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="salaryLow">薪资范围：</label>
				<div class="col-md-2" >
					<input type="text" class ="form-control" id="salaryLow" name="salaryLow" value="${recruitment.salaryLow}" laceholder="最低薪资" >
				</div>
				<div class="col-md-2" >
					<input type="text" class ="form-control" id="salaryHigh" name="salaryHigh" value="${recruitment.salaryHigh}" placeholder="最高薪资" >
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="type">发布类型：</label>
				<div class="col-md-4" >
					<select id="type" name="type" class="form-control">
						<option value="1" <c:if test="${recruitment.publishType!=null and recruitment.publishType==1}">selected</c:if>>普招</option>
						<option value="2" <c:if test="${recruitment.publishType!=null and recruitment.publishType==2}">selected</c:if>>直费</option>
						<option value="3" <c:if test="${recruitment.publishType!=null and recruitment.publishType==3}">selected</c:if>>兼职</option>
					</select>
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="type">发布单类型：</label>
				<div class="col-md-4" >
					<select id="type" name="type" class="form-control">
						<option value="1" <c:if test="${recruitment.type!=null and recruitment.type==1}">selected</c:if>>热招</option>
						<option value="2" <c:if test="${recruitment.type!=null and recruitment.type==2}">selected</c:if>>高返费</option>
						<option value="3" <c:if test="${recruitment.type!=null and recruitment.type==3}">selected</c:if>>工作轻松</option>
						<option value="4" <c:if test="${recruitment.type!=null and recruitment.type==4}">selected</c:if>>高工资</option>
					</select>
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="cashback">入职期满金额：</label>
				<div class="col-md-4" >
					<input class ="form-control" id="cashback" name="cashback" value="${recruitment.cashback}" placeholder="返现金额" >
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="requirement">岗位职责：</label>
				<div class="col-md-4" >
					<textarea class ="form-control" id="requirement" name="requirement" value="${recruitment.jobBriefs}" ></textarea>
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="requirement">入职条件：</label>
				<div class="col-md-4" >
					<textarea class ="form-control" id="requirement" name="requirement" value="${recruitment.requirement}" ></textarea>
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="requirement">补贴详情：</label>
				<div class="col-md-4" >
					<textarea class ="form-control" id="requirement" name="requirement" value="${recruitment.cashbackBriefs}" ></textarea>
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="cashbackDays">期满天数：</label>
				<div class="col-md-4" >
					<input class ="form-control" id="cashbackDays" name="cashbackDays" value="${recruitment.cashbackDays}" placeholder="期满天数" >
				</div>
			</div>
			
			<div class="">
				<label class="col-md-4 control-label"></label>
				<div class="col-md-4">
					<button type="submit" class="btn btn-primary">
						发布
					</button>
				</div>
			</div>
		</form>
	</div>
</body>
</html>