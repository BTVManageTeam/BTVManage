/**
 * 删除用户组
 * 
 * @param id
 * @param type
 * 
 */
function delGroup(id) {
	var confirm = $.scojs_confirm({
		content:"您确定要删除此用户组吗？",
		action:function(){
			confirm.destroy();
			$.ajax({
				type : "POST",
				url :  ctx + "/group/deleteByGroupId/?groupId=" + id,
				success : function(data) {
					if (data == "success") {
						$.scojs_message('删除成功!', $.scojs_message.TYPE_OK);
						selectform();
					} else if(data == "other"){
						$.scojs_message('该用户组下存在用户，删除失败!', $.scojs_message.TYPE_ERROR);
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
/**
 * 修改用户信息
 */
function toUpdateUser(id){
	$.Mark.show();
	$.ajax({
		type : 'POST',
		url : ctx + "/users/toupdateuser/?id=" + id,
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
 * 修改用户信息
 */
function updateGroup(){
	$.ajax({
		type : 'POST',
		url : ctx + "/group/updateGroup/",
		cache : false,
		data:$("#updateGroupFrom").serialize(),
		success : function(data) {
			if (data == "success") {
				$.scojs_message('修改成功!', $.scojs_message.TYPE_OK);
			} else {
				$.scojs_message('修改失败!', $.scojs_message.TYPE_ERROR);
			}
			giveup("/group/findall/");
		},
		error : function(a, b, c) {
			$.YSAjax.error(a, b, c);
		}
	});
}

/**
 * 增加用户组
 * 
 */
function addGroup(){
	var systems = $("#systems").val();
	if(systems <= 0){
		$.scojs_message('请先创建应用系统!', $.scojs_message.TYPE_ERROR);
		return;
	}
	$.ajax({
		type:'POST',
		cache : false,
		url : ctx + "/group/addGroup/",
		data: $("#addGroupFrom").serialize(),
		success: function(data){
			if (data == "success") {
				$.scojs_message('添加成功!', $.scojs_message.TYPE_OK);
			} else {
				$.scojs_message('添加失败!', $.scojs_message.TYPE_ERROR);
			}
			giveup("/group/findall/");
		},
		error:function(a, b, c){
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

/**
 * 跳转至用户组未分配用户页面
 * @param url
 * @param sectionId
 * @returns
 */
function toDistribGroupUser(url,sectionId,groupName){
	$.Mark.show();
	$.ajax({
		type : 'POST',
		url : ctx + url,
		data:{"groupId":sectionId,"groupName":groupName},
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
 * 跳转至用户组已分配用户页面
 * @param url
 * @param sectionId
 * @returns
 */
function toNotGroupUser(url,sectionId,groupName){
	$.Mark.show();
	$.ajax({
		type : 'POST',
		url : ctx + url,
		data:{"groupId":sectionId,"disflag":"1","groupName":groupName},
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
 * 加入用户组
 */
function doDisGroupUser(groupId,userId){
	$.Mark.show();
	$.ajax({
		type : 'POST',
		url : ctx + "/group/doDisGroupUser/",
		cache : false,
		data:{"groupId":groupId,"userId":userId},
		success : function(data) {
			$.Mark.hide();
			if (data == "success") {
				$.scojs_message('加入成功!', $.scojs_message.TYPE_OK);
				$("#"+userId).attr("class","btn btn-default btn-mini mydis");
				$("#"+userId).attr("onclick","");
			} else if(data == "0"){
				$.scojs_message('该用户已属于此用户组，请勿重复操作!', $.scojs_message.TYPE_ERROR);
			}else{
				$.scojs_message('加入失败!', $.scojs_message.TYPE_ERROR);
			}
			$.Mark.hide();
		},
		error : function(a, b, c) {
			$.YSAjax.error(a, b, c);
		}
	});
//	giveup('/group/findall/');
}
/**
 * 移除用户组中用户
 */
function removeGroupUser(groupId,userId){
	$.Mark.show();
	$.ajax({
		type : 'POST',
		url : ctx + "/group/removeGroupUser/",
		cache : false,
		data:{"groupId":groupId,"userId":userId},
		success : function(data) {
			$.Mark.hide();
			if (data == "success") {
				$.scojs_message('移除成功!', $.scojs_message.TYPE_OK);
				$("#"+userId).attr("class","btn btn-default btn-mini mydis");
				$("#"+userId).attr("onclick","");
			} else if(data == "0"){
				$.scojs_message('该用户已从此用户组移除，请勿重复操作!', $.scojs_message.TYPE_ERROR);
			}else{
				$.scojs_message('移除失败!', $.scojs_message.TYPE_ERROR);
			}
			$.Mark.hide();
		},
		error : function(a, b, c) {
			$.YSAjax.error(a, b, c);
		}
	});
//	giveup('/group/findall/');
}
/**
 *  批量分配用户至用户组
 * @param groupId 用户组id
 * @param partId 部门id
 */
function batchAddUserToGroup(groupId){
	var partId = $("#partId").val();
	var userName = $("#userNameId").val().trim();//用户名
	var loginId = $("#loginId").val().trim();//登录id
	var confirm = $.scojs_confirm({
		content:"您确定要将所展示用户分配至用户组吗？",
		action:function(){
			confirm.destroy();
			$.Mark.show();
			$.ajax({
				type : "POST",
				url :  ctx + "/group/bathAddUserToGroup/",
				data:{"groupId":groupId,"partId":partId,"userName":userName,"loginId":loginId},
				success : function(data) {
					$.Mark.hide();
					if (data == "success") {
						$.scojs_message('分配成功!', $.scojs_message.TYPE_OK);
//						giveup('/group/findall/');
						selectMyForm();
					}else if(data == "y"){
						$.scojs_message('用户不存在，无法分配!', $.scojs_message.TYPE_ERROR);
					}else {
						$.scojs_message('分配失败!', $.scojs_message.TYPE_ERROR);
//						giveup('/group/findall/');
						selectMyForm();
					}
				}
			});
		}
		
	});
	confirm.show();
}
/**
 * 批量分配用户至用户组（复选框选择）
 * @param groupId 用户组id
 */
function batchAddUserToGroupNew(groupId){
	var selectcheckbox=$(".selectcheckbox");//选中的用户
	var userIds="";
	for(var i=0;i<selectcheckbox.size();i++){
		 if($(selectcheckbox.get(i)).prop("checked")==true){
			 userIds=userIds+$(selectcheckbox.get(i)).attr("value")+",";
		 } 						 
	}
	 
	if(userIds==""){//未选择用户
//		$.scojs_message("请先选中要分配的用户!", $.scojs_message.TYPE_ERROR);
		batchAddUserToGroup(groupId);
	}else{//封装选中的用户id
		userIds=userIds.substring(0,userIds.length-1);
		
		var confirm = $.scojs_confirm({
			content:"您确定要将选择用户分配至用户组吗？",
			action:function(){
				confirm.destroy();
				$.Mark.show();
				$.ajax({
					type : "POST",
					url :  ctx + "/group/bathAddUserToGroupCheckBox/",
					data:{"groupId":groupId,"userIds":userIds},
					success : function(data) {
						$.Mark.hide();
						if (data == "success") {
							$.scojs_message('分配成功!', $.scojs_message.TYPE_OK);
							selectMyForm();
//							giveup('/group/findall/');
						}else {
							$.scojs_message('分配失败!', $.scojs_message.TYPE_ERROR);
//							giveup('/group/findall/');
							selectMyForm();
						}
					}
				});
			}
			
		});
		confirm.show();
	}
}
/**
 * 添加多选框的全选事件,如果设为disabled则不包含在全选里边
 */
function checkBoxAll(o){
		var checkedStatus = o.checked;
		var checkbox = $(o).parents('#generateTable').find('tr td:first-child input:checkbox');		
		checkbox.each(function() {   			  			
			if(this.disabled==false){
				this.checked = checkedStatus;
    			if (checkedStatus == this.checked) {
    				$(this).closest('.checker > span').removeClass('checked');
    			}
    			if (this.checked) {
    				$(this).closest('.checker > span').addClass('checked');
    			}
			}
			$(this).on("click", function(){
				$(o).removeAttr("checked");
    		});
		});
		
};