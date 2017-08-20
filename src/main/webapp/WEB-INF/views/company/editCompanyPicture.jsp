<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑公司图片信息</title>
<!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="${ctx}/js/common/bootstrap/bootstrap-3.3.7/dist/css/bootstrap.min.css" type="text/css" />
<link rel="stylesheet" href="${ctx}/js/common/bootstrap/bootstrap-fileinput-master/css/fileinput.css" type="text/css" />
<link rel="stylesheet" href="${ctx}/js/common/jquery/jquery-ui-1.12.1/jquery-ui.css" type="text/css" />
<link rel="stylesheet" href="${ctx}/js/common/jquery/jqGrid/css/ui.jqgrid.css" type="text/css" />

<script src="${ctx}/js/jquery/jQuery-1.12.4.0.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="${ctx}/js/common/bootstrap/bootstrap-3.3.7/dist/js/bootstrap.min.js"></script>
<script src="${ctx}/js/common/bootstrap/bootstrap-fileinput-master/js/fileinput.js"></script>
<script src="${ctx}/js/common/bootstrap/bootstrap-fileinput-master/js/locales/zh.js"></script>

<script src="${ctx}/js/common/jquery/jqGrid/js/grid.base.js"></script>
<script src="${ctx}/js/common/jquery/jqGrid/js/grid.common.js"></script>
<script src="${ctx}/js/common/jquery/jqGrid/js/i18n/grid.locale-cn.js"></script>
<script src="${ctx}/js/common/jquery/jqGrid/js/jquery.jqGrid.js"></script>


<style>/webapps
	 col-md-2, span{
		display:-moz-inline-box;
		display:inline-block;
		width:80px;
	}
</style>

<script type="text/javascript">
var dataGrid = null;

function search(){
	var params = {};
	var keyWords = $.trim($("#keyWords").val());
	if(keyWords){
		params.keyWords=keyWords;
	}
	dataGrid.jqGrid("setGridParam",{
	    postData:{"keyWords":$("#keyWords").val()},
	    page:1
	}).trigger("reloadGrid");
}
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
						alert("图片上传成功");
						//window.location.href="${ctx}/company/toCompanyListPage";
					}
		        });;
		    }
		});
		
		dataGrid = jQuery("#list").jqGrid({
		    url:"${ctx}/picture/loadCompanyPicList",
		    datatype: "local",
		    mtype : "POST",
		    height: 480,
		    width : 950,
		    jsonReader : {
								root : "resultList", // json中代表实际模型数据的入口
								page : "page.page", // json中代表当前页码的数据   
								records : "page.records", // json中代表数据行总数的数据   
								total : 'page.total', // json中代表页码总数的数据 
								repeatitems : false // 如果设为false，则jqGrid在解析json时，会根据name来搜索对应的数据元素（即可以json中元素可以不按顺序）；而所使用的name是来自于colModel中的name设定。   
							},
		    colNames : [ '标题', '类型','图片','操作'],
		    colModel : [ {
								label : 'title',
								name : 'title',
								align : 'center',
								sortable : false,
								formatter:function(cellvalue,options,rowObject){
									return '<a href="${ctx}/company/getById?id='+rowObject.id+'" style="color:blue">'+cellvalue+'</a>';
								}
							}, {
								label : 'type',
								name : 'type',
								align : 'center',
								sortable : false
							}, {
								label : 'picUrl',
								name : 'picUrl',
								align : 'center',
								sortable : false,
								formatter:function(cellValue,options,rowObject){
									return "<img src='"+cellValue+"' style='width:120px' />";
								}
							}, {
								label : 'operate',
								name : 'operate',
								align : 'center',
								sortable : false,
								formatter:function(cellValue,options,rowObject){
									var result = "";
									result = '<a href="#" style="color:blue" onclick="deleteById('+rowObject.id+')">删除</a>'+
									'<a href="#" style="color:blue" onclick="showModal('+rowObject.id+')">修改类型</a>';
									return result;
								}
							} ],
		    pager: '#pager',
		    rowNum:10,
		    rowList:[10,20,50],
		    sortname: 'id',
		    viewrecords: true,
		    sortorder: "desc",
		    caption: "图片列表"
		});
		search();
	});
	
	function search(){
		var companyId=$("#companyId").val();
		dataGrid.jqGrid("setGridParam",{
		    postData:{"fkId":$("#companyId").val()},
		    page:1,
		    datatype:'json'
		}).trigger("reloadGrid");
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
	<table id="list"></table>
	<div id="pager"></div>
</body>
</html>