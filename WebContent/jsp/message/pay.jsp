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
							url : '<%=basePath%>getAllPay', //请求方法的地址
			//title : '导入搜索',
			nowrap : false,
			//fit : true,
			fitColumns : true,
			pagination : true,
			pageSize : 50,
			pageList : [ 10, 50, 100 ],
			rownumbers : true,
			showFooter: true,
			remoteSort: false,
			singleSelect : false,
			iconCls : '',
			columns : [ [ {
				field:'check',
				checkbox:true
			},{
				title : '纳税人识别号',
				field : 'taxId',
				width : 50
			}, {
				title : '纳税人名称',
				field : 'taxName',
				width : 100
			}, {
				title : '办税员名称',
				field : 'taxAgentName',
				width : 30
			}, {
				title : '办税员号码',
				field : 'taxAgentMobile',
				width : 50
			}, {
				title : '财务负责人名称',
				field : 'adminName',
				width : 30
			}, {
				title : '财务负责人号码',
				field : 'adminMobile',
				width : 50
			}, {
				title : '法人名称',
				field : 'rep',
				width : 30
			}, {
				title : '法人号码',
				field : 'repMobile',
				width : 50
			}, {
				title : '缴款期限',
				field : 'deadline',
				width : 20
			}, {
				title : '欠缴税额',
				field : 'unpaidTax',
				width : 20
			}, {
				title : '征收项目',
				field : 'imposeType',
				width : 100,
			} ] ],
			toolbar : '#paySearch'
		});


		
		
		$('#readpayForm').form({
			url : '<%=basePath%>pay/read',
			onSubmit: function(){
				return $('#readpayForm').form('validate');
			},
			success : function(data) {
				 var res = jQuery.parseJSON(data);
					if (res) {
					$.messager.alert('操作提示', res.msg);
					$('#dg').datagrid('load');
					$('#file').val('');
				}
					else{
						$.messager.alert('操作提示', res.msg);
						$('#file').val('');
					}
			}
			
		});
			$('#formBtn').click(function(){	
				$('#readpayForm').form('submit');
		});
			
			
			$('#payMsgSend').click(function(){
				var sign = $('#sign').val();
				if (sign == null || sign == "") {
					$.messager.alert('操作提示', "请输入消息签名","info");
					return;
				}
				var rows = $('#dg').datagrid('getSelections');
				if (rows.length == 0) {
					$.messager.alert('操作提示', "请选择发送对象","info");
					return;
				} else {
					var formObj = sy.serializeObject($("#msgForm").form());
					var formStr = encodeURI(JSON.stringify(formObj),"UTF-8");
					var data = encodeURI(JSON.stringify(rows),"UTF-8");
					$.ajax({
						url : '<%=basePath%>sendPayMsg',
						type : "POST",
						dataType : "json",
						data : 'data=' + formStr + "=" + data,
						success : function(r) {
							$('#dg').datagrid("loading", "短信发送中……");
							$.messager.alert('操作提示', r.msg,r.result);
							$('#dg').datagrid("load",{});
							$('#dg').datagrid("loaded");
						},
						error : function() {
							$.messager.alert('操作提示', "服务器出错","error");
						}
					});
				}
				
			});
			$('#sendDate').datebox('setValue', formatterDate(new Date()));
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

	function formatterDate(date) {
		var day = date.getDate() > 9 ? date.getDate() : "0" + date.getDate();
		var month = (date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : "0"
		+ (date.getMonth() + 1);
		return date.getFullYear() + '-' + month + '-' + day;
		};
	
	function searchPay() {
		var data1 = sy.serializeObject($('#payTable1').form());
		var data = encodeURI(JSON.stringify(data1), "UTF-8");
		$('#dg').datagrid('load', data1);
	}

	function clearSearch() {
		$('#dg').datagrid("load", {});
		$('#payTable1').form("clear");
	}

</script>

<body class="easyui-layout">
	<div region="center" title="企业列表"
		 fit="true">
		<table id="dg" fit="true"></table>
		<div id="paySearch" style="height: 90px;">
			<form name="readpayForm" method="post"
				enctype="multipart/form-data" id="readpayForm">
				<table id="payTable" >
					<tr>
						<th>选择文件:</th>
						<td><input id="file" type="file" name="file" />
							<button id="formBtn" type="submit">导入</button></td>
					</tr>
				</table>
			</form>

			<form id="payTable1">
				<table id="payTable1" >
					<tr>
						<th>纳税人识别号:</th>
						<td><input id="taxId" type="text"
							class="easyui-validatebox textbox" name="taxId" /></td>
						<th>纳税人名称:</th>
						<td><input id="taxName" class="easyui-validatebox"
							type="text" name="taxName" /></td>
						<th>征收项目:</th>
						<td><input id="imposeType" class="easyui-validatebox"
							type="text" name="imposeType" /></td>
						<td colspan="2"><a class="easyui-linkbutton"
							icon="icon-search" href="javascript:void(0);"
							onclick="searchPay();">查找</a> <a id="clearBtn"
							class="easyui-linkbutton" icon="icon-cancel"
							href="javascript:void(0);" onclick="clearSearch();">重置</a></td>
					</tr>

				</table>
			</form>
			
			<form id="msgForm">
			<table>
				<tr>
					<th>签名:</th><td><input id="sign" class="easyui-validatebox"
						value="鄞州地税直属分局" name="sign">
					</td>
					<th>发送时间：</th><td><input id="sendDate" class="easyui-datebox"
						name="sendDate">
					</td>
					<td><a id="payMsgSend" icon="icon-ok" class="easyui-linkbutton"
						href="javascript:void(0);">发送</a></td>
				</tr>
			</table>
		</form>


		</div>

	</div>
</body>
</html>