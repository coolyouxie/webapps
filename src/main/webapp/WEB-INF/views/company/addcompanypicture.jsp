<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增或修改用户信息</title>
<!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<link rel="stylesheet" href="${ctx}/js/common/bootstrap/bootstrap-fileinput-master/css/fileinput.css" type="text/css" />

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
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
			    uploadExtraData:{"type":"company","id":$("#companyId").val()}
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
					alert(data.result);
		        });;
		    }
		});
	});
	
	function pageAjaxDone(json) {
	    YUNM.debug(json);
	    YUNM.ajaxDone(json);

	    if (json[YUNM.keys.statusCode] == YUNM.statusCode.ok) {
	        var msg = json[YUNM.keys.message];
	        // 弹出消息提示
	        YUNM.debug(msg);

	        if (YUNM.callbackType.confirmTimeoutForward == json.callbackType) {
	            $.showSuccessTimeout(msg, function() {
	                window.location = json.forwardUrl;
	            });
	        }
	    }
	}
	
	function iframeCallback(form, callback) {
	    YUNM.debug("带文件上传处理");
	    var $form = $(form), $iframe = $("#callbackframe");
	    var data = $form.data('bootstrapValidator');
	    if (data) {
	        if (!data.isValid()) {
	            return false;
	        }
	    }

	    if ($iframe.size() == 0) {
	        $iframe = $("<iframe id='callbackframe' name='callbackframe' src='about:blank' style='display:none'></iframe>").appendTo("body");
	    }
	    if (!form.ajax) {
	        $form.append('<input type="hidden" name="ajax" value="1" />');
	    }
	    form.target = "callbackframe";
	    _iframeResponse($iframe[0], callback || YUNM.ajaxDone);
	}
	
	function _iframeResponse(iframe, callback) {
	    var $iframe = $(iframe), $document = $(document);
	    $document.trigger("ajaxStart");
	    $iframe.bind("load", function(event) {
	        $iframe.unbind("load");
	        $document.trigger("ajaxStop");

	        if (iframe.src == "javascript:'%3Chtml%3E%3C/html%3E';" || // For Safari
	        iframe.src == "javascript:'<html></html>';") { // For FF, IE
	            return;
	        }

	        var doc = iframe.contentDocument || iframe.document;

	        // fixing Opera 9.26,10.00
	        if (doc.readyState && doc.readyState != 'complete')
	            return;
	        // fixing Opera 9.64
	        if (doc.body && doc.body.innerHTML == "false")
	            return;

	        var response;

	        if (doc.XMLDocument) {
	            // response is a xml document Internet Explorer property
	            response = doc.XMLDocument;
	        } else if (doc.body) {
	            try {
	                response = $iframe.contents().find("body").text();
	                response = jQuery.parseJSON(response);
	            } catch (e) { // response is html document or plain text
	                response = doc.body.innerHTML;
	            }
	        } else {
	            // response is a xml document
	            response = doc;
	        }
	        callback(response);
	    });
	}
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
		<form class="form-horizontal required-validate" action="${ctx}/save?callbackType=confirmTimeoutForward" enctype="multipart/form-data" method="post" onsubmit="return iframeCallback(this, pageAjaxDone)">
			<input id="companyId" name="companyId" value="${id}" >
			<div class="form-group">
				<div class="col-md-1"></div>
		        <div class="col-md-8 tl th">
		            <input type="file" id="image" name="image" class="projectfile" multiple value="${deal.image}" />
		            <p class="help-block">支持jpg、jpeg、png、gif格式，大小不超过2.0M</p>
		        </div>
		    </div>
		</form>
	</div>
</body>
</html>