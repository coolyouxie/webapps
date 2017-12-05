<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>报名管理</title>	
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="${ctx}/js/common/jquery/jquery-ui-1.12.1/jquery-ui.css" type="text/css" />
<link rel="stylesheet" href="${ctx}/js/common/jquery/jqGrid/css/ui.jqgrid.css" type="text/css" />
<!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="${ctx}/js/common/bootstrap/bootstrap-3.3.7/dist/css/bootstrap.min.css" type="text/css" />
<link rel="stylesheet" href="${ctx}/js/common/jquery/My97DatePicker/skin/WdatePicker.css" type="text/css" />

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
	jQuery(document).ready(function(){
		dataGrid = jQuery("#list").jqGrid({
		    url:"${ctx}/enrollment/loadEnrollmentList",
		    datatype: "json",
		    mtype : "POST",
		    height : 'auto',
		    width : 'auto',
		    jsonReader : {
								root : "resultList", // json中代表实际模型数据的入口
								page : "page", // json中代表当前页码的数据   
								records : "records", // json中代表数据行总数的数据   
								total : 'total', // json中代表页码总数的数据 
								repeatitems : false // 如果设为false，则jqGrid在解析json时，会根据name来搜索对应的数据元素（即可以json中元素可以不按顺序）；而所使用的name是来自于colModel中的name设定。   
						},
            getParam: function(){
                var rowListNum = $("#list").jqGrid('getGridParam', 'rowNum');
                if(rowListNum == undefined){
                    $('#pageSize').val(15);
                }else{
                    $('#pageSize').val(rowListNum);
                }
                //组装查询的条件参数
                var params = {
                    'company.name':$("#companyName").val()
                };
                return params;
            },
		    colNames : [ '公司名称', '发布单标题', '报名人', '手机号','报名时间','沟通结果','操作'],
		    colModel : [ {
								label : 'company.name',
								name : 'company.name',
								align : 'center',
								sortable : false,
								formatter:function(cellValue,options,rowObject){
									if(rowObject.company){
										return '<a href="${ctx}/company/getById?id='+rowObject.company.id+'" style="color:blue">'+cellValue+'</a>';
									}else{
										return '';
									}
								}
							}, {
								label : 'recruitment.title',
								name : 'recruitment.title',
								align : 'center',
								sortable : false,
								formatter:function(cellValue,options,rowObject){
									return '<a href="${ctx}/recruitment/getById?id='+rowObject.recruitment.id+'" style="color:blue">'+cellValue+'</a>';
								}
							}, {
								label : 'user.name',
								name : 'user.name',
								align : 'center',
								sortable : false,
								formatter:function(cellValue,options,rowObject){
									return '<a href="${ctx}/user/getById?id='+rowObject.user.id+'" style="color:blue">'+cellValue+'</a>';
								}
							}, {
								label : 'user.mobile',
								name : 'user.mobile',
								align : 'center',
								sortable : false
							},{
								label : 'createTimeStr',
								name : 'createTimeStr',
								align : 'center',
								sortable : false
							},{
								label : 'talkResult',
								name : 'talkResult',
								align : 'center',
								sortable : false
							},{
								label : 'operate',
								name : 'operate',
								align : 'center',
								sortable : false,
								formatter:function(cellValue,options,rowObject){
									var result = null;
									if(rowObject.isTalked<=0){
										result = "<button id='btn_"+rowObject.id+"' class='btn btn-primary btn-sm' data-toggle='modal' onclick='showModal("+rowObject.id+",1)'>沟通</button>&nbsp;&nbsp;&nbsp;&nbsp;";
									}else if(rowObject.isTalked==1){
										result = "已沟通&nbsp;&nbsp;&nbsp;&nbsp;";
									}
									return result;
								}
							} ],
		    pager: '#pager',
            pagination: true,
		    rowNum:15,
		    rowList:[15,30,50],
		    sortname: 'id',
		    viewrecords: true,
		    sortorder: "desc",
		    caption: "报名列表"
		});
	});
	
	function search(){
        $("#list").setGridParam({
	        url:"${ctx}/enrollment/loadEnrollmentList?"+encodeURI($("#searchForm").serialize())
        }).trigger('reloadGrid');
	}
	
	function deleteById(id){
		$.ajax({
			url:"${ctx}/company/deleteCompanyById",
			type:"POST",
			dataType:"JSON",
			data:{
				"id":id
			},
			success:function(response){
				alert("删除成功");
				dataGrid.trigger("reloadGrid");
			}
		});
	}
	
	function showModal(id,type){
		$("#enrollmentId").val(id);
		$("#handleType").val(type);
		$('#talkInfo').modal('show');
	}
	
	function updateEnrollmentInfo(){
		var handleType = $("#handleType").val();
		if(handleType==1){
			saveTalkInfo();
		}else if(handleType==2){
			//作废报名
			cancelEnroll();
		}
	}
	
	function saveTalkInfo(){
		$.ajax({		
			url:"${ctx}/enrollment/saveTalkInfo",
			type:"POST",
			dataType:"JSON",
			data:{
				'id':$("#enrollmentId").val(),
				'talkResult':$("#talkResult").val(),
				'isTalked':1
			},
			success:function(response){
				if(response.result=="S"){
					$('#talkInfo').modal('hide');
					alert("保存成功");
					search();
				}else{
					alert(response.errorMsg);
				}
			}
		});
	}
	
	function cancelEnroll(){
		$.ajax({		
			url:"${ctx}/enrollment/cancelEnroll",
			type:"POST",
			dataType:"JSON",
			data:{
				'id':$("#enrollmentId").val(),
				'remark':$("#talkResult").val(),
				'dataState':0
			},
			success:function(response){
				if(response.result=="S"){
					$('#talkInfo').modal('hide');
					alert("保存成功");
					search();
				}else{
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
	.input-group-sm label{
		width:100%;
	}
	.input-group-sm label span{
		width:30%;
		text-align:right;
		display:inline-block;
	}
	.input-group-sm label input{
		width:50%;
		display:inline-block;
	}
</style>
</head>
<body>
	<input type="hidden" id="enrollmentId" >
	<input type="hidden" id="handleType" >
	<div class="modal fade" id="talkInfo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">沟通结果和备注</h4>
				</div>
				<div class="modal-body">
					<textarea id="talkResult" class="form-control" ></textarea>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" onclick="updateEnrollmentInfo()">提交</button>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal -->
	</div>
	<div class="container-fluid" style="padding-right: 15px;">
		<form id="searchForm">
			<div class="row" style="margin-bottom:8px">
				<div class="col-sm-4">
					<label>
						<span>公司名称:</span>
						<input type="text" id="companyName" name="company.name" value="">
					</label>
				</div>
				<div class="col-sm-4">
					<label>
						<span>报名人:</span>
						<input type="text" id="userName" name="user.name" value="">
					</label>
				</div>
				<div class="col-sm-4">
					<label>
						<span>手机号:</span>
						<input type="text" id="userMobile" name="user.mobile" value="">
					</label>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6">
					<label>
						<span>报名时间:</span>
						<input type="text" id="enrollTimeStart" name="enrollTimeStart" onClick="WdatePicker({isShowWeek:true})">
						-
						<input type="text" id="enrollTimeEnd" name="enrollTimeEnd" onClick="WdatePicker({isShowWeek:true})">
					</label>
				</div>

				<div class="col-sm-3">
					<label>
						<span>状态:</span>
						<select id="isTalked" name="isTalked">
							<option value="">-请选择-</option>
							<option value="0">未沟通</option>
							<option value="1">已沟通</option>
						</select>
					</label>
				</div>

				<div class="col-sm-3">
					<label>
						<span>状态:</span>
						<select id="state" name="state">
							<option value="">-请选择-</option>
							<option value="1">已报名</option>
							<option value="2">已入职</option>
							<option value="3">已期满</option>
							<option value="4">已离职</option>
						</select>
					</label>
				</div>
				<div class="col-sm-1" style="text-align:right;width:200px">
					<button type='button' class="btn btn-primary btn-sm" data-toggle="modal" onclick="search()">
						查询
					</button>
				</div>
			</div>
		</form>
		<table id="list"></table>
		<div id="pager"></div>
	</div>
</body>
</html>