<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>新增活动信息</title>
	<!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
	<link rel="stylesheet" href="${ctx}/js/common/bootstrap/bootstrap-3.3.7/dist/css/bootstrap.min.css"
	      type="text/css"/>
	<link rel="stylesheet" href="${ctx}/js/common/jquery/My97DatePicker/skin/WdatePicker.css" type="text/css"/>
	<script src="${ctx}/js/jquery/jQuery-1.12.4.0.js"></script>
	<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
	<script src="${ctx}/js/common/bootstrap/bootstrap-3.3.7/dist/js/bootstrap.min.js"></script>
	<!-- 日期时间控件 -->
	<script src="${ctx}/js/common/jquery/My97DatePicker/WdatePicker.js"></script>
	<script src="${ctx}/js/common/jquery/My97DatePicker/lang/zh-cn.js"></script>
	<script src="${ctx}/js/common/common.js"></script>
	<style>
		col-md-2, span {
			display: -moz-inline-box;
			display: inline-block;
			width: 80px;
		}
	</style>

	<script type="text/javascript">
		function update() {
			$.ajax({
				url:"${ctx}/promotionConfig/updatePromotionConfig",
				type:"POST",
				dataType:"JSON",
				data:{
				    "id":$("#id").val(),
					"name":$("#name").val(),
					"brief":$("#brief").val(),
					"effectiveDateStart":$("#effectiveDateStart").val(),
					"expiryDateStart":$("#expiryDateStart").val(),
					"remark":$("#remark").val(),
					"status":$("#status").val()
                },
				success:function (response) {
					if(response){
					    if(response.result="S"){
					        alert("更新成功");
                        }else{
					        alert(response.errorMsg);
                        }
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
				添加活动信息
			</h4>
		</div>
	</div>
	<form id="saveForm" class="form-horizontal" action="${ctx}/promotionConfig/updatePromotionConfig" method="post">
		<input type="hidden" id="id" name="id" value="${config.id}">
		<input type="hidden" id="status" name="status" value="${config.status}">
		<div class="form-group">
			<label class="col-md-2 control-label" for="name">活动名称：</label>
			<div class="col-md-4">
				<input type="text" id="name" name="name" class="form-control" value="${config.name}">
			</div>
		</div>
		<div class="form-group">
			<label class="col-md-2 control-label" for="name">生效日期：</label>
			<div class="col-md-4">
				<input type="text" id="effectiveDateStart" name="effectiveDateStart" value="${config.effectiveDateSimpleStr}" onClick="WdatePicker({isShowWeek:true})">
			</div>
		</div>
		<div class="form-group">
			<label class="col-md-2 control-label" for="name">失效日期：</label>
			<div class="col-md-4">
				<input type="text" id="expiryDateStart" name="expiryDateStart" value="${config.expiryDateSimpleStr}"  onClick="WdatePicker({isShowWeek:true})">
			</div>
		</div>
		<div class="form-group">
			<label class="col-md-2 control-label" for="brief">活动内容：</label>
			<div class="col-md-4">
				<textarea name="brief" id="brief" cols="30" rows="10">${config.brief}</textarea>
			</div>
		</div>
		<div class="form-group">
			<label class="col-md-2 control-label" for="remark">备注：</label>
			<div class="col-md-4">
				<input type="text" id="remark" name="remark" value="${config.remark}" placeholder="请输入备注信息">
			</div>
		</div>
		<div class="">
			<label class="col-md-4 control-label"></label>
			<div class="col-md-4">
				<a class="btn btn-primary" onclick="update()">
					保存
				</a>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<a class="btn btn-primary" href="${ctx}/promotionConfig/toEditPromotionPicture?id=${config.id}">
					下一步
				</a>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<a class="btn btn-primary" onclick="goBack()">
					返回
				</a>
			</div>
		</div>
	</form>
</div>
</body>
</html>