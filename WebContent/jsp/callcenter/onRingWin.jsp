<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<script type="text/javascript">

</script>
<table>
	<tr>
		<th>来电号码：</th>
		<td><span id="calledNo">${onRingVo.originCallNo }</span></td>
	</tr>
	<tr>
		<th>省份:</th>
		<td><span id="province">${onRingVo.callerProvince }</span></td>
	</tr>
	<tr>
		<th>城市:</th>
		<td><span id="city">${onRingVo.callerCity }</span></td>
	</tr>

</table>

</body>
</html>