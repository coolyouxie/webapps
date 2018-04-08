<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>新增门店信息</title>
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
	<script src="${ctx}/js/common/common.js"></script>

	<style>
		col-md-2, span {
			display: -moz-inline-box;
			display: inline-block;
			width: 80px;
		}
	</style>

	<script type="text/javascript">
        function save() {
            var name = $.trim($("#name").val());
            if (!name) {
                alert("请输入门店名称");
                return;
            }
            var contactName = $.trim($("#contactName").val());
            if (!contactName) {
                alert("请输入联系人");
                return;
            }
            var contactMobile = $.trim($("#contactMobile").val());
            if (!contactMobile) {
                alert("请输入联系人手机号");
                return;
            }
            var provinceName = $("#province").find("option:selected").text();
            if (!provinceName || provinceName == '-请选择-') {
                alert("请选择省份");
                return;
            }
            $("#provinceName").val(provinceName);
            var cityName = $("#city").find("option:selected").text();
            if (!cityName || cityName == '-请选择-') {
                alert("请选择城市");
                return;
            }
            $("#cityName").val(cityName);
            var areaName = $("#area").find("option:selected").text();
            if (!areaName || areaName == '-请选择-') {
                alert("请选择区域");
                return;
            }
            $("#areaName").val(areaName);
            var address = $.trim($("#address").val());
            if (!address) {
                alert("请填写详细地址");
                return;
            }
            // $("#saveForm").submit();
            $.ajax({
                url: '${ctx}/agency/saveAgency',
                type: "POST",
                dataType: "JSON",
                data: {
                    name: name,
                    contactName: contactName,
                    contactMobile: contactMobile,
                    provinceId: $("#province").val(),
                    provinceName: provinceName,
                    cityId: $("#city").val(),
                    cityName: cityName,
                    areaId: $("#area").val(),
                    areaName: areaName,
                    address: address
                },
                success: function (response) {
                    if (response && response.result == "S") {
                        alert("添加成功");
                        window.location.href = "${ctx}/agency/toAgencyListPage";
                    } else {
                        alert("门让信息保存失败");
                        return;
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
				添加门店信息
			</h4>
		</div>
	</div>
	<form id="saveForm" class="form-horizontal" action="${ctx}/agency/saveAgency" method="post">
		<input type="hidden" id="handleType" name="handleType" value="add">
		<input type="hidden" id="provinceName" name="provinceName" value="">
		<input type="hidden" id="cityName" name="cityName" value="">
		<input type="hidden" id="areaName" name="areaName" value="">
		<div class="form-group">
			<label class="col-md-2 control-label" for="name">门店名称：</label>
			<div class="col-md-4">
				<input type="text" id="name" name="name" class="form-control" placeholder="请输入门店名称">
			</div>
		</div>

		<div class="form-group">
			<label class="col-md-2 control-label" for="contactName">联系人：</label>
			<div class="col-md-4">
				<input type="text" id="contactName" name="contactName" class="form-control" placeholder="请输入联系人姓名">
			</div>
		</div>

		<div class="form-group">
			<label class="col-md-2 control-label" for="contactMobile">联系人电话：</label>
			<div class="col-md-4">
				<input type="text" id="contactMobile" name="contactMobile" class="form-control" placeholder="请输入联系人电话">
			</div>
		</div>

		<div class="form-group">
			<label class="col-md-2 control-label" for="address">所属区域：</label>
			<div class="col-md-4">
				<select id="province" name="provinceId" onchange="loadDistrict('${ctx}','city')">
					<option>-请选择-</option>
					<c:forEach var="item" items="${provinces}">
						<option value="${item.id}">${item.name}</option>
					</c:forEach>
				</select>
				<select id="city" name="cityId" onchange="loadDistrict('${ctx}','area')">
					<option value="-1">-请选择-</option>
				</select>
				<select id="area" name="areaId">
					<option value="-1">-请选择-</option>
				</select>
				<input type="text" id="address" name="address" class="form-control" placeholder="请输入详细地址">
			</div>
		</div>

		<div class="form-group">
			<label class="col-md-4 control-label"></label>
			<div class="col-md-4">
				<a href="javascript:void(0)" onclick="save()" class="btn btn-primary">
					保存
				</a>
			</div>
		</div>
	</form>
</div>
</body>
</html>