
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
 * 当前页面的全局变量
 */
var pageVar = {
    //当前上传的区域
    uploadRegion : "",
    //当前租户信息
    companyInfo : "",
    //照片信息
    headimgurl : "",
    //视频信息
    video : { src : "", name : ""},
    //图片信息
    pic : {src : "", name : ""},
    //当前上传的文件类型
    fileType : "",
};

/**
 * 上传系统logo
 */
function initUploadPhotoPlug() {
    var config = {
        browseFileId : "upload_photo",
        /** 选择文件的ID, 默认: i_select_files */
        customered : true,
        dragAndDropArea : "upload_photo",
        /** 拖拽上传区域，Id（字符类型"i_select_files"）或者DOM对象, 默认: `i_select_files` */
        dragAndDropTips : "<span>&nbsp;</span>",
        /** 拖拽提示, 默认: `<span>把文件(文件夹)拖拽到这里</span>` */
        filesQueueHeight : 260,
        /** 文件上传容器的高度（px）, 默认: 450 */
        retryCount : 3,
        /** 重试机制 */
        maxSize : 5 * 1024 * 1024,
        /** 单个文件的最大大小，100M */
        tokenURL : base.uploadApi + "/tk",
        /** 根据文件名、大小等信息获取Token的URI（用于生成断点续传、跨域的令牌） */
        frmUploadURL : base.uploadApi+"/fd;",
        /** Flash上传的URI */
        uploadURL : base.uploadApi+"/uploadV2",
        /** HTML5上传的URI */
        swfURL : "../common/plugins/upload/stream/swf/FlashUploader.swf",
        /** SWF文件的位置 */
        multipleFiles : false,
        /** 多个文件一起上传, 默认: false */
        onComplete : function(file) {
            var msg = file.msg;
            msg = JSON.parse(msg);
            var fileUrl = msg.fileUrl;
            pageVar.headimgurl = fileUrl;
            //显示删除照片按钮
            $(".coverImg .on_delete").show();
            $("#upload_photo").hide();
            //显示照片
            $("#show_photo").attr("src",fileUrl).show();
            $("#portrait").val(fileUrl);
        },
        onUploadError : function(status, msg) {
            popup("上传错误!");
        },
        onCancel : function(file) {
            // 取消上传文件的响应事件
            stream.enable();
        },
        onSelect : function(list){
            $.each(list,function(i){
                pageVar.fileType = materialType(this.type);
                return true;
            });

            if(pageVar.fileType != "image"){
                popup("只能上传图片!");
                onCancel();
            }
        },
        onMaxSizeExceed : function() {
            popup("文件超过5M限制!");
            $("#show_photo").attr("scr","").hide();
            $("#upload_photo").show();
        },
        postVarsPerFile : {
            "companyId": base.companyId,
            "appCode": base.appCode,
            "userId" : "BTV"
        }
    };
    var stream = new Stream(config);
}

/**
 * 查看素材类型
 */
function materialType(str){
    str = str.toLowerCase();
    var type = "";
    if(str.search(/image/) != -1){
        type = "image";
    }else if(str.search(/video/) != -1){
        type = "video";
    }
    return type;
}