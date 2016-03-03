<%@page import="com.util.TaxUtil"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%String path = request.getContextPath(); 
      String basePath = request.getScheme()+"://"+ request.getServerName()+":"+request.getServerPort()+path+"/";%>
<base href="<%=basePath%>">
<link rel="stylesheet" type="text/css" href="themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="themes/icon.css">
<script type="text/javascript" src="jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="jquery/jquery.easyui.min.js"></script>
<script type="text/javascript" src="jquery/util.js"></script>
<script type="text/javascript" src="locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="audiojs/audio.min.js"></script>
<style type="text/css"> 

textarea {
    resize: none;
}
#askDiv {
    min-height: 120px; 
    max-height: 300px;
    _height: 120px; 
    margin-left: auto; 
    margin-right: auto; 
    padding: 3px; 
    outline: 0; 
    font-size: 12px; 
    word-wrap: break-word;
    overflow-x: hidden;
    overflow-y: auto;
    -webkit-user-modify: read-write-plaintext-only;
}
#answerDiv {
    min-height: 120px; 
    max-height: 300px;
    _height: 120px; 
    margin-left: auto; 
    margin-right: auto; 
    padding: 3px; 
    outline: none; 
    font-size: 12px; 
    word-wrap: break-word;
    overflow-x: hidden;
    overflow-y: auto;
    -webkit-user-modify: read-write-plaintext-only;
}
#contentDg .datagrid-btable tr{height: 50px;}

</style>
<script type="text/javascript">
audiojs.events.ready(function() {
    var as = audiojs.createAll();
  });
$(function() {
	$('#callRecordDg').datagrid({
				loadMsg : '加载电话记录中...',
				url:'<%=basePath%>getCallList',
				fitColumns:true,
				singleSelect:true,
				sortName: 'offeringTime',
		 		sortOrder: 'desc',
		 		showHeader : false,
				columns:[[
					{
						title:'来电号码',
						field: 'originCallNo',
						width:50,
						
						formatter : function(value, rec, rowIndex) {
							var src;
							if (rec.callType == 'dialout') {
								src = './images/tel_out.png';
							} else {
								src = './images/tel_in.png';
							}
							var offertingTime = dateFormat(rec.offeringTime);
							var status = formateStatus(rec.status);
							
							return "<div style='overflow: hidden;'>" + 
							"<img src="+ src +" style='float:left;margin-top:5px;height: 15px;' />" +
							"<span style='padding-left: 10px;text-align: center;font: bolder;'><b>" + value + "[" +  rec.callerProvince + "-" + rec.callerCity + "]" +  "</b></span>" +
							"<span style='float: right;'>" + offertingTime + "<br>" + status + "</span>"
							"</div>";
						}
					}
				]],
				onClickRow : function (rowIndex, rowData) {
					
					 $("#callInfodg").datagrid({url:'<%=basePath%>getCallInfoByCallNo?callNo=' + rowData.originCallNo});
					 $("#callInfodg").datagrid('load');
				}

			});	
	
	$('#callInfodg').datagrid({
		fitColumns:false,
		singleSelect:true,
		nowrap : false,
		pagination : true,
		pageSize: 20,
		pageList:[10,20,30],
		sortName: 'offeringTime',
 		sortOrder: 'desc',
 		showHeader : true,
		columns:[[
				{
					title : '来电时间',
					field: 'offeringTime',
					width:fixWidthTable(0.12),
					formatter : function(value, rec, rowIndex) {
						var offeringTime = dateFormat2(rec.offeringTime);
						return offeringTime;
					}
				},
				{
					title : '接听坐席',
					field: 'agent',
					width:fixWidthTable(0.12)
				},
				{
					title : '起始时间',
					field: 'beginTime',
					width:fixWidthTable(0.12),
					formatter : function(value, rec, rowIndex) {
						if (rec.beginTime != null) {
							var beginTime = dateFormat2(rec.beginTime);
							return beginTime;
						}
					}
				},
				{
					title : '结束时间',
					field: 'endTime',
					width:fixWidthTable(0.12),
					formatter : function(value, rec, rowIndex) {
						if (rec.endTime != null) {
							var endTime = dateFormat2(rec.endTime);
							return endTime;
						}
					}
				},
				{
					title : '通话类型',
					field: 'callType',
					width:fixWidthTable(0.12),
					formatter : function(value, rec, rowIndex) {
						var type = formatCallType(rec.callType);
						return type;
					}
				},
				
				{
					title : '通话录音',
					field: 'recordFile',
					width:fixWidthTable(0.15),
					formatter : function(value, rec, rowIndex) {
						
						return "<img style='height: 20px;' id='play"+ rowIndex +"' onclick='playRecord(\""+rec.recordFile+"\","+rowIndex+")' src='./images/play1.png'/>" + 
								"<img style='display: none; height: 20px;' id='stop"+ rowIndex  +"' onclick='stopPlay(\""+rec.recordFile+"\","+rowIndex+")' src='./images/pause1.png'/>";
// 						return "<a href='javascript:void(0);' onclick='playRecord(\""+rec.recordFile+"\")' class='easyui-linkbutton'>播放</a>" + 
// 						       "<a style='padding-left: 10px;' href='javascript:void(0);' onclick='stopPlay(\""+rec.recordFile+"\")' class='easyui-linkbutton'>停止</a>";
					}
				},
				{
					title : '状态',
					field: 'status',
					width:fixWidthTable(0.11),
					formatter : function(value, rec, rowIndex) {
						var status = formateStatus(rec.status);
						return status;
					}
				},
				{
					title : '客户满意度',
					field: 'satisfactionDegree',
					width:fixWidthTable(0.1)
				},
				{
					title:'企业',
					field: 'representComp',
					width:fixWidthTable(0.11)
				}
				
		       ]],
		       onClickRow : function (rowIndex, rowData) {
					 $("#callConsultDg").datagrid({url:'<%=basePath%>getConsultInfoByCallSheetNo?callSheetNo=' + rowData.callSheetId});
					 // $("#callConsultDg").datagrid('load');
		       }
	});
	
	$('#callConsultDg').datagrid({
		loadMsg : '加载中...',
		fitColumns:false,
		singleSelect:true,
		columns:[[
					{
						title : '咨询问题',
						field : 'questions',
						width:fixWidthTable(0.5)
					},
					{
						title:'咨询解答',
						field : 'answers',
						width:fixWidthTable(0.5)
					}     
		      	]],
		toolbar :[
				     {
				    	 text : '咨询',
				    	 iconCls : "icon-add",
				    	 handler : function() {
				    		 $('#consultDlg').dialog('open');
				    		 //$('#consultDlg').window('center'); 
							}
				     }
			  
				 ]
	});
	
	$('#contentDg').datagrid({
		loadMsg : '加载中...',
		fitColumns:false,
		singleSelect:true,
 		showHeader : false,
 		nowrap : false,
		columns:[[
					{
						title : '搜索结果',
						field : 'answers',
						width:'98%',
						heigth:fixWidthTable(2)
					}     
		      	]],
		      	 onClickRow : function (rowIndex, rowData) {
		      		$('#answerDiv').text(rowData.answers);
		      	 }

	});
	$('#consultWinClose').click(function(){
		
		$('#consultDlg').dialog('close');
		
		
	});
	$('#addConsultBtn').click(function(){
		var q_value = $("#askDiv").html();
		var a_value = $('#answerDiv').html();
		if (q_value.trim() == '' || a_value.trim() == '') {
			$.messager.alert('操作提示', "信息尚未填写完整","error");
			return;
		}
		var obj = new Object();
		obj.questions = q_value;
		obj.answers = a_value;
		var data = encodeURI(JSON.stringify(obj),"UTF-8");
		$.ajax({
			url : '<%=basePath%>addConsults',
			type : "POST",
			dataType : "json",
			data : 'data=' + data,
			success : function(r) {
				if (r.result == 'error') {
					$.messager.alert('操作提示', r.msg,"info");
				} else {
					$.messager.alert('操作提示', r.msg,"info");
					$("#askDiv").empty();
					$('#answerDiv').empty();	
				}		
			},
			error : function() {
				$.messager.alert('操作提示', "服务器出错","error");
			}
			
			
			
		});
		
		
	});
    $('#askDiv').keydown(function(event){
    	if (event.keyCode == 13) {
    		var title = $("#askDiv").text();
			 $("#contentDg").datagrid({url:'<%=basePath%>getKnowledgeContent',queryParams:{title : title}});
    		}
       });

});
	function dateFormat(offeringTime) {
		
		return new Date(offeringTime.replace(/-/ig,'/')).Format("hh:mm");  
	}
	function dateFormat2(offeringTime) {
		
		return new Date(offeringTime.replace(/-/ig,'/')).Format("YY-MM-dd hh:mm");  
	}
	Date.prototype.Format = function (fmt) { //author: meizz 
	    var o = {
			"Y+": this.getYear(),
	        "M+": this.getMonth() + 1, //月份 
	        "d+": this.getDate(), //日 
	        "h+": this.getHours() < 10? '0'+this.getHours() : this.getHours(), //小时 
	        "m+": this.getMinutes(), //分 
	        "s+": this.getSeconds(), //秒 
	        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
	        "S": this.getMilliseconds() //毫秒 
	    };
	    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	    for (var k in o)
	    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	    return fmt;
	};
	
	function formateStatus(status) {
		if (status == 'notDeal') {
			return '振铃未接听';
		} else if(status == 'dealing') {
			return '已接';
		} else {
			return '放弃';
		}
	}
    function fixWidthTable(percent) {  
    		
    	return getWidth(0.7) * percent;  
  	}  
    	          
    function getWidth(percent){  
    	return $(window).width() * percent;  
    }
    function playRecord(file,index) {
    	if (file !=null && file != '' && file !='null') {
	    	 var bgObj=document.createElement("audio");
			 bgObj.setAttribute('id','playRecord' + file);
			 bgObj.setAttribute('src',file);
			 bgObj.setAttribute('preload','auto');
			 bgObj.setAttribute('autoplay','true');
			 $('#dlg').html(bgObj);
			 $('#play'+index).hide();
			 $('#stop'+index).show();
    	}

		 
    }
    function stopPlay(file,index) {
    	var audio = document.getElementById('playRecord'+file);
    	if (audio != null) {
        	audio.pause();
   		 	$('#stop'+index).hide();
			 $('#play'+index).show();
    	}
    }
	function formatCallType(type) {
		if (type == 'normal') {
			return '<%=TaxUtil.CALL_TYPE_NORMAL%>';
		} else if (type == 'dialout') {
			return '<%=TaxUtil.CALL_TYPE_DIALOUT%>';
		} else if (type == 'transfer') {
			return '<%=TaxUtil.CALL_TYPE_TRANSFER%>';
		} else if (type == 'dialtransfer') {
			return '<%=TaxUtil.CALL_TYPE_DIALTRANSFER%>';
		}
	}
</script>
<body class="easyui-layout">

      <div region="west" title="我的通话" style="width: 300px;height: 100%;" collapsible="false">
      		<div style="width:100%;height: 100%;" fit="true" border="false">
      			<table id="callRecordDg" fit="true" border="false">
      			</table>
      		
      		</div>
      		
                
      </div>  

      
      <div region="center" title="来电历史" style="background:#eee;" fit="true" border="false">
 	 	<div class="easyui-layout" border="false" fit="true">
 	 		<div region="north" style="height: 300px;" border="false">
 	 			<table id="callInfodg" fit="true" border="false"></table>
 	 		
 	 		</div>
 	 		<div region="center" border="false" title="咨询明细">
 	 			<table id="callConsultDg" fit="true" border="false"> 
 	 				
 	 			</table>
 	 		</div>
 	 	</div>
 	  </div> 
 	<div id="dlg" class="easyui-dialog"
		style="width: 500px; height: 200px; padding: 10px 20px;" closed="true" modal="true">
	</div>
	<div id="consultDlg" class="easyui-dialog"
		style="width: 700px; height: 400px;" closed="true" modal="true" title="咨询">
		<div class="easyui-layout" border="false" fit="true">
			<div region="center">
				<div class="easyui-layout" border="false" fit="true">
					<div id="askDiv" region="north" style="height: 180px;" contenteditable="true" title="问题记录"> 
						
					</div>
					<div id="answerDiv" region="center" contenteditable="true" title="解答记录">
						
					</div>

				</div>

			</div>
			<div region="east"  style="width: 350px;height: 200px;padding-right: 2px;">
				<div class="easyui-layout" border="false" fit="true">
<!-- 					<div region="north"> -->
<!-- 					  <input id="searchContent" class="easyui-textbox"  style="float: left;overflow: hidden;width: 99%;border:1px solid #006666;height: 20px;"> -->
<!-- 					</div> -->
					<div region="center" border="false" title="搜索结果">
						<table id="contentDg" fit="true" border="false">
						
						</table>
					</div>
				</div>
				
				
			</div>
			<div region="south">
				<div align="center">
					<a id="addConsultBtn" class="easyui-linkbutton" href="javascript:void(0);" icon="icon-add">添加</a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>