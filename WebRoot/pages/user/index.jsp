<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
  login  sucess
  <a href="http://localhost:8080/SpringMvc/user/strs">sss</a>
    <a href="http://localhost:8080/SpringMvc/user/adds">sss</a>
    <a href="http://localhost:8080/SpringMvc/user/getUserInfo?user_id=1000&user_name=kanling">user</a>
      <%--<form action="http://localhost:8080/SpringMvcShiro/user/demoLogin" method="POST">  
        <br />用户帐号： <input type="text" name="username" id="username" value="" />  
        <br />登录密码： <input type="password" name="password" id="password"  
            value="" /> <br />  
        <input value="登录" type="submit">  
    </form>  
  --%></body>
</html>
