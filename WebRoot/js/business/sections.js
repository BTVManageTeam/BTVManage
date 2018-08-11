/**
 * 跳转至添加栏目页面
 */
function toAddSection(){
	giveup('/section/toAddSection/');
}
/**
 * 跳转至修改栏目页面
 */
function toUpdateSection(id){
	$.Mark.show();
	$.ajax({
		type : 'POST',
		url : ctx + "/section/toUpdateSection/?id="+id,
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
 * 删除指定栏目
 * 
 */
function delSection(id) {
	var confirm = $.scojs_confirm({
		content:"您确定要删除此栏目吗？",
		action:function(){
			confirm.destroy();
			$.ajax({
				type : "POST",
				url :  ctx + "/section/delSectionById/?sectionId=" + id,
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
 * 跳转至查看用户页面
 * @param url
 * @param sectionId
 * @returns
 */
function toSelSection(url,sectionId){
	$.Mark.show();
	$.ajax({
		type : 'POST',
		url : ctx + url,
		data:{"sectionId":sectionId},
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
 * 跳转至分配用户页面
 * @param url
 * @param sectionId
 * @returns
 */
function toDistribUser(url,sectionId,sectionName){
	$.Mark.show();
	$.ajax({
		type : 'POST',
		url : ctx + url,
		data:{"sectionId":sectionId,"sectionName":sectionName},
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
 * 跳转至未分配用户页面
 * @param url
 * @param sectionId
 * @returns
 */
function toNotDistribUser(url,sectionId,sectionName){
	$.Mark.show();
	$.ajax({
		type : 'POST',
		url : ctx + url,
		data:{"sectionId":sectionId,"disflag":"1","sectionName":sectionName},
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
 * 分配用户
 */
function doDistribUser(sectionId,userId){
	$.Mark.show();
	$.ajax({
		type : 'POST',
		url : ctx + "/section/doDistribUser/",
		cache : false,
		data:{"sectionId":sectionId,"userId":userId},
		success : function(data) {
			$.Mark.hide();
			if (data == "success") {
				$.scojs_message('分配成功!', $.scojs_message.TYPE_OK);
				$("#"+userId).attr("class","btn btn-default btn-mini mydis");
				$("#"+userId).attr("onclick","");	
			} else if(data == "0"){
				$.scojs_message('该用户已属于此栏目，请勿重复操作!', $.scojs_message.TYPE_ERROR);
			}else{
				$.scojs_message('分配失败!', $.scojs_message.TYPE_ERROR);
			}
			$.Mark.hide();
		},
		error : function(a, b, c) {
			$.YSAjax.error(a, b, c);
		}
	});
//	giveup('/section/findallSection/');
}
/**
 * 移除栏目下用户
 */
function removeDistribUser(sectionId,userId){
	$.Mark.show();
	$.ajax({
		type : 'POST',
		url : ctx + "/section/removeSection/",
		cache : false,
		data:{"sectionId":sectionId,"userId":userId},
		success : function(data) {
			$.Mark.hide();
			if (data == "success") {
				$.scojs_message('移除成功!', $.scojs_message.TYPE_OK);
				$("#"+userId).attr("class","btn btn-default btn-mini mydis");
				$("#"+userId).attr("onclick","");
			} else if(data == "0"){
				$.scojs_message('该用户已从此栏目移除，请勿重复操作!', $.scojs_message.TYPE_ERROR);
			}else{
				$.scojs_message('移除失败!', $.scojs_message.TYPE_ERROR);
			}
			$.Mark.hide();
		},
		error : function(a, b, c) {
			$.YSAjax.error(a, b, c);
		}
	});
//	giveup('/section/findallSection/');
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
 *  批量分配用户至栏目(20150910-未使用)
 * @param sectionId 栏目id
 * @param partId 部门id
 */
function batchAddUserToSection(sectionId){
	var partId = $("#partId").val();
	var userName = $("#seUserNameId").val().trim();
	var confirm = $.scojs_confirm({
		content:"您确定要将所展示用户分配至栏目吗？",
		action:function(){
			confirm.destroy();
			$.Mark.show();
			$.ajax({
				type : "POST",
				url :  ctx + "/section/bathAddUserToSection/",
				data:{"sectionId":sectionId,"partId":partId,"userName":userName},
				success : function(data) {
					$.Mark.hide();
					if (data == "success") {
						$.scojs_message('分配成功!', $.scojs_message.TYPE_OK);
//						giveup('/section/findallSection/');
						selectMyForm();
					}else if(data == "y"){
						$.scojs_message('用户不存在，无法分配!', $.scojs_message.TYPE_ERROR);
					}else {
						$.scojs_message('分配失败!', $.scojs_message.TYPE_ERROR);
//						giveup('/section/findallSection/');
						selectMyForm();
					}
					
				}
			});
		}
		
	});
	confirm.show();
	
}
/**
 * 批量分配用户至栏目（复选框选择）
 * @param sectionId
 */
function batchAddUserToSectionNew(sectionId){
	var selectcheckbox=$(".selectcheckbox");//选中的用户
	var userIds="";
	for(var i=0;i<selectcheckbox.size();i++){
		 if($(selectcheckbox.get(i)).prop("checked")==true){
			 userIds=userIds+$(selectcheckbox.get(i)).attr("value")+",";
		 } 						 
	}
	 
	if(userIds==""){//未选择用户
//		$.scojs_message("请先选中要分配的用户!", $.scojs_message.TYPE_ERROR);
		batchAddUserToSection(sectionId);
	}else{//封装选中的用户id
		userIds=userIds.substring(0,userIds.length-1);
		var confirm = $.scojs_confirm({
			content:"您确定要将选择用户分配至栏目吗？",
			action:function(){
				confirm.destroy();
				$.Mark.show();
				$.ajax({
					type : "POST",
					url :  ctx + "/section/bathAddUserToSectionCheckBox/",
					data:{"sectionId":sectionId,"userIds":userIds},
					success : function(data) {
						$.Mark.hide();
						if (data == "success") {
							$.scojs_message('分配成功!', $.scojs_message.TYPE_OK);
//							giveup('/section/findallSection/');
							selectMyForm();
						}else {
							$.scojs_message('分配失败!', $.scojs_message.TYPE_ERROR);
//							giveup('/section/findallSection/');
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