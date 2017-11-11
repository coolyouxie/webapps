<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增消息</title>
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
		var belongType = $("#belongType").val().trim();
		if(!belongType){
			belongType = 0;
		}
		$.ajax({
			url:"${ctx}/messageConfig/saveMessageConfig",
			type:"post",
			dataType:"json",
			data:{
				title:$("#title").val(),
				type:$("#type").val(),
				message:$("#message").val(),
				fkId:$("#fkId").val(),
				belongType:belongType
			},
			success:function(response){
				if(response.result=="S"){
					window.location.href = "${ctx}/messageConfig/toMessageConfigPage";
				}else{
					alert("信息保存失败，请稍后再试");

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
					添加消息
				</h4>
			</div>
		</div>
		
		<form method="post" >
			<input type="hidden" id="fkId" value="${fkId}" >
			<input type="hidden" id="belongType" value="${belongType}" >
			<div class="form-group" style="width:1000px;">
				<label>
					<span>标题：</span>
					<input id="title" name="title" >
				</label>
				<label>
					<select id="type" name="type" <c:if test="${type!=null}">disabled="disabled"</c:if>>
						<option value="1">推荐</option>
						<option value="2">分享</option>
						<option value="3" <c:if test="${type==3}">selected</c:if>>详情</option>
						<option value="4">补全信息</option>
					</select>
				</label>
				<br/>
				<label>
					<textarea rows="5" cols="55" id="message"></textarea>
				</label>
				<br/>
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