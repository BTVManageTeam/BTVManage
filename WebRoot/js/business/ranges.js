/**范围管理js*/
/**
 * 增加范围信息
 */
function addRange(){
	$.ajax({
		type:'POST',
		cache : false,
		url : ctx + "/range/addRange/",
		data: $("#addRangeFrom").serialize(),
		success: function(data){
			if (data == "success") {
				$.scojs_message('添加成功!', $.scojs_message.TYPE_OK);
			} else {
				$.scojs_message('添加失败!', $.scojs_message.TYPE_ERROR);
			}
			giveup("/range/findall/");
		},
		error:function(a, b, c){
			$.YSAjax.error(a, b, c);
		}
	});
}
/***
 * 跳转至修改范围页面
 * @param id
 */
function toUpdateRange(id){
	$.Mark.show();
	$.ajax({
		type : 'POST',
		url : ctx + "/range/toUpdateRange/?id=" + id,
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

/***
 * 修改范围信息
 */
function updateRange(){
	$.ajax({
		type : 'POST',
		url : ctx + "/range/updateRange/",
		cache : false,
		data:$("#updateRangeFrom").serialize(),
		success : function(data) {
			if (data == "success") {
				$.scojs_message('修改成功!', $.scojs_message.TYPE_OK);
			} else {
				$.scojs_message('修改失败!', $.scojs_message.TYPE_ERROR);
			}
			giveup("/range/findall/");
		},
		error : function(a, b, c) {
			$.YSAjax.error(a, b, c);
		}
	});
}

/***
 * 删除范围信息
 * @param id
 */
function delRange(id) {
	var confirm = $.scojs_confirm({
		content:"您确定要删除此租户组信息吗？",
		action:function(){
			confirm.destroy();
			$.ajax({
				type : "POST",
				url :  ctx + "/range/delRangeById/?rangeId=" + id,
				success : function(data) {
					if(data=="y"){
						$.scojs_message('租户组被占用，删除失败!', $.scojs_message.TYPE_ERROR);
						selectform();
					}else if (data == "success") {
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