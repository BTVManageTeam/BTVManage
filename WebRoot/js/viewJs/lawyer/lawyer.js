
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

/**
 * 上传系统logo
 */
function uploadSystemLogo(){
    $("#fileUpload").uploadify({
        'debug':false,
        'swf' : ctx+'/js/upload/uploadify3.1/uploadify.swf',
        'uploader' : ctx+'/systems/uploadSystemLogo/',
        'fileObjName' : 'thumbnailfile',
        'queueID' : 'fileQueue',
        'fileSizeLimit' : 2147483648,
        'auto' : true,
        'multi' : false,
        'width' : 130,
        'height' :32,
        'fileTypeExts' : '*.jpg;*.png;*.jpeg;*.bmp;*.gif;',
        'buttonImage' : ctx+'/js/upload/uploadify3.1/img/uploadbtn.png',
        'progressData':'speed',
        'preventCaching':true,
        'successTimeout':99999,
        'removeTimeout':'1',
        'removeCompleted':true,
        'removeTimeout':3,
        'onUploadStart':function(file){
            $("#thumbnail").hide();
            $("#imgDiv").addClass("imgProgress");
            $("#imgDiv").html("0%")
        },
        'onUploadProgress':function(a, b, c, d, e){
            var tPercentagenum=parseInt(parseFloat(b)/parseFloat(e)*100);
            if(tPercentagenum>100){tPercentagenum=100}
            $("#imgDiv").html(tPercentagenum+"%")
        },
        'onUploadSuccess':function(a,b,c){
            $("#imgDiv").removeClass("imgProgress");
            var image=jQuery.parseJSON(b);
            $("#systemLogoUrl").val(image.httpPath);
            var html = "<img  style='max-height:180px;max-width:240px;' id='thumbnail'  src='"+image.localPath+"'></img>";
            $("#imgDiv").html(html);
        }
    });
}