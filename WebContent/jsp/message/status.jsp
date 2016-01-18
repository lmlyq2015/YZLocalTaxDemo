<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%String path = request.getContextPath(); 
      String basePath = request.getScheme()+"://"+ request.getServerName()+":"+request.getServerPort()+path+"/";%>
<base href="<%=basePath%>">
<link rel="stylesheet" type="text/css" href="themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="themes/icon.css">
<link rel="stylesheet" type="text/css" href="themes/demo.css">
<script type="text/javascript" src="jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="jquery/jquery.easyui.min.js"></script>
<script type="text/javascript" src="locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
$(function(){
	$('#resultDg').datagrid({
		url:'<%=basePath%>getMessageResultList',
		title:'搜索',
		pagination : true,
		pageSize: 20,
		nowrap : false,
		pageList:[10,20,30],
		iconCls:'icon-reload',
		rownumbers: true,
		singleSelect:true,
		fitColumns:true,
		showFooter: true,
		remoteSort: false,
		sortName: 'sendDate',
		sortOrder: 'desc',
		columns:[[
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
				width:100	
				
			},
			{
				title:'消息内容',
				field:'content',
				width:200
			},
			{
				title:'发送时间',
				field:'sendDate',
				width:100,
				editor: {//设置其为可编辑
					type: 'datebox',//设置编辑格式
					options: {
					required: true//设置编辑规则属性
					}
					}
			},
			{
				title:'成功数',
				field:'successCount',
				width:100
			},
			{
				title:'失败数',
				field:'failCount',
				width:100,
				formatter: function (value, rec, rowIndex) {
					if (value > 0) {
						return "<a href='javascript:void(0);' class='easyui-linkbutton' style='color: red;' onclick='openFailureWin();'>"+value+"</a>";
					} else {
						return value;
					}
					
				}
			},
			{
				title:'发送人',
				field:'sendBy',
				width:100
			},
			{
				title:'状态',
				field:'operate',
				width:100,
				formatter: function (value, rec, rowIndex) {
					if (rec.failCount > 0) {
						return "<a href='javascript:void(0);' class='easyui-linkbutton' style='color: red;' id='resendBtn' onclick='reSendMsg()'>全部重新发送</a>";
					} else {
						return "成功";
					}
				}
			}
		]],
		toolbar:'#resultToolbar'

	});	

	
	
	
	
	
	initFailMsgWin();
	
	$('#clearBtn').click(function(){
		
		$('#searchForm').form('clear');
	});
	
});


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

// function exportFailMsg(){
// 		//alert("123");
// 		var msgRecord = $('#resultDg').datagrid('getSelected');
// 		$.ajax({
//                 type: "POST",
<%--                 url: '<%=basePath%>report/exportFailMsg?msgId=' + msgRecord.id + '&failCount=' + msgRecord.failCount, --%>
//                 success : function(r) {
//     				if(r) {
//     					$.messager.alert('操作提示', r.msg, r.result);
//     				}
//     			},
//     			error : function() {
//     				$.messager.alert('操作提示', '服务器出错', 'error');
//     			} 
//      });
// }

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

</script>
<body class="easyui-layout" style="width:100%;height:100%;">
      
      <div region="center"  title="发送状态列表">
      		<table id="resultDg" fit="true">
      		
      		</table>
     

  	  </div>
     <div id="failMsgWin" class="easyui-window"
         icon="icon-save"  style="width: 300px; height: 150px; padding: 5px;
        background: #fafafa;">
        <div class="easyui-layout" fit="true" border="faslse">
            <div region="center" border="false" fit="true">
                  <table id="failMsgDg" fit="true">
 
 				  </table>
            </div>
        </div>
    </div>
    <div id="resultToolbar" fit="true"> 
    	<form id="searchForm">
    	    <table>
    			<tr>
    				<th>消息内容:</th>
    				<td><input id="content" class="easyui-validatebox"  name="content"></td>
    				<th>
		  			发送时间:
		  			</th>
		  			<td>
		  			<input id="sendDate" class="easyui-datebox"  name="sendDate" editable="false"> - <input id="sendDateEnd" class="easyui-datebox"  name="sendDateEnd" editable="false"> 
		  			</td>
		  			<th>发送结果:</th>
		  			<td><select id="status" class="easyui-combobox"  name="status" editable="false">
		  				<option value="none">--请选择--</option>
		  				<option value="发送失败">失败</option>
		  				<option value="发送成功">成功</option>
		  			</select></td>
		  			<th>发送人:</th>
		  			<td><input id="sendBy" class="easyui-validatebox"  name="sendBy"></td>
    				<td>
		  			<a id="searchBtn"class="easyui-linkbutton" href="javascript:void(0)" icon="icon-search" onclick="searchStatus();">查找</a>
		  			</td>
		  			<td>
		  			<a id="clearBtn"class="easyui-linkbutton" href="javascript:void(0)" icon="icon-cancel" onclick="clearSearch();">重置</a>
		  			</td>
    			</tr>
    		
    		</table>
    	
    	</form>   
    </div>
</body>
</html>