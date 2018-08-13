
function toUpdateServiceStatus(id,lawyerId) {

    layer.confirm('请确认是否将当前状态设置为已完成？', {
        btn: ['确定', '取消'], //按钮
        btnAlign: 'c',
        title:'状态设置'
    }, function() {
        $.ajax({
            type : 'POST',
            url : ctx + "/order/updateOrder?id=" + id,
            cache : false,
            success : function(response) {
				if(response.data.code == 0){
                    $.ajax({
                        type : 'POST',
                        url : ctx + "/order/queryOrderPage?lawyerId=" + id,
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
        layer.closeAll('dialog'); //关闭信息框
    }, function() {
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