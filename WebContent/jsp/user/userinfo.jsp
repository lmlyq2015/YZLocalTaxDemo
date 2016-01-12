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
var editIndex = undefined;
	$(function() {
		$('#dg').datagrid({
			loadMsg : '数据加载中请稍后',
			url : '<%=basePath%>getAllUser',
			title : '客户信息',
			nowrap : false, //文字自动换行
			fitColumns : true, //列自适应
			pagination : true, //底部显示分页工具栏
			fit : true,
			pageSize:10,
			onClickCell: onClickCell,
			pageList:[3,5,10],
			rownumbers : true, // 当true时显示行号 
			singleSelect : true, // 只允许当前选择一行
			iconCls : 'icon-save',
			idField : 'id', //标识列
			columns : [ [ {
				title : '员工编号',
				field : 'empId',
				align : 'center',
				width : 50
			}, {
				title : '登录名',
				field : 'loginName',
				align : 'center',
				width : 50
			}, {
				title : '手机号码',
				field : 'contact',
				align : 'center',
				width : 50
			}, {
				title : '邮件',
				field : 'email',
				align : 'center',
				width : 50
			}, {
				title : '最后登录',
				field : 'lastLoginDate',
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
					panelHeight: 'auto',
					required: true,
					valueField : 'id',
					textField : 'result',
					url : '<%=basePath%>jsp/util/SendToSelf.json',
					}
					},
					formatter:  function(value,row,index) {
						if (value == "是") {
							return "是";
						} else if (value =="否") {
							return "否";
						} else {
							return value;
						}
					}
// 					onChange:function(newValue,oldValue) {
// 						if (newValue != oldValue) {
// 							return '<font color="red">'+newValue+'</font>';
// 						} else {
// 							return oldValue;
// 						}
// 						alert(newValue);
// 					}
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
// 		$('#imgLoginNameOk').hide();
// 		$('#imgOk').hide();
// 		$('#imgInvalid').hide();
// 		$('#imgLoginNameInvalid').hide();
		
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
        $('#dlg').dialog("open").dialog('setTitle', '员工信息'); ;
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
    	if ($('#fm').form("validate")) {
       	 var data1 = sy.serializeObject($('#fm').form());
    	 var data = encodeURI(JSON.stringify(data1),"UTF-8");  	 
    	 $.ajax({
    		 url : '<%=basePath%>addNewEmp',
    		 data : 'data=' + data,
    		 dataType : 'json',
    		 type : 'post',
    		 success : function(result) {
                 if (result.result > 0) {
                     $.messager.alert("提示信息", result.msg);
                     $('#dlg').dialog("close");
                     $('#dg').datagrid("load");
                 }
                 else {
                     $.messager.alert("提示信息", result.msg);
                 }
    		 },
    		 error : function() {
    			 
    		 }
    	 });
    	}

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
    function isExistEmp() {
    	var isExistEmp = $('#empId2').val();
    	if (isExistEmp != null && isExistEmp != '') {
        	$.ajax({
        		type : 'POST',
        		dataType : 'json',
        		url : '<%=basePath%>isExistEmp?empId=' + isExistEmp,
        		success : function(res) {
        			if (res.result > 0) {
        				$('#imgInvalid').show();
        				$('#imgOk').hide();
        				document.getElementById('resMsg').innerHTML='<img id="imgInvalid" alt="" src="<%=basePath%>/themes/icons/no.png">' + res.msg;
        				$('#empId2').val('');
        			} else {
        				$('#imgOk').show();
        				$('#imgInvalid').hide();
        				document.getElementById('resMsg').innerHTML='<img id="imgOk" alt="" src="<%=basePath%>/themes/icons/ok.png">' + res.msg;
        			}
        		}
        	});
    	}

    }
    function isExistLoginName() {
    	var loginName = $('#loginName2').val();
    	if (loginName != null && loginName != '') {
        	$.ajax({
        		type : 'POST',
        		dataType : 'json',
        		url : '<%=basePath%>isExistLoginName?loginName=' + loginName,
        		success : function(res) {
        			if (res.result > 0) {
        				$('#imgLoginNameOk').hide();
        				$('#imgLoginNameInvalid').show();
        				document.getElementById('loginNameMsg').innerHTML='<img id="imgLoginNameInvalid" alt="" src="<%=basePath%>/themes/icons/no.png">' + res.msg;
        				$('#loginName2').val('');
        			} else {
        				$('#imgLoginNameOk').show();
        				$('#imgLoginNameInvalid').hide();
        				document.getElementById('loginNameMsg').innerHTML='<img id="imgloginNameOk" alt="" src="<%=basePath%>/themes/icons/ok.png">' + res.msg;
        			}
        		}
        	});
    	}

    }
    $.extend($.fn.validatebox.defaults.rules, {    
        /*必须和某个字段相等*/  
        equalTo: {  
            validator:function(value,param){  
                return $(param[0]).val() == value;  
            },  
            message:'字段不匹配'  
        },
        mobile : {// 验证手机号码 
            validator : function(value) { 
                return /^(13|15|18)\d{9}$/i.test(value); 
            }, 
            message : '手机号码格式不正确' 
        } 
                 
    });
    function endEditing(){
    	if (editIndex == undefined){return true;}
    	if ($('#dg').datagrid('validateRow', editIndex)){
    		$('#dg').datagrid('endEdit', editIndex);
    		editIndex = undefined;
    		return true;
    	} else {
    		return false;
    	}
    }
    function onClickCell(index, field){
    	if (endEditing()){
    		$('#dg').datagrid('selectRow', index)
    				.datagrid('editCell', {index:index,field:field});
    		editIndex = index;
    	}
    }
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
    function saveChanges() {
    	$('#dg').datagrid('endEdit', editIndex);
    	var changes = $('#dg').datagrid('getChanges');
    	var data = encodeURI(JSON.stringify(changes),"UTF-8");
    	$.ajax({
    		url : '<%=basePath%>saveEmpChanges',
    		data : 'data=' + data,
    		type : 'post',
    		dataType : 'json',
    		success : function(res) {
    			$('#dg').datagrid('reload');
    			$.messager.alert("提示信息", res.msg,res.result);
    		},
    		error : function() {
    			$.messager.alert("提示信息", res.msg,res.result);
    		}
    		
    	});
    	
    }
</script>
</head>
<body class="easyui-layout">
	<div id="g" region="center" fit="true">
		<table id="dg">
		</table>
	</div>

	<div id="dlg" class="easyui-dialog"
		style="width: 470px; height: 300px; padding: 10px 20px;" closed="true" modal="true"
		buttons="#dlg-buttons">
		<form id="fm" method="post">
			<div class="fitem">
				<label> 员工编号 </label> <input name="empId2" id="empId2"
					class="easyui-numberbox" required="true" validtype="length[6,20]" invalidMessage="有效长度6-20" onblur="isExistEmp()"/>
				
				
				<span id="resMsg"></span>
			</div>
			<div class="fitem">
				<label> 登录名</label> <input name="loginName2" id="loginName2"
					class="easyui-validatebox" required="true" validtype="length[6,20]" invalidMessage="有效长度6-20" onblur="isExistLoginName()"/>
 				
				
				<span id="loginNameMsg"></span>
			</div>
			<div class="fitem">
				<label> 密码</label> <input type="password" name="password" id="password"
					class="easyui-validatebox" required="true" />
			</div>
			<div class="fitem">
				<label> 密码确认</label> <input id="password2" type="password" name="password2" class="easyui-validatebox" validType="equalTo['#password']" invalidMessage="两次输入密码不匹配"
					required="true" />
			</div>
			<div class="fitem">
				<label> 邮箱</label> <input name="email" validtype="email"
					class="easyui-validatebox" required="true" invalidMessage="邮箱格式不正确" />
			</div>
			<div class="fitem">
				<label> 手机号码</label> <input name="mobile" class="easyui-numberbox" required="true" validtype="mobile"/>
			</div>
			<div class="fitem">
				<label>添加收件人</label> <select id="sendToSelf" name="sendToSelf" class="easyui-combobox" style="width:150px;" editable="false" required="true">
					<option value="是" selected="selected">是</option>
					<option value="否">否</option>
				</select>
			</div>
		</form>
	</div>

	<div id="dlg-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			onclick="saveCustomer();" iconcls="icon-save">保存</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			onclick="javascript:$('#dlg').dialog('close')" iconcls="icon-cancel">取消</a>
	</div>
	<div id="userSearch" style="height: 30px;" fit="true">
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
	  			<td><a id="addBtn" class="easyui-linkbutton" icon="icon-add" href="javascript:void(0)" onclick="addCustomer();">添加员工</a></td>
	  			<td><a id="saveBtn" class="easyui-linkbutton" icon="icon-save" href="javascript:void(0)" onclick="saveChanges();">保存</a></td>
	  			</tr>
	  		</table>
	  	</form>
	  </div>
</body>
</html>