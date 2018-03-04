<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8"/>
	<title>进度管理</title>
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
        jQuery(document).ready(function () {
            dataGrid = jQuery("#list").jqGrid({
                url: "${ctx}/recruitProcess/loadRecruitProcessList",
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
                colNames: ['操作', '会员姓名', '会员手机', '状态', '报名公司', '报名时间', '入职时间', '期满时间', '招聘员', '交接人'],
                colModel: [{
                    label: 'operate',
                    name: 'operate',
                    align: 'center',
                    sortable: false,
                    formatter: function (cellValue, options, rowObject) {
                        var result = "<button id='btn_" + rowObject.id + "' class='btn btn-primary btn-sm' data-toggle='modal' onclick='showModal(" + rowObject.id + ")'>转交</button>";
                        return result;
                    }
                }, {
                    label: 'user.name',
                    name: 'user.name',
                    align: 'center',
                    sortable: false,
                    formatter: function (cellValue, options, rowObject) {
                        if (rowObject.company) {
                            return '<a href="${ctx}/company/getById?id=' + rowObject.company.id + '" style="color:blue">' + cellValue + '</a>';
                        } else {
                            return '';
                        }
                    }
                }, {
                    label: 'user.mobile',
                    name: 'user.mobile',
                    align: 'center',
                    sortable: false,
                    formatter: function (cellValue, options, rowObject) {
                        return '<a href="${ctx}/recruitment/getById?id=' + rowObject.recruitment.id + '" style="color:blue">' + cellValue + '</a>';
                    }
                }, {
                    label: 'state',
                    name: 'state',
                    align: 'center',
                    sortable: false,
                    formatter: function (cellValue, options, rowObject) {
                        var result = "";
                        if (cellValue == 20) {
                            result = "入职待审核";
                        } else if (cellValue == 21) {
                            result = "已入职";
                        } else if (cellValue == 22) {
                            result = "入职审核未通过";
                        } else if (cellValue == 30) {
                            result = "全部期满待审核";
                        } else if (cellValue == 31) {
                            result = "全部期满";
                        } else if (cellValue == 32) {
                            result = "全部期满审核未通过";
                        } else if (cellValue == 50) {
                            result = "阶段期满待审核";
                        } else if (cellValue == 51) {
                            result = "阶段期满";
                        } else if (cellValue == 52) {
                            result = "阶段期满审核未通过";
                        }
                        return result;
                    }
                }, {
                    label: 'company.name',
                    name: 'company.name',
                    align: 'center',
                    sortable: false
                }, {
                    label: 'createTimeStr',
                    name: 'createTimeStr',
                    align: 'center',
                    sortable: false
                }, {
                    label: "entryDateStr",
                    name: "entryDateStr",
                    align: "center",
                    sortable: false
                }, {
                    label: "expireDateStr",
                    name: "expireDateStr",
                    align: "center",
                    sortable: false,
                    formatter: function (cellValue, options, rowObject) {
                        var result = "";
                        if (rowObject.state == 31) {
                            result = rowObject.updateTimeStr;
                        }
                        return result;
                    }
                }, {
                    label: 'talkerName',
                    name: 'talkerName',
                    align: 'center',
                    sortable: false
                }, {
                    label: "oldTalkerName",
                    name: "oldTalkerName",
                    align: "center",
                    sortable: false
                }],
                pager: '#pager',
                pagination: true,
                rowNum: 15,
                rowList: [15, 30, 50],
                sortname: 'id',
                viewrecords: true,
                sortorder: "desc",
                caption: "进度列表"
            });
        });

        function search() {
            $("#userNameForExport").val($("#userName").val());
            $("#userMobileForExport").val($("#userMobile").val());
            $("#companyNameForExport").val($("#companyName").val());
            $("#intentionCityIdForExport").val($("#intentionCityId").val());
            $("#talkerNameForExport").val($("#talkerName").val());
            $("#enrollTimeStartForExport").val($("#enrollTimeStart").val());
            $("#enrollTimeEndForExport").val($("#enrollTimeEnd").val());
            if($("#unTalk").is(':checked')){
                $("#unTalkForExport").val($("#unTalk").val());
            }else{
                $("#unTalkForExport").val(0);
            }
            if($("#isTalked").is(':checked')){
                $("#isTalkedForExport").val($("#isTalked").val());
            }else{
                $("#isTalkedForExport").val(0);
            }
            if($("#entryState").is(':checked')){
                $("#entryStateForExport").val($("#entryState").val());
            }else{
                $("#entryStateForExport").val(0);
            }
            if($("#partExpireState").is(':checked')){
                $("#partExpireStateForExport").val($("#partExpireState").val());
            }else{
                $("#partExpireStateForExport").val(0);
            }
            if($("#allExpireState").is(':checked')){
                $("#allExpireStateForExport").val($("#allExpireState").val());
            }else{
                $("#allExpireStateForExport").val(0);
            }
            $("#list").setGridParam({
                url: "${ctx}/recruitProcess/loadRecruitProcessList?" + encodeURI($("#searchForm").serialize())
            }).trigger('reloadGrid');
        }

        function showModal(id) {
            $("#enrollmentId").val(id);
            $('#talkerInfo').modal('show');
        }

        function updateTalkerInfo() {
            var talkerId = $("#talkerModal").val();
            $.ajax({
                url: "${ctx}/enrollment/updateTalkerInfo",
                type: "POST",
                dataType: "JSON",
                data: {
                    'id': $("#enrollmentId").val(),
                    'talkerId': talkerId,
                    'talkerName': $("#talkerId_" + talkerId).html()
                },
                success: function (response) {
                    if (response.result == "S") {
                        $('#talkerInfo').modal('hide');
                        alert("保存成功");
                        search();
                    } else {
                        $('#talkerInfo').modal('hide');
                        alert(response.errorMsg);
                    }
                }
            });
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
<input type="hidden" id="enrollmentId">
<input type="hidden" id="handleType">
<div class="modal fade" id="talkerInfo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">修改招聘员</h4>
			</div>
			<div class="modal-body">
				<div class="row" style="padding-left: 15px;">
					招聘员：
					<select id="talkerModal">
						<c:forEach var="item" items="${talkers}">
							<option id="talkerId_${item.id}" value="${item.id}">${item.name}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary" onclick="updateTalkerInfo()">提交</button>
			</div>
		</div>
	</div>
</div>

<div class="container-fluid" style="padding-right: 15px;">
	<form id="exportForm" method="post" action="${ctx}/recruitProcess/exportRecruitProcess">
		<input type="hidden" id="userNameForExport" name="userNameForExport">
		<input type="hidden" id="userMobileForExport" name="userMobileForExport">
		<input type="hidden" id="companyNameForExport" name="companyNameForExport">
		<input type="hidden" id="intentionCityIdForExport" name="intentionCityIdForExport" value="0">
		<input type="hidden" id="talkerNameForExport" name="talkerNameForExport">
		<input type="hidden" id="enrollTimeStartForExport" name="enrollTimeStartForExport">
		<input type="hidden" id="enrollTimeEndForExport" name="enrollTimeEndForExport">
		<input type="hidden" id="unTalkForExport" name="unTalkForExport" value="0">
		<input type="hidden" id="isTalkedForExport" name="isTalkedForExport" value="0">
		<input type="hidden" id="entryStateForExport" name="entryStateForExport" value="0">
		<input type="hidden" id="partExpireStateForExport" name="partExpireStateForExport" value="0">
		<input type="hidden" id="allExpireStateForExport" name="allExpireStateForExport" value="0">
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
				<td>
					会员姓名:
				</td>
				<td>
					<input type="text" id="userName" name="user.name" value="">
				</td>
				<td>
					报名公司:
				</td>
				<td>
					<input type="text" id="companyName" name="company.name" value="">
				</td>
				<td>
					会员手机:
				</td>
				<td>
					<input type="text" id="userMobile" name="user.mobile">
				</td>
				<td>
				</td>
				<td>
				</td>
			</tr>
			<tr>
				<td>
					意向城市:
				</td>
				<td>
					<select id="intentionCityId" name="intentionCityId">
						<option value="0">全部</option>
						<c:forEach var="item" items="${intentionCities}">
							<option value="${item.id}">${item.city}</option>
						</c:forEach>
					</select>
				</td>
				<td>
					招聘员:
				</td>
				<td>
					<input type="text" id="talkerName" name="talkerName" value="">
				</td>
				<td>
					报名时间：
				</td>
				<td colspan="3">
					<input type="text" id="enrollTimeStart" name="enrollTimeStart"
					       onClick="WdatePicker({isShowWeek:true})">
					-
					<input type="text" id="enrollTimeEnd" name="enrollTimeEnd" onClick="WdatePicker({isShowWeek:true})">
				</td>
			</tr>
			<tr>
				<td colspan="3">
					<input type="checkbox" id="unTalk" name="unTalk" value="1">未沟通
					<input type="checkbox" id="isTalked" name="isTalked" value="1">已沟通
					<input type="checkbox" id="entryState" name="entryState" value="1">已入职
					<input type="checkbox" id="partExpireState" name="partExpireState" value="1">阶段期满
					<input type="checkbox" id="allExpireState" name="allExpireState" value="1">全部期满
				</td>
				<td>
				</td>
				<td>
				</td>
				<td>
				</td>
				<td align="right">
					<a id="exportExcel" href="#">
						<button onclick="exportExcel()" type="button" class="btn btn-primary btn-sm" >
							导出
						</button>
					</a>
				</td>
				<td align="right">
					<button type='button' class="btn btn-primary btn-sm" data-toggle="modal" onclick="search()">
						查询
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