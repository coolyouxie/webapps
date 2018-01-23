<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8"/>
	<title>报名管理</title>
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
	<script src="${ctx}/js/common/common.js"></script>


	<script type="text/javascript">
        var dataGrid = null;
        $(document).ready(function () {
            dataGrid = $("#list").jqGrid({
                url: "${ctx}/statistics/loadExpireStatisticsList",
                datatype: "json",
                mtype: "POST",
                height: 'auto',
                width: 'auto',
                rownumbers: true,
                jsonReader: {
                    root: "resultList", // json中代表实际模型数据的入口
                    page: "page", // json中代表当前页码的数据
                    records: "records", // json中代表数据行总数的数据
                    total: 'total', // json中代表页码总数的数据
                    repeatitems: false // 如果设为false，则jqGrid在解析json时，会根据name来搜索对应的数据元素（即可以json中元素可以不按顺序）；而所使用的name是来自于colModel中的name设定。
                },
                postData: {
                    'talkerId':$("#talkerId").val(),
                    'type':0
                },
                colNames: ['会员姓名', '联系方式', '报名公司', '发布标题','身份证号', '报名日期','状态'],
                colModel: [{
                    label: 'user.name',
                    name: 'user.name',
                    align: 'center',
                    sortable: false
                }, {
                    label: 'user.mobile',
                    name: 'user.mobile',
                    align: 'center',
                    sortable: false
                }, {
                    label: 'company.name',
                    name: 'company.name',
                    align: 'center',
                    sortable: false
                }, {
                    label: 'recruitment.title',
                    name: 'recruitment.title',
                    align: 'center',
                    sortable: false
                }, {
                    label: 'user.idCardNo',
                    name: 'user.idCardNo',
                    align: 'center',
                    sortable: false
                }, {
                    label: 'createTimeStr',
                    name: 'createTimeStr',
                    align: 'center',
                    sortable: false
                }, {
                    label: 'state',
                    name: 'state',
                    align: 'center',
                    sortable: false,
	                formatter:function(cellValue,options,rowObjec){
                        var result = null;
                        if(cellValue==31){
                            result = "已期满";
                        }else{
                            result = "未期满";
                        }
                        return result;
	                }
                }],
                pager: '#pager',
                pagination: true,
                rowNum: 15,
                rowList: [15, 30, 50],
                sortname: 'id',
                viewrecords: true,
                sortorder: "desc",
                caption: "期满统计列表"
            });
        });

        function search() {
        	$("#stateForExport").val($("#state").val());
            $("#list").setGridParam({
                url: "${ctx}/statistics/loadExpireStatisticsList?" + encodeURI($("#searchForm").serialize())
            }).trigger('reloadGrid');
        }
        
        function exportExcel(type){
        	var rows = $("#list").jqGrid('getGridParam', 'records');
        	if(rows==0){
        		alert("无可导出数据");
        		return ;
        	}
        	var state = $("#stateForExport").val();
        	$("#exportExcel").attr("href","${ctx}/statistics/exportStatistics?talkerId="
        			+$("#talkerId").val()+"&state="+state+"&type="+type);
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
<div class="container-fluid" style="padding-right: 15px;">
	<form id="searchForm">
		<input type="hidden" id="talkerId" name="talkerId" value="${talkerId}">
		<input type="hidden" id="stateForExport" name="stateForExport" value='0'>
		<table>
			<tr>
				<th width="70px;"></th>
				<th width="200px;"></th>
				<th width="70px;"></th>
				<th width="200px;"></th>
				<th width="70px;"></th>
				<th width="200px;"></th>
				<th width="165px;"></th>
				<th width="50px;"></th>
			</tr>
			<tr>
				<td>
					是否入职:
				</td>
				<td>
					<select id="state" name="state">
						<option value="0">-请选择-</option>
						<option value="1">已期满</option>
						<option value="2">未期满</option>
					</select>
				</td>
				<td></td>
				<td></td>
				<td></td>
				<td align="right">
					<a id="exportExcel" href="#">
						<button onclick="exportExcel(2)" type="button" class="btn btn-primary" >
							导出
						</button>
					</a>
				</td>
				<td align="right">
					<button type='button' class="btn btn-primary btn-sm" data-toggle="modal" onclick="search()">
						查询
					</button>
				</td>
				<td align="right">
					<button type='button' class="btn btn-primary btn-sm" data-toggle="modal" onclick="goBack()">
						返回
					</button>
				</td>
			</tr>
		</table>
	</form>
	<table id="list"></table>
	<div id="pager"></div>
</div>
</body>
</html>     