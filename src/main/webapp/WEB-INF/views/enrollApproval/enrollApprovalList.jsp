<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>审核列表</title>	
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
		    url:"${ctx}/enrollApproval/loadEnrollApprovalList",
		    datatype: "json",
		    mtype : "POST",
		    height : 650,
		    width : 950,
		    jsonReader : {
								root : "resultList", // json中代表实际模型数据的入口
								page : "page.page", // json中代表当前页码的数据   
								records : "page.records", // json中代表数据行总数的数据   
								total : 'page.total', // json中代表页码总数的数据 
								repeatitems : false // 如果设为false，则jqGrid在解析json时，会根据name来搜索对应的数据元素（即可以json中元素可以不按顺序）；而所使用的name是来自于colModel中的name设定。   
							},
		    colNames : [ '报名人', '手机号','身份证号','报名时间','入职公司','入职日期','发布单标题','类型','状态','备注','操作'],
		    colModel : [ {
								label : 'user.name',
								name : 'user.name',
								align : 'center',
								sortable : false,
								formatter:function(cellValue,options,rowObject){
									if(rowObject.user){
										return '<a href="${ctx}/user/getById?id='+rowObject.user.id+'" style="color:blue">'+cellValue+'</a>';
									}
									return "";
								}
							}, {
								label : 'user.mobile',
								name : 'user.mobile',
								align : 'center',
								sortable : false
							}, {
								label : 'user.idCardNo',
								name : 'user.idCardNo',
								align : 'center',
								sortable : false
							}, {
								label : 'createTimeStr',
								name : 'createTimeStr',
								align : 'center',
								sortable : false
							}, {
								label : 'company.name',
								name : 'company.name',
								align : 'center',
								sortable : false,
								formatter:function(cellValue,options,rowObject){
									var result = "无";
									if(rowObject.company){
										result = '<a href="${ctx}/company/getById?id='+rowObject.company.id+'" style="color:blue">'+cellValue+'</a>';
									}
									return result;
								}
							}, {
								label : 'entryDateStr',
								name : 'entryDateStr',
								align : 'center',
								sortable : false
							}, {
								label : 'recruitment.title',
								name : 'recruitment.title',
								align : 'center',
								sortable : false,
								formatter:function(cellValue,options,rowObject){
									var result = "无";
									if(rowObject.recruitment){
										result = '<a href="${ctx}/recruitment/getById?id='+rowObject.recruitment.id+'" style="color:blue">'+cellValue+'</a>';
									}
									return result;
								}
							}, {
								label : 'type',
								name : 'type',
								align : 'center',
								sortable : false,
								formatter:function(cellValue,options,rowObject){
									var result = null;
									if(cellValue==1){
										result = '入职审核';
									}else if(cellValue==2){
										result = "期满审核";
									}
									return result;
								}
							},{
								label : 'state',
								name : 'state',
								align : 'center',
								sortable : false,
								formatter:function(cellValue,options,rowObject){
									var result = null;
									if(cellValue==0){
										result = '待审核';
									}else if(cellValue==1){
										result = "审核通过";
									}else if(cellValue==2){
										result = "审核不通过";
									}
									return result;
								}
							},{
								label : 'failedReason',
								name : 'failedReason',
								align : 'center',
								sortable : false
							}, {
								label : 'operate',
								name : 'operate',
								align : 'center',
								sortable : false,
								formatter:function(cellValue,options,rowObject){
									var result = "";
									if(rowObject.state==0){
										result = '<button class="btn btn-primary btn-sm" onclick="showModal('+rowObject.id+',1,'+rowObject.type+')">通过</button>'+
										'<button class="btn btn-primary btn-sm" onclick="showModal('+rowObject.id+',2,'+rowObject.type+')">不通过</button>';
									}else{
										result = "已审核  ";
									}
									if(rowObject.state!=0){
										result += '  <a class="btn btn-primary btn-sm" onclick="toEntryDetail('+rowObject.id+')">期满信息</a>';
									}
									return result;
								}
							} ],
		    pager: '#pager',
		    rowNum:50,
		    rowList:[50,100,200],
		    sortname: 'id',
		    viewrecords: true,
		    sortorder: "desc",
		    caption: "审核列表",
		    gridComplete : function() { //在此事件中循环为每一行添加日志、废保和查看链接
				
			}
		});
	});
	
	function search(){
		dataGrid.jqGrid("setGridParam",{
		    postData:$("#searchForm").serialize()+"&rows="+dataGrid.jqGrid('getGridParam', 'rowNum')+"&page=1",
		    page:1
		}).trigger("reloadGrid");
	}
	
	function enrollApprovalById(id,state,remark,approvalType,reward){
	    var cashbackData = getCashbackData();
	    if((!cashbackData||cashbackData.length==0)&&approvalType==1&&state==1){
	        alert("请输入期满返额金额和天数");
	        return;
		}
		$.ajax({
			url:"${ctx}/enrollApproval/enrollApprovalById",
			type:"POST",
			dataType:"JSON",
            traditional: true,//必须指定为true
			data:{
				"id":id,
				"state":state,
				"remark":remark,
				"reward":reward,
				"approvalType":approvalType,
				"cashbackData":getCashbackData()
			},
			success:function(response){
				$('#remarkModal').modal('hide');
				$('#rewardModal').modal('hide');
				if(response.result=='S'){
					alert("审核信息更新完成");
					dataGrid.trigger("reloadGrid");
				}else{
					alert(response.errorMsg);
				}
			}
		});
	}
	
	function showModal(id,state,approvalType){
		$("#enrollApprovalId").val(id);
		$("#approvalState").val(state);
		$("#approvalType").val(approvalType);
		if(state==1){
			if(approvalType==1){
				$('#rewardModal').modal('show');
			}else{
				enrollApprovalById(id, state, "", approvalType);
			}
		}else if(state==2){
			$('#remarkModal').modal('show');
		}
	}
	
	function showRewardModal(id,state,approvalType){
		$("#enrollApprovalId").val(id);
		$("#approvalState").val(state);
		$("#approvalType").val(approvalType);
		$('#rewardModal').modal('show');
	}
	
	function enrollApprovalByIdWithRemark(){
		var id = $("#enrollApprovalId").val();
		var state = $("#approvalState").val();
		var remark = $("#remark").val().trim();
		var approvalType = $("#approvalType").val();
		if(state==2){
			if(!remark){
				alert("请填写审核不通过原因");
				return ;
			}
		}
		enrollApprovalById(id,state,remark,approvalType);
	}
	
	function enrollApprovalByIdWithReward(id,state,approvalType){
		var id = $("#enrollApprovalId").val();
		var state = $("#approvalState").val();
		var approvalType = $("#approvalType").val();
		enrollApprovalById(id,state,null,approvalType,null);
	}

	function getCashbackData() {
	    var cashbackData = [];
		for(var i=0;i<5;i++){
			var reward = $("#reward"+i).val();
			var cashbackDays = $("#cashbackDays"+i).val();
			if(reward!=null&&cashbackDays!=null){
			    if(reward&&cashbackDays){
                    cashbackData.push(reward+":"+cashbackDays);
				}
			}
		}
		return cashbackData;
    }
	
	function toEntryDetail(id){
		window.location.href="${ctx}/enrollApproval/getEntryDetailById?id="+id;
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
	<input type="hidden" id="enrollApprovalId" >
	<input type="hidden" id="approvalState" >
	<input type="hidden" id="approvalType">
	<div class="modal fade" id="remarkModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">拒绝原因</h4>
				</div>
				<div class="modal-body">
					<textarea id="remark" class="form-control" ></textarea>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" onclick="enrollApprovalByIdWithRemark()">提交</button>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal -->
	</div>
	
	<div class="modal fade" id="rewardModal" tabindex="-1" role="dialog" aria-labelledby="rewardModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="rewardModalLabel">入职当天返费金额及期满天数</h4>
				</div>
				<div class="modal-body">
					<label style="width: 205px;">
						期满天数：
						<select id="cashbackDays0" class="form-control" style="display: inline-block;width: 60%;">
							<option>请选择天数</option>
							<% for(int i=1;i<=120;i++){%>
								<option value="<%=i%>"><%=i%></option>
							<%}%>
						</select>
					</label>
					<label>
						返费金额：
						<input type="text" id="reward0" class="form-control" style="display: inline-block;width: 60%;">
					</label>

					<label style="width: 205px;">
						期满天数：
						<select id="cashbackDays1" class="form-control" style="display: inline-block;width: 60%;">
							<option>请选择天数</option>
							<% for(int i=1;i<=120;i++){%>
								<option value="<%=i%>"><%=i%></option>
							<%}%>
						</select>
					</label>
					<label>
						返费金额：
						<input type="text" id="reward1" class="form-control" style="display: inline-block;width: 60%;">
					</label>

					<label style="width: 205px;">
						期满天数：
						<select id="cashbackDays2" class="form-control" style="display: inline-block;width: 60%;">
							<option>请选择天数</option>
							<% for(int i=1;i<=120;i++){%>
								<option value="<%=i%>"><%=i%></option>
							<%}%>
						</select>
					</label>
					<label>
						返费金额：
						<input type="text" id="reward2" class="form-control" style="display: inline-block;width: 60%;">
					</label>

					<label style="width: 205px;">
						期满天数：
						<select id="cashbackDays3" class="form-control" style="display: inline-block;width: 60%;">
							<option>请选择天数</option>
							<% for(int i=1;i<=120;i++){%>
								<option value="<%=i%>"><%=i%></option>
							<%}%>
						</select>
					</label>
					<label>
						返费金额：
						<input type="text" id="reward3" class="form-control" style="display: inline-block;width: 60%;">
					</label>

					<label style="width: 205px;">
						期满天数：
						<select id="cashbackDays4" class="form-control" style="display: inline-block;width: 60%;">
							<option>请选择天数</option>
							<% for(int i=1;i<=120;i++){%>
								<option value="<%=i%>"><%=i%></option>
							<%}%>
						</select>
					</label>
					<label>
						返费金额：
						<input type="text" id="reward4" class="form-control" style="display: inline-block;width: 60%;">
					</label>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" onclick="enrollApprovalByIdWithReward()">提交</button>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal -->
	</div>
	
	<div class="container-fluid">
		<form id="searchForm">
			<div class="row" style="margin-bottom:8px">
				<div class="col-sm-3" style="width:255px">
					<label>
						<span>公司名称:</span>
						<input type="text" id="companyName" name="company.name" value="">
					</label>
				</div>
				<div class="col-sm-3" style="width:255px">
					<label>
						<span>报名人:</span>
						<input type="text" id="userName" name="user.name" value="">
					</label>
				</div>
				<div class="col-sm-3" style="width:255px">
					<label>
						<span>手机号:</span>
						<input type="text" id="userMobile" name="user.mobile" value="">
					</label>
				</div>
				<div class="col-sm-1" style="text-align:right;width:200px">
					<button type='button' class="btn btn-primary btn-sm" data-toggle="modal" onclick="search()">
						查询
					</button>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-4">
					<label>
						<span>报名时间:</span>
						<input type="text" id="enrollTimeStart" name="enrollTimeStart" onClick="WdatePicker({isShowWeek:true})" style="width: 122px;">
						-
						<input type="text" id="enrollTimeEnd" name="enrollTimeEnd" onClick="WdatePicker({isShowWeek:true})" style="width: 122px;">
					</label>
				</div>
				<div class="col-sm-2">
					<label>
						<span>类型:</span>
						<select id="type" name="type">
							<option value="">-请选择-</option>
							<option value="1">入职审核</option>
							<option value="2">期满审核</option>
						</select>
					</label>
				</div>
				<div class="col-sm-2">
					<label>
						<span>状态:</span>
						<select id="state" name="state">
							<option value="">-请选择-</option>
							<option value="1">通过</option>
							<option value="2">不通过</option>
						</select>
					</label>
				</div>
			</div>
		</form>
	</div>
	<table id="list"></table>
	<div id="pager"></div>
</body>
</html>