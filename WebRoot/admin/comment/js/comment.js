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
function getWorkList(pindex,psize){
	var name=$("#name").val();
	if(name){
	}else{
		name="noname";
	}
	var show=$("#show option:selected").val();
	var choice=$("#choice option:selected").val();
		$.ajax({
			url : app_path + 'workList/getWorkList',// 跳转到action
			data : {
				pageindex:pindex,
				pagesize:psize,
				name:name,
				show:show,
				choice:choice
			},
			type : 'post',
			cache : false,
			dataType : 'json',
			success : function(data) {
				if (data.rtnCode == "000000") {
					$("#adlist").empty();
					var list = data.responseData.workList;
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
								if(obj.username==''){
									htmlList+='<td>暂未设置用户名</td>';
								}else{
									htmlList+='<td>'+obj.username+'</td>';
								}
								htmlList+='<td>';
							var works=obj.works;
							$
							.each(
									works,
									function(index, obj) {
										htmlList+='<a href="'+obj.originalWork+'" target="_blank" style="text-decoration:none;">作品地址</a>&nbsp;&nbsp;';
									});
							htmlList+='</td>';
							if(obj.choiceshow=='0' && obj.squareshow=='0'){
								htmlList+='<td><input value="'+obj.workid+'" type="hidden" /><a style="text-decoration:none;cursor:pointer" onclick="showWork(this,1);">显示</a>&nbsp;&nbsp;<a style="text-decoration:none;cursor:pointer" onclick="showChoice(this,1);">精选</a></td>';
							}
							if(obj.choiceshow=='0' && obj.squareshow=='1'){
								htmlList+='<td><input value="'+obj.workid+'" type="hidden" /><a style="text-decoration:none;cursor:pointer" onclick="showWork(this,0);">已显示</a>&nbsp;&nbsp;<a style="text-decoration:none;cursor:pointer" onclick="showChoice(this,1);">精选</a></td>';
							}
							if(obj.choiceshow=='1' && obj.squareshow=='1'){
								htmlList+='<td><input value="'+obj.workid+'" type="hidden" /><a style="text-decoration:none;cursor:pointer" onclick="showWork(this,0);">已显示</a>&nbsp;&nbsp;<a style="text-decoration:none;cursor:pointer" onclick="showChoice(this,0);">已精选</a></td>';
							}
							if(obj.choiceshow=='1' && obj.squareshow=='0'){
								htmlList+='<td><input value="'+obj.workid+'" type="hidden" /><a style="text-decoration:none;cursor:pointer" onclick="showWork(this,1);">显示</a>&nbsp;&nbsp;<a style="text-decoration:none;cursor:pointer" onclick="showChoice(this,0);">已精选</a></td>';
							}
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
	getWorkList(pageIndex,pagSize);
}
//作品显示设置
function showWork(obj,type){
	var id=$(obj).prev().val();
		$.ajax({
			url : app_path + 'workList/showWork',// 跳转到Login.action
			data : {
				id:id,
				type:type
			},
			type : 'post',
			cache : false,
			dataType : 'json',
			success : function(data) {
				if (data.rtnCode == "000000") {
					alert("设置成功！");
					getWorkList(1, 5);
				} else{
					return;
				}
			}
		/*
		 * error : function() { // view("异常！"); alert("异常！"); }
		 */
		});
		
}
//作品精选设置
function showChoice(obj,type){
	var id=$(obj).prev().prev().val();
		$.ajax({
			url : app_path + 'workList/showChoice',// 跳转到Login.action
			data : {
				id:id,
				type:type
			},
			type : 'post',
			cache : false,
			dataType : 'json',
			success : function(data) {
				if (data.rtnCode == "000000") {
					alert("设置成功！");
					getWorkList(1, 5);
				} else{
					return;
				}
			}
		/*
		 * error : function() { // view("异常！"); alert("异常！"); }
		 */
		});
		
}