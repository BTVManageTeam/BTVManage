
function toUpdateServiceStatus(id,lawyerId) {

    var confirm = $.scojs_confirm({
        content : "确认是否将当前状态设置为已完成？",
        action : function() {
            confirm.destroy();
            $.ajax({
                type : 'POST',
                url : ctx + "/order/updateOrder?id=" + id,
                cache : false,
                success : function(response) {
                    if(response.code == 0){
                        $.ajax({
                            type : 'POST',
                            url : ctx + "/order/queryOrderPage?lawyerId=" + lawyerId,
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
                    }else{
                        alert("状态修改失败，请联系管理员！")
                    }
                },
                error : function(a, b, c) {
                    $.YSAjax.error(a, b, c);
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