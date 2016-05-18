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
		<title>后台首页</title>
		<meta http-equiv=Content-Type content=text/html;charset=UTF-8>
		<script type="text/javascript" src="${basePath }js/jquery.min.js"></script>
		<script type="text/javascript" src="${basePath }js/login.js"></script>
		<script type="text/javascript" src="${basePath }js/loginregx.js"></script>
	</head>
	<frameset rows="64,*" frameborder="NO" border="0" framespacing="0">
		<frame src="admin_top.jsp" noresize="noresize" frameborder="NO"
			name="topFrame" scrolling="no" marginwidth="0" marginheight="0"
			target="main" />
		<frameset cols="200,*" rows="560,*" id="frame">
			<frame src="left.jsp" name="leftFrame" noresize="noresize"
				marginwidth="0" marginheight="0" frameborder="0" scrolling="no"
				target="main" />
			<frame src="right.jsp" name="main" marginwidth="0" marginheight="0"
				frameborder="0" scrolling="auto" target="_self" />
		</frameset>
		<noframes>
			<body></body>
		</noframes>
		</frameset>
</html>
