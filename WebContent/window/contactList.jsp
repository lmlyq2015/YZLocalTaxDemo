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
<script type="text/javascript">
$(function() {
$('#contactListDg').datagrid({
	url: '<%=basePath%>getContactList',
	loadMsg : '加载中...',
	fitColumns:true,
	singleSelect:true,
	nowrap : false,
	columns:[[
				{
					title : '联系人名称',
					field : 'name',
					width:40,	
				},
				{
					title : '联系方式',
					field : 'number',
					width:40,
				},
				{
					title : '请选择',
					field : 'operate',
					width:20,
					formatter : function(value, rec, rowIndex) {
					
						return "<input id='r1' type='radio' name='r1' value="+rec.number+">";					}
				}
	      	]],
	      	toolbar:'#numberInput',
	      	onClickRow: function(rowIndex, rowData){
	      		
  	      	  $("input[type='radio']")[rowIndex].checked = true;
  	      	}

});
// 	$('#number').focus(function () { 
// 		if ($('#number').val() ==  this.title) {
// 			$('#number').val("");
// 		}
// 	});
// 	$('#number').blur(function () { 
// 		if ($('#number').val() ==  "") {
// 			$('#number').val(this.title);
// 		}
// 	});
	
// 	$('#number').blur();
    $('#consultTranDlgBtn').click(function(){
    	
    	var numberInput = $('#number').val().trim();
    	var listSelected = isListSelected();
		if (numberInput == '' && !listSelected) {
			$.messager.alert('操作提示', "请选择电话号码","error");
		} else if (numberInput != '' && listSelected) {
			$.messager.alert('操作提示', "输入框和列表选择重复，请重新选择","error");
		} else {
			var number = numberInput==''?$('#contactListDg').datagrid('getSelected').number:numberInput;
			//alert(number);
			var type = $('#consultTranDlgBtn').attr('title');
			if (type == 'transfer') {
				$("#phone")[0].contentWindow.softphoneBar.exTransfer(number); 
			} else if (type == 'consult') {
				$("#phone")[0].contentWindow.softphoneBar.exConsult(number);
			} else {
				$.messager.alert('提示', '转接咨询初始化出错', 'error');
			}
			$('#consultTranDlg').dialog('close');
		}	
    });
    $('#clearBtn').click(function(){
    	
   	 if ($('#number').val() != '') {
   		 $('#number').val('');
   	 };
   	 var row =  $('#contactListDg').datagrid('getSelected');
   	 var index = $('#contactListDg').datagrid('getRowIndex',row);
   	 $('#contactListDg').datagrid('clearSelections');
   	 $('#number').blur();
   	 $("input[type='radio']")[index].checked = false;

   });
    $('#number').keydown(function(event){
    	if (event.keyCode == 13) {
    		$('#consultTranDlgBtn').click();
    	}
    });
});
function isListSelected() {
	var data = $('#contactListDg').datagrid('getData');
	for (var i = 0; i < data.rows.length; i++) {
		var checked =  $("input[type='radio']")[i].checked;
		if (checked) {
			return true;
		}
	}
	return false;
}
</script>
</head>
<table id="contactListDg" fit="true">

</table>
<div id="numberInput">
	<input id="number" type="text" style="width: 99%;height:25px;border:1px solid #a4a2a2;">
</div>				
</html>