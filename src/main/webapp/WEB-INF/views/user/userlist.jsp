<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>后台管理系统</title>	
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="${ctx}/js/common/jquery/jquery-ui-1.12.1/jquery-ui.css" type="text/css" />
<link rel="stylesheet" href="${ctx}/js/common/jquery/jqGrid/css/ui.jqgrid.css" type="text/css" />
<!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="${ctx}/js/common/bootstrap/bootstrap-3.3.7/dist/css/bootstrap.min.css" type="text/css" />
<script src="${ctx}/js/jquery/jQuery-1.12.4.0.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="${ctx}/js/common/bootstrap/bootstrap-3.3.7/dist/js/bootstrap.min.js"></script>
<script src="${ctx}/js/common/jquery/jqGrid/js/grid.base.js"></script>
<script src="${ctx}/js/common/jquery/jqGrid/js/grid.common.js"></script>
<script src="${ctx}/js/common/jquery/jqGrid/js/i18n/grid.locale-cn.js"></script>
<script src="${ctx}/js/common/jquery/jqGrid/js/jquery.jqGrid.js"></script>

<script type="text/javascript">
	var dataGrid = null;
	jQuery(document).ready(function(){
		dataGrid = jQuery("#list").jqGrid({
		    url:"${ctx}/user/loadUserList",
		    datatype: "json",
		    mtype : "POST",
		    height:"auto",
		    width:'auto',
			rownumbers:true,
		    jsonReader : {
				root : "resultList", // json中代表实际模型数据的入口
				page : "page", // json中代表当前页码的数据
				records : "records", // json中代表数据行总数的数据
				total : 'total', // json中代表页码总数的数据
				repeatitems : false // 如果设为false，则jqGrid在解析json时，会根据name来搜索对应的数据元素（即可以json中元素可以不按顺序）；而所使用的name是来自于colModel中的name设定。
			},
		    colNames : [ '操作','姓名','类型','手机号','性别', '身份证号','银行卡号','总金额' ],
		    colModel : [ {
				label : 'operate',
				name : 'operate',
				align : 'center',
				sortable : false
			}, {
				label : 'name',
				name : 'name',
				align : 'center',
				sortable : false,
				formatter:function(cellValue,options,rowObject){
					return '<a href="${ctx}/user/getById?id='+rowObject.id+'" style="color:blue">'+cellValue+'</a>';
				}
			},{
				label : 'userType',
				name : 'userType',
				align : 'center',
				sortable : false,
				formatter:function(cellValue,options,rowObject){
					var result = "";
					if(cellValue==1||cellValue==2){
						result = "系统管理员";
					}else if(cellValue==3){
						result = "普通会员";
					}else if(cellValue==4){
						result = "招聘员";
					}else if(cellValue==5){
						result = "入职与期满审核员";
					}else if(cellValue==7){
						result = "提现审核员";
					}
					return result;
				}
			}, {
				label : 'mobile',
				name : 'mobile',
				align : 'center',
				sortable : false
			}, {
				label : 'gender',
				name : 'gender',
				align : 'center',
				sortable : false,
				formatter:function(cellValue,options,rowObject){
					if(cellValue==1){
						return '男';
					}else{
						return '女';
					}
				}
			}, {
				label : 'idCardNo',
				name : 'idCardNo',
				align : 'center',
				sortable : false
			}, {
				label : 'bankCardNum',
				name : 'bankCardNum',
				align : 'center',
				sortable : false
			}, {
				label:'userWallet.fee',
				name:'userWallet.fee',
				align:'center',
				sortable:false
			} ],
		    pager: '#pager',
		    rowNum:15,
		    rowList:[15,30,50],
		    sortname: 'id',
		    viewrecords: true,
		    sortorder: "desc",
		    caption: "人员列表",
		    gridComplete : function() { //在此事件中循环为每一行添加日志、废保和查看链接
				var ids = jQuery("#list").jqGrid('getDataIDs');
				for ( var i = 0; i < ids.length; i++) {
					var id = ids[i];
					var rowData = $('#list').jqGrid('getRowData', id);
					var	operateClick = '<c:if test="${!empty perMap[\'RETT_MU_USER_OP_UPDATE\']}">'
						+'<a href="${ctx}/user/toUserInfoPage?type=edit&id='+id+'" class="btn btn-primary btn-sm">' +
						'编辑' +
						'</a></c:if>' +
						'<c:if test="${!empty perMap[\'RETT_MU_USER_OP_DELETE\']}">'+
						'<a class="btn btn-primary btn-sm" onclick="deleteById('+ id + ')" >' +
						'删除' +
						'</a></c:if>';
					operateClick +='<c:if test="${!empty perMap[\'RETT_MU_USER_OP_PERMISSION\']}">' +
						'<a href="${ctx}/permission/toAddUserPermissionPage?userId='+id+'" class="btn btn-primary btn-sm">权限管理</a>' +
						'</c:if>';
					jQuery("#list").jqGrid('setRowData', id, {
						operate : operateClick
					});
				}
			}
		});
	});
	
	function search(){
		dataGrid.jqGrid("setGridParam",{
            url:"${ctx}/user/loadUserList?"+encodeURI($("#searchForm").serialize())
		}).trigger("reloadGrid");
	}
	
	function save(){
		var pwd = $.trim($("#password").val());
		var confirmPwd = $.trim($("#confirmPassword").val());
		if(pwd==null||pwd==''){
			alert("请输入密码！");
			return;
		}
		if(confirmPwd==null||confirmPwd==''){
			alert("请输入确认密码");
			return;
		}
		if(pwd!=confirmPwd){
			alert("两次输入的密码不一致");
			return;
		}
		$.ajax({
			url:"${ctx}/user/saveUser",
			type:"POST",
			dataType:"JSON",
			data:$("#saveForm").serialize(),
			success:function(response){
				$('#addModal').modal('hide');
				alert("保存成功");
				dataGrid.trigger("reloadGrid");
			}
		});
	}
	
	function setValueForUpdate(userInfo){
		if(userInfo){
			$("#name").val(userInfo.name);
			$("#age").val(userInfo.age);
			$("#idCardNo").val(userInfo.idCardNo);
			$("#mobile").val(userInfo.mobile);
			if(userInfo.gender){
				if(userInfo.gender==0){
					$("#select_id option[value='0']").attr("selected", true);
				}else{
					$("#select_id option[value='1']").attr("selected", true);
				}
			}
			$("#email").val(userInfo.email);
			$("#address").val(userInfo.address);
			$("#homeAddress").val(userInfo.homeAddress);
			$("#id").val(userInfo.id);
		}
	}
	
	function update(){
		$.ajax({
			url:"${ctx}/user/updateUser",
			type:"POST",
			dataType:"JSON",
			data:$("#updateForm").serialize(),
			success:function(response){
				$('#updateModal').modal('hide');
				alert("保存成功");
				dataGrid.trigger("reloadGrid");
			}
		});
	}
		
	function deleteById(id){
		$.ajax({
			url:"${ctx}/user/deleteById",
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
	
	function test(){
		$.ajax({
			url:"${ctx}/user/transactionTest",
			type:"POST",
			dataType:"JSON",
			success:function(response){
				alert("删除成功");
				dataGrid.trigger("reloadGrid");
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
					<th width="65px;"></th>
					<th width="150px;"></th>
				</tr>
				<tr>
					<td>
						姓名：
					</td>
					<td>
						<input type="text" id="name" name="name" >
					</td>
					<td>
						手机号：
					</td>
					<td>
						<input type="text" id="mobile" name="mobile" >
					</td>
					<td colspan="3">
						性别：
						<select id="genderType" name="gender">
							<option value="" selected>-请选择-</option>
							<option value="1">男</option>
							<option value="0">女</option>
						</select>
						类型：
						<select id="userType" name="userType">
							<option value="0" selected>全部</option>
							<option value="1">超级管理员</option>
							<option value="2">普通管理员</option>
							<option value="3">普通会员</option>
							<option value="4">招聘员</option>
							<option value="5">入职与期满审核员</option>
							<option value="7">提现审核员</option>
						</select>

					</td>
					<td align="right">
						<c:if test="${!empty perMap['RETT_MU_USER_OP_ADD']}">
							<a type='button' class="btn btn-primary btn-sm" href="${ctx}/user/toUserInfoPage?type=add">
								添加新用户
							</a>
							&nbsp;&nbsp;
						</c:if>
						<button type='button' class="btn btn-primary btn-sm" data-toggle="modal" onclick="search()">
							查询
						</button>
					</td>
				</tr>
			</table>
		</form>
		<div class="row">
			<div class="col-md-9">
				<table id="list"></table>
				<div id="pager"></div>
			</div>
		</div>
	</div>
</body>
</html>