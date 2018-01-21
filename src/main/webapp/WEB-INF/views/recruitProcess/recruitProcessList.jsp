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
                url: "${ctx}/enrollment/loadEnrollmentList",
                datatype: "json",
                mtype: "POST",
                height: 'auto',
                width: 'auto',
                rownumbers:true,
                jsonReader: {
                    root: "resultList", // json中代表实际模型数据的入口
                    page: "page", // json中代表当前页码的数据
                    records: "records", // json中代表数据行总数的数据
                    total: 'total', // json中代表页码总数的数据
                    repeatitems: false // 如果设为false，则jqGrid在解析json时，会根据name来搜索对应的数据元素（即可以json中元素可以不按顺序）；而所使用的name是来自于colModel中的name设定。
                },
                getParam: function () {
                    var rowListNum = $("#list").jqGrid('getGridParam', 'rowNum');
                    if (rowListNum == undefined) {
                        $('#pageSize').val(15);
                    } else {
                        $('#pageSize').val(rowListNum);
                    }
                    //组装查询的条件参数
                    var params = {
                        'company.name': $("#companyName").val()
                    };
                    return params;
                },
                colNames: ['操作', '公司名称', '发布单标题', '会员姓名', '联系方式', '报名时间', '招聘员', '意向城市','是否面试','面试时间','沟通备注'],
                colModel: [{
                    label: 'operate',
                    name: 'operate',
                    align: 'center',
                    sortable: false,
                    formatter: function (cellValue, options, rowObject) {
                        var result = null;
                        if (rowObject.isTalked <= 0) {
                            result = "<button id='btn_" + rowObject.id + "' class='btn btn-primary btn-sm' data-toggle='modal' onclick='showModal(" + rowObject.id + ",1)'>沟通</button>&nbsp;&nbsp;&nbsp;&nbsp;";
                        } else if (rowObject.isTalked == 1) {
                            result = "已沟通&nbsp;&nbsp;&nbsp;&nbsp;";
                        }
                        return result;
                    }
                }, {
                    label: 'company.name',
                    name: 'company.name',
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
                    label: 'recruitment.title',
                    name: 'recruitment.title',
                    align: 'center',
                    sortable: false,
                    formatter: function (cellValue, options, rowObject) {
                        return '<a href="${ctx}/recruitment/getById?id=' + rowObject.recruitment.id + '" style="color:blue">' + cellValue + '</a>';
                    }
                }, {
                    label: 'user.name',
                    name: 'user.name',
                    align: 'center',
                    sortable: false,
                    formatter: function (cellValue, options, rowObject) {
                        return '<a href="${ctx}/user/getById?id=' + rowObject.user.id + '" style="color:blue">' + cellValue + '</a>';
                    }
                }, {
                    label: 'user.mobile',
                    name: 'user.mobile',
                    align: 'center',
                    sortable: false
                }, {
                    label: 'createTimeStr',
                    name: 'createTimeStr',
                    align: 'center',
                    sortable: false
                }, {
                    label: "talkerName",
                    name: "talkerName",
                    align: "center",
                    sortable: false
                }, {
                    label: "intentionCityName",
                    name: "intentionCityName",
                    align: "center",
                    sortable: false
                }, {
                    label: "interviewIntention",
                    name: "interviewIntention",
                    align: "center",
                    sortable: false,
                    formatter: function (cellValue, options, rowObject) {
                    	var result = null;
                    	if(cellValue==1){
                    		result = "是";
                    	}else if(cellValue==2){
                    		result = "否";
                    	}else{
                    		result = "未沟通";
                    	}
                        return result;
                    }
                }, {
                    label: 'interviewTimeStr',
                    name: 'interviewTimeStr',
                    align: 'center',
                    sortable: false,
                    formatter: function (cellValue, options, rowObject) {
                    	var result = '';
                    	if(rowObject.interviewIntention==1){
	                    	result = cellValue+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+
	                    	'<button id="btn_' + rowObject.id + '" '+
	                    	'class="btn btn-primary btn-sm" data-toggle="modal" '+
	                    	'onclick="showTimeModal(' + rowObject.id + ',\''+cellValue+'\')">修改</button>';
                    	}else{
                    		result = cellValue;
                    	}
                        return result;
                    }
                }, {
                    label: "talkResult",
                    name: "talkResult",
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
            $("#list").setGridParam({
                url: "${ctx}/enrollment/loadEnrollmentList?" + encodeURI($("#searchForm").serialize())
            }).trigger('reloadGrid');
        }

        function deleteById(id) {
            $.ajax({
                url: "${ctx}/company/deleteCompanyById",
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

        function showModal(id, type) {
            $("#enrollmentId").val(id);
            $("#handleType").val(type);
            $('#talkInfo').modal('show');
        }
        
        function showTimeModal(id,currentTime) {
        	$("#enrollmentId").val(id);
        	$("#interviewTimeStrModal").val(currentTime);
            $('#interviewTimeModal').modal('show');
        }

        function updateEnrollmentInfo() {
            var handleType = $("#handleType").val();
            if (handleType == 1) {
                saveTalkInfo();
            } else if (handleType == 2) {
                //作废报名
                cancelEnroll();
            }
        }

        function saveTalkInfo() {
            var interviewIntention = $("#interviewIntentionModal").val();
            var interviewTimeStr = $("#interviewTimeStr").val().trim();
            if(interviewIntention==1&&!interviewTimeStr){
	            alert("请选择面试时间");
	            return;
            }
            var intentionCityId = $("#intentionCityIdModal").val();
            var talkResult = $("#talkResult").val().trim();
            if(!talkResult){
                alert("请填写沟通备注");
                return;
            }
            $.ajax({
                url: "${ctx}/enrollment/saveTalkInfo",
                type: "POST",
                dataType: "JSON",
                data: {
                    'id': $("#enrollmentId").val(),
                    'talkResult': talkResult,
	                'interviewIntention':interviewIntention,
	                'interviewTimeStr':interviewTimeStr,
	                'intentionCityId':intentionCityId,
                    'isTalked': 1
                },
                success: function (response) {
					if (response.result == "S") {
						$("#interviewTimeStr").val("");
						var select = document.getElementById("interviewIntentionModal");
						for(var i=0; i<select.options.length; i++){
						    if(i==0){
						        select.options[i].selected = true;
						        break;
						    }
						}
						select = document.getElementById("intentionCityIdModal");
						for(var i=0; i<select.options.length; i++){
						    if(i==0){
						        select.options[i].selected = true;
						        break;
						    }
						}
						$("#talkResult").val("");
						$('#talkInfo').modal('hide');
                        alert("保存成功");
                        search();
                    } else {
                        alert(response.errorMsg);
                    }
                }
            });
        }

        function cancelEnroll() {
            $.ajax({
                url: "${ctx}/enrollment/cancelEnroll",
                type: "POST",
                dataType: "JSON",
                data: {
                    'id': $("#enrollmentId").val(),
                    'remark': $("#talkResult").val(),
                    'dataState': 0
                },
                success: function (response) {
                    if (response.result == "S") {
                        $('#talkInfo').modal('hide');
                        alert("保存成功");
                        search();
                    } else {
                        alert(response.errorMsg);
                    }
                }
            });
        }
        
        function updateInterviewTime(){
        	var interviewTimeStr = $("#interviewTimeStrModal").val().trim();
        	if(!interviewTimeStr){
        		alert("请选择面试时间");
        		return;
        	}
        	$.ajax({
                url: "${ctx}/enrollment/updateInterviewTime",
                type: "POST",
                dataType: "JSON",
                data: {
                    'id': $("#enrollmentId").val(),
                    'interviewTimeStr':$("#interviewTimeStrModal").val()
                },
                success: function (response) {
                    if (response.result == "S") {
                        $('#interviewTimeModal').modal('hide');
                        alert("修改成功");
                        search();
                    } else {
                        alert(response.errorMsg);
                    }
                }
            });
        }

	</script>
	<style>
		.input-group-sm {
			margin-bottom: 10px;
		}

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
<div class="modal fade" id="talkInfo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">沟通结果和备注</h4>
			</div>

			<div class="modal-body">
				<div class="row" style="padding-left: 15px;">
					意向城市：
					<select id="intentionCityIdModal">
						<c:forEach var="item" items="${intentionCities}">
							<option value="${item.id}">${item.city}</option>
						</c:forEach>
					</select>
				</div>
				<div class="row" style="padding-left: 15px;">
					是否面试：
					<select id="interviewIntentionModal" >
						<option value="1">同意</option>
						<option value="2">不同意</option>
					</select>
				</div>
				<div class="row" style="padding-left: 15px;">
					面试时间：<input type="text" id="interviewTimeStr" onClick="WdatePicker({isShowWeek:true})">
				</div>
				<div class="row" style="padding-left: 15px;">
					沟通备注：
					<textarea id="talkResult" class="form-control"></textarea>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary" onclick="updateEnrollmentInfo()">提交</button>
			</div>
		</div>
	</div>
</div>

<div class="modal fade" id="interviewTimeModal" tabindex="-1" role="dialog" aria-labelledby="interviewTimeModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="interviewTimeModalLabel">沟通结果和备注</h4>
			</div>

			<div class="modal-body">
				<div class="row" style="padding-left: 15px;">
					面试时间：<input type="text" id="interviewTimeStrModal" value="2017-12-31" onClick="WdatePicker({isShowWeek:true})">
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary" onclick="updateInterviewTime()">提交</button>
			</div>
		</div>
	</div>
</div>
<div class="container-fluid" style="padding-right: 15px;">
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
					公司名称:
				</td>
				<td>
					<input type="text" id="companyName" name="company.name" value="">
				</td>
				<td>
					报名人:
				</td>
				<td>
					<input type="text" id="userName" name="user.name" value="">
				</td>
				<td>
					报名时间:
				</td>
				<td colspan="2">
					<input type="text" id="enrollTimeStart" name="enrollTimeStart" onClick="WdatePicker({isShowWeek:true})">
					-
					<input type="text" id="enrollTimeEnd" name="enrollTimeEnd" onClick="WdatePicker({isShowWeek:true})">
				</td>
				<td></td>
			</tr>
			<tr>
				<td>
					联系方式:
				</td>
				<td>
					<input type="text" id="userMobile" name="user.mobile" value="">
				</td>
				<td>
					状态:
				</td>
				<td>
					<select id="isTalked" name="isTalked">
						<option value="">全部</option>
						<option value="0">未沟通</option>
						<option value="1">已沟通</option>
					</select>
				</td>
				<td>
					<c:choose>
						<c:when test="${user.userType==1 or user.userType==2}">
							招聘专员：
						</c:when>
						<c:otherwise>
						</c:otherwise>
					</c:choose>
				</td>
				<td>
					<c:choose>
						<c:when test="${user.userType==1 or user.userType==2}">
							<input id="talkerName" name="talkerName" value="">
						</c:when>
						<c:otherwise>
							<input type="hidden" id="talkerName" name="talkerName" value="">
						</c:otherwise>
					</c:choose>
				</td>
				<td>
				</td>
				<td align="right">

				</td>
			</tr>
			<tr>
				<td>
					意向城市：
				</td>
				<td>
					<select id="intentionCityId" name="intentionCityId">
						<option value="">全部</option>
						<c:forEach var="item" items="${intentionCities}">
							<option value="${item.id}">${item.city}</option>
						</c:forEach>
					</select>
				</td>
				<td>
					是否面试：
				</td>
				<td>
					<select id="interviewIntention" name="interviewIntention">
						<option value="0">全部</option>
						<option value="1">同意</option>
						<option value="2">不同意</option>
					</select>
				</td>
				<td>
					面试时间：
				</td>
				<td colspan="2">
					<input type="text" id="interviewTimeStart" name="interviewTimeStart" onClick="WdatePicker({isShowWeek:true})">
					-
					<input type="text" id="interviewTimeEnd" name="interviewTimeEnd" onClick="WdatePicker({isShowWeek:true})">
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