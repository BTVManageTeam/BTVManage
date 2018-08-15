/**
 * 删除系统
 * 
 * @param id
 * @param type
 *            m:素材
 */
function delWB(wbId) {
	var confirm = $.scojs_confirm({
		content:"您确定要删除此媒体账号吗？",
		action:function(){
			confirm.destroy();
			$.ajax({
				type : "POST",
				url :  ctx + "/weBlog/deleteByWBId/?wbId=" + wbId,
				success : function(data) {
					if (data == "success") {
						$.scojs_message('删除成功!', $.scojs_message.TYPE_OK);
					}else {
						$.scojs_message('删除失败!', $.scojs_message.TYPE_ERROR);
					}
					giveup("/weBlog/findall/");
				}
			});
		}
		
	});
	confirm.show();
}
/**
 * 修改系统信息
 */
function toUpdateWB(id){
	$.Mark.show();
	$.ajax({
		type : 'POST',
		url : ctx + "/weBlog/toUpdateWB/?wbId=" + id,
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
 * 修改系统信息
 */
function updateWB(){
	$.ajax({
		type : 'POST',
		url : ctx + "/weBlog/updateWB/",
		cache : false,
		data:$("#updateWBFrom").serialize(),
		success : function(data) {
			if (data == "success") {
				$.scojs_message('修改成功!', $.scojs_message.TYPE_OK);
			} else {
				$.scojs_message('修改失败!', $.scojs_message.TYPE_ERROR);
			}
			giveup("/weBlog/findall/");
		},
		error : function(a, b, c) {
			$.YSAjax.error(a, b, c);
		}
	});
}

/**
 * 增加系统信息
 */
function addWB(){
	$.ajax({
		type:'POST',
		cache : false,
		url : ctx + "/weBlog/addWB",
		data: $("#addWBFrom").serialize(),
		success: function(data){
			if (data == "success") {
				$.scojs_message('添加成功!', $.scojs_message.TYPE_OK);
			} else {
				$.scojs_message('添加失败!', $.scojs_message.TYPE_ERROR);
			}
			giveup("/weBlog/findall/");
		},
		error:function(a, b, c){
			$.YSAjax.error(a, b, c);
		}
	});
}


/**
 * 上传系统logo
 */
function uploadSystemLogo(){
	$("#fileUpload").uploadify({
	 	'debug':false,
		'swf' : ctx+'/js/upload/uploadify3.1/uploadify.swf',
		'uploader' : ctx+'/weBlog/uploadWBLogo/',
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


/**
 * 上传鼠标悬浮-系统logo
 */
function uploadSystemHoverLogo(){
	$("#fileUploadHoverLogo").uploadify({
	 	'debug':false,
		'swf' : ctx+'/js/upload/uploadify3.1/uploadify.swf',
		'uploader' : ctx+'/weBlog/uploadWBLogo/',
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
			$("#hoverlogothumbnail").hide();
			$("#hoverimgDiv").addClass("imgProgress");
			$("#hoverimgDiv").html("0%")
		},
		'onUploadProgress':function(a, b, c, d, e){
			var tPercentagenum=parseInt(parseFloat(b)/parseFloat(e)*100);
			if(tPercentagenum>100){tPercentagenum=100}
			$("#hoverimgDiv").html(tPercentagenum+"%")
		},
		'onUploadSuccess':function(a,b,c){
			$("#hoverimgDiv").removeClass("imgProgress");
			var image=jQuery.parseJSON(b);
			$("#systemHoverLogoUrl").val(image.httpPath);
			var html = "<img  style='max-height:180px;max-width:240px;' id='hoverlogothumbnail'  src='"+image.localPath+"'></img>";
			$("#hoverimgDiv").html(html);
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

function ChineseToEnglish(txt) {
	var ChineseInterpunction = [  "，"];
	var EnglishInterpunction = [ ","];
	for ( var j = 0; j < ChineseInterpunction.length; j++) {
		var reg = new RegExp(ChineseInterpunction[j], "g");
		txt = txt.replace(reg, EnglishInterpunction[j]);
	}
	return txt;
}

function FullToDBC(objStr){ 
    var Str = objStr; 
    var DBCStr = ""; 
    Str = ChineseToEnglish(Str); 
    if(/.*[\u4e00-\u9fa5]+.*$/.test(Str)){        
        alert("含有汉字！");        
    } 
    for(var i = 0; i < Str.length; i++){ 
            var c = Str.charCodeAt(i); 
            if(c == 12288){ 
                    DBCStr += String.fromCharCode(32); 
                    continue; 
            } 
            if(c > 65280 && c < 65375){ 
                    DBCStr += String.fromCharCode(c - 65248); 
                    continue; 
            } 
            DBCStr += String.fromCharCode(c); 
    } 
    objStr = DBCStr; 
    return objStr;
}

function punctuation(){
	var str =$("#showUrl").val() ;
	str = FullToDBC(str);
	$("#showUrl").val(str);
}