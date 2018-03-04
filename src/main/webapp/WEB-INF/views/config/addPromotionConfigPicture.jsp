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
	<link rel="stylesheet" href="${ctx}/js/common/bootstrap/bootstrap-fileinput-master/css/fileinput.css"
	      type="text/css"/>
	<script src="${ctx}/js/jquery/jQuery-1.12.4.0.js"></script>
	<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
	<script src="${ctx}/js/common/bootstrap/bootstrap-3.3.7/dist/js/bootstrap.min.js"></script>
	<script src="${ctx}/js/common/bootstrap/bootstrap-fileinput-master/js/fileinput.js"></script>
	<script src="${ctx}/js/common/bootstrap/bootstrap-fileinput-master/js/locales/zh.js"></script>

	<style>
		col-md-2, span {
			display: -moz-inline-box;
			display: inline-block;
			width: 80px;
		}
	</style>

	<script type="text/javascript">
        var dataGrid = null;
        function search() {
            var id = $("#id").val();
            dataGrid.jqGrid("setGridParam", {
                postData: {"fkId": $("#id").val()},
                page: 1,
                datatype: 'json'
            }).trigger("reloadGrid");
        }

        $(function () {
            var projectfileoptions = {
                uploadUrl: "${ctx}/picture/uploadPicture",
                language: 'zh',
                maxFileCount: 3,
                maxFileSize: 2000,
                autoReplace: false,
                validateInitialCount: true,
                overwriteInitial: false,
                allowedFileExtensions: ["jpg", "png", "gif"],
                uploadExtraData: {"sourceType": "promotionConfig", "id": $("#id").val()}
            };
            // 文件上传框
            $('input[class=projectfile]').each(function () {
                var imageurl = $(this).attr("value");
                if (imageurl) {
                    var op = $.extend({
                        initialPreview: [ // 预览图片的设置
                            "<img src='" + imageurl + "' class='file-preview-image'>",]
                    }, projectfileoptions);
                    $(this).fileinput(op);
                } else {
                    $(this).fileinput(projectfileoptions).on("fileuploaded", function (event, data, previewId, index) {
                        var response = data.response;
                        if (response && response.result == "S") {
                            $("#newPicUrl").val(response.data);
                        }
                    });
                }
            });

            dataGrid = jQuery("#list").jqGrid({
                url: "${ctx}/picture/loadPromotionPicList",
                datatype: "local",
                mtype: "POST",
                height: 480,
                width: 950,
                jsonReader: {
                    root: "resultList", // json中代表实际模型数据的入口
                    page: "page.page", // json中代表当前页码的数据
                    records: "page.records", // json中代表数据行总数的数据
                    total: 'page.total', // json中代表页码总数的数据
                    repeatitems: false // 如果设为false，则jqGrid在解析json时，会根据name来搜索对应的数据元素（即可以json中元素可以不按顺序）；而所使用的name是来自于colModel中的name设定。
                },
                colNames: ['图片', '操作'],
                colModel: [{
                    label: 'picUrl',
                    name: 'picUrl',
                    align: 'center',
                    sortable: false,
                    formatter: function (cellValue, options, rowObject) {
                        return "<img src='" + cellValue + "' style='width:120px' />";
                    }
                }, {
                    label: 'operate',
                    name: 'operate',
                    align: 'center',
                    sortable: false,
                    formatter: function (cellValue, options, rowObject) {
                        var result = "";
                        result = '<a href="#" style="color:blue" onclick="deleteById(' + rowObject.id + ')">删除</a>';
                        return result;
                    }
                }],
                pager: '#pager',
                rowNum: 10,
                rowList: [10, 20, 50],
                sortname: 'id',
                viewrecords: true,
                sortorder: "desc",
                caption: "图片列表"
            });
            search();
        });

        function next() {
            var url = $("#newPicUrl").val().trim();
	        if (!url) {
		        alert("请先上传图片");
		        return;
	        }
            save();
        }

        function save() {
            $.ajax({
                url: "${ctx}/promotionConfig/addPromotionConfigPicture",
                type: "post",
                dataType: "json",
                data: {
                    fkId: $("#id").val(),
                    picUrl: $("#newPicUrl").val()
                },
                success: function (response) {
                    if (response.result == "S") {
                        alert("图片保存成功");
                        $("#oldPicUrl").val($("#newPicUrl").val());
                        search();
                    } else {
                        alert("信息保存失败，请稍后再试");
                    }
                }
            });
        }

        function deleteById(id) {
            $.ajax({
                url: "${ctx}/picture/deleteById",
                type: "post",
                dataType: "json",
                data: {
                    id: id
                },
                success: function (response) {
                    if (response.result == "S") {
                        alert("删除成功");
                        search();
                    } else {
                        alert("删除失败，请稍后再试");

                    }
                }
            });
        }
	</script>
</head>
<body>
<div class="container-fluid">
	<div class="row">
		<div class="col-md-4 col-md-offset-2">
			<h4>
				添加活动发布图片
			</h4>
		</div>
	</div>

	<form id="picForm" class="form-horizontal required-validate" enctype="multipart/form-data" method="post">
		<input type="hidden" id="id" name="id" value="${id}">
		<div class="form-group">
			<div class="col-md-8 tl th">
				<input type="file" id="image" name="image" class="projectfile" multiple value=""/>
				<p class="help-block">支持jpg、jpeg、png、gif格式，大小不超过2.0M</p>
			</div>
		</div>
	</form>
	<form id="textForm">
		<input type="hidden" id="newPicUrl" value="">
		<input type="hidden" id="oldPicUrl" value="">
		<div class="form-group" style="width:1000px;">
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