<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<base href="<%=basePath%>">
<head>
<title>登陆</title>
<link rel="shortcut icon" href="../favicon.ico" />
<link rel="stylesheet" type="text/css" href="themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="themes/icon.css">
<link rel="stylesheet" type="text/css" href="themes/demo.css">

<script type="text/javascript" src="jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="jquery/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js"></script>
<style>
div,a,td,span,body,font {
	color: #000000;
	font-family: "宋体";
	line-height: 16px;
	font-size: 12px;
}

.style_input {
	background:
		url(http://www.366tax.com:7001/tax366/login/images/login/bg_login_but.gif)
		top left no-repeat;
	border: none;
	height: 22px;
	width: 50px;
	font-size: 12px;
	color: #006666;
	text-align: center;
	line-height: 22px;
	padding-top: 2px;
	cursor: pointer;
}

.bg_input {
	background:
		url(http://www.366tax.com:7001/tax366/login/images/login/bg_login_input.gif)
		top left no-repeat;
	border: none;
	height: 22px;
	width: 174px;
	font-size: 12px;
	color: #D3D980;
	padding: 5px 0px 0px 5px;
}

.bg_input1 {
	background:
		url(http://www.366tax.com:7001/tax366/login/images/login/bg_input_80.gif)
		top left no-repeat;
	border: none;
	height: 22px;
	width: 174px;
	font-size: 12px;
	color: #D3D980;
	padding: 5px 0px 0px 5px;
}

a:link {
	font-size: 12px;
	color: #11DDE8;
	text-decoration: none;
}

a:visited {
	font-size: 12px;
	color: #11DDE8;
	text-decoration: none;
}

a:hover {
	font-size: 12px;
	color: #11DDE8;
	text-decoration: none;
}
</style>
<script type="text/javascript">
if (window != top)
	top.location.href = location.href; 
</script>
<script type="text/javascript">
$(function(){
	 $('#loginForm').form({
		url : '<%=basePath%>login',
		onSubmit : function() {
			return $("#loginForm").form('validate');
		},
		success : function(data) {
			var res = jQuery.parseJSON(data);
			if (res.result) {
<%-- 				VAR USER = '<%=REQUEST.GETSESSION().GETATTRIBUTE("CURRENT_USER")%>'; --%>
// 				CONSOLE.INFO(USER);
				//location.reload(true);
 				location.href='./tax.html';	

			} else {
				$.messager.alert('提示', '信息填写错误', 'error');

			}
		}
	});
});

	function submitForm() {
		$('#loginForm').submit();

	}

	function reloadImage(imgurl) {
		var verify = document.getElementById('number');
		verify.src = imgurl + "?id=" + Math.random();
	}
	function clearForm() {
		$("#loginForm").form('clear');
	}
	function onKeydown(){
		if(event.keyCode==13){
			$('#loginForm').submit();
			 event.returnValue = false; 
		}
	}
</script>
</head>
<body style="padding: 0px; margin: 0px; overflow: auto;" >
	<input type="hidden" value='' id="key" />
	<form id="loginForm">
		<div
			style="width: 100%; height: 100%; top: 0px; left: 0px; position: absolute; z-index: 2; background-color: #0e684c;"
			id="loginDiv">
			<table width="100%" height="100%" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td align="center" valign="bottom"
						style="height: 40%; background: #095483 url(http://www.366tax.com:7001/tax366/login/images/login/home_bg_top.jpg) repeat-x bottom;">
						<span style="font-size: xx-large;">鄞州地方税务局纳税服务系统 </span>
					</td>
				</tr>
				<tr>
					<td align="center" valign="top"
						style="height: 60%; background: #0185C5 url(http://www.366tax.com:7001/tax366/login/images/login/home_bg_bottom.jpg) repeat-x top;">
						<div
							style="background: url(http://www.366tax.com:7001/tax366/login/images/login/home_bottom.jpg) no-repeat left top; width: 760px; height: 252px;">
							<div style='width: 80%; height: 100px;'>
								<table border="0" align="right" cellpadding="0" cellspacing="0">
									<tr>
										<td width="60" height="40"></td>
										<td width="180">&nbsp;</td>
										<td width="200">&nbsp;</td>
									</tr>
									<tr>
										<td height="32" align="right">登录名：</td>
										<td align="left"><input class="easyui-validatebox"
											required="true" type="text" name="loginName" id="loginName"
											placeholder="登录名"></input></td>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td height="32" align="right">密码：</td>
										<td align="left"><input class="easyui-validatebox"
											required="true" type="password" name="password" id="password"
											placeholder="密码"></input></td>
										<td align="left">&nbsp;</td>
									</tr>
									<tr>
										<td height="36">&nbsp;&nbsp;验证码：</td>
										<td align="left"><input id="code" type="text" name="code"  onkeydown="onKeydown();"/></td>
										<td><img id="number" src="<%=basePath %>jsp/util/number.jsp"
											border=0 /> <a
											href="javascript:reloadImage('<%=basePath %>jsp/util/number.jsp')">看不清</a>
											</td>
									</tr>
									<tr>
										<td colspan="2" align="center"><a id="loginBtn" class="easyui-linkbutton" href="javascript:void(0)" onclick="submitForm()" >登录</a>
										<a class="easyui-linkbutton" href="javascript:void(0)" onclick="clearForm()">重置 </a></td>
									</tr>
								</table>
							</div>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>
