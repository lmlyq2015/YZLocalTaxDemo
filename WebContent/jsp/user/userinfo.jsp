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
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="themes/icon.css">
<link rel="stylesheet" type="text/css" href="themes/demo.css">
<script type="text/javascript" src="jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="jquery/jquery.easyui.min.js"></script>
<script type="text/javascript" src="locale/easyui-lang-zh_CN.js"></script>
<style type="text/css">
#fm {
	margin: 0;
	padding: 10px 30px;
}

.ftitle {
	font-size: 14px;
	font-weight: bold;
	padding: 5px 0;
	margin-bottom: 10px;
	border-bottom: 1px solid #ccc;
}

.fitem {
	margin-bottom: 5px;
}

.fitem label {
	display: inline-block;
	width: 80px;
}
</style>
<script type="text/javascript">
	$(function() {
		$('#dg').datagrid(
						{
							loadMsg : '数据加载中请稍后',
							url : '<%=basePath%>getAllUser',
			title : '客户信息',
			nowrap : false, //文字自动换行
			fitColumns : true, //列自适应
			pagination : true, //底部显示分页工具栏
			fit : true,
			pageSize:10,
			pageList:[3,5,10],
			rownumbers : true, // 当true时显示行号 
			singleSelect : true, // 只允许当前选择一行
			iconCls : 'icon-save',
			idField : 'id', //标识列
			columns : [ [ {
				title : '员工编号',
				field : 'empId',
				editor : 'text',
				align : 'center',
				width : 50
			}, {
				title : '登录名',
				field : 'loginName',
				editor : 'text',
				align : 'center',
				width : 50
			}, {
				title : '手机号码',
				field : 'contact',
				editor : 'text',
				align : 'center',
				width : 50
			}, {
				title : '邮件',
				field : 'email',
				editor : 'text',
				align : 'center',
				width : 50
			}, {
				title : '最后登录',
				field : 'lastLoginDate',
				editor : 'text',
				align : 'center',
				width : 50
			}, {
				title : '是否添加自己为收件人',
				field : 'sendToSelf',
				align : 'center',
				width : 50,
				editor: {
					type: 'combobox',
					options: {
					required: true,
					valueField : 'id',
					textField : 'result',
					url : '<%=basePath%>jsp/util/SendToSelf.json',
					}
					},
					formatter:  function(value,row,index) {
						if (value == 1) {
							return "是";
						} else if (value == 2) {
							return "否";
						} else {
							return value;
						}
					}
			}
			] ],
			toolbar : '#userSearch'
		});

		$('#searchBtn').click(function(){
			var data1 = sy.serializeObject($('#userSearchForm').form());
			var data = encodeURI(JSON.stringify(data1), "UTF-8");
			$('#dg').datagrid('load', data1);

		});
		
		$('#cancelBtn').click(function(){
			
			$('#dg').datagrid("load", {});
			$('#userSearchForm').form("clear");
			
		});
		
	});
	
	var sy = $.extend({}, sy);
	sy.serializeObject = function (form) { /*将form表单内的元素序列化为对象，扩展Jquery的一个方法*/
	    var o = {};
	    $.each(form.serializeArray(), function (index) {
	        if (o[this['name']]) {
	            o[this['name']] = o[this['name']] + "," + this['value'];
	        } else {
	            o[this['name']] = this['value'];
	        }
	    });
	    return o;
	};
	
	
// 	function searchCustomer() {
// 		var data1 = sy.serializeObject($('#userSearchForm').form());
// 		 var data = encodeURI(JSON.stringify(data1),"UTF-8");
// 		 $('#g').datagrid('load', data1);
// 	}
	
	
    var url;
    var type;
    function addCustomer() {
        $('#dlg').dialog("open").dialog('setTitle', '添加客户信息'); ;
        $('#fm').form("clear");    
    }
    function editCustomer() {
        var row = $('#dg').datagrid("getSelected");
        if (row) {
            $('#dlg').dialog("open").dialog('setTitle', '修改客户信息');
            $('#fm').form("load", row);
        }
    }
    function saveCustomer() {	
    	 var data1 = sy.serializeObject($('#fm').form());
    	 var data = encodeURI(JSON.stringify(data1),"UTF-8");  	 
    	 var title = $('#dlg').panel('options').title;
    	 if (title.indexOf('添加') >=0) {
    		 url = '<%=basePath%>addCustomer';
    	 } else {
    		 var row = $('#dg').datagrid("getSelected");
    		 url = '<%=basePath%>editCustomer?cusId=' + row.cusId;
    	 }
    	 $.ajax({
    		 url : url,
    		 data : 'data=' + data,
    		 dataType : 'json',
    		 type : 'post',
    		 success : function(result) {
                 if (result) {
                     $.messager.alert("提示信息", result.msg);
                     $('#dlg').dialog("close");
                     $('#dg').datagrid("load");
                 }
                 else {
                     $.messager.alert("提示信息", "操作失败");
                 }
    		 },
    		 error : function() {
    			 
    		 }
    	 });
    }
    function deleteCustomer() {
        var row = $('#dg').datagrid('getSelected');
      
                if (row) {
                	$.ajax({
                		url:'<%=basePath%>deleteCustomer?cusId=' + row.cusId,
                		dataType : 'json',
                		type : 'post',
                		success : function(result){
                            if (result) {
                                $('#dg').datagrid('reload');
                                $.messager.alert("提示信息", result.msg);
                            } else {
                            	$.messager.alert("提示信息", "操作失败");
                            
                		
                		
                	}   
                }
            });
        }
    }
</script>
</head>
<body class="easyui-layout">
	<div id="g" region="center" fit="true">
		<table id="dg">
		</table>
	</div>

	<div id="dlg" class="easyui-dialog"
		style="width: 400px; height: 280px; padding: 10px 20px;" closed="true"
		buttons="#dlg-buttons">
		<form id="fm" method="post">
			<div class="fitem">
				<label> 客户名称 </label> <input name="compName"
					class="easyui-validatebox" required="true" />
			</div>
			<div class="fitem">
				<label> 客户联系人</label> <input name="cusName"
					class="easyui-validatebox" required="true" />
			</div>
			<div class="fitem">
				<label> 联系电话</label> <input name="contact"
					class="easyui-validatebox" required="true" />
			</div>
			<div class="fitem">
				<label> 地址</label> <input name="address" class="easyui-validatebox"
					required="true" />
			</div>
			<div class="fitem">
				<label> 应收款</label> <input name="totalMoney"
					class="easyui-vlidatebox" required="true" />
			</div>
			<div class="fitem">
				<label> 经手人</label> <input id="empName1" type="text"
					class="easyui-combobox" name="empId" editable="false" />
			</div>
		</form>
	</div>

	<div id="dlg-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			onclick="saveCustomer();" iconcls="icon-save">保存</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			onclick="javascript:$('#dlg').dialog('close')" iconcls="icon-cancel">取消</a>
	</div>
	<div id="userSearch" style="height: 60px;" fit="true">
	  	<form id="userSearchForm">
	  		<table>
	  			<tr>
	  			<th>
	  			员工编号:
	  			</th>
	  			<td>
	  			<input id="empId" class="easyui-validatebox"  name="empId">
	  			</td>
	  			<th>
	  			登录名:
	  			</th>
	  			<td>
	  			<input id="loginName" class="easyui-validatebox"  name="loginName">
	  			</td>
	  			<th>
	  			登录时间:
	  			</th>
	  			<td>
	  			<input id="beginDate" class="easyui-datebox"  name="beginDate"> - 
	  			<input id="endDate" class="easyui-datebox"  name="endDate">
	  			</td>
	  			<td>
	  			<a id="searchBtn"class="easyui-linkbutton" href="javascript:void(0)" icon="icon-search">搜索</a>
	  			</td>
	  			<td>
	  			<a id="cancelBtn" class="easyui-linkbutton" href="javascript:void(0)" icon="icon-cancel">清除</a>
	  			</td>
	  			</tr>
	  		</table>
	  	</form>
	  </div>
</body>
</html>