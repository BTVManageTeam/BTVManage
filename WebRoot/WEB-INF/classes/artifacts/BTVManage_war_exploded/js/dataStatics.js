//统计视频和音频的数量		
function findPieInfoCategoryByVideoOrAudio(url, divId) {
	$.Mark.show();
	var beginDate = $("#beginDate").val();
	var endDate = $("#endDate").val();
	if (beginDate > endDate) {
		$.scojs_message("开始时间不能大于结束时间！", $.scojs_message.TYPE_ERROR);
		$.Mark.hide();
		return;
	}
	var pieTitle = [];
	var pieData = [];
	$.ajax({
		cache : false,
		type : 'post',
		url : ctx + url,
		data : {
			"beginDate" : beginDate,
			"endDate" : endDate
		},
		async : false,
		success : function(data) {
			data = jQuery.parseJSON(data);
			if (data == null || "" == data) {
				$.Mark.hide();
				return;
			}
			var length = data.length;
			if (length == 0) {
				pieTitle.push('-');
				pieData.push('-');
			}
			for (var i = 0; i < data.length; i++) {
				var itemTitle = data[i].itemTitle;
				if ("video" == itemTitle) {
					itemTitle = "视频";
				} else if ("audio" == itemTitle) {
					itemTitle = "音频";
				}
				pieTitle.push(itemTitle);
				var itemValue = data[i].itemData;
				var tmp = {
					name : itemTitle,
					value : itemValue
				};
				pieData.push(tmp);
			}
		}
	});
	presentEchart(divId, pieTitle, pieData, '新增资源数统计图');
	$.Mark.hide();
}

// 统计素材和成品的数量
function findPieInfoCategoryByMaterialOrPublish(url, divId) {
	$.Mark.show();
	var beginDate = $("#beginDate").val();
	var endDate = $("#endDate").val();
	if (beginDate > endDate) {
		$.scojs_message("开始时间不能大于结束时间！", $.scojs_message.TYPE_ERROR);
		$.Mark.hide();
		return;
	}
	var pieTitle = [];
	var pieData = [];
	$.ajax({
		cache : false,
		type : 'post',
		url : ctx + url,
		data : {
			"beginDate" : beginDate,
			"endDate" : endDate
		},
		async : false,
		success : function(data) {
			data = jQuery.parseJSON(data);
			if (data == null || "" == data) {
				$.Mark.hide();
				return;
			}
			var length = data.length;
			if (length == 0) {
				pieTitle.push('-');
				pieData.push('-');
			}
			for (var i = 0; i < data.length; i++) {
				var itemTitle = data[i].itemTitle;
				if ("publish" == itemTitle) {
					itemTitle = "成品";
				} else if ("generate" == itemTitle) {
					itemTitle = "素材";
				}
				pieTitle.push(itemTitle);
				var itemValue = data[i].itemData;
				var tmp = {
					name : itemTitle,
					value : itemValue
				};
				pieData.push(tmp);
			}
		}
	});
	presentEchart(divId, pieTitle, pieData, '素材成品统计图');
	$.Mark.hide();
}

// 统计已提交审核素材的数量按审核状态
function findPieInfoCategoryByCheckStatus(url, divId) {
	$.Mark.show();
	var beginDate = $("#beginDate").val();
	var endDate = $("#endDate").val();
	if (beginDate > endDate) {
		$.scojs_message("开始时间不能大于结束时间！", $.scojs_message.TYPE_ERROR);
		$.Mark.hide();
		return;
	}
	var pieTitle = [];
	var pieData = [];
	$.ajax({
		cache : false,
		type : 'post',
		url : ctx + url,
		data : {
			"beginDate" : beginDate,
			"endDate" : endDate
		},
		async : false,
		success : function(data) {
			data = jQuery.parseJSON(data);
			if (data == null || "" == data) {
				$.Mark.hide();
				return;
			}
			var length = data.length;
			if (length == 0) {
				pieTitle.push('-');
				pieData.push('-');
			}
			for (var i = 0; i < data.length; i++) {
				var itemTitle = data[i].itemTitle;
				if ("cksuccess" == itemTitle) {
					itemTitle = "审核通过";
				} else if ("cking" == itemTitle) {
					itemTitle = "审核中";
				} else if ("ckfail" == itemTitle) {
					itemTitle = "审核不通过";
				} else if ("ckwait" == itemTitle) {
					itemTitle = "未审核";
				}
				pieTitle.push(itemTitle);
				var itemValue = data[i].itemData;
				var tmp = {
					name : itemTitle,
					value : itemValue
				};
				pieData.push(tmp);
			}
		}
	});

	presentEchart(divId, pieTitle, pieData, '审核情况统计图');
	$.Mark.hide();
}
// 统计按转码状态
function findPieInfoCategoryByTranscodeStatus(url, divId) {
	$.Mark.show();
	var beginDate = $("#beginDate").val();
	var endDate = $("#endDate").val();
	if (beginDate > endDate) {
		$.scojs_message("开始时间不能大于结束时间！", $.scojs_message.TYPE_ERROR);
		$.Mark.hide();
		return;
	}
	var pieTitle = [];
	var pieData = [];
	$.ajax({
		cache : false,
		type : 'post',
		url : ctx + url,
		data : {
			"beginDate" : beginDate,
			"endDate" : endDate
		},
		async : false,
		success : function(data) {
			data = jQuery.parseJSON(data);
			if (data == null || "" == data) {
				$.Mark.hide();
				return;
			}
			var length = data.length;
			if (length == 0) {
				pieTitle.push('-');
				pieData.push('-');
			}
			for (var i = 0; i < data.length; i++) {
				var itemTitle = data[i].itemTitle;
				if ("transucess" == itemTitle) {
					itemTitle = "转码成功";
				} else if ("tranfail" == itemTitle) {
					itemTitle = "转码失败";
				} else if ("traning" == itemTitle) {
					itemTitle = "转码中";
				}
				pieTitle.push(itemTitle);
				var itemValue = data[i].itemData;
				var tmp = {
					name : itemTitle,
					value : itemValue
				};
				pieData.push(tmp);
			}
		}
	});

	presentEchart(divId, pieTitle, pieData,
			'转码情况统计图                                                 ');
	$.Mark.hide();
}

// 统计素材的格式统计
function findPieInfoCategoryByGenerateFmt(url, divId) {
	$.Mark.show();
	var beginDate = $("#beginDate").val();
	var endDate = $("#endDate").val();
	if (beginDate > endDate) {
		$.scojs_message("开始时间不能大于结束时间！", $.scojs_message.TYPE_ERROR);
		$.Mark.hide();
		return;
	}
	var pieTitle = [];
	var pieData = [];
	$.ajax({
		cache : false,
		type : 'post',
		url : ctx + url,
		data : {
			"beginDate" : beginDate,
			"endDate" : endDate
		},
		async : false,
		success : function(data) {
			if (data == null || "" == data) {
				$.Mark.hide();
				return;
			}
			data = jQuery.parseJSON(data);
			var length = data.length;
			if (length == 0) {
				pieTitle.push('-');
				pieData.push('-');
			}
			for (var i = 0; i < data.length; i++) {
				var itemTitle = data[i]._id;
				pieTitle.push(itemTitle);
				var itemValue = data[i].value;
				var tmp = {
					name : itemTitle,
					value : itemValue
				};
				pieData.push(tmp);
			}
		}
	});

	presentEchart(divId, pieTitle, pieData, '资源格式统计图');
	$.Mark.hide();
}


/** 渲染Echars报表 */
function presentEchart(divId, pieTitle, pieData, titleName) {
	require.config({
		paths : {
			echarts : 'js/echarts/example/www/js'

		}
	});
	require([ 'echarts' ,'echarts/chart/bar', 'echarts/chart/pie'], function(ec) {
		// 基于准备好的dom，初始化echarts图表
		var myChart = ec.init(document.getElementById(divId));
		var option = {
			title : {
				text : titleName,
				x : 'left',
				textStyle : {
					fontSize : 18,
					fontWeight : 'bolder',
					color : '#606060',

				}
			},
			tooltip : {
				show : true,
				formatter : "{a} <br/>{b} : {c} ({d}%)"
			},
			legend : {
				orient : 'horizontal',
				x : 'center',
				y : 'bottom',
				data : pieTitle
			},
			toolbox : {
				show : true,
				feature : {
					mark : {
						show : true
					},
					dataView : {
						show : true,
						readOnly : false
					},
					restore : {
						show : true
					},
					saveAsImage : {
						show : true
					}
				}
			},
			calculable : true,
			series : [ {
				name : '资源统计',
				type : 'pie',
				center : [ '50%', '50%' ],
				radius : 80,
				data :  pieData
			}

			]
		};

		myChart.setOption(option);
	});
}