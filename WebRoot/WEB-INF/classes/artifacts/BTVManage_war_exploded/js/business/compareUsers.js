//要导入到本地的用户id
var userArr = [];
var tempUserIds = [];
//左右比较 begin
sortitems = 1; // Automatically sort items within lists? (1 or 0)

function move(fbox, tbox) {
	for ( var i = 0; i < fbox.options.length; i++) {
		if (fbox.options[i].selected && fbox.options[i].value != "") {
			var no = new Option();
			no.value = fbox.options[i].value;
			no.text = fbox.options[i].text;
			removeRepeat(tbox,no.value);
			tbox.options[tbox.options.length] = no;
			fbox.options[i].value = "";
			fbox.options[i].text = "";
			if(fbox.name == "list2"){
				userArr.push(no.value);
			}else if(fbox.name == "list1"){
				userArr.splice($.inArray(no.value,userIds),1);
			}
		}
	}
	BumpUp(fbox);
//	if (sortitems)
//		SortD(tbox);
}

function moveall(fbox, tbox) {
	for ( var i = 0; i < fbox.options.length; i++) {
		if (fbox.options[i].value != "") {
			var no = new Option();
			no.value = fbox.options[i].value;
			no.text = fbox.options[i].text;
			removeRepeat(tbox,no.value);
			tbox.options[tbox.options.length] = no;
			fbox.options[i].value = "";
			fbox.options[i].text = "";
			if(fbox.name == "list2"){
				userArr.push(no.value);
			}else if(fbox.name == "list1"){
				userArr.splice($.inArray(no.value,userIds),1);
			}
		}
	}
	BumpUp(fbox);
//	if (sortitems)
//		SortD(tbox);
}

function BumpUp(box) {
	for ( var i = 0; i < box.options.length; i++) {
		if (box.options[i].value == "") {
			for ( var j = i; j < box.options.length - 1; j++) {
				box.options[j].value = box.options[j + 1].value;
				box.options[j].text = box.options[j + 1].text;
			}
			var ln = i;
			break;
		}
	}
	if (ln < box.options.length) {
		box.options.length -= 1;
		BumpUp(box);
	}
}

function SortD(box) {
	var temp_opts = new Array();
	var temp = new Object();
	for ( var i = 0; i < box.options.length; i++) {
		temp_opts[i] = box.options[i];
	}

	for ( var x = 0; x < temp_opts.length - 1; x++) {
		for ( var y = (x + 1); y < temp_opts.length; y++) {
			if (temp_opts[x].text > temp_opts[y].text) {
				temp = temp_opts[x].text;
				temp_opts[x].text = temp_opts[y].text;
				temp_opts[y].text = temp;
				temp = temp_opts[x].value;
				temp_opts[x].value = temp_opts[y].value;
				temp_opts[y].value = temp;
			}
		}
	}

	for ( var i = 0; i < box.options.length; i++) {
		box.options[i].value = temp_opts[i].value;
		box.options[i].text = temp_opts[i].text;
	}
}
//左右比较 end

/**
 * select去除重复
 * 
 * @param sels 要判断的select
 * @param val 要取出的值
 */
function removeRepeat(sels,val){
	for ( var i = 0; i < sels.options.length; i++) {
		if(sels.options[i].value == val){
			sels.options[i] = null;
		}
	}
}

//初始化点击事件
//$(function() {
//	$("#wpfidPlat").click(function(){
//		getUsers('p');
//	});
//	$("#wpfid").click(function(){
//		getUsers('p');
//		getUsers('a');
//	});
//	$("#yfpid").click(function(){
//		getUsers('u');
//	});
//	$("#wpfidPlat").click();
//	$("#wpfid").click();
//	$("#btnGiveup").click(function(){
//		$("#wpfidPlat").click();
//		$("#wpfid").click();
//	});
//});


/**
 * 获取数据
 * 
 * @param params
 * 			p:平台，a:新增，u:更新
 */
function getUsers(params) {
	var uri = ctx + "/users/getUsersByParams/?params=" + params;
	$.ajax({
		type : "POST",
		url : uri,
		async: false,
		success : function(data) {
			if (data != null) {
				fillSelect(params,data);
			}
		}
	});
}


/**
 * 解析后台返回的数据，然后拼接显示在页面上
 * 
 * @param params
 * @param data
 */
function fillSelect(params,data){
	if(params == "p"){
		tempUserIds = [];
		$("#selectUsersPlat").empty();
		for(var i = 0;i < data.length; i++){
			$("#selectUsersPlat").append("<option value="+data[i].userId+">"+data[i].userName+" [ID:"+data[i].userId+"]</option>");
			tempUserIds.push(data[i].userId);
		}
	}else if(params == "a"){
		userArr = [];
		$("#selectUsers").empty();
		for(var i = 0;i < data.length; i++){
			$("#selectUsers").append("<option value="+data[i].userId+">"+data[i].userName+" [ID:"+data[i].userId+"]</option>");
		}
	}else if(params == "u"){
		userArr = [];
		var uri = ctx + "/users/getUsersByParams/?params=u";
		$.ajax({
			type : "POST",
			url : uri,
			success : function(data) {
				if (data != null) {
					tempUserIds = [];
					$("#selectUsers").empty();
					for(var i = 0;i < data.length; i++){
						$("#selectUsers").append("<option value="+data[i].userId+">"+data[i].userName+" [ID:"+data[i].userId+"]</option>");
//						tempUserIds.push(data[i].userId);
					}
				}
			}
		});
		
		uri = ctx + "/users/getUsersByParams/?params=up";
		$.ajax({
			type : "POST",
			url : uri,
			success : function(data) {
				if (data != null) {
					$("#selectUsersPlat").empty();
					for(var i = 0;i < data.length; i++){
						$("#selectUsersPlat").append("<option value="+data[i].userId+">"+data[i].userName+" [ID:"+data[i].userId+"]</option>");
					}
				}
			}
		});
	}
	
}

function tempToLocal(){
//	for(var i = 0;i<tempUserIds.length; i++){
//		if(userIds.in_array(tempUserIds[i])){
//			userIds.splice($.inArray(tempUserIds[i],userIds),1);
//		}
//	}
	var compareList = $("#compareList").val();
	if(compareList <= 0){
		$.scojs_message('无差异数据!', $.scojs_message.TYPE_ERROR);
		return;
	}
	
	var selectcheckbox=$(".selectcheckbox");//获取复选框选中的值
	var checkboxval="";//复选框选中的值拼接
	var ischeck=true;//选择标识（true 则不存在选中项，false 则存在选中项）
	
	for(var i=0;i<selectcheckbox.size();i++){
		if($(selectcheckbox.get(i)).prop("checked")== true ){//选中的复选框
			checkboxval += $(selectcheckbox.get(i)).val()+",";
			 ischeck=false;
		} 
	}
	
	if(ischeck){//如果没有一个选中的则默认导入列表中所有
		var confirm = $.scojs_confirm({
			content:"您确定要导入列表中所有用户吗？",
			action:function(){
				confirm.destroy();
				importUser(checkboxval);
			}
			
		});
		confirm.show();
		return;
	}else{//导入选中用户
		importUser(checkboxval);
	}
	
}
/**
 * 导入用户
 * @param checkboxval
 */
function importUser(checkboxval){
	var uri = ctx + "/users/tempToLocal/";//请求地址
	var typeval =$("#flag").val();//标识（add 添加，delete删除，update 修改）
	$.Mark.show();
	$.ajax({
		type : "POST",
		url : uri,
		cache:false,
		data:{
			"type":typeval,
			"userIds":checkboxval,
		},
		success : function(data) {
			if(data.status == "success"){
				$.scojs_message('导入用户成功【'+data.successInt+'】,失败 【'+data.failInt+'】', $.scojs_message.TYPE_OK);
				selectMyForm($("#flag").val());
				$.Mark.hide();
			}else{
				$.scojs_message('导入失败!', $.scojs_message.TYPE_ERROR);
				$.Mark.hide();
			}
		}
	});
}

//数组中是否包含指定的元素
Array.prototype.in_array = function(e) {  
    for(i=0; i<this.length && this[i]!=e; i++);  
    return !(i==this.length);  
} 
