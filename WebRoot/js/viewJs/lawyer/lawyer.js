
/**
 * 删除律师
 * 
 * @param id
 * @param type
 *            m:素材
 */
function delLawyer(id) {
	var confirm = $.scojs_confirm({
		content : "您确定要删除此律师吗？",
		action : function() {
			confirm.destroy();
			$.ajax({
				type : "POST",
				url : ctx + "/lawyer/deleteLawyer/?id=" + id,
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
 * 修改律师信息
 */
function toUpdateLawyer(id) {
	$.Mark.show();
	$.ajax({
		type : 'POST',
		url : ctx + "/lawyer/toUpdateLawyer/?id=" + id,
		cache : false,
		success : function(response) {
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


/** *************返回操作***************************** */
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