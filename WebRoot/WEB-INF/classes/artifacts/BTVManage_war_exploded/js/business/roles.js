/**角色管理js*/

/**
 * 增加角色
 * 
 */
function addRole(){
	var authLen = $("#authLen").val();
	if(authLen <= 0){
		$.scojs_message('请先创建角色权限!', $.scojs_message.TYPE_ERROR);
		return;
	}
	$.ajax({
		type:'POST',
		cache : false,
		url : ctx + "/role/addRole",
		data: $("#addRoleForm").serialize(),
		success: function(data){
			if (data == "success") {
				$.scojs_message('添加成功!', $.scojs_message.TYPE_OK);
			} else {
				$.scojs_message('添加失败!', $.scojs_message.TYPE_ERROR);
			}
			giveup("/role/findall/");
		},
		error:function(a, b, c){
			$.YSAjax.error(a, b, c);
		}
	});
}

/**
 * 修改角色信息
 */
function toUpdateRole(id){
	$.Mark.show();
	$.ajax({
		type : 'POST',
		url : ctx + "/role/toUpdateRole/?roleId=" + id,
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
 * 角色授权菜单
 */
function authRoleMenu(id){
	$.Mark.show();
	$.ajax({
		type : 'POST',
		url : ctx + "/role/toAuthRoleMenu/?roleId=" + id,
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
 * 更新角色信息
 */
function updateRole(){
	$.ajax({
		type : 'POST',
		url : ctx + "/role/updateRole/",
		cache : false,
		data:$("#updateRoleForm").serialize(),
		success : function(data) {
			if (data == "success") {
				$.scojs_message('修改成功!', $.scojs_message.TYPE_OK);
			} else {
				$.scojs_message('修改失败!', $.scojs_message.TYPE_ERROR);
			}
			giveup("/role/findall/");
		},
		error : function(a, b, c) {
			$.YSAjax.error(a, b, c);
		}
	});
}


/**
 * 删除角色
 * 
 * @param id
 * @param type
 * 
 */
function delRole(id) {
	var confirm = $.scojs_confirm({
		content:"您确定要删除此角色吗？",
		action:function(){
			confirm.destroy();
			$.ajax({
				type : "POST",
				url :  ctx + "/role/deleteByRoleId/?roleId=" + id,
				success : function(data) {
					if (data == "success") {
						$.scojs_message('删除成功!', $.scojs_message.TYPE_OK);
						selectform();
					}else{
						$.scojs_message('删除失败!', $.scojs_message.TYPE_ERROR);
						selectform();
					}
				}
			});
		}
		
	});
	confirm.show();
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
