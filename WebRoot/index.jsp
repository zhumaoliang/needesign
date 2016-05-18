<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<title>jQuery弹出窗口 - 计划-博客园</title> 
<meta name="keywords" content="www.cnblogs.com/jihua"/>
<style type="text/css"> 
.window{ 
    width:250px; 
    background-color:#d0def0; 
    position:absolute; 
    padding:2px; 
    margin:5px; 
    display:none; 
    } 
.content{ 
    height:150px; 
    background-color:#FFF; 
    font-size:14px; 
    overflow:auto; 
    } 
    .title{ 
        padding:2px; 
        color:#0CF; 
        font-size:14px; 
        } 
.title img{ 
        float:right; 
        } 
</style> 
<script type="text/javascript" src="http://down.hovertree.com/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" language="javascript">
    $(document).ready(function () {
        $("#btn_center").click(function () {
            popCenterWindow();
        });

        $("#btn_right").click(function () {
            popRightWindow();
        });
        $("#btn_left").click(function () {
            popLeftWindow();
        });

    }); 
 
</script> 
</head> 
<body>
 <div style="width:1000px;margin-left:auto;margin-right:auto;margin-top:160px;">
<input type="button" value="居中窗口" id="btn_center" /> 
<input type="button" value="居左下角" id="btn_left" /> 
<input type="button" value="居右下角" id="btn_right" /> 
</div>
   <div class="window" id="center"> 
<div id="title" class="title"><img src="http://pic002.cnblogs.com/images/2012/451207/2012100814082487.jpg" alt="关闭" />计划 博客园-居中窗口</div> 
<div class="content">jihua.cnblogs.com</div> 
   </div> 
    
  <div class="window" id="left"> 
<div id="title2" class="title"><img src="http://pic002.cnblogs.com/images/2012/451207/2012100814082487.jpg" alt="关闭" />计划 博客园-居左窗口</div> 
<div class="content">jihua.cnblogs.com</div> 
  </div> 
  <div class="window" id="right"> 
<div id="title3" class="title"><img src="http://pic002.cnblogs.com/images/2012/451207/2012100814082487.jpg" alt="关闭" />计划 博客园-居右窗口</div> 
<div class="content">jihua.cnblogs.com</div> 
  </div>
  <script type="text/javascript">
  //获取窗口的高度 
var windowHeight; 
//获取窗口的宽度 
var windowWidth; 
//获取弹窗的宽度 
var popWidth; 
//获取弹窗高度 
var popHeight; 
function init(){ 
   windowHeight=$(window).height(); 
   windowWidth=$(window).width(); 
   popHeight=$(".window").height(); 
   popWidth=$(".window").width(); 
} 
//关闭窗口的方法 
function closeWindow(){ 
    $(".title img").click(function(){ 
        $(this).parent().parent().hide("slow"); 
        }); 
    } 
    //定义弹出居中窗口的方法 
    function popCenterWindow(){ 
        init(); 
        //计算弹出窗口的左上角Y的偏移量 
    var popY=(windowHeight-popHeight)/2; 
    var popX=(windowWidth-popWidth)/2; 
    //alert('jihua.cnblogs.com'); 
    //设定窗口的位置 
    $("#center").css("top",popY).css("left",popX).slideToggle("slow");  
    closeWindow(); 
    } 
    function popLeftWindow(){ 
        init(); 
        //计算弹出窗口的左上角Y的偏移量 
    var popY=windowHeight-popHeight; 
    //var popX=-(windowWidth-popWidth); 
    //alert(popY); 
    //设定窗口的位置 
    $("#left").css("top",popY-50).css("left",50).slideToggle("slow"); 
    closeWindow(); 
    } 
    function popRightWindow(){ 
        init(); 
        //计算弹出窗口的左上角Y的偏移量 
    var popY=windowHeight-popHeight; 
    var popX=windowWidth-popWidth; 
    //alert(www.cnblogs.com/jihua); 
    //设定窗口的位置 
    $("#right").css("top",popY-50).css("left",popX-50).slideToggle("slow"); 
    closeWindow(); 
    } </script> 
</body> 
</html>
