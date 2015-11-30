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
		pagination : false,
		pageSize:20,
		pageList:[20,30,40],
		iconCls:'icon-reload',
		rownumbers: true,
		singleSelect:false,
		fitColumns:true,
		idField:'id',
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
				width:8	
				
			},
			{
				title:'纳税人名称',
				field:'taxer',
				width:10
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
				field:'lawRepMobile',
				width:8
			},
			{
				title:'办税员',
				field : 'taxEmp',
				width : 5
			},
			{
				title:'办税员手机',
				field:'taxEmpMobile',
				width:8
			},
			{
				title:'财务主管',
				field:'fanAdmin',
				width:5
			},
			{
				title:'财务主管手机',
				field:'fanAdminMobile',
				width:8
			}
		]],
		toolbar:'#enterpriceSearch'

	});	
});

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
						<textarea style="width: 200px;height: 400px;" class="easyui-validatebox"  name="content" placeholder="请输入短信内容（根据运营商政策规定，建议每次提交小于等于225个字符  含签名、空格、字母、符号等） " maxlength="255"></textarea>
						</td>
					</tr>
					<tr height="30" align="center">
						<td>
						签名:<input id="sign" class="easyui-validatebox" value="鄞州地税直属分局" name="sign">
						</td>
					</tr>
					<tr height="50" align="center">
						<td>
						发送时间：<input id="sendTime" class="easyui-datebox"  name="sendTime">
						</td>
					</tr>
					<tr align="center">
						<td>
						<a icon="icon-ok" class="easyui-linkbutton" href="javascript:void(0);">发送</a><a icon="icon-cancel" class="easyui-linkbutton" href="javascript:void(0);">清空</a>
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