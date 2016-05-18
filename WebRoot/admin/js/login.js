/**
 * 用户check登录
 */
function checkLogin() {
	var username = $("#login_name").val();
	if (!username) {
		return false;
	}
	var userpwd = $("#login_pwd").val();
	if (!userpwd) {
		return false;
	}
	return true;
}
/**
 * 注销
 * @returns {Boolean}
 */
function logout(){
	if (confirm("您确定要退出吗？"))
		$.ajax({
			url : app_path + 'admin/logout',// 跳转到Login.action
			data : {
			},
			type : 'post',
			cache : false,
			dataType : 'json',
			success : function(data) {
				if (data.rtnCode == "000000") {
					top.location = app_path+"admin/login.jsp";
				} else{
					return;
				}
			}
		/*
		 * error : function() { // view("异常！"); alert("异常！"); }
		 */
		});
}
/**
 * admin login
 */
function login() {
	var login_name = $("#login_name").val();
	var login_pwd = $("#login_pwd").val();
	$.ajax({
		url : app_path + 'admin/login.do',// 跳转到Login.action
		data : {
			'name' : login_name,
			'pwd' : login_pwd
		},
		type : 'post',
		cache : false,
		dataType : 'json',
		success : function(data) {
			if (data.rtnCode == "000000") {
				window.location.href = app_path + "admin/index.jsp";
			} else if (data.rtnCode == "100001") {
				$("#login_pwd").css("border","1px solid #000");
				alert("账号不存在！");
				$("#login_name").css("border","1px solid red");
			} else if (data.rtnCode == "100002") {
				$("#login_name").css("border","1px solid #000");
				alert("密码错误！");
				$("#login_pwd").css("border","1px solid red");
			}else{
				return;
			}
		}
	/*
	 * error : function() { // view("异常！"); alert("异常！"); }
	 */
	});
}
