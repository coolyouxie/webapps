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

	var projectFileOptions = null;
	var upload = null;
	$(function(){
		projectFileOptions = {
			    uploadUrl: "${ctx}/fileUpload/uploadBannerPic",
			    language:'zh',
			    maxFileCount: 1,
			    maxFileSize:2000,
			    autoReplace:false,
			    validateInitialCount: true,
			    overwriteInitial: false,
			    allowedFileExtensions: ["jpg", "png", "gif"],
			    uploadExtraData:{"sourceType":"banner"}
		};
		upload = $('input[class=projectfile]').each(function() {
		    var imageurl = $(this).attr("value");
		    if (imageurl) {
		        var op = $.extend({
		            initialPreview : [ // 预览图片的设置
		            "<img src='" + imageurl + "' class='file-preview-image'>", ]
		        }, projectfileoptions);
		        $(this).fileinput(op);
		    } else {
		        $(this).fileinput(projectFileOptions).on("fileuploaded", function (event, data, previewId, index){
		        	var response = data.response;
					if(response&&response.result=="S"){
						$("#picUrl").val(response.data);
					}
		        });
		    }
		});
	});

	function next(){
		$.ajax({
			url:"${ctx}/bannerConfig/saveBannerConfig",
			type:"post",
			dataType:"json",
			data:{
				id:$("#id").val(),
				title:$("#title").val(),
				type:$("#type").val(),
				picUrl:$("#picUrl").val()
			},
			success:function(response){
				if(response.result=="S"){
					window.location.href = "${ctx}/bannerConfig/toBannerConfigPage";
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
					修改Banner信息
				</h4>
			</div>
		</div>
		
		<!-- 图片信息 -->
		<form enctype="multipart/form-data">
			<input type="hidden" id="id" value="${bannerConfig.id}">
			<div class="form-group" style="width:1000px;">
				<div id="imgInput" class="row" >
					<div class="col-md-8 tl th">
						<input type="file" id="image" name="image" class="projectfile" multiple value="" />
						<p class="help-block">支持jpg、jpeg、png、gif格式，大小不超过2.0M</p>
					</div>
				</div>
			</div>
		</form>
		
		<form method="post" >
			<input type="hidden" id="picUrl" name="picUrl" >
			<div class="form-group" style="width:1000px;">
				<label>
					<span>当前图片：</span>
					<img src="${bannerConfig.picUrl}" style="width:120px;" />
				</label>
				<br/>
				<label>
					<span>标题：</span>
					<input id="title" name="title" value="${bannerConfig.title}">
				</label>
				<label>
					<select id="type" name="type">
						<option value="1" <c:if test="${bannerConfig.type==1}">selected</c:if>>推荐</option>
						<option value="2" <c:if test="${bannerConfig.type==2}">selected</c:if>>分享</option>
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