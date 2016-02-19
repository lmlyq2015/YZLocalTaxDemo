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
<style type="text/css"> 

textarea {
    resize: none;
}


</style>
<script type="text/javascript">
$(function() {
	$(window).resize(function(){ 
		$("#enterpriceDg").datagrid("resize",{width:getWidth(0.6)});
	});
	$('#enterpriceDg').datagrid({
<%-- 	url:'<%=basePath%>jsp/message/enterprice_json.json', --%>
		loadMsg : '数据加载中请稍后',
		url:'<%=basePath%>getAllComp',
		title:'搜索',
		pagination : true,
		pageSize:10,
		nowrap : false,
		pageList:[10,30,40],
		iconCls:'icon-reload',
		rownumbers: true,
		singleSelect:false,
		fitColumns:true,
		showFooter: true,
		remoteSort: false,
		idField: 'taxId',
		width: getWidth(0.6),
		columns:[[{
				field:'check',
				checkbox:true
			},{
				title : '纳税人电子档案号',
				field : 'eid',
				hidden : 'true',
				width : fixWidthTable(0.1),
				align:'center'
			},{
				title : '纳税人识别号',
				field : 'taxId',
				width : fixWidthTable(0.12),
				align:'center'				
			},{
				title : '纳税人名称',
				field : 'taxName',
				width : fixWidthTable(0.15),
				align:'center'
				
			},{
				title : '地址',
				field : 'address',
				width : fixWidthTable(0.1),
				align:'center'
			},{
				title : '税收管理员',
				field : 'taxAdmin',
				width : fixWidthTable(0.05),
				align:'center'
			},{
				title : '企业状态',
				field : 'state',
				hidden : 'true',
				width : fixWidthTable(0.05),
				align:'center'
			},{
				title : '法人',
				field : 'rep',
				width : fixWidthTable(0.05),
				align:'center'
			},{
				title : '法人手机',
				field : 'repMobile',
				width : fixWidthTable(0.08),
				align:'center'
			},{
				title : '办税员',
				field : 'taxAgentName',
				width : fixWidthTable(0.05),
				align:'center'
			},{
				title : '办税员手机',
				field : 'taxAgentMobile',
				width : fixWidthTable(0.08),
				align:'center'
			}, {
				title : '财务主管',
				field : 'adminName',
				width : fixWidthTable(0.05),
				align:'center'
			},{
				title : '财务主管手机',
				field : 'adminMobile',
				width : fixWidthTable(0.08),
				align:'center'
			}]],
		toolbar:'#enterpriceSearch'

	});	
	
	$('#readCompForm').form({
		url : '<%=basePath%>report/readComp',
		onSubmit: function(){
			return $('#readCompForm').form('validate');
		},
		success : function(data) {
			 var res = jQuery.parseJSON(data);
				if (res) {

				$.messager.alert('操作提示', res.msg);
				$('#enterpriceDg').datagrid('load');
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
			$('#enterpriceDg').datagrid("loading");
			$('#readCompForm').form('submit');}
	});
	
	$('#msgSend').click(function(){
		var content = $('#content').val();
// 		var sign = $('#sign').val();
		if (content == null || content == "") {
			$.messager.alert('操作提示', "请输入消息内容","info");
			return;
		}
// 		if (sign == null || sign == "") {
// 			$.messager.alert('操作提示', "请输入消息签名","info");
// 			return;
// 		}
		var dis = $("#msgSend").linkbutton("options").disabled;
		if(dis == true){
			return null;
		}else{
		var rows = $('#enterpriceDg').datagrid('getSelections');
		if (rows.length == 0) {
			$.messager.alert('操作提示', "请选择发送对象","info");
			return;
		} else {
			var formObj = sy.serializeObject($("#msgForm").form());
			var formStr = encodeURI(JSON.stringify(formObj),"UTF-8");
			var data = encodeURI(JSON.stringify(rows),"UTF-8");
			$.ajax({
				url : '<%=basePath%>sendNotificationMsg',
				type : "POST",
				dataType : "json",
				data : 'data=' + formStr + "=" + data,
				success : function(r) {
					$.messager.alert('操作提示', r.msg,r.result);
					$('#content').val('');
					$('#enterpriceDg').datagrid('unselectAll');
				},
				error : function() {
					$.messager.alert('操作提示', "服务器出错","error");
				}
			});
		};
		};
	});
	//$('#sendDate').datebox('setValue', formatterDate(new Date()));

	$('#msgSendWithURL').click(function(){
		var content = $('#content').val();
		if (content == null || content == "") {
			$.messager.alert('操作提示', "请输入消息内容","info");
			return;
		}
		var rows = $('#enterpriceDg').datagrid('getSelections');
		if (rows.length == 0) {
			$.messager.alert('操作提示', "请选择发送对象","info");
			return;
		} else {
			var formObj = sy.serializeObject($("#msgForm").form());
			var formStr = encodeURI(JSON.stringify(formObj),"UTF-8");
			var data = encodeURI(JSON.stringify(rows),"UTF-8");
			$.ajax({
				url : '<%=basePath%>sendNotificationMsgWithURL',
				type : "POST",
				dataType : "json",
				data : 'data=' + formStr + "=" + data,
				success : function(r) {
					$.messager.alert('操作提示', r.msg,r.result);
					$('#content').val('');
					$('#enterpriceDg').datagrid('unselectAll');
				},
				error : function() {
					$.messager.alert('操作提示', "服务器出错","error");
				}
			});
		}
		
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
 function formatterDate(date) {
	var day = date.getDate() > 9 ? date.getDate() : "0" + date.getDate();
	var month = (date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : "0"
	+ (date.getMonth() + 1);
	return date.getFullYear() + '-' + month + '-' + day;
	};
	
	function searchComp() {
		var data1 = sy.serializeObject($('#CompSearch').form());
		var data = encodeURI(JSON.stringify(data1), "UTF-8");
		$('#enterpriceDg').datagrid('load', data1);
	}

	function clearSearch() {
		$('#enterpriceDg').datagrid("load", {});
		$('#CompSearch').form("clear");
	}
	
	function clearContent(){
		$('#content').val('');
		$("#msgSend").linkbutton("enable");
	}
	
	function textup() {
	    var s = document.getElementById('content').value;
	    if (s.length > 240) {
	    	$.messager.alert('操作提示', "字数超过短信发送限制，请选择微网页发送！","info");
	    	$("#msgSend").linkbutton("disable");
	    }else{
	    	$("#msgSend").linkbutton("enable");
	    };    
	}
	
	function getWidth(percent){  
	    return $(window).width() * percent;  
	}
	
	function fixWidthTable(percent){  
        return getWidth(0.6) * percent;  
	} 
</script>
<body class="easyui-layout">

      <div region="west" title="消息内容" style="width: 233px;height: 500px;">          
			<form id="msgForm">					
	
<!-- 				
<table> -->
<!-- 					<tr> -->
<!-- 						<td><input id="sendDate" class="easyui-datebox" type="text" name="sendDate"></td>	 -->
<!-- 						<td><a icon="icon-ok" class="easyui-linkbutton" href="javascript:void(0);">发送</a></td> -->
						
<!-- 						<td><a icon="icon-cancel" class="easyui-linkbutton" href="javascript:void(0);">清空</a></td> -->
<!-- 						<td><a icon="icon-save" class="easyui-linkbutton" href="javascript:void(0);">保存草稿</a></td> -->
<!-- 					</tr> -->
<!-- 					<tr> -->
<!-- 					<th>消息内容:</th> -->
<!-- 						<td><textarea style="width: 300px;" class="easyui-validatebox"  name="content" style="height: 100px;" required="true" placeholder="根据运营商政策规定，建议每次提交小于等于225个字符 -->
<!-- 									  （含签名、空格、字母、符号等） " maxlength="255"></textarea><span -->
<!-- 							style="color: red; margin-left: 2px;">*</span></td> -->
					
<!-- 					</tr> -->
<!-- 				</table> -->
					<table>
					<tr>
						<td>
						<textarea id="content" style="width: 218px;height: 400px;" class="easyui-validatebox"  name="content" onKeyUp="textup()"></textarea>
						</td>
					</tr>
<!-- 					<tr height="30" align="center"> -->
<!-- 						<td> -->
<!-- 						签名:<input id="sign" class="easyui-validatebox" value="鄞州地税直属分局" name="sign"> -->
<!-- 						</td> -->
<!-- 					</tr> -->
<!-- 					<tr height="50" align="center"> -->
<!-- 						<td> -->
<!-- 						发送时间：<input id="sendDate" class="easyui-datetimebox"  name="sendDate"> -->
<!-- 						</td> -->
<!-- 					</tr> -->
					
						<tr><td>
						<a id="msgSend" icon="icon-ok" class="easyui-linkbutton" href="javascript:void(0);">短信发送</a>
						<a id="msgSendWithURL" icon="icon-ok" class="easyui-linkbutton" href="javascript:void(0);">微网页发送</a>
						</td></tr>					
						<tr align="center"><td><a icon="icon-cancel" class="easyui-linkbutton" href="javascript:void(0);" onclick="clearContent();">清空</a></td></tr>				
<!-- 					<td> -->
<!-- 						<a icon="icon-save" class="easyui-linkbutton" href="javascript:void(0);">保存草稿</a>					 -->
<!-- 					</td> -->
				
					</table>
			</form>
		
      </div>  

      
      <div region="center" title="企业列表" style="padding:5px;background:#eee;" fit="true">
		<table id="enterpriceDg" fit="true">
		
		</table>
	  <div id="enterpriceSearch" style="height: 60px;">
	  <form name="readCompForm" method="post"
				enctype="multipart/form-data" id="readCompForm">
				<table id="reportTable" >
					<tr>
						<th>选择文件:</th>
						<td><input id="file" type="file" name="file" />
							<button id="formBtn" type="submit">导入</button></td>
					</tr>
				</table>
			</form>
	  	<form id="CompSearch">
	  		<table>
	  			<tr>
	  			<th>
	  			纳税人识别号:
	  			</th>
	  			<td>
	  			<input id="taxId" class="easyui-validatebox"  name="taxId">
	  			</td>
	  			<th>
	  			纳税人名称:
	  			</th>
	  			<td>
	  			<input id="taxName" class="easyui-validatebox"  name="taxName">
	  			</td>
	  			<td>
	  			<a class="easyui-linkbutton" href="javascript:void(0)" icon="icon-search" onclick="searchComp();">查找</a>
	  			</td>
	  			<td>
	  			<a class="easyui-linkbutton" href="javascript:void(0)" icon="icon-cancel" onclick="clearSearch();">重置</a>
	  			</td>
	  			</tr>
	  		</table>
	  	</form>
	  </div>
  </div> 
</body>
</html>