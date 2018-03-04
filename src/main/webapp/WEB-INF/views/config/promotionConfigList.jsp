<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8"/>
	<title>活动配置管理</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<link rel="stylesheet" href="${ctx}/js/common/jquery/jquery-ui-1.12.1/jquery-ui.css" type="text/css"/>
	<link rel="stylesheet" href="${ctx}/js/common/jquery/jqGrid/css/ui.jqgrid.css" type="text/css"/>
	<!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
	<link rel="stylesheet" href="${ctx}/js/common/bootstrap/bootstrap-3.3.7/dist/css/bootstrap.min.css"
	      type="text/css"/>
	<link rel="stylesheet" href="${ctx}/js/common/jquery/My97DatePicker/skin/WdatePicker.css" type="text/css"/>
	<script src="${ctx}/js/jquery/jQuery-1.12.4.0.js"></script>
	<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
	<script src="${ctx}/js/common/bootstrap/bootstrap-3.3.7/dist/js/bootstrap.min.js"></script>
	<script src="${ctx}/js/common/jquery/jqGrid/js/grid.base.js"></script>
	<script src="${ctx}/js/common/jquery/jqGrid/js/grid.common.js"></script>
	<script src="${ctx}/js/common/jquery/jqGrid/js/i18n/grid.locale-cn.js"></script>
	<script src="${ctx}/js/common/jquery/jqGrid/js/jquery.jqGrid.js"></script>
	<!-- 日期时间控件 -->
	<script src="${ctx}/js/common/jquery/My97DatePicker/WdatePicker.js"></script>
	<script src="${ctx}/js/common/jquery/My97DatePicker/lang/zh-cn.js"></script>

	<script type="text/javascript">
        var dataGrid = null;
        $(document).ready(function () {
            dataGrid = $("#list").jqGrid({
                url: "${ctx}/promotionConfig/loadPromotionConfigList",
                datatype: "json",
                mtype: "POST",
                height: "auto",
                width: "auto",
                rownumbers: true,
                jsonReader: {
                    root: "resultList", // json中代表实际模型数据的入口
                    page: "page", // json中代表当前页码的数据
                    records: "records", // json中代表数据行总数的数据
                    total: 'total', // json中代表页码总数的数据
                    repeatitems: false // 如果设为false，则jqGrid在解析json时，会根据name来搜索对应的数据元素（即可以json中元素可以不按顺序）；而所使用的name是来自于colModel中的name设定。
                },
                colNames: ['活动名称', '活动内容','生效日期','失效日期','状态','备注', '操作'],
                colModel: [{
                    label: 'name',
                    name: 'name',
                    align: 'center',
                    sortable: false
                }, {
                    label: 'brief',
                    name: 'brief',
                    align: 'center',
                    sortable: false
                }, {
                    label: 'effectiveDateStr',
                    name: 'effectiveDateStr',
                    align: 'center',
                    sortable: false
                }, {
                    label: 'expiryDateStr',
                    name: 'expiryDateStr',
                    align: 'center',
                    sortable: false
                }, {
                    label: 'status',
                    name: 'status',
                    align: 'center',
                    sortable: false,
                    formatter:function (cellValue,options,rowObject) {
                        if(cellValue==1){
                            return "生效";
                        }else{
                            return "失效";
                        }
                    }
                }, {
                    label: 'remark',
                    name: 'remark',
                    align: 'center',
                    sortable: false
                }, {
                    label: 'operate',
                    name: 'operate',
                    align: 'center',
                    sortable: false,
                    formatter: function (cellValue, options, rowObject) {
                        var editInfo = "<a class='btn btn-sm' href='${ctx}/promotionConfig/getById?id='"+rowObject.id+"'  style='color:blue'>编辑</a>";
                        var editStatus = null;
                        if(rowObject.status==1){
                            editStatus = "&nbsp;&nbsp;<a class='btn btn-sm' onclick='editStatus("+rowObject.id+",2)'  style='color:blue'>失效</a>";
                        }else{
                            editStatus = "&nbsp;&nbsp;<a class='btn btn-sm' onclick='editStatus("+rowObject.id+",1)'  style='color:blue'>生效</a>";
                        }
                        return editInfo+editStatus;
                    }
                }],
                pager: '#pager',
                rowNum: 10,
                rowList: [10, 20, 40],
                sortname: 'id',
                viewrecords: true,
                sortorder: "desc",
                caption: "奖品配置列表"
            });
        });

        function search() {
            dataGrid.jqGrid("setGridParam", {
                url: "${ctx}/promotionConfig/loadPromotionConfigList?" + encodeURI($("#searchForm").serialize())
            }).trigger("reloadGrid");
        }

		function editStatus(id,status) {
            $.ajax({
                url: "${ctx}/promotionConfig/updateStatusById",
                type: "POST",
                dataType: "JSON",
                data: {
                    "id": id,
	                "status":status
                },
                success: function (response) {
                    if(response.result=="S"){
                        alert("删除成功");
                        dataGrid.trigger("reloadGrid");
                    }else{
                        alert(response.errorMsg);
                    }
                }
            });
		}

        function deleteById(id) {
            $.ajax({
                url: "${ctx}/promotionConfig/deleteById",
                type: "POST",
                dataType: "JSON",
                data: {
                    "id": id
                },
                success: function (response) {
                    if(response.result=="S"){
                        alert("删除成功");
                        dataGrid.trigger("reloadGrid");
                    }else{
                        alert(response.errorMsg);
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
	<form id="searchForm">
		<table>
			<tr>
				<th width="70px;"></th>
				<th width="200px;"></th>
				<th width="70px;"></th>
				<th width="200px;"></th>
				<th width="70px;"></th>
				<th width="200px;"></th>
				<th width="135px;"></th>
				<th width="50px;"></th>
			</tr>
			<tr>
				<td>活动姓名：</td>
				<td><input type="text" id="name" name="name"></td>
				<td>生效日期：</td>
				<td colspan="3">
					<input type="text" id="effectiveDateStart" name="effectiveDateStart" onClick="WdatePicker({isShowWeek:true})">
					-
					<input type="text" id="effectiveDateEnd" name="effectiveDateEnd" onClick="WdatePicker({isShowWeek:true})">
				</td>
				<td></td>
				<td>
					<a href="${ctx}/promotionConfig/toAddPromotionConfig" type="button" class="btn btn-primary btn-sm" >
						添加
					</a>
				</td>
			</tr>
			<tr>
				<td>状态：</td>
				<td>
					<select id="status" name="status">
						<option value="-1">-请选择-</option>
						<option value="1">生效</option>
						<option value="2">失效</option>
					</select>
				</td>
				<td>失效日期：</td>
				<td colspan="3">
					<input type="text" id="expiryDateStart" name="expiryDateStart" onClick="WdatePicker({isShowWeek:true})">
					-
					<input type="text" id="expiryDateEnd" name="expiryDateEnd" onClick="WdatePicker({isShowWeek:true})">
				</td>
				<td></td>
				<td>
					<button type='button' class="btn btn-primary btn-sm" onclick="search()">
						查询
					</button>
				</td>
			</tr>
		</table>
	</form>
</div>
<table id="list"></table>
<div id="pager"></div>
</body>
</html>