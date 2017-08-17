<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>配置信息</title>
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
	function saveFeeConfig(type){
		var title = null;
		var fee = null;
		var id = null;
		if(type==1){
			title=$("#cashbackTitle").val();
			fee=$("#cashbackFee").val();
			id=$("#cashbackId").val();
		}else if(type==2){
			title=$("#redPacketsTitle").val();
			fee=$("#redPacketsFee").val();
			id=$("#redPacketsId").val();
		}else if(type==3){
			title=$("#recommendTitle").val();
			fee=$("#recommendFee").val();
			id=$("#recommendId").val();
		}
		
		$.ajax({
			url:"${ctx}/feeConfig/saveFeeConfig",
			type:"post",
			dataType:"JSON",
			data:{
				title:title,
				fee:fee,
				id:id
			},
			success:function(response){
				if(response.result=="success"){
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
					添加配置信息
				</h4>
			</div>
		</div>
		<div class="row">
			<input type="hidden" id="cashbackId" value="${cashback.id}">
			<div class="form-group">
				<label class="col-md-2 control-label" for="title">提现金额：</label>
				<div class="col-md-2" >
					<input type="text" id="cashbackTitle" class="form-control" placeholder="请输入标题" value="${cashback.title}">
				</div>
				<div class="col-md-2" >
					<input type="text" id="cashbackFee" class="form-control" placeholder="请输入金额" value="${cashback.fee}">
				</div>
					
				<div class="col-md-2">
					<button type="submit" class="btn btn-primary" onclick="saveFeeConfig(1)">
						保存
					</button>
				</div>
			</div>
		</div>		
		
		<div class="row">
			<input type="hidden" id="redPacketsId" value="${redPackets.id}">
			<div class="form-group">
				<label class="col-md-2 control-label" for="title">红包金额：</label>
				<div class="col-md-2" >
					<input type="text" id="redPacketsTitle" class="form-control" placeholder="请输入标题" value="${redPackets.title}">
				</div>
				<div class="col-md-2" >
					<input type="text" id="redPacketsFee" class="form-control" placeholder="请输入金额" value="${redPackets.fee}">
				</div>
				<div class="col-md-2">
					<button type="button" class="btn btn-primary" onclick="saveFeeConfig(2)">
						保存
					</button>
				</div>
			</div>
		</div>
		
		<div class="row">
			<input type="hidden" id="recommendId" value="${recommendFee.id}">
			<div class="form-group">
				<label class="col-md-2 control-label" for="recommendTitle">推荐金额：</label>
				<div class="col-md-2" >
					<input type="text" id="recommendTitle" class="form-control" placeholder="请输入标题" value="${recommendFee.title}">
				</div>
				<div class="col-md-2" >
					<input type="text" id="recommendFee" class="form-control" placeholder="请输入金额" value="${recommendFee.fee}">
				</div>
				<div class="col-md-2">
					<button type="button" class="btn btn-primary" onclick="saveFeeConfig(3)">
						保存
					</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>