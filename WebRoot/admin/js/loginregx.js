/**
 * 登陆验证
 */
function checkLoginName() {
	var username = $("#login_name").val();
	
//	var regx='/^(?!_)(?!.*?_$)[a-zA-Z0-9_u4e00-u9fa5]+$/';
//	if(!username.match(regx)){
//		alert("只含有汉字、数字、字母、下划线不能以下划线开头和结尾!");
//	}
	$(".login-check").hide();
}
/**
 * 密码验证
 */
function checkLoginPwd() {
	var pwd = $("#login_pwd").val();
	
	$(".login-check").hide();
}