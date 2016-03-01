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
$(function(){
	$(window).resize(function(){ 
		 $("#resultDg").datagrid("resize",{width:getWidth(0.6)});   
	});
	$('#resultDg').datagrid({
		url:'<%=basePath%>getMessageResultList',
		title:'搜索',
		pagination : true,
		pageSize: 10,
		nowrap : false,
		pageList:[10,20,30],
		iconCls:'icon-reload',
		rownumbers: true,
		singleSelect:false,
		fitColumns:false,
		showFooter: true,
		onClickCell: onClickCell,
		idField: 'id',
		remoteSort: false,
		width: getWidth(0.6),
// 		sortName: 'sendDate',
// 		sortOrder: 'desc',
		columns:[[{
			field:'check',
			checkbox:true
		},
// 			{
// 				//title:'<input id=\"detailcheckbox\" type=\"checkbox\"  >',
// 				field:'check',
// 				checkbox:true
// 				//width:2,
//  				formatter: function (value, rec, rowIndex) {
//                      return "<input type=\"checkbox\"  name=\"PD\" value=\"" + value + "\" >";
//                  }
// 			},
			{
				title:'消息编号',
				field:'id',
				hidden:'true',
				width:fixWidthTable(0.05),
				align:'center'	
				
			},{
				title:'纳税人识别号',
				field:'taxId',
				width:fixWidthTable(0.25),
				align:'center'
			},
			{
				title:'消息内容',
				field:'content',
				width:fixWidthTable(0.35),
				align:'center'
			},
			{
				title:'发送时间',
				field:'sendDate',
				width:fixWidthTable(0.3),
				align:'center'
			},
			{
				title:'发送人',
				field:'sendBy',
				width:fixWidthTable(0.2),
				align:'center'
			},
			{
				title:'接收人',
				field:'receiver',
				width:fixWidthTable(0.2),
				align:'center',
				editor: {//设置其为可编辑
					type: 'combobox',
					options: {
					required: true,
					valueField : 'RecId',
					textField : 'RecName',
					url : '<%=basePath%>window/RecJson.json',
					}
					},
					formatter:  function(value,row,index) {
						if (value == 1) {
							return "办税员";
						} else if (value == 2) {
							return "财务主管";
						} else if (value == 3) {
							return "法人";
						} else {
							return value;
						}
					}
			},
			{
				title:'状态',
				field:'operate',
				width:fixWidthTable(0.1),
				align:'center',
				formatter: function (value, rec, rowIndex) {
					if (rec.failCount > 0) {
						return '<font color="red">' + "失败" + '</font>';
					} else {
						return "成功";
					}
				}
			},
			{
				title:'状态详情',
				field:'resultMsg',
				width:fixWidthTable(0.2),
				align:'center'
			}
		]],
		toolbar:'#resultToolbar'

	});	

	
	
	
	
	
	initFailMsgWin();
	
	$('#clearBtn').click(function(){
		
		$('#searchForm').form('clear');
	});
	
});

$.extend($.fn.datagrid.methods, {
	editCell: function(jq,param){
		return jq.each(function(){
			var opts = $(this).datagrid('options');
			var fields = $(this).datagrid('getColumnFields',true).concat($(this).datagrid('getColumnFields'));
			for(var i=0; i<fields.length; i++){
				var col = $(this).datagrid('getColumnOption', fields[i]);
				col.editor1 = col.editor;
				if (fields[i] != param.field){
					col.editor = null;
				}
			}
			$(this).datagrid('beginEdit', param.index);
			for(var i=0; i<fields.length; i++){
				var col = $(this).datagrid('getColumnOption', fields[i]);
				col.editor = col.editor1;
			}
		});
	}
});
var editIndex = undefined;
function endEditing(){
	if (editIndex == undefined){return true;}
	if ($('#resultDg').datagrid('validateRow', editIndex)){
		$('#resultDg').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickCell(index, field){
	if (endEditing()){
		$('#resultDg').datagrid('selectRow', index)
				.datagrid('editCell', {index:index,field:field});
		editIndex = index;
	}
}


function openFailureWin(row) {
	$('#failMsgWin').window('open');
 	$('#failMsgWin').window('refresh','<%=basePath%>window/failureWin.jsp');
}
function initFailMsgWin() {
	$('#failMsgWin').window({
		title : '详细信息',
		width : 800,
		modal : true,
		shadow : true,
		closed : true,
		height : 250,
		resizable : false,
		onBeforeOpen : function() {
			
		}
	});
}
function reSendMsg() {
	alert('123');
}

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

function searchStatus() {
	var data1 = sy.serializeObject($('#searchForm').form());
// 	var st = $('#status').combobox('getText');
// 	var statusValue = encodeURI(st);
	var data = encodeURI(JSON.stringify(data1), "UTF-8");
	$('#resultDg').datagrid('load', data1);
}

function clearSearch() {
	$('#resultDg').datagrid("load", {});
	$('#searchForm').form("clear");
}

function getWidth(percent){  
    return $(window).width() * percent;  
}

function fixWidthTable(percent){  
    return getWidth(0.6) * percent;  
}

function exportFailMsg(){
	var rows = $('#resultDg').datagrid('getSelections');
	var i = 0;
	if (rows.length == 0) {
		$.messager.alert('操作提示', "请选择导出对象","info");
		return;
	}else if(rows.length > 10){
		$.messager.alert('操作提示', "请选择小于等于十条的数据","info");
		return;
	}else{
		var arr = [] ;
			 for(i;i < rows.length;i++){
				 var obj = new Object();
				 obj.id = rows[i].id;
				 arr.push(obj);
			 }
			 var data = encodeURI(JSON.stringify(arr),"UTF-8");
		     var form = $("<form>");  
		  	 form.attr('style','display:none');  
		  	 form.attr('target','');  
		  	 form.attr('method','post');  
		  	 form.attr('action','<%=basePath%>report/exportFailMsg?data=' + data);  
		  	 $('body').append(form); 
	      	 form.form('submit',{
	            success : function(r) {
	            	$('#resultDg').datagrid("clearSelections");
	            	$('#resultDg').datagrid("reload");
	     		},
	     		error : function() {
	     			$.messager.alert('操作提示', '服务器出错', 'error');
	     			$('#resultDg').datagrid("clearSelections");
	     		} 
	     });
}
}

function reSendFailMsg() {
	var rows = $('#resultDg').datagrid('getSelections');
	if (rows.length == 0) {
		$.messager.alert('操作提示', "请选择发送对象","info");
		return;
	}else if(rows.length > 10){
		$.messager.alert('操作提示', "请选择小于等于十条的数据","info");
		return;
	}else{
		var data = encodeURI(JSON.stringify(rows),"UTF-8");
		$.messager.confirm("操作提示","确认发送？" , function (d) {
			if (d) {
				 $.ajax({
		    			url : '<%=basePath%>reSendMsg',
		    			data : 'data=' + data,
		    			dataType : 'json',
		    			type : 'post',
		    			success : function(r) {
		    				if(r) {
		    					$.messager.alert('操作提示', r.msg, r.result);
		    					$('#resultDg').datagrid("clearSelections");
	    						$('#resultDg').datagrid('reload');
		    			}
				 },
	    			error : function() {
	    				$.messager.alert('操作提示', '服务器出错', 'error');
	    			} 
				 });
		}
		 });
	}
}	

function updateReceiver(){
	var rows = $('#resultDg').datagrid('getSelections');
	var rec = $('#receiverSelect').combobox('getValue');
	if (rows.length == 0) {
		$.messager.alert('操作提示', "请选择修改对象","info");
		return;
	}else{
	for(var i = 0;i < rows.length;i++){
	   $('#resultDg').datagrid('updateRow',{
		   index: i,
		   row:{
			   receiver: rec
		   }});
	}
}
}
// 	$('#failMsgDg').datagrid('selectRow',index);
// 	$('#failMsgDg').datagrid('refreshRow',index);
// 	var message = $('#resultDg').datagrid('getSelected');
// 	var receiver = $('#failMsgDg').datagrid('getSelected');
// 	var messageJson = encodeURI(JSON.stringify(message),"UTF-8");
// 	var receiverJson = encodeURI(JSON.stringify(receiver),"UTF-8");
// 	 $.messager.confirm("操作提示","确认发送？" , function (d) {
// 		 if (d) {
// 			 $.ajax({
<%-- 	    			url : '<%=basePath%>reSendMsg', --%>
// 	    			data : 'data=' + messageJson + '=' + receiverJson,
// 	    			dataType : 'json',
// 	    			type : 'post',
// 	    			success : function(r) {
// 	    				if(r) {
// 	    					$.messager.alert('操作提示', r.msg, r.result);
// 	    					$('#failMsgWin').window('close');
// 	    					$('#resultDg').datagrid('reload');
// 	    				}
// 	    			},
// 	    			error : function() {
// 	    				$.messager.alert('操作提示', '服务器出错', 'error');
// 	    			} 
// 			 });			 
// 		 }
// 	 });
	
</script>
<body class="easyui-layout" style="width: 100%; height: 100%;">

	<div region="center" title="发送状态列表">
		<table id="resultDg" fit="true"></table>
	</div>

	<div id="resultToolbar" style="height: 57px;">
		<form id="searchForm">
			<table>
				<tr>
					<th>消息内容:</th>
					<td><input id="content" class="easyui-validatebox"
						name="content"></td>
					<th>发送时间:</th>
					<td><input id="sendDate" class="easyui-datebox"
						name="sendDate" editable="false"> - <input
						id="sendDateEnd" class="easyui-datebox" name="sendDateEnd"
						editable="false"></td>
					<th>发送结果:</th>
					<td><select id="status" class="easyui-combobox" name="status"
						editable="false">
							<option value="请选择">--请选择--</option>
							<option value="发送失败">失败</option>
							<option value="发送成功">成功</option>
					</select></td>
					<th>发送人:</th>
					<td><input id="sendBy" class="easyui-validatebox"
						name="sendBy"></td>
					<td><a id="searchBtn" class="easyui-linkbutton"
						href="javascript:void(0)" icon="icon-search"
						onclick="searchStatus();">查找</a></td>
					<td><a id="clearBtn" class="easyui-linkbutton"
						href="javascript:void(0)" icon="icon-cancel"
						onclick="clearSearch();">重置</a></td>
				</tr>
				<tr>
					<td><a id="exportBtn" class="easyui-linkbutton"
						href="javascript:void(0)" icon="icon-save"
						onclick="exportFailMsg();">导出失败</a></td>
					<td><a id="sendBtn" class="easyui-linkbutton"
						href="javascript:void(0)" icon="icon-report"
						onclick="reSendFailMsg();">重发短信</a></td>
					<th>接收人:</th>
					<td><select id="receiverSelect" name="receiverSelect"
						class="easyui-combobox" editable="false">
							<option value="1">办税员</option>
							<option value="2">财务主管</option>
							<option value="3">法人</option>
					</select><a id="updateBtn" class="easyui-linkbutton"
						href="javascript:void(0)" icon="icon-edit"
						onclick="updateReceiver();">批量修改接收人</a></td>

				</tr>

			</table>

		</form>
	</div>
</body>
</html>