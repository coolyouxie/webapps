<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<script>

</script>
<html lang="zh-CN">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>管理系统</title>
	<!--Basic Styles-->
	<!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
	<link rel="stylesheet" href="${ctx}/js/common/bootstrap/bootstrap-3.3.7/dist/css/bootstrap.min.css"
	      type="text/css"/>
	<link rel="stylesheet" href="${ctx}/css/dashboard.css"/>
</head>
<body>
<input type="hidden" id="basePath" value="${ctx}">
<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
			        aria-expanded="false" aria-controls="navbar">
				<span class="sr-only">Toggle navigation</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">后台管理</a>
		</div>
		<div id="navbar" class="navbar-collapse collapse">
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
					   aria-expanded="false">${user.name} <span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="${ctx}/user/toResetPasswordPage?id=${user.id}" target="mainContainer">重置密码</a></li>
						<li><a href="${ctx}/login/logout?userId=${user.id}">退出系统</a></li>
					</ul>
				</li>
			</ul>
		</div>
	</div>
</nav>

<div class="container-fluid">
	<div class="row">
		<div class="col-sm-3 col-md-2 sidebar">
			<ul class="nav nav-sidebar">
				<c:if test="${!empty perMap['RETT_MU_COMPANY']}">
					<li><a href="${ctx}/company/toCompanyListPage" target="mainContainer">公司管理</a></li>
				</c:if>
				<c:if test="${!empty perMap['RETT_MU_USER']}">
					<li><a href="${ctx}/user/toUserListPage" target="mainContainer">用户管理</a></li>
				</c:if>
				<c:if test="${!empty perMap['RETT_MU_RECRUITMENT']}">
					<li><a href="${ctx}/recruitment/toRecruitmentPage" target="mainContainer">发布单管理</a></li>
				</c:if>
				<c:if test="${!empty perMap['RETT_MU_CONFIG']}">
					<li><a href="${ctx}/paramConfig/toParamConfigPage" target="mainContainer">配置管理</a></li>
				</c:if>
				<c:if test="${!empty perMap['RETT_MU_BANNER']}">
					<li><a href="${ctx}/bannerConfig/toBannerConfigPage" target="mainContainer">Banner管理</a></li>
				</c:if>
				<c:if test="${!empty perMap['RETT_MU_MESSAGE']}">
					<li><a href="${ctx}/messageConfig/toMessageConfigPage" target="mainContainer">消息管理</a></li>
				</c:if>
				<c:if test="${!empty perMap['RETT_MU_MENU']}">
					<li><a href="${ctx}/permission/toMenuPermissionListPage" target="mainContainer">菜单权限管理</a></li>
				</c:if>
				<c:if test="${!empty perMap['RETT_MU_OPERATE']}">
					<li><a href="${ctx}/permission/toOperatePermissionListPage" target="mainContainer">操作权限管理</a></li>
				</c:if>
				<c:if test="${!empty perMap['RETT_MU_RECRUIT_STATISTICS']}">
					<li><a href="${ctx}/statistics/toRateListPage" target="mainContainer">招聘统计</a></li>
				</c:if>
				<c:if test="${!empty perMap['RETT_MU_RECRUIT_PROCESS']}">
					<li><a href="${ctx}/recruitProcess/toRecruitProcessListPage" target="mainContainer">招聘进度</a></li>
				</c:if>
			</ul>
			<ul class="nav nav-sidebar">
				<c:if test="${!empty perMap['RETT_MU_ENROLLMENT']}">
					<li><a href="${ctx}/enrollment/toEnrollmentListPage" target="mainContainer">报名列表</a></li>
				</c:if>
				<c:if test="${!empty perMap['RETT_MU_GROUP']}">
					<li><a href="${ctx}/groupUser/toGroupUserListPage" target="mainContainer">团队用户列表</a></li>
				</c:if>
				<c:if test="${!empty perMap['RETT_MU_RECOMMEND']}">
					<li><a href="${ctx}/recommend/toRecommendListPage" target="mainContainer">推荐列表</a></li>
				</c:if>
				<c:if test="${!empty perMap['RETT_MU_ENTRYAPPROVE']}">
					<li><a href="${ctx}/entryApproval/toEntryApprovalListPage" target="mainContainer">入职审核列表</a></li>
				</c:if>
				<c:if test="${!empty perMap['RETT_MU_EXPIREAPPROVE']}">
					<li><a href="${ctx}/expireApproval/toExpireApprovalListPage" target="mainContainer">期满审核列表</a></li>
				</c:if>
				<c:if test="${!empty perMap['RETT_MU_EXPENDITURE']}">
					<li><a href="${ctx}/applyExpenditure/toExpenditureApproveListPage" target="mainContainer">提现审核列表</a>
					</li>
				</c:if>
			</ul>
			<ul class="nav nav-sidebar">
				<c:if test="${!empty perMap['RETT_MU_SHARE']}">
					<li><a href="${ctx}/appServer/toSharePage">分享页面</a></li>
				</c:if>
				<c:if test="${!empty perMap['RETT_MU_SHARE']}">
					<li><a href="${ctx}/awardConfig/toAwardConfigPage" target="mainContainer">奖品配置</a></li>
				</c:if>
				<c:if test="${!empty perMap['RETT_MU_PRIZEDRAW']}">
					<li><a href="${ctx}/appServer/toPrizeDraw?params={userId:19}">抽奖页面</a></li>
				</c:if>
			</ul>
		</div>
		<div class="col-sm-10 col-md-offset-2 main" style="padding: 20px 0 0 0;">
			<iframe name="mainContainer" width="100%" height="540px" style="margin-bottom:20px;" frameborder="0"
			        scrolling="auto">
			</iframe>
		</div>
	</div>
</div>
<script src="${ctx}/js/jquery/jQuery-1.12.4.0.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="${ctx}/js/common/bootstrap/bootstrap-3.3.7/dist/js/bootstrap.min.js"></script>
</body>
</html>