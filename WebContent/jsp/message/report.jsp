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
							loadMsg : '数据加载中请稍后',
							url : '<%=basePath%>getAllReport', //请求方法的地址
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
				field : 'taxId',
				editor : 'text',
				align : 'center',
				width : 100
			}, {
				title : '纳税人名称',
				field : 'taxName',
				editor : 'text',
				align : 'center',
				width : 100
			}, {
				title : '办税员名称',
				field : 'taxAgentName',
				editor : 'text',
				align : 'center',
				width : 100
			}, {
				title : '办税员号码',
				field : 'taxAgentMobile',
				editor : 'text',
				align : 'center',
				width : 100
			}, {
				title : '财务负责人名称',
				field : 'adminName',
				editor : 'text',
				align : 'center',
				width : 100
			}, {
				title : '财务负责人号码',
				field : 'adminMobile',
				align : 'center',
				width : 100
			}, {
				title : '法人名称',
				field : 'rep',
				align : 'center',
				width : 100
			}, {
				title : '法人号码',
				field : 'repMobile',
				align : 'center',
				width : 100
			}, {
				title : '申报年',
				field : 'year',
				align : 'center',
				width : 100
			}, {
				title : '申报月',
				field : 'month',
				align : 'center',
				width : 100
			}, {
				title : '征收项目',
				field : 'imposeType',
				align : 'center',
				width : 100
			} ] ],
			toolbar : '#reportSearch'

		});

	});

	var sy = $.extend({}, sy);
	sy.serializeObject = function(form) { /*将form表单内的元素序列化为对象，扩展Jquery的一个方法*/
		var o = {};
		$.each(form.serializeArray(), function(index) {
			if (o[this['name']]) {
				o[this['name']] = o[this['name']] + "," + this['value'];
			} else {
				o[this['name']] = this['value'];
			}
		});
		return o;
	};

	function searchReport() {
		var data1 = sy.serializeObject($('#reportTable').form());
		var data = encodeURI(JSON.stringify(data1), "UTF-8");
		$('#dg').datagrid('load', data1);
	}

	function clearSearch() {
		$('#dg').datagrid("load", {});
		$('#reportTable').form("clear");
	}

	  function check() {  
		     var excel_file = $('#excel_file').val();  
		     if (excel_file == "" || excel_file.length == 0) {  
		         alert("请选择文件路径！");  
		         return false;  
		     } else {  
		        return true;  
		    }  
		} 
</script>

<body class="easyui-layout" fit="true">
	<div region="center">
		<table id="dg" class="easyui-datagrid"
			style="width: 100%; height: 100%"></table>
		<div id="reportSearch" style="height: 60px;">
			<form name="readReportForm" method="post" action="<%=basePath%>/report/read"
				enctype="multipart/form-data" id="readReportForm">
				<table id="reportTable" style="width: 400px; height: 100%">
					<tr>
						<th>选择文件:</th>
						<td><input id="file" type="file" name="file" />
							<button type="submit">导入</button></td>
					</tr>
				</table>
			</form>

			<form id="reportTable">
				<table id="reportTable" style="width: 100%; height: 100%">
					<tr>
						<th>纳税人识别号:</th>
						<td><input id="taxId" type="text"
							class="easyui-validatebox textbox" name="taxId" /></td>
						<th>纳税人名称:</th>
						<td><input id="taxName" class="easyui-validatebox"
							type="text" name="taxName" /></td>
						<th>申报年月:</th>
						<td><input id="year" class="easyui-validatebox" type="text"
							name="year" /><input id="month" class="easyui-validatebox"
							type="text" name="month" /></td>
						<th>征收项目:</th>
						<td><input id="imposeType" class="easyui-validatebox"
							type="text" name="imposeType" /></td>
						<td colspan="2"><a class="easyui-linkbutton"
							icon="icon-search" href="javascript:void(0);"
							onclick="searchReport();">查找</a> <a id="clearBtn"
							class="easyui-linkbutton" icon="icon-cancel"
							href="javascript:void(0);" onclick="clearSearch();">重置</a></td>
					</tr>

				</table>
			</form>

		</div>

	</div>
</body>
</html>