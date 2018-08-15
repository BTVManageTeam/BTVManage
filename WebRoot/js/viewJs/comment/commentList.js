
//发送律师卡片或者内容
function sendLawyer(lawyerId,openId,commentId){
	var txtContent = "";
	if("" == lawyerId){
        txtContent= $("#"+openId+"txtContent").val();
	}
    var json ={"openId":openId,"content":txtContent,"id":commentId};
    $.ajax({
        type : 'POST',
        headers:{"Content-Type":"application/json"},
        url : ctx + "api/xy/wechatMessage/v1/replyWechatMessage",
        data:JSON.stringify(json),
        cache : false,
        success : function(response) {
        	if(response.code =="0"){
                $.scojs_message('发送成功!', $.scojs_message.TYPE_OK);
                //刷新在线客服页面
                $.ajax({
                    type : 'POST',
                    url : ctx+"/api/xy/wechatMessage/v1/queryWechatMessage4page/",
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


//发送律师卡片或者内容
function sendTemlateMessage(lawyerId,openId,commentId){
    var txtContent = "";
    if("" == lawyerId){
        txtContent= $("#"+openId+"txtContent").val();
    }
    var json ={"openId":openId,"lawyerId":lawyerId,"id":commentId};
    $.ajax({
        type : 'POST',
        headers:{"Content-Type":"application/json"},
        url : ctx + "api/xy/wechatMessage/sendTemlateMessage/",
        data:JSON.stringify(json),
        cache : false,
        success : function(response) {
            if(response.code =="0"){
                $.scojs_message('发送成功!', $.scojs_message.TYPE_OK);
                //刷新在线客服页面
                $.ajax({
                    type : 'POST',
                    url : ctx+"/api/xy/wechatMessage/v1/queryWechatMessage4page/",
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

/**/
function replayBtn(e) {
    $(e).parents(".message_list").find(".quick_reply_box").show();
    $(e).parents(".message_list").siblings().find(".quick_reply_box").hide();
}
$(function () {
    $(".replay_up").click(function () {
        $(".quick_reply_box").hide();
    });
})
/*计数*/
function statInputNum(id) {
    var textArea = $("#" + id).find("textArea");
    var numItem = $("#" + id).find(".word");
    var max = numItem.text(),
        curLength;
    textArea[0].setAttribute("maxlength", max);
    curLength = textArea.val().length;
    numItem.text(max - curLength);
    textArea.on('input propertychange', function() {
        numItem.text(max - $(this).val().length);
    });
}