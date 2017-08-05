<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增或修改用户信息</title>
<!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

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
					查看公司信息
				</h4>
			</div>
			<form id="addRecruitment" action="${ctx}/recruitment/toAddRecruitmentPage" method="post">
				<input type="hidden" name="companyId" value="${company.id}">
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
</body>
</html>