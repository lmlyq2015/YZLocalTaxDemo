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
<script type="text/javascript">
$(function(){
	$('#resultDg').datagrid({
		url:'<%=basePath%>getMessageResultList',
		title:'搜索',
		pagination : true,
		pageSize:20,
		nowrap : false,
		pageList:[20,30,40],
		iconCls:'icon-reload',
		rownumbers: true,
		singleSelect:true,
		fitColumns:true,
		showFooter: true,
		remoteSort: false,
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
				width:10	
				
			},
			{
				title:'消息内容',
				field:'content',
				width:15
			},
			{
				title:'发送时间',
				field:'sendDate',
				width:15,
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
				width:15
			},
			{
				title:'失败数',
				field:'failCount',
				width:5,
				formatter: function (value, rec, rowIndex) {
					if (value > 0) {
						return "<a style='color: red;' onclick='openFailureWin();'>"+value+"</a>";
					} else {
						return value;
					}
					
				}
			},
			{
				title:'发送人',
				field:'sendBy',
				width:7
			},
			{
				title:'消息类型',
				field:'msgType',
				width:7
			}
		]]

	});	

	
	
	
	
	
	updateDeviceWin();
	
});


function openFailureWin(row) {
	$('#updateDeviceWin').window('open');
	
}
function updateDeviceWin() {
	$('#updateDeviceWin').window({
		title : '设备运行参数',
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


</script>
<body class="easyui-layout" fit="true">
      
      <div region="center"  title="发送状态列表" fit="true">
      		<table id="resultDg">
      		
      		</table>
     

  	  </div> 
     <div id="updateDeviceWin" class="easyui-window" title="修改设备参数" collapsible="false" minimizable="false"
        maximizable="false" icon="icon-save"  style="width: 300px; height: 150px; padding: 5px;
        background: #fafafa;">
        <div class="easyui-layout" fit="true">
            <div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
                 
            </div>
        </div>
    </div>
</body>
</html>