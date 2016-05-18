/**
 * 初始化
 */
function init(){
	
}
/**
 * 重置
 */
function reset(){
	$("#name").val("");
}
/**
 * 获取广告列表
 */
function getAdveryList(pindex,psize){
	var name=$("#name").val();
	if(name){
	}else{
		name="noname";
	}
		$.ajax({
			url : app_path + 'adverty/getAdvertyList',// 跳转到action
			data : {
				pageindex:pindex,
				pagesize:psize,
				name:name
			},
			type : 'post',
			cache : false,
			dataType : 'json',
			success : function(data) {
				if (data.rtnCode == "000000") {
					$("#adlist").empty();
					var list = data.responseData.adList;
					var pageVo = data.responseData.pageVo;
					var htmlList='';
//					if (list && list.length) {
//						htmlList+='<tr>';
//						htmlList+='<td>'+list.'';
//					}
					$
					.each(
							list,
							function(index, obj) {
								htmlList+='<tr>';
								htmlList+='<td>'+obj.companyname+'</td>';
								htmlList+='<td><a href="'+obj.adurl+'" target="_blank" style="text-decoration:none;">广告地址</a></td>';
								htmlList+='<td><input value="'+obj.id+'" type="hidden" /><a style="text-decoration:none;cursor:pointer" onclick="showEditAd(this);">编辑</a>&nbsp;&nbsp;<a style="text-decoration:none;cursor:pointer" onclick="delAd(this)">删除</a></td>';
								htmlList+='</tr>';
							});
					$("#adlist").append(htmlList);
					//分页实现
					pageInfo(pageVo);
				} else{
					return;
				}
			}
		/*
		 * error : function() { // view("异常！"); alert("异常！"); }
		 */
		});
}
//分页实现
function pageInfo(pageVo){
	$(".pagination").empty();
	var pageInfoHtml='';
	if (pageVo.totalRow != 0) {
		var pageIndex = pageVo.pageIndex - 1;
		if (pageVo.pageIndex == 1) {
			pageInfoHtml += '<li class="disabled"><a href="javascript:;" id="upPage" >&laquo;</a></li>';
		} else {
			pageInfoHtml += '<li><a href="javascript:;" id="upPage" onclick="getNextPageList('+pageIndex+','+pageVo.pageSize+')">&laquo;</a></li>';
		}
		var start = pageVo.pageIndex - (Math.ceil(pageVo.pageSize / 2) - 1);
		//限制开始页数，每页数小于总页数时
		if (pageVo.pageSize < pageVo.totalPage) {
			if (start < 1) {
				start = 1;
			}
			else if (start + pageVo.pageSize > pageVo.totalPage) {
				start = pageVo.totalPage - pageVo.pageSize + 1;
			}
		}
		else {
			start = 1;
		}
		var end = start + pageVo.pageSize - 1;
		//限制结束页数，当结束页数大于总页数时
		if (end > pageVo.totalPage) {
			end = pageVo.totalPage;
		}
		for (var i = start; i <= end; i++) {
			if (i == pageVo.pageIndex) {
				pageInfoHtml += '<li class="active"><a href="javascript:;"  onclick="getNextPageList('+i+','+pageVo.pageSize+')" >'
					+ i + '</a></li>';
			}
			else {
				pageInfoHtml += '<li><a href="javascript:;"  onclick="getNextPageList('+i+','+pageVo.pageSize+')" >'
					+ i + '</a></li>';
			}
		}
		if (pageVo.pageIndex < pageVo.totalPage) {
			var pageIndex = pageVo.pageIndex + 1;
			pageInfoHtml += '<li><a href="javascript:;" onclick="getNextPageList('+pageIndex+','+pageVo.pageSize+')" >&raquo;</a></li>';
		} else {
			pageInfoHtml += '<li><a href="javascript:;" onclick="getNextPageList('+pageVo.pageIndex+','+pageVo.pageSize+')" >&raquo;</a></li>';
		}
		$(".pagination").append(pageInfoHtml);
	}
}
//分页点击事件
function getNextPageList(pageIndex,pagSize){
	getAdveryList(pageIndex,pagSize);
}
//删除广告
function delAd(obj){
	var id=$(obj).prev().prev().val();
	if (confirm("您确定要删除吗？"))
		$.ajax({
			url : app_path + 'adverty/delAd',// 跳转到Login.action
			data : {
				id:id
			},
			type : 'post',
			cache : false,
			dataType : 'json',
			success : function(data) {
				if (data.rtnCode == "000000") {
					alert("删除成功！");
					getAdveryList(1, 5);
				} else{
					return;
				}
			}
		/*
		 * error : function() { // view("异常！"); alert("异常！"); }
		 */
		});
		
}
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
    $(divName).css( { position : 'absolute', 'top' : 35, left : left + scrollLeft } ).show();  
}  
function showAd(divName){  
    showMask();  
    letDivCenter(divName);  
	$("#add_name").bind("change", checkCompanyName);
	$("#add_url").bind("change", checkCompanyUrl);
	getQiNiuToken();
	getKeyOne();
	getKeyTwo();
}  

function getKeyOne() {
    var s = [];
    var hexDigits = "0123456789abcdefghijklmn";
    for (var i = 0; i < 36; i++) {
        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
    }
    s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
    var uuid = s.join("");
    var tv = $('input[name=key]');
	$(tv[0]).val(uuid);
}
function getKeyTwo() {
    var s = [];
    var hexDigits = "0123456789abcdefghijklmn";
    for (var i = 0; i < 36; i++) {
        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
    }
    s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
    var uuid = s.join("");
    var tv = $('input[name=key]');
	$(tv[1]).val(uuid);
}
function closeAddAd(){
	  $("#mask").hide(); 
	  $("#add_url").val("");
	  $("#add_name").val("");
	  $("#add_location").val("");
	  $("#add_introduce").val("");
		var tv = $('input[name=file]');
		$(tv[0]).val("");
		$(tv[1]).val("");
		var kv = $('input[name=key]');
		$(kv[0]).val("");
		$(kv[1]).val("");
		 $("#add_url_regex").html("");
		  $("#add_name_regex").html("");
		  $("#add_adbig_regex").html("");
		  $("#add_adsmall_regex").html("");
	  $("#addAd").hide();  
}
/**
 * 添加广告
 * */
function addAd(){
	var nameval=$("#add_name_val").val();
	var urlval=$("#add_url_val").val();
	var locationval=$("#add_location_val").val();
	var introduceval=$("#add_introduce_val").val();
	var adbigval=$("#add_adbig_val").val();
	var adsmallval=$("#add_adsmall_val").val();
	if(nameval == 1 && urlval==1 && locationval==1 && introduceval==1 &&
			adbigval==1 && adsmallval==1){
		uploadBig();
	}else{
		alert("请按照正确提示填写数据！");
		if ($("#add_name").val() == "")
			$("#add_name_regex").text("必填项");
		if ($("#add_url").val() == "")
			$("#add_url_regex").text("必填项");
		var tv = $('input[name=file]');
		if ($(tv[0]).val() == "")
			$("#add_adbig_regex").text("比填项");

		if ($(tv[1]).val() == "")
			$("#add_adsmall_regex").text("必填项");
	}
}
/**
 * 插入广告图(首页)
 * */
function uploadBig() {
	$("#big_upload").ajaxSubmit({
        success:function(data){
            $("#add_adbig_path").val(data.key);
            uploadSmall();
        },
        error:function(){
      	  alert("error");
        }
    });
}
/**
 * 上传广告图列表
 */
function uploadSmall() {
	$("#small_upload").ajaxSubmit({
        success:function(data){
            $("#add_adsmall_path").val(data.key);
            addAdInfo();
        },
        error:function(){
      	  alert("error");
        }
    });
}

/**
 * 获取七牛上传token
 */
function getQiNiuToken(){
	$.ajax({
		url : app_path + 'user/getQiniuToken',// 跳转到Login.action
		data : {
		},
		type : 'post',
		cache : false,
		dataType : 'json',
		success : function(data) {
			if (data.rtnCode == "000000") {
				var token=data.responseData;
				var tv = $('input[name=token]');
				$(tv[0]).val(token);
				$(tv[1]).val(token);
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
插入广告
**/
function addAdInfo(){
	var companyname=$("#add_name").val();
	var companyurl=$("#add_url").val();
	var companylocation=$("#add_location").val();
	var companyintroduce=$("#add_introduce").val();
	var adbig=$("#add_adbig_path").val();
	var adsmall=$("#add_adsmall_path").val();
	$.ajax({
		url : app_path + 'adverty/addAd',// 跳转到Login.action
		data : {
			companyname:companyname,
			companyurl:companyurl,
			companylocation:companylocation,
			companyintroduce:companyintroduce,
			adbig:adbig,
			adsmall:adsmall
		},
		type : 'post',
		cache : false,
		dataType : 'json',
		success : function(data) {
			if (data.rtnCode == "000000") {
				alert("添加成功！");
				closeAddAd();
				getAdveryList(1, 5);
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
 * 编辑广告部分
 */
function editAd(obj){
	$("#edit_name").bind("change", checkEditCompanyName);
	$("#edit_url").bind("change", checkEditCompanyUrl);
	$.ajax({
		url : app_path + 'adverty/getAdById',// 跳转到Login.action
		data : {
			id:obj
		},
		type : 'post',
		cache : false,
		dataType : 'json',
		success : function(data) {
			if (data.rtnCode == "000000") {
				var ad=data.responseData;
				$("#edit_name").val(ad.companyname);
				$("#edit_url").val(ad.linkurl);
				$("#edit_location").val(ad.companylocation);
				$("#edit_introduce").val(ad.companyintroduction);
				$("#isuse").empty();
				var html="";
				if(ad.isusead =="0"){
					html+='<option selected="selected" value="0">否</option>';
					html+='<option  value="1">是</option>';
					$("#isuse").append(html);
				}
				if(ad.isusead =="1"){
					html+='<option  value="0">否</option>';
					html+='<option selected="selected" value="1">是</option>';
					$("#isuse").append(html);
//					$("select[name='isuse'] option[value='1']").attr(
//							"selected", "selected");
//					$("select[name='isuse'] option[value='0']").attr(
//							"selected", false);
				}
			} else{
				return;
			}
		}
	/*
	 * error : function() { // view("异常！"); alert("异常！"); }
	 */
	});
}
//兼容火狐、IE8  
function showEditMask(){  
    $("#editmask").css("height",$(document).height());  
    $("#editmask").css("width",$(document).width());  
    $("#editmask").show();  
}  
//让指定的DIV始终显示在屏幕正中间  
function editLetDivCenter(divName){   
    var top = ($(window).height() - $(divName).height())/2;   
    var left = ($(window).width() - $(divName).width())/2;   
    var scrollTop = $(document).scrollTop();   
    var scrollLeft = $(document).scrollLeft();   
    $(divName).css( { position : 'absolute', 'top' : 20, left : left + scrollLeft } ).show();  
}  
function showEditAd(obj){ 
	var id = $(obj).prev().val();
	$("#adid").val(id);
		showEditMask();  
    editLetDivCenter("#editAd");  
	getEditQiNiuToken();
	getEditKeyOne();
	getEditKeyTwo();
	editAd(id);
}  
function getEditKeyOne() {
    var s = [];
    var hexDigits = "0123456789abcdefghijklmn";
    for (var i = 0; i < 36; i++) {
        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
    }
    s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
    var uuid = s.join("");
    var tv = $('input[name=key]');
	$(tv[2]).val(uuid);
}
function getEditKeyTwo() {
    var s = [];
    var hexDigits = "0123456789abcdefghijklmn";
    for (var i = 0; i < 36; i++) {
        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
    }
    s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
    var uuid = s.join("");
    var tv = $('input[name=key]');
	$(tv[3]).val(uuid);
}
function closeEditAd(){
	 $("#editmask").hide(); 
	  var tv = $('input[name=file]');
		$(tv[2]).val("");
		$(tv[3]).val("");
		var kv = $('input[name=key]');
		$(kv[2]).val("");
		$(kv[3]).val("");
		 $("#edit_url_regex").html("");
		  $("#edit_name_regex").html("");
		  $("#edit_adbig_regex").html("");
		  $("#edit_adsmall_regex").html("");
		  $("#edit_adsmall_regex").html("");
//		  $("select[name='isuse'] option[value='1']").attr(
//					"selected", false);
//			$("select[name='isuse'] option[value='0']").attr(
//					"selected", false);
	  $("#editAd").hide();  
}
//编辑保存
function editSaveAd(){
	var nameval=$("#edit_name_val").val();
	var urlval=$("#edit_url_val").val();
	var locationval=$("#edit_location_val").val();
	var introduceval=$("#edit_introduce_val").val();
	var adbigval=$("#edit_adbig_val").val();
	var adsmallval=$("#edit_adsmall_val").val();
	var isuseval=$("#edit_isuse_val").val();
	if(nameval == 1 && urlval==1 && locationval==1 && introduceval==1 &&
			adbigval==1 && adsmallval==1 && isuseval==1){
		var tv = $('input[name=file]');
		var bigad=$(tv[2]).val();
		var smallad=$(tv[3]).val();
		if(bigad==''){
			if(smallad==''){
				 editSave();
			}else{
				uploadEditSmall();
			}
		}else{
if(smallad==''){
	uploadEditBigTwo();
			}else{
				uploadEditBig();
			}
		}
	}else{
		alert("请按照正确提示填写数据！");
		if ($("#edit_name").val() == "")
			$("#edit_name_regex").text("必填项");
		if ($("#add_url").val() == "")
			$("#add_url_regex").text("必填项");
	}
}
/**
 * 编辑部分插入广告图(首页)
 * */
function uploadEditBig() {
	$("#edit_big_upload").ajaxSubmit({
        success:function(data){
            $("#edit_adbig_path").val(data.key);
            uploadEditSmall();
        },
        error:function(){
      	  alert("error");
        }
    });
}
function uploadEditBigTwo() {
	$("#edit_big_upload").ajaxSubmit({
        success:function(data){
            $("#edit_adbig_path").val(data.key);
            editSave();
        },
        error:function(){
      	  alert("error");
        }
    });
}
/**
 * 编辑部分上传广告图列表
 */
function uploadEditSmall() {
	$("#edit_small_upload").ajaxSubmit({
        success:function(data){
            $("#edit_adsmall_path").val(data.key);
            editSave();
        },
        error:function(){
      	  alert("error");
        }
    });
}
function editSave(){
var id=$("#adid").val();
	var companyname=$("#edit_name").val();
	var companyurl=$("#edit_url").val();
	var companylocation=$("#edit_location").val();
	var companyintroduce=$("#edit_introduce").val();
	var adbig=$("#edit_adbig_path").val();
	var adsmall=$("#edit_adsmall_path").val();
	var isuse=$("#isuse option:selected").val();
	$.ajax({
		url : app_path + 'adverty/editAd',// 跳转到Login.action
		data : {
			id:id,
			companyname:companyname,
			companyurl:companyurl,
			companylocation:companylocation,
			companyintroduce:companyintroduce,
			adbig:adbig,
			adsmall:adsmall,
			isuse:isuse
		},
		type : 'post',
		cache : false,
		dataType : 'json',
		success : function(data) {
			if (data.rtnCode == "000000") {
				alert("编辑成功！");
				closeEditAd();
				getAdveryList(1, 5);
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
 * 获取七牛上传token
 */
function getEditQiNiuToken(){
	$.ajax({
		url : app_path + 'user/getQiniuToken',// 跳转到Login.action
		data : {
		},
		type : 'post',
		cache : false,
		dataType : 'json',
		success : function(data) {
			if (data.rtnCode == "000000") {
				var token=data.responseData;
				var tv = $('input[name=token]');
				$(tv[2]).val(token);
				$(tv[3]).val(token);
			} else{
				return;
			}
		}
	/*
	 * error : function() { // view("异常！"); alert("异常！"); }
	 */
	});
}