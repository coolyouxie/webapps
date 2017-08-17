<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增或修改用户信息</title>
<!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="${ctx}/js/common/bootstrap/bootstrap-3.3.7/dist/css/bootstrap.min.css" type="text/css" />
<link rel="stylesheet" href="${ctx}/js/common/bootstrap/bootstrap-fileinput-master/css/fileinput.css" type="text/css" />

<script src="${ctx}/js/jquery/jQuery-1.12.4.0.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="${ctx}/js/common/bootstrap/bootstrap-3.3.7/dist/js/bootstrap.min.js"></script>
<script src="${ctx}/js/common/bootstrap/bootstrap-fileinput-master/js/fileinput.js"></script>
<script src="${ctx}/js/common/bootstrap/bootstrap-fileinput-master/js/locales/zh.js"></script>
<style>/webapps
	 col-md-2, span{
		display:-moz-inline-box;
		display:inline-block;
		width:80px;
	}
</style>

<script type="text/javascript">
	$(function(){
		var projectfileoptions = {
			    uploadUrl: "${ctx}/fileUpload/pictureUpload",
			    language:'zh',
			    maxFileCount: 4,
			    maxFileSize:2000,
			    autoReplace:false,
			    validateInitialCount: true,
			    overwriteInitial: false,
			    allowedFileExtensions: ["jpg", "png", "gif"],
			    uploadExtraData:{"sourceType":"company","id":$("#companyId").val()}
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
						window.location.href="${ctx}/company/toCompanyListPage";
					}
		        });;
		    }
		});
	});
	
</script>
</head>
<body>
	<div class="container-fluid" style="height:480px;">
		<div class="row">
			<div class="col-md-3 col-md-offset-2">
				<h4>
					添加公司图片
				</h4>
			</div>
		</div>
		<form class="form-horizontal required-validate" enctype="multipart/form-data" method="post" >
			<input type="hidden" id="companyId" name="companyId" value="${company.id}" >
			<div class="form-group">
				<div class="col-md-1"></div>
		        <div class="col-md-8 tl th">
		            <input type="file" id="image" name="image" class="projectfile" multiple value="" />
		            <p class="help-block">支持jpg、jpeg、png、gif格式，大小不超过2.0M</p>
		        </div>
		    </div>
		</form>
	</div>
</body>
</html>