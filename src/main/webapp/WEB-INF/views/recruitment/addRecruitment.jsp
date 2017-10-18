<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增或修改发布信息</title>
<link rel="stylesheet" href="${ctx}/js/common/bootstrap/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.css" type="text/css" />
<!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="${ctx}/js/common/bootstrap/bootstrap-3.3.7/dist/css/bootstrap.min.css" type="text/css" />
<script src="${ctx}/js/jquery/jQuery-1.12.4.0.js"></script>
<link rel="stylesheet" href="${ctx}/js/common/bootstrap/bootstrap-fileinput-master/css/fileinput.css" type="text/css" />
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="${ctx}/js/common/bootstrap/bootstrap-3.3.7/dist/js/bootstrap.min.js"></script>
<script src="${ctx}/js/common/bootstrap/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script src="${ctx}/js/common/bootstrap/bootstrap-datetimepicker-master/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
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
	$(function(){
		var projectfileoptions = {
				uploadUrl: "${ctx}/picture/uploadPicture",
				language:'zh',
				maxFileCount: 1,
				maxFileSize:2000,
				autoReplace:false,
				validateInitialCount: true,
				overwriteInitial: false,
				allowedFileExtensions: ["jpg", "png", "gif"],
				uploadExtraData:{"sourceType":"banner"}
			};
			// 文件上传框
			$('input[class=projectfile]').each(function() {
			    var imageurl = $(this).attr("value");
			    if (imageurl) {
			        var op = $.extend({
			            initialPreview : [ // 预览图片的设置
			            "<img src='" + imageurl + "' class='file-preview-image'>", ]
			        }, projectfileoptions);
			        $(this).fileinput(op);
			    } else {
			        $(this).fileinput(projectfileoptions).on("fileuploaded", function (event, data, previewId, index){
			        	var response = data.response;
						if(response&&response.result=="S"){
							$("#msgModal").modal("hide");
							$("#picUrl").val(response.data);
						}
					});;
				}
		});
	});
	
	function showModal(){
		$("#msgModal").modal("show");
	}
</script>
</head>
<body>
<form id="picForm" enctype="multipart/form-data" method="post" >
	<div class="modal fade" id="msgModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-md" style="width: 580px;height:300px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
						&times;
					</button>
					<h4 class="modal-title" id="myModalLabel">
						添加图片
					</h4>
				</div>
				<div class="modal-body">
					<input type="file" id="image" name="image" class="projectfile" multiple value="" />
					<p class="help-block">支持jpg、jpeg、png、gif格式，大小不超过2.0M</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>
	</form>

	<div class="container-fluid">
		<div class="row">
			<div class="col-md-4 col-md-offset-2">
				<h4>
					添加发布单
				</h4>
			</div>
		</div>
		<form id="saveForm" class="form-horizontal" action="${ctx}/recruitment/saveRecruitment"  method="post">
			<input type="hidden" id="handleType" name="handleType" value="add">
			<input type="hidden" id="companyId" name="company.id" value="${company.id}" >
			<input type="hidden" id="picUrl" name="bannerConfig.picUrl" value="">
			<div class="form-group">
				<label class="col-md-2 control-label" for="">公司名称：</label>
				<div class="col-md-4" >
					<label class="col-md-8 control-label" >${company.name}</label>
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="title">标题：</label>
				<div class="col-md-4" >
					<input type="text" id="title" name="title" class="form-control" placeholder="标题" required>
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="salaryBriefs">薪酬福利：</label>
				<div class="col-md-4" >
					<textarea id="salaryBriefs" name="salaryBriefs" class="form-control" row="3" ></textarea>
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="briefInfo">其他福利：</label>
				<div class="col-md-4" >
					<textarea id="briefInfo" name="briefInfo" class="form-control" row="3" ></textarea>
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="mobile" >手机号：</label>
				<div class="col-md-4" >
					<input type="text" id="mobile" name="mobile" class="form-control" value="" placeholder="请输入发布人手机号" >
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="mobile" >QQ：</label>
				<div class="col-md-4" >
					<input type="text" id="QQ" name="QQ" class="form-control" value="" placeholder="请输入发布人QQ号" >
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="weiXin" >微信：</label>
				<div class="col-md-4" >
					<input type="text" id="weiXin" name="weiXin" class="form-control" value="" placeholder="请输入发布人微信号" >
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="workType">工种：</label>
				<div class="col-md-4" >
					<input type="text" id="workType" name="workType" class="form-control" value="" placeholder="工种" >
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="salaryLow">薪资范围：</label>
				<div class="col-md-2" >
					<input type="text" class ="form-control" id="salaryLow" name="salaryLow" value="" >
				</div>
				<div class="col-md-2" >
					<input type="text" class ="form-control" id="salaryHigh" name="salaryHigh" value="" >
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="publishType">招工类型：</label>
				<div class="col-md-4" >
					<select id="type" name="publishType" class="form-control">
						<option value="1">普招</option>
						<option value="2">直招</option>
						<option value="3">兼职</option>
					</select>
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="type">发布单类型：</label>
				<div class="col-md-4" >
					<select id="type" name="type" class="form-control">
						<option value="1" >热招</option>
						<option value="2" >高返费</option>
						<option value="3" >工作轻松</option>
						<option value="4" >高工资</option>
					</select>
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="isBanner">Banner展示：</label>
				<div class="col-md-4" >
					<select id="isBanner" name="isBanner" class="form-control">
						<option value="2" >否</option>
						<option value="1" >是</option>
					</select>
					<button type="button" class="btn btn-primary" onclick="showModal()">
						上传Banner图片
					</button>
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="cashback">入职期满金额：</label>
				<div class="col-md-4" >
					<input class ="form-control" id="cashback" name="cashback" value="" placeholder="返现金额" >
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="jobBriefs">岗位职责：</label>
				<div class="col-md-4" >
					<textarea class ="form-control" id="jobBriefs" name="jobBriefs" ></textarea>
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="requirement">入职条件：</label>
				<div class="col-md-4" >
					<textarea class ="form-control" id="requirement" name="requirement" ></textarea>
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="cashbackBriefs">补贴详情：</label>
				<div class="col-md-4" >
					<textarea class ="form-control" id="cashbackBriefs" name="cashbackBriefs" ></textarea>
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label" for="cashbackDays">期满天数：</label>
				<div class="col-md-4" >
					<input class ="form-control" id="cashbackDays" name="cashbackDays" value="" placeholder="期满天数" >
				</div>
			</div>
			
			<div class="">
				<label class="col-md-4 control-label"></label>
				<div class="col-md-4">
					<button type="submit" class="btn btn-primary">
						发布
					</button>
				</div>
			</div>
		</form>
	</div>
</body>
</html>