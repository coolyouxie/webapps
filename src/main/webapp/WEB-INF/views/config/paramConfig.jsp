<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>费用参数配置信息</title>
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
	function showTypeStyle(selVal,recId){
		if(selVal == 1){
			$("#minNumPart" + recId).hide();
		}else{
			$("#minNumPart" + recId).show();
		}
	}
	
	function saveParamConfig(id){
		$.ajax({
			url:"${ctx}/paramConfig/saveParamConfig",
			type:"post",
			dataType:"JSON",
			data:$("#paramConfig"+id).serialize(),
			success:function(response){
				if(response.result=="S"){
					alert("保存成功");
				}else{
					alert(response.errorMsg);
				}
			}
		})
	}
</script>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-4 col-md-offset-2">
				<h4>
					奖励金额配置信息
				</h4>
			</div>
		</div>
		<c:forEach items="${paramConfigList}" var="paramConfig" varStatus="stat">
			<div class="row">
				<form id="paramConfig${paramConfig.id}" class="form-horizontal">
					<input type="hidden" name="id" value="${paramConfig.id}">
					<input type="hidden" name="name" value="${paramConfig.name}">
					<div class="form-group">
						<label class="col-sm-2 control-label">${paramConfig.name}：</label>
						<div class="col-sm-5">
							<div class="row">
								<div class="col-sm-3">
									<select class="form-control" name="type" onchange="showTypeStyle(this.value,${paramConfig.id});">
										<option value="1" <c:if test="${paramConfig.type==1}">selected="selected"</c:if>>固定金额</option>
										<option value="2" <c:if test="${paramConfig.type==2}">selected="selected"</c:if>>浮动范围</option>
									</select>
								</div>
								<div class="col-sm-4" id="minNumPart${paramConfig.id}"
									<c:if test="${paramConfig.type==1}">
										style="display: none;"
									</c:if>
								>
										<input type="text" id="minNum" name="minNum" class="col-sm-4 form-control" placeholder="金额范围下限" value="${paramConfig.minNum}">
								</div>
								<div class="col-sm-4">
									<input type="text" id="maxNum" name="maxNum" class="col-sm-4 form-control" placeholder="金额范围上限或固定金额" value="${paramConfig.maxNum}">
								</div>
							</div>
						</div>
						<div class="col-md-2">
							<button type="button" class="btn btn-primary" onclick="saveParamConfig(${paramConfig.id});">
								保存
							</button>
						</div>
					</div>
				</form>
			</div>
		</c:forEach>
	</div>
</body>
</html>