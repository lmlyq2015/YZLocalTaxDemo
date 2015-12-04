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
	$('#enterpriceDg').datagrid({
		url:'<%=basePath%>jsp/message/enterprice_json.json',
		title:'搜索',
		pagination : true,
		pageSize:20,
		nowrap : false,
		pageList:[20,30,40],
		iconCls:'icon-reload',
		rownumbers: true,
		singleSelect:false,
		fitColumns:true,
		showFooter: true,
		remoteSort: false,
		columns:[[
			{
				//title:'<input id=\"detailcheckbox\" type=\"checkbox\"  >',
				field:'check',
				checkbox:true
				//width:2,
// 				formatter: function (value, rec, rowIndex) {
//                     return "<input type=\"checkbox\"  name=\"PD\" value=\"" + value + "\" >";
//                 }
			},
			{
				title:'纳税人识别号',
				field:'taxNo',
				width:10	
				
			},
			{
				title:'纳税人名称',
				field:'taxName',
				width:15
			},
			{
				title:'生产经营地址',
				field:'address',
				width:15
			},
			{
				title:'法人',
				field:'lawRep',
				width:5
			},
			
			{
				title:'法人手机',
				field:'lawRepMob',
				width:7
			},
			{
				title:'办税员',
				field : 'taxer',
				width : 5
			},
			{
				title:'办税员手机',
				field:'taxerMob',
				width:8
			},
			{
				title:'财务主管',
				field:'admin',
				width:5
			},
			{
				title:'财务主管手机',
				field:'adminMob',
				width:7
			}
		]],
		toolbar:'#enterpriceSearch'

	});	
	$('#msgSend').click(function(){
		var content = $('#content').val();
		var sign = $('#sign').val();
		if (content == null || content == "") {
			$.messager.alert('操作提示', "请输入消息内容","info");
			return;
		}
		if (sign == null || sign == "") {
			$.messager.alert('操作提示', "请输入消息签名","info");
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
		}
		
	});
	$('#sendDate').datebox('setValue', formatterDate(new Date()));

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
</script>
<body class="easyui-layout">

      <div region="west" title="消息内容" style="width: 215px;height: 500px;">          
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
						<textarea id="content" style="width: 200px;height: 400px;" class="easyui-validatebox"  name="content" placeholder="请输入短信内容（根据运营商政策规定，建议每次提交小于等于225个字符  含签名、空格、字母、符号等） " maxlength="255"></textarea>
						</td>
					</tr>
					<tr height="30" align="center">
						<td>
						签名:<input id="sign" class="easyui-validatebox" value="鄞州地税直属分局" name="sign">
						</td>
					</tr>
					<tr height="50" align="center">
						<td>
						发送时间：<input id="sendDate" class="easyui-datebox"  name="sendDate">
						</td>
					</tr>
					<tr align="center">
						<td>
						<a id="msgSend" icon="icon-ok" class="easyui-linkbutton" href="javascript:void(0);">发送</a><a icon="icon-cancel" class="easyui-linkbutton" href="javascript:void(0);">清空</a>
						</td>
					</tr>
				
<!-- 					<td> -->
<!-- 						<a icon="icon-save" class="easyui-linkbutton" href="javascript:void(0);">保存草稿</a>					 -->
<!-- 					</td> -->
				
					</table>
			</form>
		
      </div>  

      
      <div region="center" title="企业列表" style="padding:5px;background:#eee;" fit="true">
		<table id="enterpriceDg">
		
		</table>
	  <div id="enterpriceSearch">
	  	<form>
	  		<table>
	  			<tr>
	  			<th>
	  			纳税人识别号:
	  			</th>
	  			<td>
	  			<input id="taxNo" class="easyui-validatebox"  name="taxNo">
	  			</td>
	  			<th>
	  			纳税人名称:
	  			</th>
	  			<td>
	  			<input id="taxName" class="easyui-validatebox"  name="taxName">
	  			</td>
	  			<td>
	  			<a class="easyui-linkbutton" href="javascript:void(0)" icon="icon-search">搜索</a>
	  			</td>
	  			<td>
	  			<a class="easyui-linkbutton" href="javascript:void(0)" icon="icon-cancel">清除</a>
	  			</td>
	  			</tr>
	  		</table>
	  	</form>
	  </div>
  </div> 
</body>
</html>