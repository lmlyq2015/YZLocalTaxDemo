<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <title>后台管理</title>

    <link rel="stylesheet" type="text/css" href="../../themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../../themes/icon.css">
	<script type="text/javascript" src="../../jquery/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="../../jquery/jquery.easyui.min.js"></script>
	<link rel="stylesheet" type="text/css" href="../../css/common.css"/>
    <link rel="stylesheet" type="text/css" href="../../css/main.css"/>
    <script type="text/javascript" src="../../js/libs/modernizr.min.js"></script>
</head>
<body>
<script type="text/javascript">
var operate;
	$(function(){
		$('#tt').tree({   
		    url:'../../getFoldTree',
		    animate:true,
		    onContextMenu : function(e,node) {
		    	e.preventDefault();
				$('#mm').menu('show', {
					left: e.pageX,
					top: e.pageY
				}).data("currfold",node);
		    },
		    onClick : function(node) {
		    	
				var path = getNodePath(node.target);
				if (path == '') {
					path = '<span class="crumb-name">当前位置:</span>' + node.text;
				} else {
					path = '<span class="crumb-name">当前位置:</span>' + getNodePath(node.target) + '>' + node.text;
				}
				$("#iframe").contents().find('#path').html(path);
				$("#iframe").contents().find('#dgDiv').css('display','block');
				$("#iframe").contents().find('#resultDiv').css('display','none');
				$("#iframe").contents().find('#addBtn').css('display','block');
				$("#iframe").contents().find('#searchByKewordsDiv').css('display','none');
				$("#iframe").contents().find('#titleDiv').css('display','block');
				$("#iframe").contents().find('#searchResultDg').datagrid({
					url : '../../getContentByNode',
					loadMsg : '加载中...',
					fitColumns:false,
					singleSelect:true,
			 		showHeader : true,
			 		nowrap : false,
					pagination : true,
					pageSize:5,
					pageList:[5,10,15],
			 		queryParams : {'nodeId':node.id},
					columns:[[
								{
									title : '标题',
									field : 'questions',
									width:fixWidthTable(0.2)
								},
								{
									title : '内容',
									field : 'answers',
									width:fixWidthTable(0.2)
								},
								{
									title : '采集员',
									field : 'editor',
									width:fixWidthTable(0.2)
								},
								{
									title : '更新时间',
									field : 'updateDate',
									width:fixWidthTable(0.3)
								},
								{
									title : '操作',
									field : 'operate',
									width:fixWidthTable(0.2),
									formatter : function(value,row,index) {
										return "<a href='javascript:void(0);' class='easyui-linkbutton' onclick='updateContent(\""+ row.id +"\",\""+ row.questions +"\",\""+ row.answers +"\");'>更新</a>" + 
												"<a href='javascript:void(0);' class='easyui-linkbutton' style='padding-left:20px;' onclick='deleteContent(\"" + row.id + "\")'>删除</a>";
									}
								}
					      	]],
					      	onLoadSuccess : function(data) {
					      		if (data.rows.length == 0) {
					      			$("#iframe").contents().find('#dgDiv').css('display','none');
					      			$("#iframe").contents().find('#titleDiv').html("此目录还没发布任何内容!");
					      		} else {
					      			$("#iframe").contents().find('#titleDiv').html("");
					      		}
					      	}
				});
		    	
		    }
 		});
		
		
		$('#mm-add').click(function(){
			operate = 'add';
			$('#dlg').dialog('open');
			$('#fName').val('');
			//$("input").removeAttr("disabled");
			
		});
		
		
		$('#mm-update').click(function(){
			operate = 'update';
			$('#dlg').dialog('open');
			var target = $('#mm').data("currfold");
			$('#fName').val(target.text);
			//$('#fName').attr('disabled','disabled');
			
		});
		
		
		$('#mm-delete').click(function(){
			operate = 'delete';
			var node = $('#mm').data("currfold");
			var parent = $('#tt').tree('getParent',node.target);
			var child = $('#tt').tree('getChildren',node.target);
			var childJson = encodeURI(JSON.stringify(child),"UTF-8");
			if (parent == null) {
				$.messager.alert('操作提示', "根节点无法删除","info");
				return;
			} else {
			    $.messager.confirm("操作提示", "删除目录将同时子目录和目录下所有知识点，确定删除？", function (data) {
			        if (data) {
			           		$.ajax({
			           			url : '../../deleteNode',
			           			type : 'post',
			           			dataType : 'json',
			           			data : "data=" + node.id + "=" + childJson ,
			           			success : function(data) {
			           				$.messager.alert('操作提示', "删除成功","info");
			           				$("#tt").tree('reload');
			           			},
			           			error : function() {
			           				$.messager.alert('操作提示', "删除失败，请重试","error");
			           			}	
			           		});
			          }
			      });
			}
		});
		
		
		$('#closeBtn').click(function(){
			$('#dlg').dialog('close');
		});
		
		$('#okBtn').click(function(){
			var target = $('#mm').data("currfold");
			var data = encodeURI(JSON.stringify(target),"UTF-8");
			var name = $('#fName').val();
			if (name == '') {
				$.messager.alert('操作提示', "请输入文件名","error");
				return;
			}
			$.ajax({
				url : '../../addNode',
				type : "POST",
				dataType : "json",
				data : 'data=' + data + "=" + name + "=" + operate,
				success : function(r) {
					$("#tt").tree('reload');
					$('#dlg').dialog('close');
					
				},
				error : function() {
					$.messager.alert('操作提示', "添加目录出错,请重试","error");
					$('#dlg').dialog('close');
				}
			});

			
			
		});

	});
	function getNodePath(target) {
		var path='';
		var parent = $('#tt').tree('getParent',target);
		if (parent != null) {
			path = getNodePath(parent.target);
			if (path == '') {
				return path + parent.text;
			} else {
				return path + '>' + parent.text;
			}
		} else {
			return '';
		}
	}
    function fixWidthTable(percent) {  
		
    	return getWidth(0.7) * percent;  
  	}  
    	          
    function getWidth(percent){  
    	return $(window).width() * percent;  
    }

</script>
<div class="container clearfix">
    <div class="sidebar-wrap">
        <div class="sidebar-title">
            <h1>知识库目录</h1>
        </div>
        <div class="sidebar-content">
			<ul id="tt"></ul>  
    
        </div>
    </div>
    <!--/sidebar-->
    <div class="main-wrap">
    	<iframe id="iframe" name="mainFrame" scrolling="auto" frameborder="0"  src="../../jsp/knowledge/search.jsp" style="width:100%;height:700px;"></iframe>
    </div>
    <!--/main-->
</div>
	<div id="mm" class="easyui-menu" style="width: 120px;">
		<div id="mm-add" iconCls="icon-add">新增</div>
		<div id="mm-update" iconCls="icon-edit">修改</div>
		<div id="mm-delete" iconCls="icon-cancel">删除</div>
	</div>
	<div id="dlg" class="easyui-dialog"
		style="width: 400px; height: 250 px; padding: 10px 20px;" closed="true" modal="true" title="添加目录信息" buttons="#foldDialogBtn">
		       <form action="#" method="post">
                    <table class="search-tab">
                        <tr>
                            <th width="70">目录名称:</th>
                            <td><input class="common-text" placeholder="关键字" name="keywords" value="" id="fName" type="text"></td>
                        </tr>
                    </table>
                </form>
	</div>
	<div id="foldDialogBtn">
		<button id="okBtn"  class="btn btn-default" iconcls="icon-save">确定</button>
		<button id="closeBtn"  class="btn btn-default" iconcls="icon-cancel">取消</button>
	</div>
</body>
</html>