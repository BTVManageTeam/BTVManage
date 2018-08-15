var line_videosize = new Array(); // 视频大小集合
var line_audiosize = new Array(); // 音频大小集合
var line_picsize = new Array(); // 图片大小集合
var line_videodur = new Array(); // 视频时长集合
var line_audiodur = new Array(); // 音频时长集合
var line_time = new Array(); // 时间点集合
var line_cmspushok = new Array(); // cms推送成功集合
var line_cmspushfail = new Array(); // cms推送失败集合
var line_videototal = new Array(); // 素材-视频个数集合
var line_audiototal = new Array(); // 素材-音频个数集合
var line_fastoknum = new Array(); // 快编成功数集合
var line_fastfailnum = new Array(); // 快编失败数集合
var line_fastingnum = new Array(); // 快编中数集合
var line_fastdurnum = new Array(); // 快编时长集合
var line_cdnwatoknum = new Array(); // cdn推送成功集合-华通
var line_cdnwatfailnum = new Array(); // cdn推送失败集合-华通
var line_cdnwatingnum = new Array(); // cdn推送中集合-华通
var line_cdnstreamoknum = new Array(); // cdn推送成功集合-清流迅
var line_cdnstreamfailnum = new Array(); // cdn推送失败集合-清流迅
var line_cdnstreamingnum = new Array(); // cdn推送中集合-清流迅
var line_usernamecode = new Array(); // 员工工作量统计-用户名集合
var line_empuploadnum = new Array(); // 员工工作量统计-上传集合
var line_empfastencodnum = new Array(); // 员工工作量统计-快编集合
var line_empchecknum = new Array(); // 员工工作量统计-审核集合
var line_emppublishnum = new Array(); // 员工工作量统计-发布集合
var line_empdownloadnum = new Array(); // 员工工作量统计-下载集合
var line_tranoknum = new Array(); //转码成功集合
var line_tranfailnum = new Array(); //转码失败集合
var line_traningnum = new Array(); //转码中集合
var line_checkoknum = new Array(); //审核通过集合
var line_checkfailnum = new Array(); //审核失败集合
var line_checkingnum = new Array(); //审核中集合
var line_checkwaitnum = new Array(); //待审核集合
// 获取折线图数据-资源大小统计
function findLine(url, divId) {
	$.Mark.show();
	var beginDate = $("#beginDate").val();
	var endDate = $("#endDate").val();
	if (beginDate > endDate) {
		$.scojs_message("开始时间不能大于结束时间！", $.scojs_message.TYPE_ERROR);
		$.Mark.hide();
		return;
	}
	var timeGranularity = $(".edit_time_a a.active").attr("id"); // 时间粒度：按时/按日/按周/按月
	$.ajax({
		type: 'POST',
		url: ctx + url,
		cache: false,
		data: {
			"beginDate": beginDate,
			"endDate": endDate,
			"timeGranularity": timeGranularity
		},
		async: false,
		success: function(data) {
			line_videosize = []; // 视频集合
			line_audiosize = []; // 音频集合
			line_picsize = []; // 图片集合
			line_time = []; // 时间点集合
			if (data == null || "" == data) {
				$.Mark.hide();
				return;
			} else {
				var len = data.length;
				if (len == 0) {
					line_videosize.push('-');
					line_audiosize.push('-');
					line_picsize.push('-');
				}
				$(data).each(function(i) {
					line_time.push(data[i].statisticDate);
					if (data[i].statisticVS != null) {
						line_videosize.push(data[i].statisticVS);
					} else {
						line_videosize.push('-');
					}
					if (data[i].statisticAS != null) {
						line_audiosize.push(data[i].statisticAS);
					} else {
						line_audiosize.push('-');
					}
					if (data[i].statisticPS != null) {
						line_picsize.push(data[i].statisticPS);
					} else {
						line_picsize.push('-');
					}
				});
				var echardata = [{
					name: '视频',
					type: 'bar',
					data: line_videosize
				}, {
					name: '音频',
					type: 'bar',
					data: line_audiosize
				}, {
					name: '图片',
					type: 'bar',
					data: line_picsize
				}];
				var ledata = ['视频', '音频','图片'];
				var formt ="MB";
				presentEchart(echardata, divId, '资源大小统计', ledata,formt,true);
			}

		}
	});
	$.Mark.hide();
}

// 获取折线图数据--视，音频时长

function findDurationLine(url, divId) {
//	$.Mark.show();
	var beginDate = $("#beginDate").val();
	var endDate = $("#endDate").val();
	if (beginDate > endDate) {
		$.scojs_message("开始时间不能大于结束时间！", $.scojs_message.TYPE_ERROR);
//		$.Mark.hide();
		return;
	}
	var timeGranularity = $(".edit_time_a a.active").attr("id"); // 时间粒度：按时/按日/按周/按月
	$.ajax({
		type: 'POST',
		url: ctx + url,
		cache: false,
		data: {
			"beginDate": beginDate,
			"endDate": endDate,
			"timeGranularity": timeGranularity
		},
		async: false,
		success: function(data) {
			line_videodur = []; // 视频时长集合
			line_audiodur = []; // 音频时长集合
			line_time = []; // 时间点集合
			if (data == null || "" == data) {
				//$.Mark.hide();
				return;
			} else {
				var len = data.length;
				if (len == 0) {
					line_videodur.push('-');
					line_audiodur.push('-');
				}
				$(data).each(function(i) {
					line_time.push(data[i].statisticDate);
					if (data[i].statisticVD != null) {
						line_videodur.push(data[i].statisticVD);
					} else {
						line_videodur.push('-');
					}
					if (data[i].statisticAD != null) {
						line_audiodur.push(data[i].statisticAD);
					} else {
						line_audiodur.push('-');
					}
				});
				var echardata = [{
					name: '视频',
					type: 'line',
					data: line_videodur
				}, {
					name: '音频',
					type: 'line',
					data: line_audiodur
				}];
				var ledata = ['视频', '音频'];
				var formt ="分钟";
				presentEchart(echardata, divId, '资源时长统计', ledata,formt,false);
			}

		}
	});
//	$.Mark.hide();
}
// 获取折线图数据--cms推送状态

function findcmsStatusLine(url, divId) {
	//$.Mark.show();
	var beginDate = $("#beginDate").val();
	var endDate = $("#endDate").val();
	if (beginDate > endDate) {
		$.scojs_message("开始时间不能大于结束时间！", $.scojs_message.TYPE_ERROR);
		//$.Mark.hide();
		return;
	}
	var timeGranularity = $(".edit_time_a a.active").attr("id"); // 时间粒度：按时/按日/按周/按月

	$.ajax({
		type: 'POST',
		url: ctx + url,
		cache: false,
		data: {
			"beginDate": beginDate,
			"endDate": endDate,
			"timeGranularity": timeGranularity
		},
		async: false,
		success: function(data) {
			line_cmspushok = []; // cms推送成功集合
			line_cmspushfail = []; // cms推送失败集合
			line_time = []; // 时间点集合
			if (data == null || "" == data) {
				//$.Mark.hide();
				return;
			} else {
				var len = data.length;
				if (len == 0) {
					line_cmspushok.push('-');
					line_cmspushfail.push('-');
				}
				$(data).each(function(i) {
					line_time.push(data[i].statisticDate);
					if (data[i].cmsPushSuccess != null) {
						line_cmspushok.push(data[i].cmsPushSuccess);
					} else {
						line_cmspushok.push('-');
					}
					if (data[i].cmsPushFail != null) {
						line_cmspushfail.push(data[i].cmsPushFail);
					} else {
						line_cmspushfail.push('-');
					}
				});
				
			}
			var  magictype= ['line', 'bar', 'stack', 'tiled'];
			var echardata =[{
				name: '成功',
				type: 'bar',
				stack: '数量',
				itemStyle: {
					normal: {
						label: {
							show: true,
							position: 'outer'
						}
					}
				},
				data: line_cmspushok
			},{
				name: '失败',
				type: 'bar',
				stack: '数量',
				itemStyle: {
					normal: {
						label: {
							show: true,
							position: 'outer'
						}
					}
				},
				data: line_cmspushfail
			}];
			var legdata =['成功', '失败'];
			presentBarEchart(magictype,line_time, echardata, divId, "cms推送统计", legdata,"个");
		}
	});
	//$.Mark.hide();

}
// 获取折线图数据--素材个数

function findSourceLine(url, divId) {
	//$.Mark.show();
	var beginDate = $("#beginDate").val();
	var endDate = $("#endDate").val();
	if (beginDate > endDate) {
		$.scojs_message("开始时间不能大于结束时间！", $.scojs_message.TYPE_ERROR);
		//$.Mark.hide();
		return;
	}
	var timeGranularity = $(".edit_time_a a.active").attr("id"); // 时间粒度：按时/按日/按周/按月

	$.ajax({
		type: 'POST',
		url: ctx + url,
		cache: false,
		data: {
			"beginDate": beginDate,
			"endDate": endDate,
			"timeGranularity": timeGranularity
		},
		async: false,
		success: function(data) {
			line_videototal = []; // 素材-视频个数集合
			line_audiototal = []; // 素材-音频个数集合
			line_time = []; // 时间点集合
			if (data == null || "" == data) {
				//$.Mark.hide();
				return;
			} else {
				var len = data.length;
				if (len == 0) {
					line_videototal.push('-');
					line_audiototal.push('-');
				}
				$(data).each(function(i) {
					line_time.push(data[i].statisticDate);
					if (data[i].sourceVideoNum != null) {
						line_videototal.push(data[i].sourceVideoNum);
					} else {
						line_videototal.push('-');
					}
					if (data[i].sourceAudioNum != null) {
						line_audiototal.push(data[i].sourceAudioNum);
					} else {
						line_audiototal.push('-');
					}
				});
				var echardata = [{
					name: '视频',
					type: 'bar',
					data: line_videototal
				}, {
					name: '音频',
					type: 'bar',
					data: line_audiototal
				}];
				var ledata = ['视频', '音频'];
				var formt="个";
				presentEchart(echardata, divId, '新增资源数统计', ledata,formt,true);
			}
		}
	});
	//$.Mark.hide();
}
// 获取折线图数据--快编个数

function findFastEncodine(url, divId) {
	//$.Mark.show();
	var beginDate = $("#beginDate").val();
	var endDate = $("#endDate").val();
	if (beginDate > endDate) {
		$.scojs_message("开始时间不能大于结束时间！", $.scojs_message.TYPE_ERROR);
		//$.Mark.hide();
		return;
	}
	var timeGranularity = $(".edit_time_a a.active").attr("id"); // 时间粒度：按时/按日/按周/按月

	$.ajax({
		type: 'POST',
		url: ctx + url,
		cache: false,
		data: {
			"beginDate": beginDate,
			"endDate": endDate,
			"timeGranularity": timeGranularity
		},
		async: false,
		success: function(data) {
			line_fastoknum = []; // 快编成功数集合
			line_fastfailnum = []; // 快编失败数集合
			line_fastingnum = []; // 快编中数集合
			line_fastdurnum = []; // 快编时长集合
			line_time = []; // 时间点集合
			if (data == null || "" == data) {
				//$.Mark.hide();
				return;
			} else {
				var len = data.length;
				if (len == 0) {
					line_fastoknum.push('-');
					line_fastfailnum.push('-');
					line_fastingnum.push('-');
					line_fastdurnum.push('-');
				}
				$(data).each(function(i) {
					line_time.push(data[i].statisticDate);
					if (data[i].fastEncodSuccessNum != null) {
						line_fastoknum.push(data[i].fastEncodSuccessNum);
					} else {
						line_fastoknum.push('-');
					}
					if (data[i].fastEncodFailNum != null) {
						line_fastfailnum.push(data[i].fastEncodFailNum);
					} else {
						line_fastfailnum.push('-');
					}
					if (data[i].fastEncodIngNum != null) {
						line_fastingnum.push(data[i].fastEncodIngNum);
					} else {
						line_fastingnum.push('-');
					}
					if (data[i].fastEncodDurationNum != null) {
						line_fastdurnum.push(data[i].fastEncodDurationNum);
					} else {
						line_fastdurnum.push('-');
					}
				});
				var echardata = [{
					name: '快编成功数',
					type: 'line',
					data: line_fastoknum
				}, {
					name: '快编失败数',
					type: 'line',
					data: line_fastfailnum
				}, {
					name: '快编中数',
					type: 'line',
					data: line_fastingnum
				}, {
					name: '快编时长',
					type: 'line',
					data: line_fastdurnum
				}];
				var ledata = ['快编成功数', '快编失败数', '快编中数', '快编时长'];
				presentEchart(echardata, divId, '快编统计', ledata,false);
			}
		}
	});
	//$.Mark.hide();
}
// 获取折线图数据--cdn发布状态
function findCDNStatus(url, divId) {
	//$.Mark.show();
	var beginDate = $("#beginDate").val();
	var endDate = $("#endDate").val();
	if (beginDate > endDate) {
		$.scojs_message("开始时间不能大于结束时间！", $.scojs_message.TYPE_ERROR);
		//$.Mark.hide();
		return;
	}
	var timeGranularity = $(".edit_time_a a.active").attr("id"); // 时间粒度：按时/按日/按周/按月

	$.ajax({
		type: 'POST',
		url: ctx + url,
		cache: false,
		data: {
			"beginDate": beginDate,
			"endDate": endDate,
			"timeGranularity": timeGranularity
		},
		async: false,
		success: function(data) {
			line_cdnwatoknum = []; // cdn推送成功集合-华通
			line_cdnwatfailnum = []; // cdn推送失败集合-华通
			line_cdnwatingnum = []; // cdn推送中集合-华通
			line_cdnstreamoknum = []; // cdn推送成功集合-清流迅
			line_cdnstreamfailnum = []; // cdn推送失败集合-清流迅
			line_cdnstreamingnum = []; // cdn推送中集合-清流迅
			line_time = []; // 时间点集合
			if (data == null || "" == data) {
				//$.Mark.hide();
				return;
			} else {
				var len = data.length;
				if (len == 0) {
					line_cdnwatoknum.push('-'); // cdn推送成功集合-华通
					line_cdnwatfailnum.push('-'); // cdn推送失败集合-华通
					line_cdnwatingnum.push('-'); // cdn推送中集合-华通
					line_cdnstreamoknum.push('-'); // cdn推送成功集合-清流迅
					line_cdnstreamfailnum.push('-'); // cdn推送失败集合-清流迅
					line_cdnstreamingnum.push('-'); // cdn推送中集合-清流迅
				}
				$(data).each(

				function(i) {
					line_time.push(data[i].statisticDate);
					if (data[i].cdnStreamStatuokNum != null) {
						line_cdnstreamoknum.push(data[i].cdnStreamStatuokNum);
					} else {
						line_cdnstreamoknum.push('-');
					}
					if (data[i].cdnStreamStatuFailNum != null) {
						line_cdnstreamfailnum.push(data[i].cdnStreamStatuFailNum);
					} else {
						line_cdnstreamfailnum.push('-');
					}
					if (data[i].cdnStreamStatuIngNum != null) {
						line_cdnstreamingnum.push(data[i].cdnStreamStatuIngNum);
					} else {
						line_cdnstreamingnum.push('-');
					}
					if (data[i].cdnWatStatuokNum != null) {
						line_cdnwatoknum.push(data[i].cdnWatStatuokNum);
					} else {
						line_cdnwatoknum.push('-');
					}
					if (data[i].cdnWatStatuFailNum != null) {
						line_cdnwatfailnum.push(data[i].cdnWatStatuFailNum);
					} else {
						line_cdnwatfailnum.push('-');
					}
					if (data[i].cdnWatStatuIngNum != null) {
						line_cdnwatingnum.push(data[i].cdnWatStatuIngNum);
					} else {
						line_cdnwatingnum.push('-');
					}
				});
				var echardata = [{
					name: 'cdn推送成功数(华通)',
					type: 'line',
					data: line_cdnwatoknum
				}, {
					name: 'cdn推送失败数(华通)',
					type: 'line',
					data: line_cdnwatfailnum
				}, {
					name: 'cdn推送中数(华通)',
					type: 'line',
					data: line_cdnwatingnum
				}, {
					name: 'cdn推送成功数(清流迅)',
					type: 'line',
					data: line_cdnstreamoknum
				}, {
					name: 'cdn推送失败数(清流迅)',
					type: 'line',
					data: line_cdnstreamfailnum
				}, {
					name: 'cdn推送中数(清流迅)',
					type: 'line',
					data: line_cdnstreamingnum
				}];

				var ledata = ['cdn推送成功数(华通)', 'cdn推送失败数(华通)', 'cdn推送中数(华通)', 'cdn推送成功数(清流迅)', 'cdn推送失败数(清流迅)', 'cdn推送中数(清流迅)'];
				presentEchart(echardata, divId, 'cdn统计', ledata,false);

			}

		}
	});
	//$.Mark.hide();
}
// 获取折线图数据--员工工作量

function findWorkLoad(url, divId) {
	$.Mark.show();
	var beginDate = $("#beginDate").val();
	var endDate = $("#endDate").val();
	if (beginDate > endDate) {
		$.scojs_message("开始时间不能大于结束时间！", $.scojs_message.TYPE_ERROR);
		$.Mark.hide();
		return;
	}
	$.ajax({
		type: 'POST',
		url: ctx + url,
		cache: false,
		data: {
			"beginDate": beginDate,
			"endDate": endDate
		},
		async: false,
		success: function(data) {
			line_usernamecode = []; // 员工工作量统计-用户名集合
			line_empuploadnum = []; // 员工工作量统计-上传集合
			line_empfastencodnum = []; // 员工工作量统计-快编集合
			line_empchecknum = []; // 员工工作量统计-审核集合
			line_emppublishnum = []; // 员工工作量统计-发布集合
			line_empdownloadnum = []; // 员工工作量统计-下载集合
			if (data == null || "" == data) {
				$.Mark.hide();
				return;
			} else {
				var len = data.length;
				if (len == 0) {
					line_usernamecode.push('-'); // 员工工作量统计-用户名集合
					line_empuploadnum.push('-'); // 员工工作量统计-上传集合
					line_empfastencodnum.push('-'); // 员工工作量统计-快编集合
					line_empchecknum.push('-'); // 员工工作量统计-审核集合
					line_emppublishnum.push('-'); // 员工工作量统计-发布集合
					line_empdownloadnum.push('-'); // 员工工作量统计-下载集合
				} else {
					$(data).each(function(i) {
						$.each(data[i], function(key, value) {
							if ("userNameCode" == key) {
								if (6<value.length) {
									value = value.substring(0, 6)+"\n"+ value.substring(6, value.length);
								}
								line_usernamecode.push(value);
							}
							if ("empUploadNum" == key) {
								line_empuploadnum.push(value);
							}
							if ("empFastEncodNum" == key) {
								line_empfastencodnum.push(value);
							}
							if ("empCheckNum" == key) {
								line_empchecknum.push(value);
							}
							if ("empPublishNum" == key) {
								line_emppublishnum.push(value);
							}
							if ("empDownloadNum" == key) {
								line_empdownloadnum.push(value);
							}
						});
					});

				}
			}
			require.config({
				paths: {
					echarts: 'js/echarts/example/www/js'
				}
			});

			require(['echarts', 'echarts/chart/bar', 'echarts/chart/line'

			], function(ec) {
				// --- 折柱 ---
				var myChart = ec.init(document.getElementById(divId));
				var option = {
					tooltip: {
						trigger: 'axis',
						axisPointer: { // 坐标轴指示器，坐标轴触发有效
							type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
						}
					},
					legend: {
						data: ['上传', '下载', '快编', '审核', '发布']
					},
					toolbox: {
						show: true,
						feature: {
							mark: {
								show: true
							},
							dataView: {
								show: true,
								readOnly: false
							},
							magicType: {
								show: true,
								type: ['line', 'bar', 'stack', 'tiled']
							},
							restore: {
								show: true
							},
							saveAsImage: {
								show: true
							}
						}
					},
					calculable: true,
					xAxis: [{
						type: 'value',
						 axisLabel : {
		                     formatter: '{value} 个'
		                 }
					}],
					//纵坐标，用户名
					yAxis: [{
						type: 'category',
						data: line_usernamecode
					}],
					series: [{
						name: '上传',
						type: 'bar',
						stack: '总量',
						itemStyle: {
							normal: {
								label: {
									show: true,
									position: 'insideLeft'
								}
							}
						},
						data: line_empuploadnum
					}, {
						name: '下载',
						type: 'bar',
						stack: '总量',
						itemStyle: {
							normal: {
								label: {
									show: true,
									position: 'insideLeft'
								}
							}
						},
						data: line_empdownloadnum
					}, {
						name: '快编',
						type: 'bar',
						stack: '总量',
						itemStyle: {
							normal: {
								label: {
									show: true,
									position: 'insideLeft'
								}
							}
						},
						data: line_empfastencodnum
					}, {
						name: '审核',
						type: 'bar',
						stack: '总量',
						itemStyle: {
							normal: {
								label: {
									show: true,
									position: 'insideLeft'
								}
							}
						},
						data: line_empchecknum
					}, {
						name: '发布',
						type: 'bar',
						stack: '总量',
						itemStyle: {
							normal: {
								label: {
									show: true,
									position: 'insideLeft'
								}
							}
						},
						data: line_emppublishnum
					}]
				};
				myChart.setOption(option);

			});

		}
	});
	$.Mark.hide();

} 

//获取折线图数据--转码状态
function findTranscodeStatusLine(url, divId) {
//	$.Mark.show();
	var beginDate = $("#beginDate").val();
	var endDate = $("#endDate").val();
	if (beginDate > endDate) {
		$.scojs_message("开始时间不能大于结束时间！", $.scojs_message.TYPE_ERROR);
//		$.Mark.hide();
		return;
	}
	var timeGranularity = $(".edit_time_a a.active").attr("id"); // 时间粒度：按时/按日/按周/按月
	$.ajax({
		type: 'POST',
		url: ctx + url,
		cache: false,
		data: {
			"beginDate": beginDate,
			"endDate": endDate,
			"timeGranularity": timeGranularity
		},
		async: false,
		success: function(data) {
			line_tranoknum = []; // 转码成功集合
			line_tranfailnum = []; // 转码失败集合
			line_traningnum = []; // 转码中集合
			line_time = []; // 时间点集合
			if (data == null || "" == data) {
				//$.Mark.hide();
				return;
			} else {
				var len = data.length;
				if (len == 0) {
					line_tranoknum.push('-');
					line_tranfailnum.push('-');
					line_traningnum.push('-');
				}
				$(data).each(function(i) {
					line_time.push(data[i].statisticDate);
					if (data[i].transSucNum != null) {
						line_tranoknum.push(data[i].transSucNum);
					} else {
						line_tranoknum.push('-');
					}
					if (data[i].transFailNum != null) {
						line_tranfailnum.push(data[i].transFailNum);
					} else {
						line_tranfailnum.push('-');
					}
					if (data[i].transIngNum != null) {
						line_traningnum.push(data[i].transIngNum);
					} else {
						line_traningnum.push('-');
					}
				});
				var echardata = [{
					name: '成功',
					type: 'line',
					data: line_tranoknum
				}, {
					name: '失败',
					type: 'line',
					data: line_tranfailnum
				}, {
					name: '转码中',
					type: 'line',
					data: line_traningnum
				}];
				var ledata = ['成功', '失败','转码中'];
				var formt ="个";
				presentEchart(echardata, divId, '转码状态统计', ledata,formt,false);
			}

		}
	});
//	$.Mark.hide();
}


//统计素材的格式统计-饼状图
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

	presentEchartPie(divId, pieTitle, pieData, '资源格式统计');
	$.Mark.hide();
}

//获取折线图数据--审核状态
function findCheckStatusLine(url, divId) {
//	$.Mark.show();
	var beginDate = $("#beginDate").val();
	var endDate = $("#endDate").val();
	if (beginDate > endDate) {
		$.scojs_message("开始时间不能大于结束时间！", $.scojs_message.TYPE_ERROR);
//		$.Mark.hide();
		return;
	}
	var timeGranularity = $(".edit_time_a a.active").attr("id"); // 时间粒度：按时/按日/按周/按月
	$.ajax({
		type: 'POST',
		url: ctx + url,
		cache: false,
		data: {
			"beginDate": beginDate,
			"endDate": endDate,
			"timeGranularity": timeGranularity
		},
		async: false,
		success: function(data) {
			line_checkoknum = []; // 审核通过集合
			line_checkfailnum = []; // 审核失败集合
			line_checkingnum = []; // 审核中集合
			line_checkwaitnum = []; // 待审核集合
			line_time = []; // 时间点集合
			if (data == null || "" == data) {
				//$.Mark.hide();
				return;
			} else {
				var len = data.length;
				if (len == 0) {
					line_checkoknum.push('-');
					line_checkfailnum.push('-');
					line_checkingnum.push('-');
					line_checkwaitnum.push('-');
				}
				$(data).each(function(i) {
					line_time.push(data[i].statisticDate);
					if (data[i].checkokNum != null) {
						line_checkoknum.push(data[i].checkokNum);
					} else {
						line_checkoknum.push('-');
					}
					if (data[i].checkFailNum != null) {
						line_checkfailnum.push(data[i].checkFailNum);
					} else {
						line_checkfailnum.push('-');
					}
					if (data[i].checkIngNum != null) {
						line_checkingnum.push(data[i].checkIngNum);
					} else {
						line_checkingnum.push('-');
					}if (data[i].checkWaitNum != null) {
						line_checkwaitnum.push(data[i].checkWaitNum);
					} else {
						line_checkwaitnum.push('-');
					}
				});
				var echardata = [{
					name: '审核通过',
					type: 'bar',
					data: line_checkoknum
				}, {
					name: '审核失败',
					type: 'bar',
					data: line_checkfailnum
				}, {
					name: '审核中',
					type: 'bar',
					data: line_checkingnum
				}, {
					name: '待审核',
					type: 'bar',
					data: line_checkwaitnum
				}];
				var ledata = ['审核通过', '审核失败','审核中','待审核'];
				var formt ="个";
				presentEchart(echardata, divId, '审核状态统计', ledata,formt,true);
			}

		}
	});
//	$.Mark.hide();
}
/** 渲染Echars报表 */
function presentEchart(echardata, divId, titleval, legdata,formt,boundaryGapFlag) {
	require.config({
		paths: {
			echarts: 'js/echarts/example/www/js'
		}
	});

	require(['echarts', 'echarts/chart/bar', 'echarts/chart/line'

	], function(ec) {
		// --- 折柱 ---
		var myChart = ec.init(document.getElementById(divId));
		var option = {
			tooltip: {
				trigger: 'axis'
			},
			title: {
				text: titleval
			},
			legend: {
				orient: 'horizontal',
				x: 'center',
				y: 'bottom',
				data: legdata
			},
			toolbox: {
				show: true,
				feature: {
					mark: {
						show: true
					},
					dataView: {
						show: true,
						readOnly: false
					},
					magicType: {
						show: true,
						type: ['line', 'bar']
					},
					restore: {
						show: true
					},
					saveAsImage: {
						show: true
					}
				}
			},
			calculable: true,
			xAxis: [{
				type: 'category',
				boundaryGap: boundaryGapFlag,//柱状图改为true,折线图设置为false
				data: line_time
			}],
			yAxis: [{
				type: 'value',
				 axisLabel : {
                     formatter: '{value} '+formt
                 }

			}],
			series: echardata
		};
		myChart.setOption(option);

	});

}
/**
 * 渲染Echars报表-堆积柱状图
 * @param magictype 图形切换 ['line', 'bar', 'stack', 'tiled']
 * @param ydata y轴数据
 * @param echardata 内容数组
 * @param divId 展示数据div的id
 * @param titleval 标题
 * @param legdata 图例
 */
function presentBarEchart(magictype,ydata, echardata, divId, titleval, legdata,formt) {
	require.config({
		paths: {
			echarts: 'js/echarts/example/www/js'
		}
	});
	require(['echarts', 'echarts/chart/bar', 'echarts/chart/line' ], function(ec){
		var myChart = ec.init(document.getElementById(divId));
		var option ={
				tooltip: {
					trigger: 'axis',
					axisPointer: { // 坐标轴指示器，坐标轴触发有效
						type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
					}
				},
				title: {
			        text: titleval
			     },
				legend: {
					orient: 'horizontal',
					x: 'center',
					y: 'bottom',
					data: legdata
				},	
				toolbox: {
					show: true,
					feature: {
						mark: {
							show: true
						},
						dataView: {
							show: true,
							readOnly: false
						},
						magicType: {
							show: true,
							type: magictype
						},
						restore: {
							show: true
						},
						saveAsImage: {
							show: true
						}
					}
				},
				calculable: true,
				xAxis: [{
					type: 'category',
					data: ydata 
				}],
				//纵坐标，时间
				yAxis: [{
					type: 'value',
					 axisLabel : {
	                     formatter: '{value} '+formt
	                 }
				}],
				series:echardata
				
		};
		myChart.setOption(option);
		
	});
}

/** 渲染Echars报表-饼状图 */
function presentEchartPie(divId, pieTitle, pieData, titleName) {
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
				center : [ '50%', 150 ],
				radius : 80,
				data :  pieData
			}

			]
		};

		myChart.setOption(option);
	});
}