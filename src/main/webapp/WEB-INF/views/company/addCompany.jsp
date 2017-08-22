<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增或修改用户信息</title>
<!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="${ctx}/js/common/bootstrap/bootstrap-3.3.7/dist/css/bootstrap.min.css" type="text/css" />
<script src="${ctx}/js/jquery/jQuery-1.12.4.0.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="${ctx}/js/common/bootstrap/bootstrap-3.3.7/dist/js/bootstrap.min.js"></script>

<style>
	 col-md-2, span{
		display:-moz-inline-box;
		display:inline-block;
		width:80px;
	}
</style>

<script type="text/javascript">
	$(function(){
		if("${result}"){
			alert("${result}");
		}
	});
</script>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-4 col-md-offset-2">
				<h4>
					添加公司信息
				</h4>
			</div>
		</div>
		<form id="saveForm" class="form-horizontal" action="${ctx}/company/saveCompany"  method="post">
			<input type="hidden" id="handleType" name="handleType" value="add_next">
			<div class="form-group">
				<label class="col-md-2 control-label" for="name">公司名称：</label>
				<div class="col-md-4" >
					<input type="text" id="name" name="name" class="form-control" placeholder="请输入公司名称" >
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="isBanner">Banner展示：</label>
				<div class="col-md-4" >
					<select id="isBanner" name="isBanner" class="form-control">
						<option value="2" >否</option>
						<option value="1" >是</option>
					</select>
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="type">公司性质：</label>
				<div class="col-md-4" >
					<input type="text" id="type" name="type" class="form-control" placeholder="请输入公司性质">
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="companySize">公司规模：</label>
				<div class="col-md-4" >
					<input type="text" id="companySize" name="companySize" class="form-control" placeholder="请输入公司规模">
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="contactName">联系人：</label>
				<div class="col-md-4" >
					<input type="text" id="contactName" name="contactName" class="form-control" placeholder="请输入联系人" >
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="telephone">联系电话：</label>
				<div class="col-md-4" >
					<input type="text" id="telephone" name="telephone" class="form-control" placeholder="请输入联系电话" >
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="mobile">手机号：</label>
				<div class="col-md-4" >
					<input type="mobile" id="mobile" name="mobile" class="form-control" placeholder="请输入手机号" >
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="email">email：</label>
				<div class="col-md-4" >
					<input type="email" id="email" name="email" class="form-control" placeholder="请输入email" >
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="briefs">公司简介：</label>
				<div class="col-md-4" >
					<textarea class ="form-control" id="briefs" name="briefs" rows="5" ></textarea>
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="environment">公司环境：</label>
				<div class="col-md-4" >
					<textarea class ="form-control" id="environment" name="environment" rows="5" ></textarea>
				</div>
			</div>
			
			<div class="">
				<label class="col-md-4 control-label"></label>
				<div class="col-md-4">
					<button type="submit" class="btn btn-primary">
						下一步
					</button>
				</div>
			</div>
		</form>
	</div>
</body>
</html>