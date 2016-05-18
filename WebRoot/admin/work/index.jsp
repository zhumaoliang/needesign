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
		<title>作品首页</title>
		<link rel="stylesheet" href="../../admin/css/bootstrap.min.css">
		<script src="../../admin/js/jquery.min.js"></script>
		<script src="../../admin/js/bootstrap.min.js"></script>
		<script src="../../admin/work/js/work.js"></script>
		<script src="../../admin/js/jquery-form.js"></script>
		<script src="../../admin/work/js/workregx.js"></script>
		<script type="text/javascript">
		var app_path='<%=basePath%>';
	
	$(document).ready(function() {
		getWorkList(1, 5);
	});
</script>

	</head>
	<body>
		<form class="form-inline">
			<div class="form-group" style="margin-left: 130px;">
				<label for="name">
					用户名称
				</label>
				<input type="text" class="form-control" id="name" />
			</div>
			<div class="form-group" style="margin-left: 30px;">
				<label for="name">
					是否显示
				</label>
				<select id="show" name="show" class="input-select"
					style="width: 80px;height: 34px;">
					<option selected="selected" value="2">
						全部
					</option>
					<option value="0">
						否
					</option>
					<option value="1">
						是
					</option>
				</select>
			</div>
			<div class="form-group" style="margin-left: 30px;">
				<label for="name">
					是否精选
				</label>
				<select id="choice" name="choice" class="input-select"
					style="width: 80px;height: 34px;">
					<option selected="selected" value="2">
						全部
					</option>
					<option value="0">
						否
					</option>
					<option value="1">
						是
					</option>
				</select>
			</div>
			<div style="margin-left: 780px; margin-top: -34px;">
				<button type="button" class="btn btn-default"
					onclick="getWorkList(1,5);">
					查询
				</button>
			</div>
			<div style="margin-left: 870px; margin-top: -34px;">
				<button type="button" class="btn btn-default" onclick="reset();">
					重置
				</button>
			</div>
		</form>
		<%--
		<hr style="height: 1px; border: none; border-top: 1px solid #555555;" />

		--%>
		<div style="margin-top: 150px;">
			<table class="table table-bordered">
				<caption>
					作品列表
					<%--
					
					<a onclick="showAd('#addAd');"
						style="margin-left: 1030px; text-decoration: none; cursor: pointer">添加</a>
				--%>
				</caption>
				<thead>
					<tr>
						<th style="width: 250px;">
							用户名称
						</th>
						<th style="width: 450px;">
							作品集合
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
	</body>
</html>