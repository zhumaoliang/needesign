/**
 * 验证公司名称
 */
function checkCompanyName() {
	var name = $("#add_name").val();
	var regex = /^\s*$/g;
	if (regex.test(name)) {
		$("#add_name_regex").html("必填项");
		$("#add_name_val").val("0");
		return;
	}
	$("#add_name_regex").html("");
	$("#add_name_val").val("1");
}
/**
 * 验证公司网址
 */
function checkCompanyUrl() {
	var url = $("#add_url").val();
	var regex = /^\s*$/g;
	if (regex.test(url)) {
		$("#add_url_regex").html("必填项");
		$("#add_url_val").val("0");
		return;
	}
	var regex = /^(http|https|ftp):\/\/(([A-Z0-9][A-Z0-9_-]*)(\.[A-Z0-9][A-Z0-9_-]*)+)(:(\d+))?\/?/i;
	if (!regex.test(url)) {
		$("#add_url_regex").html("请输入有效的URL");
		$("#add_url_val").val("0");
		return;
	}
	$("#add_url_regex").html("");
	$("#add_url_val").val("1");
}
/**
 * 验证广告图
 */
function checkAdBig() {
	var bad = $('input[name=file]');  
	var name=$(bad[0]).val();
	var regex = /^\s*$/g;
	if (regex.test(name)) {
		$("#add_adbig_regex").html("必填项");
		$("#add_adbig_val").val("0");
		return;
	}
	var tname = (name.substr(name.lastIndexOf(".") + 1)).toLowerCase();
	if (tname != "jpg" && tname != "png") {
		$("#add_adbig_regex").html("请选择扩展名为jpg/png");
		$("#add_adbig_val").val("0");
		return;
	}
	$("#add_adbig_regex").html("");
	$("#add_adbig_val").val("1");
}
/**
 * 验证广告图(列表)
 */
function checkAdSmall() {
	var sad = $('input[name=file]');  
	var name=$(sad[1]).val();
	var regex = /^\s*$/g;
	if (regex.test(name)) {
		$("#add_adsmall_regex").html("必填项");
		$("#add_adsmall_val").val("0");
		return;
	}
	var tname = (name.substr(name.lastIndexOf(".") + 1)).toLowerCase();
	if (tname != "jpg" && tname != "png") {
		$("#add_adsmall_regex").html("请选择扩展名为jpg/png");
		$("#add_adsmall_val").val("0");
		return;
	}
	$("#add_adsmall_regex").html("");
	$("#add_adsmall_val").val("1");
}
/**======================================================编辑部分===============================================*/
/**
 * 验证公司名称
 */
function checkEditCompanyName() {
	var name = $("#edit_name").val();
	var regex = /^\s*$/g;
	if (regex.test(name)) {
		$("#edit_name_regex").html("必填项");
		$("#edit_name_val").val("0");
		return;
	}
	$("#edit_name_regex").html("");
	$("#edit_name_val").val("1");
}
/**
 * 验证公司网址
 */
function checkEditCompanyUrl() {
	var url = $("#edit_url").val();
	var regex = /^\s*$/g;
	if (regex.test(url)) {
		$("#edit_url_regex").html("必填项");
		$("#edit_url_val").val("0");
		return;
	}
	var regex = /^(http|https|ftp):\/\/(([A-Z0-9][A-Z0-9_-]*)(\.[A-Z0-9][A-Z0-9_-]*)+)(:(\d+))?\/?/i;
	if (!regex.test(url)) {
		$("#edit_url_regex").html("请输入有效的URL");
		$("#edit_url_val").val("0");
		return;
	}
	$("#edit_url_regex").html("");
	$("#edit_url_val").val("1");
}
/**
 * 验证广告图
 */
function checkEditAdBig() {
	var bad = $('input[name=file]');  
	var name=$(bad[2]).val();
//	var regex = /^\s*$/g;
//	if (regex.test(name)) {
//		$("#add_adbig_regex").html("必填项");
//		$("#add_adbig_val").val("0");
//		return;
//	}
	var tname = (name.substr(name.lastIndexOf(".") + 1)).toLowerCase();
	if (tname != "jpg" && tname != "png") {
		$("#edit_adbig_regex").html("请选择扩展名为jpg/png");
		$("#edit_adbig_val").val("0");
		return;
	}
	$("#edit_adbig_regex").html("");
	$("#edit_adbig_val").val("1");
}
/**
 * 验证广告图(列表)
 */
function checkEditAdSmall() {
	var sad = $('input[name=file]');  
	var name=$(sad[3]).val();
//	var regex = /^\s*$/g;
//	if (regex.test(name)) {
//		$("#add_adsmall_regex").html("必填项");
//		$("#add_adsmall_val").val("0");
//		return;
//	}
	var tname = (name.substr(name.lastIndexOf(".") + 1)).toLowerCase();
	if (tname != "jpg" && tname != "png") {
		$("#edit_adsmall_regex").html("请选择扩展名为jpg/png");
		$("#edit_adsmall_val").val("0");
		return;
	}
	$("#edit_adsmall_regex").html("");
	$("#edit_adsmall_val").val("1");
}