/** zhangcz添加的系统公用的js方法**/
var refreshId; //定时刷新的ID
var resultPage = "";//返回的页面
/**
 * 清空查询条件 清除classname为conditions的input框
 */
function emptyCon() {
	$("#vmsform").find(".conditions").val("");
	$("#vmsform").find(".Wdate").val("");
}

// 页面按钮提示
function btnTip() {
	$("button[id^='btnTip']").tipso({
		useTitle : true, // 是否设置title为提示内容
		position : "top", // 提示位置：bottom left right top
		background : "#3399FF",
		color : '#ffffff', // 字体色，默认白色
		width : 90, // 宽度 默认200
		delay : 0
	});
	$($("a[id^='aTip']")).tipso({
		useTitle : true, // 是否设置title为提示内容
		position : "top", // 提示位置：bottom left right top
		background : "#0096e4",
		color : '#ffffff', // 字体色，默认白色
		width : 200, // 宽度 默认200
		delay : 0
	});
	$($("a[id^='msgTip']")).tipso({
		useTitle : true, // 是否设置title为提示内容
		position : "top", // 提示位置：bottom left right top
		background : "#0096e4",
		color : '#ffffff', // 字体色，默认白色
		width : 90, // 宽度 默认200
		delay : 0
	});
	$("button[id^='btnTip']").click(function(){
		$(this).tipso('hide');
	});
	$($("a[id^='aTip']")).click(function(){
		$(this).tipso('hide');
	});
	$($("a[id^='msgTip']")).click(function(){
		$(this).tipso('hide');
	});
}
/**
 * 页面中统一的查询方法针对翻页的方法
 * 
 * @return
 */
function selectform() {
	$.Mark.show();
	var options = {
		async : false,
		success : function(data) {
			$.Mark.hide();
			$("#content").html(data);
		},
		error : function(a, b, c) {
			$.YSAjax.error(a, b, c);
		}

	};
	$('#vmsform').ajaxSubmit(options);
}

/**
 * 页面中统一的查询方法针对翻页的方法不加蒙版
 * 
 * @return
 */
function selectformNoMark() {
	var options = {
		async : false,
		success : function(data) {
			return data;
		}
	};
	$('#vmsform').ajaxSubmit(options);
}

/**
 * 页面中统一的查询方法针对条件查询的方法
 * 
 * @return
 */
function selectformbyone() {
	$("#currentPage").val("1");
	$.Mark.show();
	var options = {
		async : false,
		success : function(data) {
			$.Mark.hide();
			$("#content").html(data);
		},
		error : function(a, b, c) {
			$.YSAjax.error(a, b, c);
		}

	};
	$('#vmsform').ajaxSubmit(options);
}

/**
 * 关闭dialog的统一方法
 * 
 * @return
 */
function closeDialog() {
	$("#dialog").remove();
	$.Mark.dialoghide();
}
/**
 * 打开dialog的添加信息的方法
 * 
 * @param o
 * @return
 */
function sDialog(o) {
	$.Mark.show();
	var stitle = $(o).attr("stitle");
	if (stitle == null)
		stitle = '操作';
	var width = 600;
	if ($(o).attr("swidth") != null)
		width = $(o).attr("swidth");
	var height = 400;
	if ($(o).attr("sheight") != null)
		height = $(o).attr("sheight");
	var id = null;
	if ($(o).attr("sid") != null)
		id = $(o).attr("sid");
	$("#dialog").remove();
	var $dialog = $('<div id="dialog"></div>').dialog({
		title : stitle,
		autoOpen : false,
		width : width,
		height : height,
		close : function() {
			$(this).remove();
			$.Mark.dialoghide();
		},
	});
	$("#dialog").dialog("open");
	var url = $(o).attr("shref");
	if (url == null) {
		url = $(o).attr("href");
	}
	$.ajax({
		type : 'POST',
		url : url,
		data : 'id=' + id,
		cache : false,
		success : function(response) {
			$.Mark.hide();
			$.Mark.dialogshow();
			$("#dialog").html(response);
		},
		error : function(a, b, c) {
			$.YSAjax.error(a, b, c);
		}
	});
}

/**
 * Tables的排序功能materialOfGather 首先取得排序名sname；
 * 
 * @param o
 * @return
 */
function sortFormbyoneMG(o) {
	var o = $(o);
	var name = o.attr("sname");
	var order = $("#dialogOrderBy").val();
	if (order == "asc") {
		$("#dialogOrder").val(name);
		$("#dialogOrderBy").val("desc");
	}
	if (order == "desc") {
		$("#dialogOrder").val(name);
		$("#dialogOrderBy").val("asc");
	}
	// 执行页面查询方法
	// selectform();
	selectformbyoneMG();
}
/**
 * Tables的排序功能成品集 首先取得排序名sname；
 * 
 * @param o
 * @return
 */

function sortFormbyoneGG(o) {
	var o = $(o);
	var name = o.attr("sname");
	var order = $("#dialogOrderBy").val();
	if (order == "asc") {
		$("#dialogOrder").val(name);
		$("#dialogOrderBy").val("desc");
	}
	if (order == "desc") {
		$("#dialogOrder").val(name);
		$("#dialogOrderBy").val("asc");
	}
	// 执行页面查询方法
	selectformbyoneGG();
}

/**
 * Tables的排序功能 首先取得排序名sname；
 * 
 * @param o
 * @return
 */
function sortForm(o) {
	var o = $(o);
	var name = o.attr("sname");
	var order = $("#orderBy").val();
	if (order == "asc") {
		$("#order").val(name);
		$("#orderBy").val("desc");
	}
	if (order == "desc") {
		$("#order").val(name);
		$("#orderBy").val("asc");
	}
	// 执行页面查询方法
	selectform();
}

/**
 * 菜单中的打开连接
 * 
 * @param o
 * @return
 */
function openurl(o) {
	$.Mark.show();
	url = $(o).attr("shref");
	$.ajax({
		type : 'POST',
		url : url,
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
 * 菜单中的打开连接
 * 
 * @param o
 * @return
 */
function openKeyUrl(o) {
	$.Mark.show();
	url = $(o).attr("shref");
	$
			.ajax({
				type : 'POST',
				url : url,
				cache : false,
				success : function(response) {
					$.Mark.hide();
					if (response == "success") {
						$.scojs_message('禁用云key成功!', $.scojs_message.TYPE_OK);
						giveup("yunkey/key");
					} else if (response == "disable") {
						$.scojs_message('禁用成功!云 key已经禁用!无需再次激活!',
								$.scojs_message.TYPE_ERROR);
					} else if (response == "noExist") {
						$.scojs_message('认证设备不存在!', $.scojs_message.TYPE_ERROR);
					} else if (response == "eqlengthError") {
						$.scojs_message('设备id or 序列号 or动态密码填写错误!',
								$.scojs_message.TYPE_ERROR);
					} else if (response == 'done') {
						$.scojs_message('云Key已经被禁用!无需再次禁用',
								$.scojs_message.TYPE_ERROR);
					} else {
						$
								.scojs_message('禁用云Key失败!',
										$.scojs_message.TYPE_ERROR);
					}

				},
				error : function(a, b, c) {
					$.YSAjax.error(a, b, c);
				}
			});
}
/**
 * 解锁云key
 * 
 * @param o
 */
function openUnlockKeyUrl(o) {
	$.Mark.show();
	url = $(o).attr("shref");
	$
			.ajax({
				type : 'POST',
				url : url,
				cache : false,
				success : function(response) {
					$.Mark.hide();
					if (response == "success") {
						$.scojs_message('解锁云key成功!', $.scojs_message.TYPE_OK);
						giveup("yunkey/key");
					} else if (response == "disable") {
						$.scojs_message('解锁成功!云 key已经解锁!无需再次解锁!',
								$.scojs_message.TYPE_ERROR);
					} else if (response == "noExist") {
						$.scojs_message('认证设备不存在!', $.scojs_message.TYPE_ERROR);
					} else if (response == "eqlengthError") {
						$.scojs_message('设备id or 序列号 or动态密码填写错误!',
								$.scojs_message.TYPE_ERROR);
					} else if (response == 'done') {
						$.scojs_message('云Key已经被解锁!无需再次解锁',
								$.scojs_message.TYPE_ERROR);
					} else {
						$
								.scojs_message('解锁云Key失败!',
										$.scojs_message.TYPE_ERROR);
					}

				},
				error : function(a, b, c) {
					$.YSAjax.error(a, b, c);
				}
			});
}

/**
 * 在頁面中打开新的页面，同时隐藏content,重建一个新的div
 * 
 * @param o
 */
function openHiddenContent(o) {
	clearInterval(refreshId);
	$.Mark.show();
	url = $(o).attr("shref");
	$.ajax({
		type : 'POST',
		url : url,
		cache : false,
		success : function(response) {
			// $.Mark.hide();
			$("#content").css("display", "none");
			$("#contenttwo").remove();
			$("#content").after("<div id='contenttwo'></div>");
			$("#contenttwo").html(response);
		},
		error : function(a, b, c) {
			$.YSAjax.error(a, b, c);
		}
	});
};

/**
 * 在頁面中打开新的页面，同时隐藏content,重建一个新的div
 * 
 * @param o
 */
function openHiddenContentMark(o) {
	clearInterval(refreshId);
	$.Mark.show();
	url = $(o).attr("shref");
	$.ajax({
		type : 'POST',
		url : url,
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
};

/**
 * 添加多选框的全选事件,如果设为disabled则不包含在全选里边
 */
function checkBoxAll(o) {
	var checkedStatus = o.checked;
	var checkbox = $(o).parents('.widget-box').find(
			'tr td:first-child input:checkbox');
	checkbox.each(function() {
		if (this.disabled == false) {
			this.checked = checkedStatus;
			if (checkedStatus == this.checked) {
				$(this).closest('.checker > span').removeClass('checked');
			}
			if (this.checked) {
				$(this).closest('.checker > span').addClass('checked');
			}
		}
		$(this).on("click", function() {
			$(o).removeAttr("checked");
		});
	});

};
/**
 * 高级搜索
 */
function highsearch(o) {
	var highSearchName = ($(o).html()).replace(/(^\s*)|(\s*$)/g, "");
	if (highSearchName == '高级搜索') {
		$(o).html("关闭");
		var id = $(o).attr("data-target");
		$(id).slideDown(400);
		$("#highSearch_name").val("关闭");
	} else {
		emptyCon();
		$(o).html("高级搜索");
		var id = $(o).attr("data-target");
		$(id).slideUp(400);
		$("#highSearch_name").val("");
	}
}

/**
 * 时间选择器的初始化
 */
jQuery.DateTimePicker = {
	// 选到小时
	dayToHour : function() {
		$(".datetimepicker").remove();
		$(".form_date").datetimepicker({
			todayBtn : 1,
			pickerPosition : 'bottom-left',
			autoclose : 1,
			todayHighlight : 1,
			startView : 2,
			minView : 1
		});
	},
	dayToDay : function() {
		$(".datetimepicker").remove();
		$(".form_day").datetimepicker('remove');
		$(".form_day").datetimepicker({
			format : 'yyyy/mm/dd',
			todayBtn : 1,
			autoclose : 1,
			todayHighlight : 1,
			startView : 2,// 从天开始选择
			minView : 2
		// 最小时间粒度为天
		});
	},
	// 选到天
	day : function() {
		$(".datetimepicker").remove();
		$(".form_date").datetimepicker({
			todayBtn : 1,
			pickerPosition : 'bottom-left',
			autoclose : 1,
			todayHighlight : 1,
			startView : 2,
			minView : 2
		});
	}

};
// 全局蒙版的添加
jQuery.Mark = {
	show : function() {
		$("#ajax_mask").css("display", "block");
		$("#ajax_mask_inner").css("display", "block");
	},
	hide : function() {
		$("#ajax_mask").css("display", "none");
		$("#ajax_mask_inner").css("display", "none");
	},
	dialogshow : function() {
		$("#ajax_mask").css("z-index", "20");
		$("#ajax_mask").css("display", "block");
	},
	dialoghide : function() {
		$("#ajax_mask").css("display", "none");
	}
};
// 针对于ajax的一些公共的处理方法
jQuery.YSAjax = {
	error : function(XMLHttpRequest, textStatus, errorThrown) {
		$.Mark.hide();
		$.scojs_message("查询出错:" + textStatus + "!", $.scojs_message.TYPE_ERROR);
	}
};
// 针对当前系统的核心方法
jQuery.YSCore = {
	// 折叠
	// <input type="button"
	// shref="<%=request.getContextPath()%>/material/findall/" srefresh="true"
	// sopen="no" onclick="$.YSCore.collapse(this,'${s.id}')" value="123">
	collapse : function(o, id) {
		var $o = $(o);
		var $tr = $(o).parents("tr");
		var status = $o.attr("sopen");
		var shref = $o.attr("shref");
		var srefresh = $o.attr("srefresh");
		if (status == "no") {
			$tr.after("<tr style='padding:0px;margin:0px'></tr>");
			var $td = $("<td style='padding:0px;margin:0px;' colspan='"
					+ $tr.find("td").size() + "' id='td" + id + "'></td>");
			var $shtml = $("<div style='display:none;margin:0px;padding:0px;background:#FFFFFF' id='sdiv_"
					+ id + "'></div>");
			$td.appendTo($tr.next());
			$td.append($shtml);
			$.Mark.show();
			$.ajax({
				type : 'POST',
				url : shref,
				async : false,
				cache : false,
				success : function(response) {
					$.Mark.hide();
					$shtml.html(response);

					var checkedStatus = $("#check" + id).attr("checked");
					var incheckbox = $("#generateTable").find("." + id + "");
					incheckbox.each(function() {
						if (checkedStatus == "checked") {
							$(this).attr("checked", true);
						} else {
							$(this).attr("checked", false);
						}
					});
				},
				error : function(a, b, c) {
					$.Mark.hide();
					$.YSAjax.error(a, b, c);
				}
			});
			$o.attr("sopen", "open");
			$o.find("i").removeClass("icon-plus-sign").addClass(
					"icon-minus-sign");
			$shtml.css("display", "");
			// $shtml.css("display","none");
			// $shtml.slideDown("slow");

		} else {
			if ($o.attr("sopen") == "close") {
				$o.attr("sopen", "open");
				$o.find("i").removeClass("icon-plus-sign").addClass(
						"icon-minus-sign");
				$tr.next().find("td").css("border-top", "1px solid #DDDDDD");
				if (srefresh == "true") {
					$.Mark.show();
					$.ajax({
						type : 'POST',
						url : shref,
						async : false,
						cache : false,
						success : function(response) {
							$.Mark.hide();
							$("#sdiv_" + id).html(response);

							var checkedStatus = $("#check" + id)
									.attr("checked");
							var incheckbox = $("#generateTable").find(
									"." + id + "");
							incheckbox.each(function() {
								if (checkedStatus == "checked") {
									$(this).attr("checked", true);
								} else {
									$(this).attr("checked", false);
								}
							});
						},
						error : function(a, b, c) {
							$.Mark.hide();
							$.YSAjax.error(a, b, c);
						}
					});
				}
				//
				$("#sdiv_" + id).css("display", "");
				// $("#sdiv_"+id).css("display","none");
				// $("#sdiv_"+id).slideDown("slow");
			} else {
				$o.attr("sopen", "close");
				$o.find("i").removeClass("icon-minus-sign").addClass(
						"icon-plus-sign");
				// $("#sdiv_"+id).slideUp("slow",function(){
				// $tr.next().find("td").css("border-top","0px");
				// });
				$("#sdiv_" + id).css("display", "none");
				$tr.next().find("td").css("border-top", "0px");
			}
		}
	},
	// 新的方法删除确认
	delConfirm : function(o, id) {
		var smessage = "确定要提交该操作?";
		if ($(o).attr("smessage") != null)
			smessage = $(o).attr("smessage");
		var confirm = $.scojs_confirm({
			content : smessage,
			action : function() {
				$.Mark.show();
				var url = $(o).attr("href");
				$.ajax({
					type : 'POST',
					url : url,
					data : 'id=' + id,
					success : function(data) {
						$.Mark.hide();
						if (data == "success") {
							$.scojs_message('操作成功!', $.scojs_message.TYPE_OK);
							selectform();
						} else {
							$
									.scojs_message('操作失败!',
											$.scojs_message.TYPE_ERROR);
							selectform();
						}
					},
					error : function(a, b, c) {
						$.YSAjax.error(a, b, c);
					}
				});

				confirm.destroy();
			}
		});
		confirm.show();
	}
// 新的方法删除确认
};

/**
 * 屏蔽全局的回车和退格键
 * 
 * @param o
 * @return
 * 
 * $(document).ready(function(){ $(document).keydown(function(e){
 * if(e.keyCode==13 || (e.keyCode == 8 && $(e.target).is(":not(input)"))){
 * e.preventDefault(); } });; });
 */

function Map() {
	var myArrays = new Array();
	// 添加键值，如果键重复则替换值
	this.put = function(key, value) {
		var v = this.get(key);
		if (v == null) {
			var len = myArrays.length;
			myArrays[len] = {
				Key : key,
				Value : value
			};
		} else {
			for ( var i = 0; i < myArrays.length; i++) {
				if (myArrays[i].Key == key) {
					myArrays[i].Value = value;
				}
			}
		}
	},
	// 根据键获取值
	this.get = function(key) {
		for ( var i = 0; i < myArrays.length; i++) {
			if (myArrays[i].Key == key) {
				return myArrays[i].Value;
			}
		}
		return null;
	},
	// 以数组形式返回值列表
	this.getValues = function() {
		var newArray = new Array();
		for ( var i = 0; i < myArrays.length; i++) {
			newArray[i] = myArrays[i].Value;
		}
		return newArray;
	}
}

/* main页面用到的js */
var upload_id = "";
var flow_id = "";
function showUnreadNews() {
	var polls = $(".pollStatus");
	var indexValue = "处理中,推送中,转码中";
	var map = new Map();
	for ( var j = 0; j < polls.size(); j++) {
		var $td = $(polls.get(j));
		var pollValue = $td.html().replace(/(^\s*)|(\s*$)/g, "");
		if (indexValue.indexOf(pollValue) > -1) {
			var trid = $td.attr("sid");
			map.put(j, trid);
		}
	}
	if (map.getValues().toString() != "") {
		$('#vmsform').ajaxSubmit(function(data) {
			var maps = map.getValues().toString();
			var tridvalue = new Array();
			tridvalue = maps.split(",");
			var tranmap = new Map();
			for ( var i = 0; i < tridvalue.length; i++) {
				// console.log("名字"+tridvalue[i]);
				var $tr = $("#tr" + tridvalue[i])
				var $newtr = $(data).find("#tr" + tridvalue[i]);
				var content = $newtr.find(".pollStatus").html();
				if (content == null) {
					content = ""
				}
				var pollValueT = content.replace(/(^\s*)|(\s*$)/g, "");
				// var
				// pollValueT=$newtr.find(".pollStatus").html().replace(/(^\s*)|(\s*$)/g,"");
				// 判断返回的值是否改变，如果发生变化则更新该条tr
				// console.log("返回的名字："+pollValueT);
				if (indexValue.indexOf(pollValueT) < 0) {
					$tr.replaceWith($newtr);
				} else {
					tranmap.put(i, tridvalue[i]);
				}
			}
			showTransInfo(tranmap);
		});
	}
	;
}
setInterval('showUnreadNews()', 6000);

function doLogout() {
	var logouturi = ctx + "/lizhiyun/doLogout/";
	$.ajax({
		type : 'POST',
		url : logouturi,
		success : function(data) {
			if ("success" == data) {
				window.location = ctx;
			} else {
				document.getElementById("errorMsg").style.display = "block";
			}
		}
	});
}

