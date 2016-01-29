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
<link rel="stylesheet" type="text/css" href="themes/style.css">

<script type="text/javascript" src="jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="jquery/jquery.easyui.min.js"></script>
<style>

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
 				location.href='./tax.xxxx';	

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
<body>

  <section class="container">
    <div class="login">
      <h1 style="font-size:22px">鄞州地方税务局纳税服务系统</h1>
      <form id="loginForm">
        <p><input class="easyui-validatebox" required="true" type="text" name="loginName" id="loginName" placeholder="登录名"></input></p>
        <p><input class="easyui-validatebox" required="true" type="password" name="password" id="password" placeholder="密码"></input></p>
		<p><input class="easyui-validatebox"  required="true" id="code" type="text" name="code" placeholder="验证码"></input></p>
			<p style="text-align:right"><img width="25%" id="number" src="<%=basePath %>jsp/util/number.jsp"/>&nbsp&nbsp
				<a href="javascript:reloadImage('<%=basePath %>jsp/util/number.jsp')">看不清</a>
				</p>
       
        <p class="submit"><input type="submit" name="login" value="登录" onclick="submitForm();" >
        				  <input type="submit" name="commit" value="重置" onclick="clearForm();" ></p>
      </form>
    </div>
	
  </section>
<div style="text-align:center;">
<p>宁波汇民信息科技有限公司</p>
</div>
</body>
</html>
