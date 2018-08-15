/** 此js加载ztree 并绑定单击事件（在栏目分配用户列表与用户组分配用户列表引用） */
var zTreeMenu;
var zNodesMenu = [];
var settingMenu = {
	edit:{
		enable:true,
		showRemoveBtn: false,
		showRenameBtn: false,
		drag:{
			prev:false, // 向上拖拽
			next:false, // 向下拖拽
			inner:false// 元素存放到同一级另一元素内部
		}
	},
	view : {
		selectedMulti : false,
		showLine : false,
		dblClickExpand : false
	},
	async: {
		enable: true,
		url: ctx + "/department/findAllRecuChild/",
		autoParam:["id"],
		dataFilter: filter // 异步加载方法
	},
	data : {
		simpleData : {
			enable : true,
			idKey : "id", // 在isSimpleData格式下，当前节点id属性
			pIdKey : "pid" // 在isSimpleData格式下，当前节点的父节点id属性
		}
	},
	callback:{
		onClick: zTreeOnClick,
// beforeClick: beforeClick,
// beforeAsync:zTreeOnAsyncSuccess,//异步加载成功的fun
		asyncError: zTreeOnAsyncError // 加载错误的fun
	}
};

/**
 * ztree单击事件
 * 
 * @param event
 * @param treeId
 * @param treeNode
 */
function zTreeOnClick(event, treeId, treeNode) {
// alert(treeNode.tId + ", " + treeNode.name);
// $("#partId").val(treeNode.id);
// selectMyForm();//重新加载数据
	var note_id = treeNode.id;
	if(note_id =="1"){
		$.scojs_message('请选择部门!', $.scojs_message.TYPE_ERROR);
		$("#departNameId").blur();
	}else{
		$("#departNameId").val(treeNode.name);
		$("#departId").val(note_id);
		$("#suoshushangid").focus();
		$("#depdivId").css("display","none");
	}
	
	
};
/** 异步加载数据 */
function filter(treeId, parentNode, childNodes) {
	if(!childNodes){
		return null;
	}else{
		return childNodes;
	}
}
/** 异步加载点击之前触发的方法 */
function beforeClick(treeId, treeNode) {
	if (!treeNode.isParent) {
		alert("请选择父节点");
		return false;
	} else {
		return true;
	}
}
/** 异步加载成功方法 */
function zTreeOnAsyncSuccess(event, treeId, msg){
	alert("加载成功");
}
/** 异步加载失败方法 */
function zTreeOnAsyncError(event, treeId, msg){
	alert("加载失败");
}

// 初始树
function initTree() {
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		dataType : "json",
		url : ctx + "/department/getZtreeOnceList/",// 请求的action路径
		error : function() {// 请求失败处理函数
			alert('请求失败');
		},
		success : function(data) { // 请求成功后处理函数
			zNodesMenu = (data); // 把后台封装好的简单Json格式赋给treeNodes
		}
	});
	$("#depdivId").css("display","block");
	$.fn.zTree.init($("#menutree"), settingMenu, zNodesMenu);
	zTreeMenu = $.fn.zTree.getZTreeObj("menutree");
//	zTreeMenu.expandAll(true);
}