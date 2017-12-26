<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8"/>
	<title>用户权限管理</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<link rel="stylesheet" href="${ctx}/js/common/jquery/jquery-ui-1.12.1/jquery-ui.css" type="text/css"/>
	<link rel="stylesheet" href="${ctx}/js/common/jquery/jqGrid/css/ui.jqgrid.css" type="text/css"/>
	<!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
	<link rel="stylesheet" href="${ctx}/js/common/bootstrap/bootstrap-3.3.7/dist/css/bootstrap.min.css"
	      type="text/css"/>
	<script src="${ctx}/js/jquery/jQuery-1.12.4.0.js"></script>
	<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
	<script src="${ctx}/js/common/bootstrap/bootstrap-3.3.7/dist/js/bootstrap.min.js"></script>
	<script src="${ctx}/js/common/jquery/jqGrid/js/grid.base.js"></script>
	<script src="${ctx}/js/common/jquery/jqGrid/js/grid.common.js"></script>
	<script src="${ctx}/js/common/jquery/jqGrid/js/i18n/grid.locale-cn.js"></script>
	<script src="${ctx}/js/common/jquery/jqGrid/js/jquery.jqGrid.js"></script>

	<script type="text/javascript">
        function changeMenuState(menuId){
            var list = $("#tdId_"+menuId).find("input[name=operateId]:checked");
            if(list.length>0){
                //Id,name等标签属于html元素的属性，它们可以用attr()方法修改
	            //checked属于html元素的特性，在jqeury1.6版本以后只能使用prop()方法修改
                $("#menuId_"+menuId).prop('checked',true);
            }
        }

        function changeOperateState(menuId) {
            var isChecked = $('#menuId_'+menuId).prop('checked');
            var list = $("#tdId_"+menuId).find("input[name=operateId]");
            if(isChecked){
                for(var i=0;i<list.length;i++){
                    $(list[i]).prop("checked",true);
                }
            }else{
                for(var i=0;i<list.length;i++){
                    $(list[i]).prop("checked",false);
                }
            }
        }

        function save() {
            var menuId = [];
            $("input[name=menuId]:checked").each(function () {
                menuId.push($(this).val());
            });
            var operateId = [];
            $("input[name=operateId]:checked").each(function(){
                operateId.push($(this).val());
            });
            $.ajax({
                url: "${ctx}/permission/saveUserPermission",
                type: "POST",
                dataType: "JSON",
	            traditional:true,
                data: {
                    "userId":$("#userId").val(),
                    "menuId": menuId,
                    "operateId": operateId
                },
                success: function (response) {
                    if(response.result=="S"){
                        window.location.href = "${ctx}/user/toUserListPage";
                    }else{
                        alert(response.errorMsg);
                        return ;
                    }

                }
            });
        }
	</script>
	<style>
		.input-group-sm label {
			width: 100%;
		}

		.input-group-sm label span {
			width: 30%;
			text-align: right;
			display: inline-block;
		}

		.input-group-sm label input {
			width: 50%;
			display: inline-block;
		}
	</style>
</head>
<body>
<div class="container-fluid">
	<div class="row">
		<div class="col-md-3">
			<h2>
				用户权限管理
			</h2>
		</div>
	</div>
	<form id="addForm">
		<input type="hidden" name="userId" id="userId" value="${userId}">
		<table style="border:solid 1px grey">
			<c:forEach var="menu" items="${permissions}">
				<tr style="border:solid 1px grey">
					<td width="200px;" style="border:solid 1px grey">
						<input type="checkbox" id="menuId_${menu.id}" onchange="changeOperateState(${menu.id})" name="menuId" value="${menu.id}"
						       <c:if test="${menu.checkFlag=='true'}">checked="checked"</c:if> />${menu.name}
					</td>
					<td id="tdId_${menu.id}">
						<c:forEach var="operate" items="${menu.childPermissions}">
							<input type="checkbox" name="operateId" group="menu_${menu.id}" value="${operate.id}" onchange="changeMenuState(${menu.id})"
							       <c:if test="${operate.checkFlag=='true'}">checked="checked"</c:if>>${operate.name}
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</c:forEach>
					</td>
				</tr>
			</c:forEach>
		</table>
		<br/>
		<a class="btn btn-primary btn-sm" onclick="save()">确定</a>
	</form>
</div>
<table id="list"></table>
<div id="pager"></div>
</body>
</html>