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
		<title>广告首页</title>
		<link rel="stylesheet" href="../../admin/css/bootstrap.min.css">
		<script src="../../admin/js/jquery.min.js"></script>
		<script src="../../admin/js/bootstrap.min.js"></script>
		<script src="../../admin/advertisement/js/adverty.js"></script>
		<script src="../../admin/js/jquery-form.js"></script>
			<script src="../../admin/advertisement/js/advertyregx.js"></script>
		<script type="text/javascript">
		var app_path='<%=basePath%>';
	$(document).ready(function() {
		getAdveryList(1, 5);
	});
</script>
<style type="text/css">
.mask {
	position: absolute;
	top: 0px;
	filter: alpha(opacity = 60);
	background-color: #FCFCFC;
	z-index: 1002;
	left: 0px;
	opacity: 0.5;
	-moz-opacity: 0.5;
}
.editmask {
	position: absolute;
	top: 0px;
	filter: alpha(opacity = 60);
	background-color: #FCFCFC;
	z-index: 1002;
	left: 0px;
	opacity: 0.5;
	-moz-opacity: 0.5;
}
.addAd {
	position: absolute;
	z-index: 1003;
	width: 420px;
	height: 430px;
	text-align: center;
	display: none;
	border: 1px solid #E0E0E0;
	background-color: #E0E0E0;
}
.editAd {
	position: absolute;
	z-index: 1003;
	width: 420px;
	height: 470px;
	text-align: center;
	display: none;
	border: 1px solid #E0E0E0;
	background-color: #E0E0E0;
}
</style>
	</head>
	<body>
		<div id="mask" class="mask"></div>
		<div id="editmask" class="editmask"></div>
		<form class="form-inline">
			<div class="form-group" style="margin-left: 200px;">
				<label for="name">
					公司名称
				</label>
				<input type="text" class="form-control" id="name" />
			</div>
			<div style="margin-left: 500px; margin-top: -34px;">
				<button type="button" class="btn btn-default"
					onclick="getAdveryList(1,5);">
					查询
				</button>
			</div>
			<div style="margin-left: 600px; margin-top: -34px;">
				<button type="button" class="btn btn-default" onclick="reset();">
					重置
				</button>
			</div>
		</form><%--
		<hr style="height: 1px; border: none; border-top: 1px solid #555555;" />

		--%><div style="margin-top: 150px;">
			<table class="table table-bordered">
				<caption>
					广告列表
					<a onclick="showAd('#addAd');"
						style="margin-left: 1030px; text-decoration: none; cursor: pointer">添加</a>
				</caption>
				<thead>
					<tr>
						<th>
							公司名称
						</th>
						<th>
							广告图片
						</th>
						<th>
							操作
						</th>
					</tr>
				</thead>
				<tbody id="adlist">

				</tbody>
			</table>
		</div>
		<div style="margin-top: -30px; margin-left: 850px;">
			<ul class="pagination">
			</ul>
		</div>
		<!-- 添加页面 -->
		<div id="addAd" class="addAd">
			<a
				style="z-index: inherit; margin-left: 400px; margin-top: 100px; cursor: pointer; text-decoration: none;"
				onclick="closeAddAd();">X</a>
				<table class="table table-bordered">
   <tbody>
      <tr>
         <td style="width: 70px;"><font style="color: red;">*</font>公司名称</td>
         <td><input id="add_name" name="add_name" />
						<font id="add_name_regex" style="color: red;"></font>
						<input id="add_name_val" type="hidden"></td>
      </tr>
      <tr >
        <td>
						<font style="color: red;">*</font>公司网址
					</td>
					<td>
						<input id="add_url" name="add_url" />
						<font id="add_url_regex" style="color: red;"></font>
						<input id="add_url_val" type="hidden">
					</td>
      </tr>
      <tr>
        <td>
						公司地址
					</td>
					<td>
						<input id="add_location" name="add_location" />
						<input id="add_location_val" type="hidden" value="1">
					</td>
      </tr>
        <tr>
       <td>
						公司简介
					</td>
					<td>
						<input id="add_introduce" name="add_introduce" />
						<input id="add_introduce_val" type="hidden" value="1">
					</td>
      </tr>
       <tr>
      
       <td>
					<font style="color: red;">*</font>	广告图(启动页)
					</td>
					<td>
					<font>仅允许上传一个附件，支持jpg,png</font>
						</br>
						 <form method="post" action="http://upload.qiniu.com/"  enctype="multipart/form-data"
					id="big_upload" name="big_upload">
						<input id="file" name="file" type="file" onchange="checkAdBig()">
				<font id="add_adbig_regex" style="color: red;"></font>
						<input id="add_adbig_val" type="hidden" >
						<input id="add_adbig_path" type="hidden">
							<input type="hidden" id="token" name="token" value=""/>
							<input type="hidden" id="key" name="key" value=""/>
							</form>
					</td>
						
      </tr>
       <tr>
       
       <td>
						<font style="color: red;">*</font>广告图(发现)
					</td>
					<td>
					<font>仅允许上传一个附件，支持jpg,png</font>
						</br>
						 <form method="post" action="http://upload.qiniu.com/" styleId="TranForm" enctype="multipart/form-data"
					id="small_upload" name="small_upload">
						<input id="file" name="file" type="file" onchange="checkAdSmall()">
				<font id="add_adsmall_regex" style="color: red;"></font>
						<input id="add_adsmall_val" type="hidden" >
						<input id="add_adsmall_path" type="hidden">
						<input type="hidden" id="token" name="token" value=""/>
						<input type="hidden" id="key" name="key" value=""/>
							</form>
					</td>
				
      </tr>
      <tr>
      <td colspan="2">
      <button style="margin-left: 330px;" type="button" class="btn btn-default" onclick="addAd()">
					添加
				</button>
      </td>
      </tr>
   </tbody>
</table>
		</div>
		<!--编辑页面 -->
		<div id="editAd" class="editAd">
			<a
				style="z-index: inherit; margin-left: 400px; margin-top: 100px; cursor: pointer; text-decoration: none;"
				onclick="closeEditAd();">X</a>
				<table class="table table-bordered">
   <tbody>
      <tr>
         <td><font style="color: red;">*</font>公司名称</td>
         <td><input id="edit_name" name="add_name" />
						<font id="edit_name_regex" style="color: red;"></font>
						<input id="edit_name_val" type="hidden" value="1"></td>
      </tr>
      <tr>
        <td>
						<font style="color: red;">*</font>公司网址
					</td>
					<td>
						<input id="edit_url" name="add_url" />
						<font id="edit_url_regex" style="color: red;"></font>
						<input id="edit_url_val" type="hidden" value="1">
					</td>
      </tr>
      <tr>
        <td>
						公司地址
					</td>
					<td>
						<input id="edit_location" name="add_location" />
						<input id="edit_location_val" type="hidden" value="1">
					</td>
      </tr>
        <tr>
       <td>
						公司简介
					</td>
					<td>
						<input id="edit_introduce" name="add_introduce" />
						<input id="edit_introduce_val" type="hidden" value="1">
					</td>
      </tr>
       <tr>
      
       <td>
					<font style="color: red;">*</font>	广告图(启动页)
					</td>
					<td>
					<font>仅允许上传一个附件，支持jpg,png</font>
						</br>
						 <form method="post" action="http://upload.qiniu.com/"  enctype="multipart/form-data"
					id="edit_big_upload" name="edit_big_upload">
						<input id="file" name="file" type="file" onchange="checkEditAdBig()">
				<font id="edit_adbig_regex" style="color: red;"></font>
						<input id="edit_adbig_val" type="hidden" value="1">
						<input id="edit_adbig_path" type="hidden">
							<input type="hidden" id="token" name="token" />
							<input type="hidden" id="key" name="key" value=""/>
							</form>
					</td>
						
      </tr>
       <tr>
       
       <td>
						<font style="color: red;">*</font>广告图(发现)
					</td>
					<td>
					<font>仅允许上传一个附件，支持jpg,png</font>
						</br>
						 <form method="post" action="http://upload.qiniu.com/" styleId="TranForm" enctype="multipart/form-data"
					id="edit_small_upload" name="edit_small_upload">
						<input id="file" name="file" type="file" onchange="checkEditAdSmall()">
				<font id="edit_adsmall_regex" style="color: red;"></font>
						<input id="edit_adsmall_val" type="hidden" value="1" >
						<input id="edit_adsmall_path" type="hidden">
						<input type="hidden" id="token" name="token" />
						<input type="hidden" id="key" name="key" value=""/>
							</form>
					</td>
				
      </tr>
       <tr>
       <td>
						是否启用
					</td>
					<td>
					<select id="isuse" name="isuse" class="input-select"
					style="width: 80px;height: 34px;">
					<%--<option value="0">
						否
					</option>
					<option value="1">
						是
					</option>
				--%></select>
						<input id="edit_isuse_val" type="hidden" value="1">
					</td>
      </tr>
      <tr>
      <td colspan="2">
      <button style="margin-left: 330px;" type="button" class="btn btn-default" onclick="editSaveAd();">
					保存
				</button>
      </td>
      </tr>
   </tbody>
</table>
		</div>
		<input type="hidden" id="adid" name="adid" value=""  />
	</body>
</html>