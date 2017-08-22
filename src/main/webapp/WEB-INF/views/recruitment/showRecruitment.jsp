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
<link rel="stylesheet" href="${ctx}/js/common/bootstrap/bootstrap-3.3.7/dist/css/bootstrap.min.css" type="text/css" />
<script src="${ctx}/js/jquery/jQuery-1.12.4.0.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="${ctx}/js/common/bootstrap/bootstrap-3.3.7/dist/js/bootstrap.min.js"></script>
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
				<label class="col-md-2 control-label" for="salaryBriefs">薪酬福利：</label>
				<div class="col-md-4" >
					${recruitment.salaryBriefs}
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="briefInfo">其他福利：</label>
				<div class="col-md-4" >
					${recruitment.briefInfo}
				</div>
			</div>
			
			<%-- <div class="form-group">
				<label class="col-md-2 control-label" for="recruitmentNumber">招聘人数：</label>
				<div class="col-md-4" >
					${recruitment.recruitmentNumber}
				</div>
			</div> --%>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="mobile" >手机号：</label>
				<div class="col-md-4" >
					${recruitment.mobile}
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="mobile" >QQ：</label>
				<div class="col-md-4" >
					${recruitment.QQ}
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="weiXin" >微信：</label>
				<div class="col-md-4" >
					${recruitment.weiXin}
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="workType">工种：</label>
				<div class="col-md-4" >
					${recruitment.workType}
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="salaryLow">薪资范围：</label>
				<div class="col-md-2" >
					${recruitment.salaryLow}
				</div>
				<div class="col-md-2" >
					${recruitment.salaryHigh}
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="type">发布类型：</label>
				<div class="col-md-4" >
					<select id="type" name="type" class="form-control" disabled="disabled">
						<option value="1" <c:if test="${recruitment.publishType!=null and recruitment.publishType==1}">selected</c:if>>普招</option>
						<option value="2" <c:if test="${recruitment.publishType!=null and recruitment.publishType==2}">selected</c:if>>直费</option>
						<option value="3" <c:if test="${recruitment.publishType!=null and recruitment.publishType==3}">selected</c:if>>兼职</option>
					</select>
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="type">发布单类型：</label>
				<div class="col-md-4" >
					<select id="type" name="type" class="form-control" disabled="disabled">
						<option value="1" <c:if test="${recruitment.type!=null and recruitment.type==1}">selected</c:if>>热招</option>
						<option value="2" <c:if test="${recruitment.type!=null and recruitment.type==2}">selected</c:if>>高返费</option>
						<option value="3" <c:if test="${recruitment.type!=null and recruitment.type==3}">selected</c:if>>工作轻松</option>
						<option value="4" <c:if test="${recruitment.type!=null and recruitment.type==4}">selected</c:if>>高工资</option>
					</select>
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="isBanner">Banner展示：</label>
				<div class="col-md-4" >
					<select id="isBanner" name="isBanner" class="form-control" disabled="disabled">
						<option value="2" <c:if test="${recruitment.isBanner==2}">selected</c:if>>否</option>
						<option value="1" <c:if test="${recruitment.isBanner==1}">selected</c:if>>是</option>
					</select>
				</div>
			</div>
			
			<c:if test="${recruitment.isBanner==1}">
				<div class="form-group">
					<label class="col-md-2 control-label">Banner图片：</label>
					<div class="col-md-4" >
						<img src="${recruitment.picture.picUrl}" style="width:120px;" />
					</div>
				</div>
			</c:if>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="cashback">入职期满金额：</label>
				<div class="col-md-4" >
					${recruitment.cashback}
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="requirement">岗位职责：</label>
				<div class="col-md-4" >
					${recruitment.jobBriefs}
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="requirement">入职条件：</label>
				<div class="col-md-4" >
					${recruitment.requirement}
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="requirement">补贴详情：</label>
				<div class="col-md-4" >
					${recruitment.cashbackBriefs}
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="cashbackDays">期满天数：</label>
				<div class="col-md-4" >
					${recruitment.cashbackDays}
				</div>
			</div>
			
		</form>
	</div>
</body>
</html>