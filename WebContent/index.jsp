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
<body class="easyui-layout">

      <div region="north" title="North Title" split="true" style="height:100px;"></div>  

      <div region="south" title="South Title" split="true" style="height:100px;"></div>  

      <div region="east" iconCls="icon-reload" title="East" split="true" style="width:100px;"></div>  

      <div region="west" split="true" title="West" style="width:100px;"></div>  

      <div region="center" href="./page/center.html" title="center title" style="padding:5px;background:#eee;">

  </div> 
</body>
</html>