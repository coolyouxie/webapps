<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8"/>
	<title>门店信息管理</title>
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
	<script src="${ctx}/js/common/common.js"></script>

	<script type="text/javascript">
        var dataGrid = null;
        CONTEXT_PATH = '${ctx}';
        jQuery(document).ready(function () {

            dataGrid = jQuery("#list").jqGrid({
                url: "${ctx}/agency/loadAgencyList",
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
                colNames: ['门店名称', '联系人', '联系方式', '所属区域', '操作'],
                colModel: [{
                    label: 'name',
                    name: 'name',
                    align: 'center',
                    sortable: false,
                    formatter: function (cellvalue, options, rowObject) {
                        return '<a href="${ctx}/agency/getById?id=' + rowObject.id + '" style="color:blue">' + cellvalue + '</a>';
                    }
                }, {
                    label: 'contactName',
                    name: 'contactName',
                    align: 'center',
                    sortable: false
                }, {
                    label: 'contactMobile',
                    name: 'contactMobile',
                    align: 'center',
                    sortable: false
                }, {
                    label: 'pcaStr',
                    name: 'pcaStr',
                    align: 'center',
                    sortable: false,
	                formatter:function(cellValue,option,rowObject){
                        return rowObject.provinceName+rowObject.cityName+rowObject.areaName;
	                }
                }, {
                    label: 'operate',
                    name: 'operate',
                    align: 'center',
                    sortable: false
                }],
                pager: '#pager',
                rowNum: 15,
                rowList: [15, 30, 50],
                sortname: 'id',
                viewrecords: true,
                sortorder: "desc",
                caption: "门店列表",
                gridComplete: function () { //在此事件中循环为每一行添加日志、废保和查看链接
                    var ids = jQuery("#list").jqGrid('getDataIDs');
                    for (var i = 0; i < ids.length; i++) {
                        var id = ids[i];
                        var rowData = $('#list').jqGrid('getRowData', id);
                        operateClick = '<a href="${ctx}/agency/toEditAgencyPage?id=' + id + '" style="color:blue">编辑</a> ' +
	                        '<a href="#" style="color:blue" onclick="confirmDel(' + id + ')" >删除</a>';
                        jQuery("#list").jqGrid('setRowData', id, {
                            operate: operateClick
                        });
                    }
                }
            });
        });

        function search() {
            dataGrid.jqGrid("setGridParam", {
                url: "${ctx}/agency/loadAgencyList?" + encodeURI($("#searchForm").serialize())
            }).trigger("reloadGrid");
        }

        function confirmDel(id) {
            var flag = confirm("确认要删除记录吗？");
            if(flag){
                deleteById(id);
            }else{
                return;
            }
        }

        function deleteById(id) {
            $.ajax({
                url: "${ctx}/agency/deleteAgencyById",
                type: "POST",
                dataType: "JSON",
                data: {
                    "id": id
                },
                success: function (response) {
                    alert("删除成功");
                    dataGrid.trigger("reloadGrid");
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
				<th></th>
				<th></th>
				<th></th>
				<th></th>
				<th></th>
				<th></th>
				<th></th>
			</tr>
			<tr>
				<td colspan="3">门店名称门店名称/联系人/联系方式:</td>
				<td><input type="text" id="keywords" name="keywords" value=""></td>
				<td><a type='button' class="btn btn-primary btn-sm" href="${ctx}/agency/toAgencyInfoPage?type=add">
					新增
				</a></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>所属区域:&nbsp;&nbsp;</td>
				<td>
					<select id="province" name="provinceId" onchange="loadDistrict('${ctx}','city')">
						<option value="-1">-请选择-</option>
						<c:forEach var="item" items="${provinces}">
							<option value="${item.id}">${item.name}</option>
						</c:forEach>
					</select>
					&nbsp;&nbsp;
				</td>
				<td>
					<select id="city" name="cityId" onchange="loadDistrict('${ctx}','area')">
						<option value="-1">-请选择-</option>
					</select>
					&nbsp;&nbsp;
				</td>
				<td>
					<select id="area" name="areaId">
						<option value="-1">-请选择-</option>
					</select>
					&nbsp;&nbsp;
				</td>
				<td>
					<button type='button' class="btn btn-primary btn-sm" data-toggle="modal" onclick="search()">
						查询
					</button>
				</td>
				<td></td>
				<td></td>
			</tr>
		</table>
	</form>
</div>
<table id="list"></table>
<div id="pager"></div>
</body>
</html>