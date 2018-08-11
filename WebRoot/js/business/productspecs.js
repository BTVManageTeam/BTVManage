/**
 * 跳转至添加产品规格页面
 */
function toAddProductSpecs(){
	giveup('/productspecs/toAddProductSpecs/');
}
/**
 * 跳转至修改产品规格页面
 */
function toUpdateProductSpecs(id){
	$.Mark.show();
	$.ajax({
		type : 'POST',
		url : ctx + "/productspecs/toUpdateProductSpecs/?id="+id,
		cache : false,
		success : function(response) {
			/*$.Mark.hide();
			$("#content").css("display", "block");
			$("#contenttwo").remove();
			$("#contentthree").remove();
			$("#content").html(response);
			$.Mark.hide();*/
			$.Mark.hide();
			$("#content").css("display", "none");
			$("#contenttwo").remove();
			$("#content").after("<div id='contenttwo'></div>");
			$("#contenttwo").html(response);
		},
		error : function(a, b, c) {
			$.YSAjax.error(a, b, c);
		}
	});
}

/**
 * 删除指定产品规格
 * 
 */
function delProductSpecs(id) {
	var confirm = $.scojs_confirm({
		content:"您确定要删除此产品规格吗？",
		action:function(){
			confirm.destroy();
			$.ajax({
				type : "POST",
				url :  ctx + "/productspecs/delProductSpecsById/?pid=" + id,
				success : function(data) {
					if (data == "success") {
						$.scojs_message('删除成功!', $.scojs_message.TYPE_OK);
						selectform();
					} else {
						$.scojs_message('删除失败!', $.scojs_message.TYPE_ERROR);
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