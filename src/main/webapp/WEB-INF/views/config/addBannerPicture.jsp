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
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
<script src="${ctx}/js/common/bootstrap/bootstrap-fileinput-master/js/fileinput.js"></script>
<script src="${ctx}/js/common/bootstrap/bootstrap-fileinput-master/js/locales/zh.js"></script>
</head>
<script type="text/javascript">

var projectFileOptions = null;
var upload = null;
$(function(){
	projectFileOptions = {
		    uploadUrl: "${ctx}/fileUpload/pictureUpload",
		    language:'zh',
		    maxFileCount: 1,
		    maxFileSize:2000,
		    autoReplace:false,
		    validateInitialCount: true,
		    overwriteInitial: false,
		    allowedFileExtensions: ["jpg", "png", "gif"],
		    uploadExtraData:{"id":$("#id").val(),"sourceType":"banner"}
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
				if(response&&response.result=="success"){
					alert("添加成功");
				}
	        });
	    }
	}); 
});
</script>
<body>
	<form enctype="multipart/form-data">
		<input type="hidden" id="id" value="${id}">
		<div class="form-group" style="width:1000px;">
			<div id="imgInput" class="row" >
				<div class="col-md-8 tl th">
					<input type="file" id="image" name="image" class="projectfile" multiple value="" />
					<p class="help-block">支持jpg、jpeg、png、gif格式，大小不超过2.0M</p>
				</div>
			</div>
		</div>
	</form>
</body>
</html>