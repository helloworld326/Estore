<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>商城后台管理系统</title>
<link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
<script type="text/javascript" src="easyui/jquery.min.js"></script>
<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
<script type="text/javascript">
$(function(){
	
	$('#tt').tree({
		onClick: function(node){
			//创建新的选项卡，如果已经存在，就选中当前选项卡
				var flag = $('#center').tabs("exists", node.text);
				if(flag){
					$('#center').tabs("select", node.text);
					return;
				}
				if (node.text === '商品查看') {
					$('#center').tabs('add', {
					    title:node.text,
					    content:'<table id="dg"></table>',
					    closable:true
					});
				//使用数据网格展示数据
				$('#dg').datagrid({
					pagination:true,
					fit:true,
				    url:'${pageContext.request.contextPath}/admin?method=pageGood',
				    columns:[[
				              //field 数据字段  title：数据的标题
						{field:'id',title:'id',width:100},
						{field:'name',title:'name',width:100},
						{field:'marketprice',title:'marketprice',width:100},
						{field:'estoreprice',title:'estoreprice',width:100},
						{field:'category',title:'category',width:100},
						{field:'num',title:'num',width:100},
						{field:'imgurl',title:'imgurl',width:100,formatter: function(value,row,index){
							return "<img src='${pageContext.request.contextPath}/admin?method=picture&imgurl="+value+"' style='width:50px'>";
						}},
						{field:'description',title:'description',width:300}
				    ]]
				});
			} else if (node.text === '商品上传') {
				$('#center').tabs('add', {
					    title:node.text,
					    href:"add_good.jsp",
					    closable:true
				});
			} else {
				$('#center').tabs('add', {
					    title:node.text,
					    content:'<table id="hot"></table>',
					    closable:true
					});
				$("#hot").datagrid({
					pagination:true,
					fit:true,
				    url:'${pageContext.request.contextPath}/admin?method=getHot',
				    columns:[[
						{field:'gid',title:'id',width:100},
						{field:'name',title:'name',width:200},
						{field:'hot',title:'hot',width:200}
				    ]]
				});
			}
		}
	});
	
});

</script>
</head>
<body class="easyui-layout">
    <div data-options="region:'north',title:'聚美优品(山寨版)后台商品管理系统',split:true" style="height:100px;">
    	<marquee><font style="color:blue;font-size:25px;">欢迎,${user.username },来到聚美优品(山寨版)商城后台管理系统！！！</font></marquee>
    </div>
    <div data-options="region:'south',title:'友情链接',split:true" style="height:100px;">
    	<a href="${pageContext.request.contextPath }/">商城首页</a>
    </div>
    
    <div data-options="region:'west',title:'商品管理',split:true" style="width:200px;">
    <ul id="tt" class="easyui-tree">
	    <li>
			<span>商城管理</span>
			<ul>
				<li>
					<span>商品管理</span>
					<ul>
						<li><span>商品查看</span></li>
						<li><span>商品上传</span></li>
						<li><span>热门统计</span></li>
					</ul>
				</li>
			</ul>
		</li>
	</ul>
    </div>
    <div data-options="region:'center',title:'管理信息'" style="padding:5px;background:#eee;">
 	   <div id="center" class="easyui-tabs" style="width:500px;height:250px;" data-options="fit:true"></div>
    </div>
</body>
</html>