<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>用户登录</title>
		<!-- Custom Theme files -->
		<link href="${basePath }css/login.css" rel="stylesheet"
			type="text/css" media="all" />
		<!-- Custom Theme files -->
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="viewport"
			content="width=device-width, initial-scale=1, maximum-scale=1">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="keywords"
			content="Login form web template, Sign up Web Templates, Flat Web Templates, Login signup Responsive web template, Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, SonyErricsson, Motorola web design" />
		<!--Google Fonts-->
		<link
			href='http://fonts.useso.com/css?family=Roboto:400,100,100italic,300,300italic,400italic,500,500italic,700,700italic,900,900italic'
			rel='stylesheet' type='text/css'>
		<script type="text/javascript" src="${basePath }js/jquery.min.js"></script>
		<script type="text/javascript">
var app_path='<%=basePath%>';
</script>
		<script type="text/javascript" src="${basePath }js/login.js"></script>
		<script type="text/javascript" src="${basePath }js/loginregx.js"></script>
	</head>
	<body>
		<!--header start here-->
		<form action="${basePath }login.do" method="post" onsubmit="return checkLogin();">
		<div class="login">
			<div class="login-main">
				<div class="login-top">
					<img src="${basePath }images/head-img.png" alt="" />
					<h1>
						管理员登录
					</h1>
					<input type="text" placeholder="Email" required=""
						name="login_name" id="login_name" value="${login_name }" onfocus="checkLoginName();"/>
					<input type="password" placeholder="密码" required=""
						name="login_pwd" id="login_pwd" value="${login_pwd }" onfocus="checkLoginName();"/>
						
					<div class="login-bottom">
		 			  <div class="login-check">
			 			<font style="color: red;"><a >${errorMsg }</a></font>
			 		  </div>
						<div class="clear">
						</div>
					</div>
					<input type="submit" value="登录" />
				</div>
			</div>
		</div>
		</form>
		<!--header end here-->
	</body>
</html>