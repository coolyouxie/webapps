<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增Banner信息</title>
<!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="${ctx}/js/common/bootstrap/bootstrap-3.3.7/dist/css/bootstrap.min.css" type="text/css" />
<link rel="stylesheet" href="${ctx}/js/common/bootstrap/bootstrap-fileinput-master/css/fileinput.css" type="text/css" />
<script src="${ctx}/js/jquery/jQuery-1.12.4.0.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="${ctx}/js/common/bootstrap/bootstrap-3.3.7/dist/js/bootstrap.min.js"></script>
<script src="${ctx}/js/common/bootstrap/bootstrap-fileinput-master/js/fileinput.js"></script>
<script src="${ctx}/js/common/bootstrap/bootstrap-fileinput-master/js/locales/zh.js"></script>
<style>
	 col-md-2, span{
		display:-moz-inline-box;
		display:inline-block;
		width:80px;
	}
</style>

<script type="text/javascript">
	function next(){
		$.ajax({
			url:"${ctx}/messageConfig/saveMessageConfig",
			type:"post",
			dataType:"json",
			data:{
				id:$("#id").val(),
				title:$("#title").val(),
				type:$("#type").val(),
				message:$("#message").val(),
				belongType:$("#belongType").val()
			},
			success:function(response){
				if(response.result=="S"){
					window.location.href = "${ctx}/messageConfig/toMessageConfigPage";
				}else{
					alert("信息保存失败，请稍后再试");
					return ;
				}
			}
		});
	}
	
</script>
</head>
<body>
	<div class="container-fluid" style="height:480px;">
		<div class="row">
			<div class="col-md-3 col-md-offset-2">
				<h4>
					修改消息
				</h4>
			</div>
		</div>
		
		<form method="post" >
			<div class="form-group" style="width:1000px;">
				<input type="hidden" name="belongType" value="${messageConfig.belongType}">
				<label>
					<span>标题：</span>
					<input id="title" name="title" value="${messageConfig.title}">
				</label>
				<label>
					<select id="type" name="type">
						<option value="1" <c:if test="${messageConfig.type==1}">selected</c:if>>推荐</option>
						<option value="2" <c:if test="${messageConfig.type==2}">selected</c:if>>分享</option>
					</select>
				</label>
				<label>
					<button type="button" class="btn btn-primary" onclick="next()">
						保存
					</button>
				</label>
			</div>
		</form>
	</div>
</body>
</html>