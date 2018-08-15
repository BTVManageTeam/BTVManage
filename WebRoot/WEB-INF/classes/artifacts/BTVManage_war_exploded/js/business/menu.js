var zTreeMenu;
var zNodesMenu = [];
var settingMenu = {
	view : {
		addHoverDom : addHoverDom,
		removeHoverDom : removeHoverDom,
		selectedMulti : false
	},
	edit:{
		removeTitle : "删除",
		renameTitle : "编辑",
		enable : true,
		showRenameBtn:showRenameBtn,
		showRemoveBtn:showRemoveBtn
	},
	async: {
		enable: true,
		url: ctx + "/department/findAllRecuChild/",
		autoParam:["id"],
		dataFilter: filter //异步加载方法
	},
	data : {
		simpleData : {
			enable : true,
			idKey : "id", // 在isSimpleData格式下，当前节点id属性
			pIdKey : "pid" // 在isSimpleData格式下，当前节点的父节点id属性
		}
	},
	callback : {
		beforeDrag : beforeDrag,
		beforeRemove : beforeRemove,
		beforeRename : beforeRename,
		onRemove : onRemove,
		onRename : onRename
	}
};
var zNodesMenu = getDepartmentData();
var log, className = "dark";
function beforeDrag(treeId, treeNodes) {
	return false;
}
function beforeEditName(treeId, treeNode) {
	return true;
}
function beforeRemove(treeId, treeNode) {
	className = (className === "dark" ? "" : "dark");
	showLog("[ " + getTime() + " beforeRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
	var zTree = $.fn.zTree.getZTreeObj("menutree");
	zTree.selectNode(treeNode);
	if (confirm("确认删除【" + treeNode.name + "】吗？")) {
		var delresult = removeDepartment(treeNode.id);
		if (delresult=="success") {
			zNodesMenu = getDepartmentData();
			initData();
		}else if(delresult=="error"){
			alert("部门下存在用户,删除失败！");
			return false;
		}else{
			return false;
		}
	} else {
		return false;
	}
}
function onRemove(e, treeId, treeNode) {
	// showLog("[ " + getTime() + " onRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " +
	// treeNode.name);
}
function beforeRename(treeId, treeNode, newName, isCancel) {
	className = (className === "dark" ? "" : "dark");
	// showLog((isCancel ? "<span style='color:red'>" : "") + "[ " + getTime() +
	// " beforeRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name + (isCancel ?
	// "</span>" : ""));
	if (newName.length == 0) {
		var zTree = $.fn.zTree.getZTreeObj("menutree");
//		alert("节点名称不能为空。");
//		setTimeout(function() {
//			zTree.editName(treeNode)
//		}, 10);
		 zTree.removeNode(treeNode);
		return false;
	} else if (newName.length > 15) {
		alert("名称不能超过15个字。");
		return false;
	} else {
		addDepartment(treeNode.id, treeNode.getParentNode().id, newName);
	}
	return true;
}
function onRename(e, treeId, treeNode, isCancel) {
}
function showRemoveBtn(treeId, treeNode) {
	if(treeNode.id=="1"){
		return false;
	}
//	return !treeNode.isParent && treeNode.name != "新奥特云视";
	return true;
}
function showRenameBtn(treeId, treeNode) {
	if(treeNode.id=="1"){
		return false;
	}
	return true;
}

function showLog(str) {
}
function getTime() {
	var now = new Date(), h = now.getHours(), m = now.getMinutes(), s = now.getSeconds(), ms = now.getMilliseconds();
	return (h + ":" + m + ":" + s + " " + ms);
}

function addHoverDom(treeId, treeNode) {
	var sObj = $("#" + treeNode.tId + "_span");
	if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0)
		return;
	var addStr = "<span class='button add' id='addBtn_" + treeNode.tId + "' title='添加' onfocus='this.blur();'></span>";
	sObj.after(addStr);
	var btn = $("#addBtn_" + treeNode.tId);
	if (btn)
		var treeNodes;
	btn.bind("click", function() {
		var zTree = $.fn.zTree.getZTreeObj("menutree");
		var nodeId = Math.uuid(32);
		treeNodes = zTree.addNodes(treeNode, {
			id : nodeId,
			pId : treeNode.id,
			name : ""
		});
		if (treeNodes) {
			zTree.editName(treeNodes[0]);
		}
		return false;
	});
};
function removeHoverDom(treeId, treeNode) {
	$("#addBtn_" + treeNode.tId).unbind().remove();
};



/**
 * 添加部门信息
 * 
 * @param newId
 * @param parentId
 * @param newName
 */
function addDepartment(newId, parentId, newName) {
	var blnAdd = false;
	$.ajax({
		type : 'POST',
		url : ctx + "/department/addDepartment/",
		data:{id:newId,pId:parentId,name:newName},
		cache : false,
		async : false,
		success : function(data) {
			if ("success" == data) {
				blnAdd = true;
			}
		}
	});
	return blnAdd;
}
/**
 * 删除信息
 * 
 * @returns success:成功
 */
function removeDepartment(removeId) {
	var blnRemove = "no";
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx + "/department/removeDepartment/?id=" + removeId,
		cache : false,
		async : false,
		dataType:"text",
		success : function(data) {
			blnRemove=data;
		}
	});
	return blnRemove;
}
//初始树
$(function() {
	initData();
});

/**
 * 获取信息
 * 
 * @returns 信息
 */
function getDepartmentData() {
	var zNodesMenu;
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
			zNodesMenu = data;
		}
	});
	return zNodesMenu;
}
function initData(){
	$.fn.zTree.init($("#menutree"), settingMenu, zNodesMenu);
	zTreeMenu = $.fn.zTree.getZTreeObj("menutree");
}

/** 异步加载数据 */
function filter(treeId, parentNode, childNodes) {
	if(!childNodes){
		return null;
	}else{
		return childNodes;
	}
}

