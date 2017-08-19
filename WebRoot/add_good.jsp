<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
<!-- 设置文件上传的表单 -->
<form id="ff" method="post" action="${pageContext.request.contextPath }/admin?method=addGood" enctype="multipart/form-data">
    <div>
		<label for="name">商品名称:</label>
		<input class="easyui-validatebox" type="text" name="name" data-options="required:true,missingMessage:'商品必须书写名称'" />
    </div>
    <div>
		<label for="marketprice">市场价格:</label>
		<input type="text" class="easyui-numberbox" value="100" name="marketprice" data-options="min:0,precision:2">
    </div>
     <div>
		<label for="estoreprice">商城价格:</label>
		<input type="text" class="easyui-numberbox" value="100" name="estoreprice" data-options="min:0,precision:2">
    </div>
    <div>
		<label for="category">商品分类:</label>
		<select id="cc" class="easyui-combobox" name="category" style="width:200px;">
		    <option value="图书、电子书刊、音像">图书、电子书刊、音像</option>
		    <option value="手机数码">手机数码</option>
		    <option value="电脑办公">电脑办公</option>
		</select>
    </div>
     <div>
		<label for="num">商品库存:</label>
		<input type="text" class="easyui-numberbox" value="100" name="num" data-options="min:0">
    </div>
     <div>
		<label for="imgurl">商品图片:</label>
		<input class="easyui-filebox" style="width:200px" name="imgurl">
    </div>
     <div>
		<label for="description">商品描述:</label>
		<input class="easyui-textbox" data-options="multiline:true" name="description" style="width:200px;height:100px">
    </div>
    <input type="submit" value="上传商品" class="easyui-linkbutton">
</form>
</body>
</html>