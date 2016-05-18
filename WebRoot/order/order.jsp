<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
response.sendRedirect("http://localhost/needesign/work/recordOrder?name=ss");
//request.getRequestDispatcher("work/recordOrder").forward(request,response);
%>


