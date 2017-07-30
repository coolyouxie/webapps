<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	$(function(){
		if("${result}"){
			alert("${result}");
		}
	});
	function saveCompany(){
		$.ajax({
			url:"${pageContext.request.contextPath}/company/saveCompany",
			type:"POST",
			dataType:"JSON",
			data:$("#saveForm").serialize(),
			success:function(response){
				alert(response);
			}
		})
	}
	
</script>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-3 col-md-offset-2">
				<h4>
					添加公司信息
				</h4>
			</div>
		</div>
		<form id="saveForm" class="form-horizontal" action="${pageContext.request.contextPath}/company/saveCompany"  method="post">
			<div class="form-group">
				<label class="col-md-2 control-label" for="name">公司名称：</label>
				<div class="col-md-3" >
					<input type="text" id="name" name="name" class="form-control" placeholder="请输入姓名" >
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="telephone">联系电话：</label>
				<div class="col-md-3" >
					<input type="text" id="telephone" name="telephone" class="form-control" placeholder="请输入联系电话" >
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="mobile">手机号：</label>
				<div class="col-md-3" >
					<input type="mobile" id="mobile" name="mobile" class="form-control" placeholder="请输入手机号" >
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="email">email：</label>
				<div class="col-md-3" >
					<input type="email" id="email" name="email" class="form-control" placeholder="请输入email" >
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="enterpriseLegalPerson" >法人代表：</label>
				<div class="col-md-3" >
					<input type="text" id="enterpriseLegalPerson" name="enterpriseLegalPerson" class="form-control" placeholder="请输入法人代表">
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="address">地址：</label>
				<div class="col-md-3" >
					<input type="text" id="address" name="address" class="form-control" placeholder="请输入公司地址" >
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-3 control-label"></label>
				<div class="col-md-3">
					<button type="submit" class="btn btn-primary">
						保存
					</button>
				</div>
			</div>
		</form>
	</div>
</body>
</html>