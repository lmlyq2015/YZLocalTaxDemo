<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="../../themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../../themes/icon.css">
	<script type="text/javascript" src="../../jquery/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="../../jquery/jquery.easyui.min.js"></script>
	<link rel="stylesheet" type="text/css" href="../../css/common.css"/>
    <link rel="stylesheet" type="text/css" href="../../css/main.css"/>
    <script type="text/javascript" src="../../js/libs/modernizr.min.js"></script>
    <script type="text/javascript" src="../../locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
<script type="text/javascript">
	$(function(){
		$('#addBtn').click(function(){
			$('#resultDiv').css('display','block');
			$('#titleDiv').css('display','none');
			$('#searchByKewordsDiv').css('display','none');
			$('#dgDiv').css('display','none');

		});
		
		$('#returnBtn').click(function() {
		
			$('#resultDiv').css('display','none');
			
		});
		
		$('#myform').form({
			url : '../../addContentByNode',
			onSubmit: function(){
				if ($('#title').val() == '') {
					$.messager.alert('操作提示', "请输入标题","info");
					return false;
				}
				return true;
			},
			success : function(data) {
				$.messager.alert('操作提示', "添加成功","info");
				var node = parent.$('#tt').tree('getSelected');
				node.target.click();
			}
			
		});
		$('#submitBtn').click(function() {
			var node = parent.$('#tt').tree('getSelected');
			var id = node.id;
			$('#nodeId').val(id);
			$('#myform').submit();
		});
		$('#seatchBtn').click(function() {
			var keywords = $('#keywords').val();
			if (keywords.trim() == '') {
				return;
			} else {
				$('#searchByKewordsDiv').css('display','block');
				$('#resultDiv').css('display','none');
				$('#dgDiv').css('display','none');
				$('#titleDiv').css('display','block');
				$('#searchByKewordsDg').datagrid({
						url : '../../searchContentByKeyWords',
						loadMsg : '加载中...',
						fitColumns:false,
						singleSelect:true,
				 		showHeader : false,
				 		nowrap : false,
						pagination : true,
						pageSize:5,
						pageList:[5,10,20],	
						queryParams : {'keywords':keywords},
						columns:[[	
								       {
								        title : '搜索结果',
								        field : 'questions',
										width :window.parent.fixWidthTable(1),
								        formatter : function(value,row,index) {
								        	var path = getNodePath(row.foldId);
								        	return '<div class="result-wrap">' +
								        				'<a href="javascript:void(0);">'+value+'</a><br>' +
								        				'<span>'+row.answers+'</span><br>'+	
								        				'<span>所属目录: '+path+'</span><span style="padding-left:10px;">更新时间: '+row.updateDate+'</span>'+
								        			'</div>';
								        }
								       }
								          
								  ]],
							      onLoadSuccess : function(data) {
							      		if (data.rows.length == 0) {	      			
							      			$('#titleDiv').html("<span>当前搜索关键词：<font style='color: red;'>"+keywords+"</font>  找到相关结果0个,没有找到相关知识点</span>");
							      			$('#searchByKewordsDiv').css('display','none');
							      		} else {
							      			$('#titleDiv').html("<span>当前搜索关键词：<font style='color: red;'>"+keywords+"</font>  找到相关结果"+data.rows.length+"个</span>");
							      		}
							      	}
						
						});
			}
		});
		
	});
	function getNodePath(id) {
		var node  =  parent.$('#tt').tree('find',id);
		var path = window.parent.getNodePath(node.target);
		return path + ">" +node.text;
	}
	function clickSearch(event){
	    if(event.keyCode == 13){
	    	$('#seatchBtn').click();
	    }
	}
</script>
        <div class="crumb-wrap">
            <div id="path" class="crumb-list"><span class="crumb-name">当前位置:</span></div>
        </div>
        <div class="search-wrap">
            <div class="search-content">
                    <table class="search-tab">
                        <tr>
                            <th width="70">关键字:</th>
                            <td><input class="common-text" placeholder="关键字" name="keywords" id="keywords" type="text" onkeydown="clickSearch(event)"></td>
                            <td><button id="seatchBtn" class="btn btn-primary">查询</button></td>
                        	<td><button id="addBtn" class="btn btn-primary" style="display: none;">+新增</button></td>
                        </tr>
                    </table>
            </div>
        </div>
        <div class="result-wrap">
                <div class="result-title" id="titleDiv">
                </div>
                <div class="result-content" id="dgDiv">
                    <table id="searchResultDg" width="100%" style="display: none">
						
                    </table>
                    
                </div>
                 <div class="result-content" id="searchByKewordsDiv">
                    <table id="searchByKewordsDg" width="100%" style="display: none">
						
                    </table>
                    
                </div>
          <div>
          
	      <div id="resultDiv" class="result-wrap" style="display: none;">
	            <div class="result-content">
	                <form id="myform" name="myform">
	                    <table class="insert-tab" width="100%">
	                        <tbody><tr>
	                        </tr>
	                            <tr>
	                                <th><i class="require-red">*</i>标题：</th>
	                                <td>
	                                    <input class="common-text required" id="title" name="title" size="50" type="text">
	                                </td>
	                            </tr>
	                            <tr style="display: none">
	                                <th><i class="require-red">*</i>内容：</th>
	                                <td><input name="nodeId" id="nodeId" type="text"></td>
	                            </tr>
	                            <tr>
	                                <th>内容：</th>
	                                <td><textarea name="content" class="common-textarea" id="content" cols="30" style="width: 98%;" rows="10"></textarea></td>
	                            </tr>
	                            <tr>
	                                <th></th>
	                                <td>
	                                    <input id="submitBtn" class="btn btn-primary btn6 mr10" value="提交" type="button">
	                                    <input id="returnBtn" class="btn btn6" value="返回" type="button">
	                                </td>
	                            </tr>
	                        </tbody></table>
	                </form>
	            </div>
	      </div>
    </div>
 </div>
</body>
</html>