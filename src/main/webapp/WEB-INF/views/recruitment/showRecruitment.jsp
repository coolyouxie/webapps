<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>发布单信息</title>
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
	
</script>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-4 col-md-offset-2">
				<h4>
					发布单详情
				</h4>
			</div>
		</div>
		<form id="saveForm" class="form-horizontal" action="${ctx}/recruitment/saveRecruitment"  method="post">
			<input type="hidden" id="handleType" name="handleType" value="add">
			<input type="hidden" id="companyId" name="companyId" value="${recruitment.company.id}" >
			<div class="form-group">
				<label class="col-md-2 control-label" for="">公司名称：</label>
				<div class="col-md-4" >
					${recruitment.company.name}
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="title">标题：</label>
				<div class="col-md-4" >
					${recruitment.title }
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="briefInfo">信息简介：</label>
				<div class="col-md-4" >
					${recruitment.briefInfo }
				</div>
			</div>
			
			<!-- <div class="form-group">
				<label class="col-md-2 control-label" for="endDate">结束日期：</label>
				<div class="col-md-4" >
					<input type="text" id="endDateStr" name="endDateStr" class="form_datetime" readonly>
				</div>
			</div> -->
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="recruitmentNumber">招聘人数：</label>
				<div class="col-md-4" >
					${recruitment.recruitmentNumber }
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="commision" >推荐佣金：</label>
				<div class="col-md-4" >
					${recruitment.commision }
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="workType">工种：</label>
				<div class="col-md-4" >
					${recruitment.workType }
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="workAddress">工作地址：</label>
				<div class="col-md-4" >
					${recruitment.workAddress }
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="salaryLow">最低薪资：</label>
				<div class="col-md-4" >
					${recruitment.salaryLow }
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="salaryHigh">最高薪资：</label>
				<div class="col-md-4" >
					${recruitment.salaryHigh }
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="requirement">入职条件：</label>
				<div class="col-md-4" >
					${recruitment.requirement }
				</div>
			</div>
			
			<!-- <div class="form-group">
				<label class="col-md-2 control-label" for="publishTime">发布时间：</label>
				<div class="col-md-4" >
					<input type="text" id="publishTimeStr" name="publishTimeStr" class="form_datetime" placeholder="发布时间" required readonly>
				</div>
			</div> -->
			
			<%-- <div class="form-group">
				<label class="col-md-2 control-label" for="userId">发布人：</label>
				<div class="col-md-4" >
					${recruitment.userId }
				</div>
			</div> --%>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="type">发布单类型：</label>
				<div class="col-md-4" >
					${recruitment.type }
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="cashback">返现金额：</label>
				<div class="col-md-4" >
					${recruitment.cashback }
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="cashbackDays">期满天数：</label>
				<div class="col-md-4" >
					${recruitment.cashbackDays }
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="cashbackForBroker">推荐人佣金：</label>
				<div class="col-md-4" >
					${recruitment.cashbackForBroker }
				</div>
			</div>
			
		</form>
	</div>
</body>
</html>