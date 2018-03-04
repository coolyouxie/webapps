<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8"/>
	<title>会员兑奖列表</title>
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
                url: "${ctx}/userAwardExchange/loadUserAwardExchangeList",
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
                colNames: ['会员姓名', '会员手机','报名时间','奖品','是否领取','领取时间'],
                colModel: [{
                    label: 'userName',
                    name: 'userName',
                    align: 'center',
                    sortable: false
                }, {
                    label: 'userMobile',
                    name: 'userMobile',
                    align: 'center',
                    sortable: false
                }, {
                    label: 'enrollTimeFullStr',
                    name: 'enrollTimeFullStr',
                    align: 'center',
                    sortable: false
                }, {
                    label: 'awardName',
                    name: 'awardName',
                    align: 'center',
                    sortable: false
                }, {
                    label: 'status',
                    name: 'status',
                    align: 'center',
                    sortable: false,
                    formatter:function (cellValue,options,rowObject) {
                        if(cellValue==0){
                            return "未领取";
                        }else{
                            return "已领取";
                        }
                    }
                }, {
                    label: 'exchangeTimeFullStr',
                    name: 'exchangeTimeFullStr',
                    align: 'center',
                    sortable: false
                }],
                pager: '#pager',
                rowNum: 10,
                rowList: [10, 20, 40],
                sortname: 'id',
                viewrecords: true,
                sortorder: "desc",
                caption: "会员兑奖列表"
            });
        });

        function search() {
            $("#userNameForExport").val($("#userName").val());
            $("#userMobileForExport").val($("#userMobile").val());
            $("#awardNameForExport").val($("#awardName").val());
            $("#enrollTimeStartForExport").val($("#enrollTimeStart").val());
            $("#enrollTimeEndForExport").val($("#enrollTimeEnd").val());
            $("#statusForExport").val($("#status").val());
            dataGrid.jqGrid("setGridParam", {
                url: "${ctx}/userAwardExchange/loadUserAwardExchangeList?" + encodeURI($("#searchForm").serialize())
            }).trigger("reloadGrid");
        }

        function exportExcel(){
            var rows = $("#list").jqGrid('getGridParam', 'records');
            if(rows==0){
                alert("无数据可导出");
                return ;
            }
            $("#exportForm").submit();
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
	<form id="exportForm" method="post" action="${ctx}/userAwardExchange/exportExcel">
	<input type="hidden" id="userNameForExport" name="userNameForExport">
	<input type="hidden" id="userMobileForExport" name="userMobileForExport">
	<input type="hidden" id="awardNameForExport" name="awardNameForExport">
	<input type="hidden" id="enrollTimeStartForExport" name="enrollTimeStartForExport">
	<input type="hidden" id="enrollTimeEndForExport" name="enrollTimeEndForExport">
	<input type="hidden" id="statusForExport" name="statusForExport" value="-1">
	</form>
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
				<td>会员姓名：</td>
				<td><input type="text" id="userName" name="userName"></td>
				<td>会员手机：</td>
				<td><input type="text" id="userMobile" name="userMobile"></td>
				<td>奖品：</td>
				<td><input type="text" id="awardName" name="awardName"></td>
				<td>&nbsp;</td>
				<td>
					<a id="exportExcel" href="#">
						<button onclick="exportExcel()" type="button" class="btn btn-primary btn-sm" >
							导出
						</button>
					</a>
				</td>
			</tr>
			<tr>
				<td>报名时间：</td>
				<td colspan="3">
					<input type="text" id="enrollTimeStart" name="enrollTimeStart" onClick="WdatePicker({isShowWeek:true})">
					-
					<input type="text" id="enrollTimeEnd" name="enrollTimeEnd" onClick="WdatePicker({isShowWeek:true})">
				</td>
				<td>是否领取：</td>
				<td>
					<select id="status" name="status">
						<option value="-1" selected>-请选择-</option>
						<option value="0">未领取</option>
						<option value="1">已领取</option>
					</select>
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