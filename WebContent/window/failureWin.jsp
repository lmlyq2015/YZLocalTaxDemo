<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<script type="text/javascript">
var editFlag = undefined;
var msgRecord = $('#resultDg').datagrid('getSelected');
$(function(){
	$('#failMsgDg').datagrid({
		url:'<%=basePath%>getFailMsgState?msgId=' + msgRecord.id + '&failCount=' + msgRecord.failCount,
		pagination : true,
		pageSize:10,
		nowrap : false,
		pageList:[10,20,30],
		iconCls:'icon-reload',
		rownumbers: true,
		singleSelect:true,
		fitColumns:true,
		showFooter: true,
		remoteSort: false,
		onClickCell: onClickCell,
		columns:[[
			{
				title:'纳税人识别号',
				field:'taxNo',
				width:100					
			},
			{
				title:'纳税人名称',
				field:'taxName',
				width:200
			},
			{
				title:'状态码',
				field:'status',
				width:100
			},
			{
				title:'失败原因',
				field:'resultMsg',
				width:100
			},
			{
				title:'接收人',
				field:'receiver',
				width:100,
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
				title : '操作',
				field : 'operate',
				width:100,
				formatter:  function(value,row,index) {
					return '<a href="javascript:void(0);" class="easyui-linkbutton" onclick="reSendFailMsg()"><u>发送</u></a>';
				}
			}
		]],
		toolbar : 
			[
// 		     {
// 		    	 text : '修改短信接收人',
// 		    	 iconCls : "icon-edit",
// 		    	 handler : function() {
// 		 			var rows = $("#failMsgDg").datagrid('getSelections');
// 					if (rows.length < 1) {
// 						$.messager.alert('操作提示', '请选择要修改的行', 'warning');
// 					} else {
// 					 	if (editFlag != undefined) {
// 					 		$("#dg").datagrid('endEdit', editFlag);//结束编辑，传入之前编辑的行
// 					 	}
// 					 	if (editFlag == undefined) {
// 							var rows = $("#failMsgDg").datagrid('getSelections');
// 							var index = $("#failMsgDg").datagrid('getRowIndex', rows[0]);
// 							$("#failMsgDg").datagrid('beginEdit', index);
// 							editFlag = index;
// 					 	}	
// 					}
// 		    	 }
		    	 
// 		     } ,"-",
		     {
		    	 text : '撤销',
		    	 iconCls : "icon-redo",
		    	 handler : function() {
		 			$("#failMsgDg").datagrid('rejectChanges');
					editFlag = undefined;
		    	 }	 
		     },"-",
// 		 	{
// 		 	 	text: "保存",
// 		 	 	iconCls: "icon-save",
// 		 	 	handler: function () {
// 		 	 		$("#failMsgDg").datagrid('endEdit', editIndex);
// 		 	 	}
// 		 	},"-",
		 	{
		 		text: "导出失败信息",
		 	 	iconCls: "icon-save",
		 	 	handler: function () {
		 	 		exportFailMsg()
		 	 	}		
		 	},"-",
		 	{
		 		text: "关闭窗口",
		 	 	iconCls: "icon-cancel",
		 	 	handler: function () {
		 	 		$('#failMsgWin').window('close');
		 	 	}	
		 	}
		     ]

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
	if ($('#failMsgDg').datagrid('validateRow', editIndex)){
		$('#failMsgDg').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickCell(index, field){
	if (endEditing()){
		$('#failMsgDg').datagrid('selectRow', index)
				.datagrid('editCell', {index:index,field:field});
		editIndex = index;
	}
}
function reSendFailMsg() {
	var message = $('#resultDg').datagrid('getSelected');
	var receiver = $('#failMsgDg').datagrid('getSelected');
	var messageJson = encodeURI(JSON.stringify(message),"UTF-8");
	var receiverJson = encodeURI(JSON.stringify(receiver),"UTF-8");
	 $.messager.confirm("操作提示","确认发送？" , function (d) {
		 if (d) {
			 $.ajax({
	    			url : '<%=basePath%>reSendMsg',
	    			data : 'data=' + messageJson + '=' + receiverJson,
	    			dataType : 'json',
	    			type : 'post',
	    			success : function(r) {
	    				if(r) {
	    					$.messager.alert('操作提示', r.msg, r.result);
	    					$('#failMsgWin').window('close');
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

function exportFailMsg(){
		//alert("123");
		var msgRecord = $('#resultDg').datagrid('getSelected');
        var form = $("<form>");  
      	 form.attr('style','display:none');  
      	 form.attr('target','');  
      	 form.attr('method','post');  
      	 form.attr('action','<%=basePath%>report/exportFailMsg?msgId=' + msgRecord.id);  
      	 $('body').append(form); 
      	 form.form('submit',{

        success : function(r) {

		},
		error : function() {
			$.messager.alert('操作提示', '服务器出错', 'error');
		} 
});
}
	
</script>



<table id="failMsgDg" fit="true">

</table>

</html>