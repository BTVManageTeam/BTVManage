/**
 * 跳转至添加所属商页面
 */
function toAddOwnerBusiness(){
	giveup('/ownerbusiness/toAddOwnerBusiness/');
}
/**
 * 跳转至修改所属商页面
 */
function toUpdateOwnerBusiness(id){
	$.Mark.show();
	$.ajax({
		type : 'POST',
		url : ctx + "/ownerbusiness/toUpdateOwnerBusiness/?id="+id,
		cache : false,
		success : function(response) {
			$.Mark.hide();
			$("#content").css("display", "block");
			$("#contenttwo").remove();
			$("#contentthree").remove();
			$("#content").html(response);
			$.Mark.hide();
		},
		error : function(a, b, c) {
			$.YSAjax.error(a, b, c);
		}
	});
}

/**
 * 删除指定所属商
 * 
 */
function delOwnerBusiness(id) {
	var confirm = $.scojs_confirm({
		content:"您确定要删除此所属商吗？",
		action:function(){
			confirm.destroy();
			$.ajax({
				type : "POST",
				url :  ctx + "/ownerbusiness/delOwnerBusinessById/?ownerBusId=" + id,
				success : function(data) {
					if(data=="other"){
						$.scojs_message('该所属商被占用，无法删除!', $.scojs_message.TYPE_ERROR);
					}else if (data == "success") {
						$.scojs_message('删除成功!', $.scojs_message.TYPE_OK);
						loadComps();//刷新主页顶部所属商列表
						selectform();
					} else {
						$.scojs_message('删除失败!', $.scojs_message.TYPE_ERROR);
						loadComps();//刷新主页顶部所属商列表
						selectform();
					}
				}
			});
		}
		
	});
	confirm.show();
}

     
/**
 * 返回指定页面
 * @param url
 */
function giveup(url) {
	$.Mark.show();
	$.ajax({
		type : 'POST',
		url : ctx + url,
		cache : false,
		success : function(response) {
			$.Mark.hide();
			$("#content").css("display", "block");
			$("#contenttwo").remove();
			$("#contentthree").remove();
			$("#content").html(response);
			$.Mark.hide();
		},
		error : function(a, b, c) {
			$.YSAjax.error(a, b, c);
		}
	});
};  