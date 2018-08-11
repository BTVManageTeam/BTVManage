var setting = {
	view : {
		addHoverDom : addHoverDom,
		removeHoverDom : removeHoverDom,
		selectedMulti : false
	},
	edit : {
		removeTitle : "删除",
		renameTitle : "编辑",
		enable : true,
		showRenameBtn:showRenameBtn,
		showRemoveBtn:showRemoveBtn
	},
	data : {
		simpleData : {
			enable : true,
			idKey : "id",
			pIdKey : "pid"
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

var zNodes = getDepartmentData();
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
	var zTree = $.fn.zTree.getZTreeObj("treeDepartment");
	zTree.selectNode(treeNode);
	if (confirm("确认删除【" + treeNode.name + "】吗？")) {
		var delresult = removeDepartment(treeNode.id);
		if (delresult=="success") {
			zNodes = getDepartmentData();
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
		var zTree = $.fn.zTree.getZTreeObj("treeDepartment");
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
		var ckval = checkDept(treeNode.id,newName);
		if(ckval){
			addDepartment(treeNode.id, treeNode.getParentNode().id, newName);
		}else{
			alert("部门名称已存在！")
			return false;
		}
	}
	return true;
}
function onRename(e, treeId, treeNode, isCancel) {
}
function showRemoveBtn(treeId, treeNode) {
	if(treeNode.id=="1"){
		return false;
	}
	return !treeNode.isParent && treeNode.name != "新奥特云视";
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
		var zTree = $.fn.zTree.getZTreeObj("treeDepartment");
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
function selectAll() {
	var zTree = $.fn.zTree.getZTreeObj("treeDepartment");
	zTree.setting.edit.editNameSelectAll = $("#selectAll").attr("checked");
}

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
/***校验操作*/
function checkDept(newId,newName){
	var ckflag = false;
	$.ajax({
		type : 'GET',
		url : ctx + "/department/checkDept/?id=" + newId +"&name=" + newName,
		cache : false,
		async : false,
		dataType:"text",
		success : function(data) {
			 if(data=="y"){
				 ckflag=true;
			 }
		},error:function(){
			alert("失败");
		}
	});
	return ckflag;
}

/**
 * 获取信息
 * 
 * @returns 信息
 */
function getDepartmentData() {
	var departmentData;
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		dataType : "json",
		url : ctx + "/department/findallDepartments/",// 请求的action路径
		error : function() {// 请求失败处理函数
			alert('请求失败');
		},
		success : function(data) { // 请求成功后处理函数
			departmentData = data;
		}
	});
	return departmentData;
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

$(document).ready(function() {
	initData();
});

function initData() {
	$.fn.zTree.init($("#treeDepartment"), setting, zNodes);
	var treeObj = $.fn.zTree.getZTreeObj("treeDepartment");
	treeObj.expandAll(true);
}
