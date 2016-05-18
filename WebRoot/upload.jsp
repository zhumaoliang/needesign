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
		<base href="<%=basePath%>">

		<title>My JSP 'upload.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<script src="jquery.min.js"></script>
		<script src="jquery-form.js"></script>
<style type="text/css">
 .mask {    
            position: absolute; top: 0px; filter: alpha(opacity=60); background-color: #FCFCFC;  
            z-index: 1002; left: 0px;  
            opacity:0.5; -moz-opacity:0.5;  
        }  
    .model{  
            position: absolute; z-index: 1003;   margin-top:250px;
            width:320px; height:320px; text-align:center;  
            background-color:#0066FF; display: none;  
        }  
</style>
<script type="text/javascript">
//兼容火狐、IE8  
function showMask(){  
    $("#mask").css("height",$(document).height());  
    $("#mask").css("width",$(document).width());  
    $("#mask").show();  
}  
//让指定的DIV始终显示在屏幕正中间  
function letDivCenter(divName){   
    var top = ($(window).height() - $(divName).height())/2;   
    var left = ($(window).width() - $(divName).width())/2;   
    var scrollTop = $(document).scrollTop();   
    var scrollLeft = $(document).scrollLeft();   
    $(divName).css( { position : 'absolute', 'top' : top + scrollTop, left : left + scrollLeft } ).show();  
}  
function showAll(divName){  
    showMask();  
    letDivCenter(divName);  
}  
function closeAddAd(){
	  $("#mask").hide();  
	  $("#model").hide();  
}
function show2(){
	  $("#test").ajaxSubmit({
          success:function(data){
              alert(data.hash);
          },
          error:function(){
        	  alert("error");
          }
      });
}

function getQiNiuToken(){
	$.ajax({
		url :  'http://localhost/needesign/user/getQiniuToken',// 跳转到Login.action
		data : {
		},
		type : 'post',
		cache : false,
		dataType : 'json',
		success : function(data) {
			if (data.rtnCode == "000000") {
				var token=data.responseData;
				$("#token").val(token);
			} else{
				return;
			}
		}
	/*
	 * error : function() { // view("异常！"); alert("异常！"); }
	 */
	});
}
function t(){
	var daily_id = $('input[name=file]');  
	
	alert($(daily_id[0]).val());
	alert($(daily_id[1]).val());


}
function w(){
	var a=$("#file")[0].value;
	var b=$("#file")[1].value;
	alert(a);
	alert(b);
}
function uuid() {
    var s = [];
    var hexDigits = "0123456789abcdefghijklmn";
    for (var i = 0; i < 39; i++) {
        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
    }
    s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
   

    var uuid = s.join("");
}
</script>
	</head>

	<body>
	<script type="text/javascript">
	
	</script>
	  <input type="button" onclick="uuid()" value="testt"/>
	<i>http://upload.qiniu.com/</i>
	<a href="https://www.needesigner.net/needesign/user/getUserInfo?userid=5b4f8c97d4fd48bb8e3c3a925bfcff3d" >tttt</a>
	<form method="POST" action="http://upload.qiniu.com/"
			id="test" name="test" enctype="multipart/form-data">
			<input id="token" name="token" type="hidden" value="l9g6EIYt9xR-QSx5FzrisZFwdth8cHHrqxDyhf1Y:O_H5v3eU1DomU_yoEx9XoZxj45w=:eyJzY29wZSI6IndvcmtpbWciLCJkZWFkbGluZSI6MTQ2MjkzNzgzMH0=">
			<input id="key" name="key" type="hidden" value="2222">
		<input type="file" id="file" name="file" onchange="t()"/>
		<input type="submit" value="aa"/>
		</form>
	<form method="POST" action="https://www.needesigner.net/needesign/dynamic/getDynamicList"
			id="test1" name="test1" >
			<input id="pageindex" name="pageindex"  value="1">
				<input id="type" name="type"  value="0">
		<input type="submit" value="aa"/>
		</form>
	</body>
</html>
