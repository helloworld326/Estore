<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set value="${pageContext.request.contextPath }" var="root"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>登录</title>
<%@include file="inc/common_head.jsp"%>
</head>
<script type="text/javascript">
	window.onload = function() {
		document.getElementById("rememberId").value = decodeURI("${cookie.username.value}");
	};
</script>
<body>
	<%@include file="inc/header.jsp"%>
	<div class="block block1">
		<div class="blank"></div>
		<div class="usBox clearfix">
			<div class="usBox_1">
				<div class="login_tab">
					<ul>
						<li class="active">用户登录</li>
						<li onclick="location.href='register.jsp';"><a
							href="javascript:;">用户注册</a></li>
					</ul>
				</div>
				<form name="formLogin" action="${root }/user?method=login"
					method="post" onSubmit="return userLogin()">
					<table width="100%" border="0" align="left" cellpadding="3"
						cellspacing="5">
						<tr>
							<td width="25%" align="right">用户名</td>
							<td width="65%"><input name="username" id="rememberId"
								type="text" size="25" class="inputBg" /></td>
						</tr>
						<tr>
							<td align="right">密码</td>
							<td><input name="password" value="${user.password }"
								type="password" size="15" class="inputBg" /></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td><input type="checkbox" value="on" name="remember" id="remember" /> <label for="remember">记住用户名</label></td>
							<!-- 
								<td >
									<input type="checkbox" value="on" name="autoLogin" id="autoLogin" />
									<label for="autoLogin">自动登陆</label>
								</td> 
							-->
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td align="left"><span style="color:red;">${loginInfo }</span><br />
								<input type="submit" name="submit" value="" class="us_Submit" />
							</td>
						</tr>
					</table>
				</form>
				<div class="blank"></div>
			</div>
		</div>
		<%@include file="inc/footer.jsp"%>
</body>
</html>