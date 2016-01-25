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

</script>
<body class="easyui-layout">

<!--       <div region="west" title="通话" style="width: 250px;height: 500px;" spilt="true">   -->
      
      
<!--    	  </div> -->
             
      <div region="center" title="来电详情" style="padding:5px;background:#eee;" fit="true" border="false" spilt="true">
<%-- 			      	<iframe name="phone" scrolling="auto" frameborder="0"  src="<%=basePath%>edb_bar/phoneBar/phonebar.html?loginName=8000@hmxx&password=8000&loginType=sip" style="width:100%;height:100%;"></iframe> --%>
			
  	  </div>

</body>
</html>




