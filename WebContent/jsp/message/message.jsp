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
<body class="easyui-layout" fit="true">

      <div region="north" title="消息内容" split="true" style="height:100px;">
      			<div title="高级搜索">
			<form id="searchForm">
				<table>
					<tr>
						<th height="50">标题:</th>
						<td height="50"><input id="title" class="easyui-validatebox" type="text" name="title"></td>
						<td width="20"></td>
						<th>消息内容:</th>
						<td><textarea style="width: 300px;" class="easyui-validatebox"  name="content"></textarea></td>
						<td width="20"></td>
						<th>发送时间:</th>
						<td><input id="sendDate" class="easyui-datebox" type="text" name="sendDate"></td>	
						<td><a class="easyui-linkbutton" href="javascript:void(0);">发送</a></td>
						
						<td><a class="easyui-linkbutton" href="javascript:void(0);">清空</a></td>
					</tr>

				</table>

			</form>
		</div>
      
      </div>  

      
      <div region="center" href="./page/center.html" title="企业列表" style="padding:5px;background:#eee;">

  </div> 
</body>
</html>