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
		$(window).resize(function(){ 
			$("#dg").datagrid("resize",{width:getWidth(0.6)});
		});
		$('#dg').datagrid(
						{
							loadMsg : '数据加载中请稍后',
							url : '<%=basePath%>getAllReport', //请求方法的地址
			//title : '导入搜索',
			nowrap : false,
// 			fit : true,
			fitColumns : false,
			pagination : true,
			border : false,
			pageSize : 20,
			pageList : [ 10, 20, 100 ],
			rownumbers : true,
			showFooter: true,
			remoteSort: false,
			idField: 'id',
			singleSelect : false,
			iconCls : '',
			width: getWidth(0.6),
			columns : [ [ {
				field:'check',
				checkbox:true
			},{
				title : '纳税人识别号',
				field : 'taxId',
				width : fixWidthTable(0.15),
				align:'center'
			}, {
				title : '纳税人名称',
				field : 'taxName',
				width : fixWidthTable(0.25),
				align:'center'
			}, {
				title : '办税员名称',
				field : 'taxAgentName',
				width : fixWidthTable(0.10),
				align:'center'
			}, {
				title : '办税员号码',
				field : 'taxAgentMobile',
				width : fixWidthTable(0.15),
				align:'center'
			}, {
				title : '财务负责人名称',
				field : 'adminName',
				width : fixWidthTable(0.15),
				align:'center'
			}, {
				title : '财务负责人号码',
				field : 'adminMobile',
				width : fixWidthTable(0.15),
				align:'center'
			}, {
				title : '法人名称',
				field : 'rep',
				width : fixWidthTable(0.10),
				align:'center'
			}, {
				title : '法人号码',
				field : 'repMobile',
				width : fixWidthTable(0.15),
				align:'center'
			}, {
				title : '所属时期起',
				field : 'startTime',
				width : fixWidthTable(0.10),
				align:'center'
			}, {
				title : '所属时期止',
				field : 'endTime',
				width : fixWidthTable(0.10),
				align:'center'
			}, {
				title : '征收项目',
				field : 'imposeType',
				width : fixWidthTable(0.15),
				align:'center'
			} ] ],
			toolbar : '#reportSearch'
		});


		
		
		$('#readReportForm').form({
			url : '<%=basePath%>report/read',
			onSubmit: function(){
				return $('#readReportForm').form('validate');
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
				var file = $('#file').val();
				if (file == null || file == "") {
					$.messager.alert('操作提示', "请选择导入文件","info");
					return false;
				} else if (file.replace(/.+\./,"") != "xls" && file.replace(/.+\./,"") != "xlsx") {
					$.messager.alert('操作提示', "导入文件类型错误","info");
					$('#file').val('');
					return false;
				} else {
				$('#dg').datagrid("loading");
				$('#readReportForm').submit();}
		});
			
			
			$('#ReportMsgSend').click(function(){
// 				var sign = $('#sign').val();
// 				if (sign == null || sign == "") {
// 					$.messager.alert('操作提示', "请输入消息签名","info");
// 					return;
// 				}
				var rows = $('#dg').datagrid('getSelections');
				if (rows.length == 0) {
					$.messager.alert('操作提示', "请选择发送对象","info");
					return;
				} else {
// 					var formObj = sy.serializeObject($("#msgForm").form());
// 					var formStr = encodeURI(JSON.stringify(formObj),"UTF-8");
					var data = encodeURI(JSON.stringify(rows),"UTF-8");
					$.ajax({
						url : '<%=basePath%>sendReportMsg',
						type : "POST",
						dataType : "json",
// 						data : 'data=' + formStr + "=" + data,
						data : 'data=' + data,
						success : function(r) {
							$.messager.alert('操作提示', r.msg,r.result);
							$('#dg').datagrid("reload");
						},
						error : function() {
							$.messager.alert('操作提示', "服务器出错","error");
						}
					});
				}
				
			});
			
			
			$('#ReportDelete').click(function(){
				var rows = $('#dg').datagrid('getSelections');
				if (rows.length == 0) {
					$.messager.alert('操作提示', "请选择删除对象","info");
					return;
				} else {
					var data = encodeURI(JSON.stringify(rows),"UTF-8");
					$.ajax({
						url : '<%=basePath%>deleteReport',
						type : "POST",
						dataType : "json",
						data : 'data=' + data,
						success : function(r) {
							$.messager.alert('操作提示', r.msg,r.result);
							$('#dg').datagrid("reload");
						},
						error : function() {
							$.messager.alert('操作提示', "服务器出错","error");
						}
					});
				}			
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

	function formatterDate(date) {
		var day = date.getDate() > 9 ? date.getDate() : "0" + date.getDate();
		var month = (date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : "0"
		+ (date.getMonth() + 1);
		return date.getFullYear() + '-' + month + '-' + day;
		};
	
	function searchReport() {
		var data1 = sy.serializeObject($('#reportTable1').form());
		var data = encodeURI(JSON.stringify(data1), "UTF-8");
		$('#dg').datagrid('load', data1);
	}

	function clearSearch() {
		$('#dg').datagrid("load", {});
		$('#reportTable1').form("clear");
		$('#dg').datagrid("clearSelections");
	}
	
	function getWidth(percent){  
	    return $(window).width() * percent;  
	}
	
	function fixWidthTable(percent){  
        return getWidth(0.6) * percent;  
	} 
	

</script>

<body class="easyui-layout">
	<div region="center" title="企业列表" fit="true">
		<table id="dg" fit="true"></table>
		<div id="reportSearch" style="height: 60px;">
			<form name="readReportForm" method="post"
				enctype="multipart/form-data" id="readReportForm">
				<table id="reportTable">
					<tr>
						<th>选择文件:</th>
						<td><input id="file" type="file" name="file" /> <a
							id="formBtn" href="javascript:void(0);" class="easyui-linkbutton">导入</a></td>
					</tr>
				</table>
			</form>

			<form id="reportTable1">
				<table id="reportTable1">
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
							onclick="searchReport();">查找</a> <a id="clearBtn"
							class="easyui-linkbutton" icon="icon-cancel"
							href="javascript:void(0);" onclick="clearSearch();">重置</a></td>
						<td><a id="ReportMsgSend" icon="icon-ok"
							class="easyui-linkbutton" href="javascript:void(0);">发送</a></td>
						<td><a id="ReportDelete" icon="icon-tip"
							class="easyui-linkbutton" href="javascript:void(0);">删除</a></td>
					</tr>

				</table>
			</form>

			<!-- 			<form id="msgForm"> -->
			<!-- 			<table> -->
			<!-- 				<tr> -->
			<!-- 					<th>签名:</th><td><input id="sign" class="easyui-validatebox" -->
			<!-- 						value="鄞州地税直属分局" name="sign"> -->
			<!-- 					</td> -->
			<!-- 					<th>发送时间：</th><td><input id="sendDate" class="easyui-datebox" -->
			<!-- 						name="sendDate"> -->
			<!-- 					</td> -->
			<!-- 					<td><a id="ReportMsgSend" icon="icon-ok" class="easyui-linkbutton" -->
			<!-- 						href="javascript:void(0);">发送</a></td> -->
			<!-- 				</tr> -->
			<!-- 			</table> -->
			<!-- 		</form> -->


		</div>

	</div>
</body>
</html>