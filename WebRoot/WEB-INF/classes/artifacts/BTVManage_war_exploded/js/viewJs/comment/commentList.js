
//发送律师卡片或者内容
function sendLawyer(lawyerId,commentId){
	var txtContent = "";
	if("" == lawyerId){
        txtContent= $("#txtContent").val();
	}
    $.ajax({
        type : 'POST',
        url : ctx + "/comment/insertCommentReply?commentId=" + commentId+"&lawyerId="+lawyerId+"&txtContent="+txtContent,
        cache : false,
        success : function(response) {
        	if(response =="success"){
                $.scojs_message('发送成功!', $.scojs_message.TYPE_OK);
                //刷新在线客服页面
                $.ajax({
                    type : 'POST',
                    url : ctx+"/comment/queryCommentPage/",
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
			}else{
                $.scojs_message('发送失败!', $.scojs_message.TYPE_ERROR);
			}
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