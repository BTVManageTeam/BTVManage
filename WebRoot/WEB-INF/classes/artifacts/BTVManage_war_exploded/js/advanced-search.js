function searchNew(tableid) {
	var jsonStr = "{}";
	$('[sid=' + "query" + ']').each(function() {
		var jsonObj = JSON.parse(jsonStr);
		if (this.value != ""&&this.value != "请输入标题") {
			if (this.value == "time") {
				jsonObj["time"] = this.name;
				var startTime = $("#startTime").val();
				var endTime = $("#endTime").val();
				if (startTime != "" && startTime != "开始时间") {
					jsonObj["startTime"] = startTime;
				}
				if (endTime != "" && endTime != "结束时间") {
					jsonObj["endTime"] = endTime;
				}
			} else {
				var n = this.name.replace("1", ".");
				jsonObj["" + n + ""] = this.value;
			}
			jsonStr = JSON.stringify(jsonObj);
		}
	});
	var param = jsonStr;
	$("#" + tableid).jqGrid('setGridParam', {
		datatype : 'json',
		postData : {
			'param' : param
		}, // 发送数据
		page : 1
	}).trigger("reloadGrid"); // 重新载入
}

$(function() {

	// 判断浏览器是否支持placeholder属性
	supportPlaceholder = 'placeholder' in document.createElement('input');

	// 当浏览器不支持placeholder属性时，调用placeholder函数
	if (!supportPlaceholder) {
		$('input').each(function() {
			if ($(this).attr("type") == "text") {
				placeholder($(this));
			}
		});
	}

});

function placeholder(input) {
	var text = input.attr('placeholder');
	var defaultValue = input.defaultValue;
	if (!defaultValue) {
		input.val(text).addClass("phcolor");
	}

	input.focus(function() {
		if (input.val() == text) {
			input.val("");
		}
	});

	input.blur(function() {
		if (input.val() == "") {
			$(this).val(text).addClass("phcolor");
		}
	});

	// 输入的字符不为灰色
	input.keydown(function() {
		$(this).removeClass("phcolor");
	});

}

function search(tableid) {
	var jsonStr = "{}";
	var time = "time";
	var startTime = "";
	var endTime = "";

	$('[sid=' + "query" + ']').each(function() {
		var jsonObj = JSON.parse(jsonStr);
		if (this.value != "") {
			jsonObj["" + this.name + ""] = this.value;
			jsonStr = JSON.stringify(jsonObj);
		}
	});
	$('[name=' + "ckb" + ']:checkbox').each(function() {
		if (this.checked) {
			var params = this.id.split("_");
			var value = $("#" + params[0]).val();
			if (value != "") {
				params[0] = params[0].replace("1", ".");
				var jsonObj = JSON.parse(jsonStr);
				if (params[0] == time) {
					startTime = $("#startTime").val();
					endTime = $("#endTime").val();
					if (startTime != "") {
						jsonObj["startTime"] = startTime;
					}
					if (endTime != "") {
						jsonObj["endTime"] = endTime;
					}
				}
				;
				jsonObj[params[0]] = value;
				jsonStr = JSON.stringify(jsonObj);

			}
		}
	});

	var param = jsonStr;
	$("#" + tableid).jqGrid('setGridParam', {
		datatype : 'json',
		postData : {
			'param' : param
		}, // 发送数据
		page : 1
	}).trigger("reloadGrid"); // 重新载入
}

function convert() {
	var a = $("#advanced").css("display");
	if (a == "none") {
		$("#advanced").css("display", "");
	} else {
		$("#advanced").css("display", "none");
		unAllCkb();
	}
}

function allORunall(items) {
	var btnText = $('#btnCKB').val();
	if ('全选' == btnText) {
		allCkb(items);
		$('#btnCKB').val("全不选");
	} else {
		unAllCkb();
		$('#btnCKB').val("全选");
	}
}

/**
 * 全选
 * 
 * items 复选框的name
 */
function allCkb(items) {
	$('[name=' + items + ']:checkbox').each(function() {
		$("#" + this.id + "_" + this.value).slideDown(400);
		this.checked = true;
	});

}

/**
 * 全不选
 * 
 */
function unAllCkb() {
	$('[type=checkbox]:checkbox').each(function() {
		$("#" + this.id + "_" + this.value).slideUp(400);
		this.checked = false;
	});
};

/**
 * 反选
 * 
 * items 复选框的name
 */
function inverseCkb(items) {
	$('[name=' + items + ']:checkbox').each(function() {
		if (this.checked) {
			$("#" + this.id + "_" + this.value).slideUp(400);
		} else {
			$("#" + this.id + "_" + this.value).slideDown(400);
		}
		this.checked = !this.checked;
	});
};

function conversion(id, value, checked) {
	if (checked) {
		$("#" + id + "_" + value).slideDown(400);
	} else {
		$("#" + id + "_" + value).slideUp(400);
	}
};

/**
 * 高级搜索
 */
function searchShow(o) {
	var highSearchName = $('#highSearch_name').val();
	if (highSearchName == '高级搜索') {
		var id = $(o).attr("data-target");
		$(id).slideDown(400);
		$("#highSearch_name").val("关闭");
	} else {
		emptyCon();
		var id = $(o).attr("data-target");
		$(id).slideUp(400);
		$("#highSearch_name").val("高级搜索");
	}
}

/**
 * 根据下拉框修改输入框的名称和提示信息
 * 
 * @param txtName
 *            输入框的名称
 */
function changeParamName(txtName) {
	$("#txtTempName").val("");
	$("#txtTempName").attr("name", txtName);
	var checkVal = $("#selectTemp").val();
	$("#txtTempName").attr("placeholder", "请输入" + checkVal);
}