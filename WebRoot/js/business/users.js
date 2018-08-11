/**
 * 删除用户
 * 
 * @param id
 * @param type
 *            m:素材
 */
function delUser(id) {
	var confirm = $.scojs_confirm({
		content : "您确定要删除此用户吗？",
		action : function() {
			confirm.destroy();
			$.ajax({
				type : "POST",
				url : ctx + "/users/deleteByid/?id=" + id,
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
 * 修改用户信息
 */
function toUpdateUser(id) {
	$.Mark.show();
	$.ajax({
		type : 'POST',
		url : ctx + "/users/toupdateuser/?id=" + id,
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
 * 修改用户信息
 */
function updateUser() {
	$.ajax({
		type : 'POST',
		url : ctx + "/users/updateUser/",
		cache : false,
		data : $("#updateUsersForm").serialize(),
		success : function(data) {
			if (data == "success") {
				$.scojs_message('添加成功!', $.scojs_message.TYPE_OK);
			} else {
				$.scojs_message('添加失败!', $.scojs_message.TYPE_ERROR);
			}
//			giveup("/users/findall/");
			/**修改后隐藏当前页面，返回主界面，并刷新用户列表*/
			$('#content').css('display','block');
			$('#contenttwo').remove();
			selectform();
		},
		error : function(a, b, c) {
			$.YSAjax.error(a, b, c);
		}
	});
}

/**
 * 修改用户信息
 */
function toUpdateUserPwd(id) {
	$.Mark.show();
	$.ajax({
		type : 'POST',
		url : ctx + "/users/toupdatepwd/?id=" + id,
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
 * 上传xml文件
 */
function ajaxFileUploadForXml(){
	 $.Mark.show();
	 var fileNameVal = $("#file2").val();
	 if(null !=fileNameVal && fileNameVal!=""){
		 if(0<fileNameVal.lastIndexOf(".")&&fileNameVal.substring(fileNameVal.lastIndexOf(".")+1)=="xml"){
			 var uri =ctx+ "/upload/?ansyType=3";
		     $.ajaxFileUpload ({
	          url:uri, //你处理上传文件的服务端
	          secureuri:false,
	          fileElementId:'file2',
	          dataType: 'json',
	          success: function (data){
	        	  $.Mark.hide();
	        	 	   $("#file2").val("");
	                  if(data == "success"){
	                	  $.scojs_message("上传成功!", $.scojs_message.TYPE_OK);
	                	  giveup("/users/compareUser/");
	                  }else{
	                	  $.scojs_message("上传失败!", $.scojs_message.TYPE_ERROR);
	                  }
	                },  
	                error : function(data, status, e) {  
	                	$.scojs_message("上传失败!", $.scojs_message.TYPE_ERROR);
	                	 $.Mark.hide();
	                } 
	             })
		 }else{
			 $.scojs_message("只能上传xml文件!", $.scojs_message.TYPE_ERROR);
			 $.Mark.hide();
		 }
	 }else{
		 $.scojs_message("请选择要上传的xml文件!", $.scojs_message.TYPE_ERROR);
		 $.Mark.hide();
	 }
} 
/**
 * 上传excel文件
 */
function ajaxFileUpload(companyId) {
	$.Mark.show();
	var fileNameVal = $("#file").val();
	if (null != fileNameVal && fileNameVal != "") {
		var suffix = fileNameVal.substring(fileNameVal.lastIndexOf(".") + 1);
		if (0 < fileNameVal.lastIndexOf(".") && (suffix == "xls" || suffix == "xlsx")) {
			var uri = ctx + "/upload/?ansyType=1&companyId=" + companyId;
			$.ajaxFileUpload({
				url : uri, // 你处理上传文件的服务端
				secureuri : false,
				fileElementId : 'file',
				dataType : 'json',
				success : function(data) {
					$.Mark.hide();
					$("#file").val("");
					if (data == "success") {
						$.scojs_message("导入完成!", $.scojs_message.TYPE_OK);
						giveup("/users/findall/");
					} else if (data == "repeat") {
						$.scojs_message("登录名中有重复数据！", $.scojs_message.TYPE_ERROR);
					} else {
						$.scojs_message("导入失败!", $.scojs_message.TYPE_ERROR);
					}
				},
				error : function(data, status, e) {
					$.scojs_message("导入失败!", $.scojs_message.TYPE_ERROR);
					$.Mark.hide();
				}
			})
		} else {
			$.scojs_message("只能上传xls/xlsx格式的文件!", $.scojs_message.TYPE_ERROR);
			$.Mark.hide();
		}
	} else {
		$.scojs_message("请选择要上传的xls/xlsx文件!", $.scojs_message.TYPE_ERROR);
		$.Mark.hide();
	}
}
/**
 * 跳转至添加用户界面
 */
function toAddUser() {
	giveup('/users/toAddUser/');
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
 * 上传文件触发事件
 */
function fileChangeInput() {
	$("#choiceUserFileId").val($("#file2").val());
}
