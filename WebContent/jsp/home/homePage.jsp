<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%String path = request.getContextPath(); 
      String basePath = request.getScheme()+"://"+ request.getServerName()+":"+request.getServerPort()+path+"/";%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<base href="<%=basePath%>">
<head>
<title>鄞州地方税务局纳税服务系统</title>
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<title></title>
<link href="css/default.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="themes/icon.css" />
<script type="text/javascript" src="jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src='jquery/outlook2.js'> </script>
<script type="text/javascript" src="jquery/jquery.easyui.min.js"></script>
<script type="text/javascript" src="jquery/jquery.tabs.extend.js"></script>
<script type="text/javascript">
function closes(){
	$("#Loading").fadeOut("normal",function(){
		$(this).remove();
	});
}
var pc;
$.parser.onComplete = function(){
	if(pc) clearTimeout(pc);
	pc = setTimeout(closes, 1000);
}
</script>

<script type="text/javascript">
	 
	 var _menus = {"menus":[
						{"menuid":"1","icon":"icon-message","menuname":"信息中心",
							"menus":[
									{"menuname":"公告通知","icon":"icon-notice","url":"jsp/message/message.jsp"},			
									{"menuname":"催缴通知","icon":"icon-pay","url":"jsp/message/pay.jsp"},
									{"menuname":"催报通知","icon":"icon-report","url":"jsp/message/report.jsp"},
									{"menuname":"消息状态","icon":"icon-nav","url":"jsp/message/status.jsp"}
// 									{"menuname":"草稿箱","icon":"icon-nav","url":""}
								]
						},
						{"menuid":"2","icon":"icon-knowledge","menuname":"知识库",
							"menus":[
								{"menuname":"模块开发中","icon":"","url":""}   
								]
						},
// 						{"menuid":"3","icon":"icon-weixin","menuname":"微信公众平台",
// 							"menus":[
// 								]
// 						},
						{"menuid":"4","icon":"icon-callcenter","menuname":"呼叫中心",
							"menus":[
								{"menuname":"通话记录","icon":"icon-call-record","url":"jsp/callcenter/callHome.jsp"}
								]
						},
// 						{"menuid":"5","icon":"icon-sys","menuname":"数据采集",
// 							"menus":[
// 								]
// 						},
						{"menuid":"6","icon":"icon-user","menuname":"用户管理",
							"menus":[{"menuname":"个人信息","icon":"icon-nav","url":"jsp/user/userinfo.jsp"}
// 									{"menuname":"权限设置","icon":"icon-nav","url":"#"}
								]
						}]};
	 
        //设置登录窗口
        function openPwd() {
            $('#w').window({
                title: '修改密码',
                width: 300,
                modal: true,
                shadow: true,
                closed: true,
                height: 160,
                resizable:false,
            });
        }
        //关闭登录窗口
        function close() {
            $('#w').window('close');
        }

        function closeLogin() {
        	close();
        }

        //修改密码
        function serverLogin() {
            var $newpass = $('#txtNewPass');
            var $rePass = $('#txtRePass');

            if ($newpass.val() == '') {
                msgShow('系统提示', '请输入密码！', 'warning');
                return false;
            }
            if ($rePass.val() == '') {
                msgShow('系统提示', '请在一次输入密码！', 'warning');
                return false;
            }

            if ($newpass.val() != $rePass.val()) {
                msgShow('系统提示', '两次密码不一至！请重新输入', 'warning');
                return false;
            }

            $.post('<%=basePath%>changePwd?newpwd=' + $newpass.val(), 
            	function(msg) {
                	msgShow('系统提示', '恭喜，密码修改成功！', 'info');
                	$newpass.val('');
               		$rePass.val('');
               	 	close();
            });
            
        }
		function detail() {
			//$("#p11").panel('refresh','<%=basePath%>login.jsp');
		    var currTab =  self.parent.$('#tabs').tabs('getSelected'); //获得当前tab
		    //var url = $(currTab.panel('options').content).attr('src');
		    self.parent.$('#tabs').tabs('update', {
		      tab : currTab,
		      options : {
		       content : createFrame('<%=basePath%>login.jsp')
		      }
		     });
		}
        $(function() {
        
            openPwd();
            //
            $('#editpass').click(function() {
                $('#w').window('open');
            });

            $('#btnEp').click(function() {
                serverLogin();
            });

           

            $('#loginOut').click(function() {
                $.messager.confirm('系统提示', '您确定要退出本次登录吗?', function(r) {

                    if (r) {
                        location.href = '<%=basePath%>jsp/login/login.html';
                    }
                });

            });
			
//             $('#p11').panel({
//                 title: '到期账款',
//                 width: 400,
//                 modal: false,
//                 iconCls : 'icon-ok',
//                 shadow: true,
//                 height: 250,
//                 draggable:false,
//                 resizable:false,
//             });
			
            var options = {
            	    title: "到期账款",
            	    iconCls: 'icon-ok',
            	    href : '<%=basePath%>getMaturityMoneyList',
            	    tools: [[
            	        {
            	            iconCls: 'icon-add',
            	            handler: function () { alert('add'); }
            	        }, {
            	            iconCls: 'icon-edit',
            	            handler: function () { alert('edit'); }
            	        }]]

            	};
            $("#p11").panel(options);
            
            //init datagrid
            $('#dlg').dialog({
            	onOpen:function(){
            		
            	}
            });
            
            $('#ringBtn').click(function(){
            	
            	if(!$('#tabs').tabs('exists','通话记录')){
            		
            		$('#tabs').tabs('addIframeTab',{
            			//tab参数为一对象，其属性同于原生add方法参数
            			tab:{
                            title:'通话记录',
                            closable:true,
                			width:$('#mainPanle').width()-10,
                			height:$('#mainPanle').height()-26,
                            tools:[{
                                iconCls:'icon-mini-refresh',
                                handler:function(e){
                                    //var title = $(e.target).parent().parent().text();
                                    $('#tabs').tabs('updateIframeTab',{'which':'通话记录'});
                                }
                            }]
                        },
            			//iframe参数用于设置iframe信息，包含：
            			//src[iframe地址],frameBorder[iframe边框,，默认值为0],delay[淡入淡出效果时间]
            			//height[iframe高度，默认值为100%],width[iframe宽度，默认值为100%]
            			iframe:{src:'./jsp/callcenter/callHome.jsp'}
            		});

            	} else {
            		$('#tabs').tabs('select','通话记录');
            		$('#tabs').tabs('updateIframeTab',{'which':'通话记录'});
            	}          	
            	$('#dlg').dialog('close');
            	
            	
            }); 
            
            $('#consultTranDlgBtn').click(function(){
            	
            	var numberInput = $('#number').val().trim();
            	var listSelected = isListSelected();
        		if (numberInput == '' && !listSelected) {
        			$.messager.alert('操作提示', "请选择电话号码","error");
        		} else if (numberInput != '' && listSelected) {
        			$.messager.alert('操作提示', "输入框和列表选择重复，请重新选择","error");
        		} else {
        			var number = numberInput==''?$('#contactListDg').datagrid('getSelected').number:numberInput;
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
        });
		
	function ringAlert(msg) {
		$.messager.alert('提示', msg, 'info');

	}
	
	function message(msg) {
        $.messager.show({  
            title: "操作提示",  
            msg: msg,  
            showType: 'slide',  
            timeout: 1000  
        });  
	}
	function onRingPopWindow(data) {
		var object = new Object();
		object.callSheetId = data.callSheetId;
		object.originCallNo = data.originCallNo;
		object.callerProvince = data.callerProvince;
		object.callerCity = data.callerCity;
		object.offeringTime = data.offeringTime;
		object.agent = data.agent;
		object.status = data.status;
		object.callType = data.callType;
		object.callId = data.callId;
		object.originCalledNo = data.originCalledNo;
		
		var ringData = encodeURI(JSON.stringify(object),"UTF-8");
		$.ajax({
			url : '<%=basePath%>saveWhenRing',
			type : 'post',
			data : 'data=' + ringData,
			dataType : 'json',
			success : function(r){
				$('#dlg').dialog('open');
				$('#dlg').dialog('refresh','<%=basePath%>jsp/callcenter/onRingWin.jsp');
			},
			error : function() {
				$.messager.alert('提示', '来电记录出错', 'error');
			}
		});

	}
	function createFrame(url)
	{
		var s = '<iframe name="mainFrame" scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
		return s;
	}
	
	function openPhoneNoDlg(type) {
		$('#consultTranDlgBtn').attr('title',type); 
		$('#consultTranDlg').dialog('open');
		$('#consultTranDlg').dialog('refresh','<%=basePath%>window/contactList.jsp');
	}

    </script>

</head>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
<div id='Loading' style="position:absolute;z-index:1000;top:0px;left:0px;width:100%;height:100%;background:#DDDDDB url('style/images/bodybg.jpg');text-align:center;padding-top: 20%;"><h1>
<font color="#15428B">加载中···</font></h1></div>
	<noscript>
		<div
			style="position: absolute; z-index: 100000; height: 2046px; top: 0px; left: 0px; width: 100%; background: white; text-align: center;">
			<img src="images/noscript.gif" alt='抱歉，请开启脚本支持！' />
		</div>
	</noscript>
	<div region="north" split="true" border="false" id="north"
		style="overflow: hidden; height: 50%; background: url(images/layout-north-hd-bg.gif) #7f99be repeat-x center 100%; line-height: 35px; color: #fff; font-family: Verdana, 微软雅黑, 黑体;">
		<span style="float: right; padding-right: 20px;" class="head">你好，${current_user.loginName }
			<a href="javascript:void(0)" id="editpass">修改密码</a> <a
			href="javascript:void(0)" id="loginOut">安全退出</a></span> <span
			style="padding-left: 10px; font-size: 18px;"><img
			src="images/blocks.gif" width="20" height="20" align="absmiddle" />
			鄞州地方税务局纳税服务系统</span>
			 
			<div style="float: right;line-height: 50px" fit="true">
				<iframe id="phone" align="middle" name="phone" scrolling="no" frameborder="0"  src="<%=basePath%>edb_bar/phoneBar/phonebar.html?loginName=${current_user.callCenterAccount }&password=${current_user.callCenterPwd }&loginType=gateway" width="500px" height="60px"></iframe>
			</div>
	</div>
	<div region="south" split="true"
		style="height: 30px; background: #D2E0F2;">
		<div class="footer">By 宁波汇民信息科技有限公司 Email:ming.liu@nbhmit.com</div>
	</div>
	<div region="west" split="true" title="导航菜单" style="width: 130px;"
		id="west">
		<div class="easyui-accordion" fit="true" border="false">
			<!--  导航内容 -->

		</div>

	</div>
<!-- 	<div region="east" split="true" title="效果" style="width: 230px;" -->
<!-- 		id="east"> -->
<!-- 		</div> -->
	<div id="mainPanle" region="center"
		style="background: #eee; overflow-y: hidden">
		<div id="tabs" class="easyui-tabs" fit="true" border="false">
			<div title="公告通知" id="home" fit="true" border="false">

				 <iframe name="mainFrame" scrolling="auto" frameborder="0"  src="<%=basePath%>jsp/message/message.jsp" style="width:100%;height:100%;"></iframe>
			</div>
		</div>
	</div>

	</div>

	<!--修改密码窗口-->
	<div id="w" class="easyui-window" title="修改密码" collapsible="false"
		minimizable="false" maximizable="false" icon="icon-save"
		style="width: 300px; height: 150px; padding: 5px; background: #fafafa;">
		<div class="easyui-layout" fit="true">
			<div region="center" border="false"
				style="padding: 10px; background: #fff; border: 1px solid #ccc;">
				<table cellpadding=3>
					<tr>
						<td>新密码：</td>
						<td><input id="txtNewPass" type="Password" class="txt01" /></td>
					</tr>
					<tr>
						<td>确认密码：</td>
						<td><input id="txtRePass" type="Password" class="txt01" /></td>
					</tr>
				</table>
			</div>
			<div region="south" border="false"
				style="text-align: right; height: 30px; line-height: 30px;">
				<a id="btnEp" class="easyui-linkbutton" icon="icon-ok"
					href="javascript:void(0);"> 确定</a> <a id="cancel"
					class="easyui-linkbutton" icon="icon-cancel"
					href="javascript:void(0);" onclick="closeLogin()">取消</a>
			</div>
		</div>
	</div>

	<div id="mm" class="easyui-menu" style="width: 150px;">
		<div id="mm-tabclose">关闭</div>
		<div id="mm-tabcloseall">全部关闭</div>
		<div id="mm-tabcloseother">除此之外全部关闭</div>
		<div class="menu-sep"></div>
		<div id="mm-tabcloseright">当前页右侧全部关闭</div>
		<div id="mm-tabcloseleft">当前页左侧全部关闭</div>
		<div class="menu-sep"></div>
		<div id="mm-exit">退出</div>
	</div>
	
	<div id="dlg" class="easyui-dialog" title="来电提醒"
		style="width: 250px; height: 150px; padding: 10px 20px;" closed="true" modal="true"
		buttons="#dlg-buttons">
		
	</div>
	<div id="dlg-buttons">
		<a id="ringBtn" href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-save">确定</a>
	</div>
	<div id="consultTranDlg" class="easyui-dialog" title="电话薄"
		style="width: 350px; height: 400px;" closed="true" modal="true"
		buttons="#consultTranDlg-button">
	</div>
	<div id="consultTranDlg-button">
		<a id="consultTranDlgBtn" href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-save">确定</a>
		<a id="clearBtn" href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-cancel">清除</a>
	</div>
</body>
</html>