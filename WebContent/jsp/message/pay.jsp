<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<base href="<%=basePath%>">
<link rel="stylesheet" type="text/css" href="themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="themes/icon.css">
<link rel="stylesheet" type="text/css" href="themes/demo.css">
<script type="text/javascript" src="jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="jquery/jquery.easyui.min.js"></script>
<script type="text/javascript" src="locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	$(function() {
		$('#dg').datagrid(
						{
							loadMsg : '数据加载中请稍后3231',
							url : '<%=basePath%>getAllCustomer', //请求方法的地址
			title : '催报信息',
			nowrap : false, 
			fitColumns : false, 
			pagination : true, 
			fit : true,
			pageSize : 3,
			pageList : [ 3, 5, 10 ],
			rownumbers : true, 
			singleSelect : false,
			iconCls : '',
			idField : 'id',
			columns : [ [ {
				title : '纳税人识别号',
				field : 'compName',
				editor : 'text',
				align : 'center',
				width : 70
			}, {
				title : '纳税人名称',
				field : 'cusName',
				editor : 'text',
				align : 'center',
				width : 70
			}, {
				title : '办税员名称',
				field : 'contact',
				editor : 'text',
				align : 'center',
				width : 70
			}, {
				title : '办税员号码',
				field : 'address',
				editor : 'text',
				align : 'center',
				width : 70
			}, {
				title : '财务负责人名称',
				field : 'totalMoney',
				editor : 'text',
				align : 'center',
				width : 70
			}, {
				title : '财务负责人号码',
				field : 'empId',
				align : 'center',
				width : 70
			}, {
				title : '法人名称',
				field : 'empId',
				align : 'center',
				width : 70
			}, {
				title : '法人号码',
				field : 'empId',
				align : 'center',
				width : 70
			}, {
				title : '缴款期限',
				field : 'empId',
				align : 'center',
				width : 70
			},  {
				title : '应缴税额',
				field : 'empId',
				align : 'center',
				width : 70
			}, {
				title : '实缴税额',
				field : 'empId',
				align : 'center',
				width : 70
			},  {
				title : '欠缴税额',
				field : 'empId',
				align : 'center',
				width : 70
			},  {
				title : '征收项目',
				field : 'empId',
				align : 'center',
				width : 70
			},  {
				title : '纳税人电子档案号',
				field : 'empId',
				align : 'center',
				width : 70
			},] ],
			toolbar : [ {
				id : 'btnImport',
				text : '导入',
				iconCls : 'icon-tip',
				handler : function() {

				}
			}, '-', {
				id : 'btnAdd',
				text : '添加',
				iconCls : 'icon-add',
				handler : function() {
					addCustomer('add');
				}
			}, '-', {
				id : 'btnEdit',
				text : '修改',
				iconCls : 'icon-edit',
				handler : function() {
					editCustomer('edit');
				}
			}, '-', {
				id : 'btnDel',
				text : '删除',
				iconCls : 'icon-cancel',
				handler : function() {
					deleteCustomer();
				}
			}, '-' ]
		});

	});
</script>

<body class="easyui-layout" fit="true">
	<div region="north" title="催报信息查询" split="true" style="height: 100px;">
		<table id="customerTable" style="width: 100%; height: 100%">
			<tr>
				<th>纳税人识别号:</th>
				<td><input id="compName" type="text"
					class="easyui-validatebox textbox" name="compName" /></td>
				<th>纳税人名称:</th>
				<td><input id="cusName" class="easyui-validatebox" type="text"
					name="cusName" /></td>
				<th>缴款期限:</th>
				<td><input id="beginMoney" class="easyui-validatebox"
					type="text" name="beginMoney" /></td>
				<th>征收项目:</th>
				<td><input id="beginMoney" class="easyui-validatebox"
					type="text" name="beginMoney" /></td>
				<td colspan="2"><a class="easyui-linkbutton" icon="icon-search"
					href="javascript:void(0);" onclick="searchCustomer();">查找</a> <a
					id="clearBtn" class="easyui-linkbutton" icon="icon-cancel"
					href="javascript:void(0);" onclick="clearSearch();">重置</a></td></tr>
		</table>
	</div>

	<div region="center">
		<table id="dg" class="easyui-datagrid" style="width: 100%; height: 100%"></table>
	</div>
</body>
</html>