var zNodes =[];
$(function(){
	var setting = {
        view: {
            addHoverDom: addHoverDom,//用于当鼠标移动到节点上时，显示用户自定义控件，显示隐藏状态同 zTree 内部的编辑、删除按钮
            removeHoverDom: removeHoverDom,//设置鼠标移到节点上，在后面显示一个按钮
            selectedMulti: false// 禁止多点同时选中的功能
        },
		data: {
			simpleData: {
				enable: true
			}
		}
		};
	$.ajax({
		type:"POST",
		url:"menu/getMenuZtree/",
		async:false,
//		contentType:"application/json ; charset=utf-8",
		dataType:"json",
		data:"appCode="+$("#systemAppCodeId").val(),
		success:function(data){
			if(data.code == 0){
				zNodes = data.data;
			}
		}
	});
	zTreeObj = $.fn.zTree.init($("#menuTreeMty"), setting, zNodes);
	
	
	$("#menuFormId").Validform({
		tiptype : 4,
		ajaxPost : true,
		beforeSubmit : function(curform) {
			saveMenu();
			return false;
		}
	});
});
function addHoverDom(treeId, treeNode) {
    var sObj = $("#" + treeNode.tId + "_span");
    //删除子节点
    if(!treeNode.isParent && $("#removeBtn_" + treeNode.tId).length == 0){
    	var addStr2 = "<span class='button remove' id='removeBtn_"
			+ treeNode.tId
			+ "' title='remove node' onclick=\"deltreeNode('"+treeNode.name+"','"+treeNode.id+"');\"></span>";
	sObj.after(addStr2);
    }
    //更新节点
    if (treeNode.id != 1 && $("#editBtn_" + treeNode.tId).length == 0) {
    	var addStr1 = "<span class='button edit' id='editBtn_" + treeNode.tId
		+ "' title='edit node' ></span>";
    	sObj.after(addStr1);
    	var btn = $("#editBtn_" + treeNode.tId);
    	if (btn) btn.bind("click", function(){
    		initValues();
	    	var treeObj = $.fn.zTree.getZTreeObj("menuTreeMty");
	    	var node = treeObj.getNodeByParam("id", treeNode.pId, null);
	    	$("#parentName").val(node.name);
	    	$("#nid").val(treeNode.id);
	    	$.ajax({
	    		type:"POST",
	    		url:"menu/findMenuById/",
	    		async:false,
	    		contentType:"application/json ; charset=utf-8",
	    		dataType:"json",
	    		data:'{"id":"'+treeNode.id+'"}',
	    		success:function(data){
	    			if(data.code == 0){
	    				var menu = data.data;
	    				var isDefault = menu.isDefault;//是否是默认创建（1：默认，0：非默认）
	    				$("#name").val(menu.name);
	    				if(menu.menuName == undefined){
	    					$("#menuName").val("");
	    				}else{
	    					$("#menuName").val(menu.menuName);
	    				}
	    				$("input[type=radio][name='isDefault'][value='"+isDefault+"']").prop("checked","checked");
	    				$("#menuUrl").val(menu.url);
	    				$("#menuIcon").val(menu.icon);
	    				$("#memo").val(menu.memo);
	    				$("#extended1").val(menu.extended1);
	    				$("#extended2").val(menu.extended2);
	    				$("#menuFormDivId").css("display","block");
	    			}
	    		}
	    	});
	    });
    }
    //新增
    if (treeNode.isParent && $("#addBtn_" + treeNode.tId).length == 0) {
    	var addStr3 = "<span class='button add' style='margin-right:2px; background-position:-144px -0px; vertical-align:top; *vertical-align:middle' id='addBtn_"+
    	treeNode.tId + "' title='add node' ></span>";
    	sObj.after(addStr3);
    	var btn = $("#addBtn_" + treeNode.tId);
	    if (btn) btn.bind("click", function(){
	    	initValues();
	    	$("#menuFormDivId").css("display","block");
	    	$("#parentName").val(treeNode.name);
	    });
    }
    if (!treeNode.isParent && treeNode.pId == 1 && $("#addBtn_" + treeNode.tId).length == 0) {
    	var addStr3 = "<span class='button add' style='margin-right:2px; background-position:-144px -0px; vertical-align:top; *vertical-align:middle' id='addBtn_"+
    	treeNode.tId + "' title='add node' ></span>";
    	sObj.after(addStr3);
    	var btn = $("#addBtn_" + treeNode.tId);
	    if (btn) btn.bind("click", function(){
	    	initValues();
	    	$("#menuFormDivId").css("display","block");
	    	$("#parentName").val(treeNode.name);
	    });
    }
};
//设置鼠标移到节点上，在后面显示一个按钮
function removeHoverDom(treeId, treeNode) {
    $("#removeBtn_" + treeNode.tId).unbind().remove();
    $("#editBtn_" + treeNode.tId).unbind().remove();
    $("#addBtn_" + treeNode.tId).unbind().remove();
};

/**
 * 保存菜单
 */
function saveMenu(){
	
	var zTree = $.fn.zTree.getZTreeObj("menuTreeMty");
	var parentNode = zTree.getSelectedNodes()[0];
	var jsonStr = "{}";
	var jsonObj = JSON.parse(jsonStr);
	var nameVal = $("#name").val();//菜单名称
	var menuNameAliasVal = $("#menuName").val();//菜单别名
	var menuUrl = $("#menuUrl").val();//菜单地址
	var extended1 = $("#extended1").val();//审核级别
	var extended2 = $("#extended2").val();//审核按钮信息
	var menuIcon = $("#menuIcon").val();//菜单图标
	var memo = $("#memo").val();//菜单描述
	var nid = $("#nid").val();//更新菜单项的ID，新增不传值
	var isDefaultVal = $("input[name=isDefault]:checked").val();//是否默认（1：默认，0：非默认）
	
	jsonObj["name"] = nameVal;
	jsonObj["menuName"] = menuNameAliasVal;
	jsonObj["url"] = menuUrl;
	jsonObj["extended1"] = extended1;
	jsonObj["extended2"] = extended2;
	jsonObj["icon"] = menuIcon;
	jsonObj["memo"] = memo;
	jsonObj["isDefault"] = isDefaultVal;
	
	if(nid != ""){
		jsonObj["id"] = parentNode.id;
		jsonStr = JSON.stringify(jsonObj);
		$.ajax({
			type:"POST",
			url:"menu/updateMenu/",
			async:false,
			contentType:"application/json ; charset=utf-8",
			dataType:"json",
			data:jsonStr,
			success:function(data){
				if(data.code == 0){
					$("#menuFormDivId").css("display","none");
					if(menuNameAliasVal != ""){
						parentNode.name = menuNameAliasVal;
					}else{
						parentNode.name = nameVal;
					}
					zTree.updateNode(parentNode);
					$.scojs_message("修改成功!", $.scojs_message.TYPE_OK);
				}else{
					$.scojs_message("修改成功!", $.scojs_message.TYPE_ERROR);
				}
			}
		});
	}else{
		var appCodeVal = $("#systemAppCodeId").val();
		jsonObj["pid"] = parentNode.id;
		jsonObj["appCode"] = appCodeVal ;
		jsonStr = JSON.stringify(jsonObj);
		$.ajax({
			type:"POST",
			url:"menu/saveMenu/",
			async:false,
			contentType:"application/json ; charset=utf-8",
			dataType:"json",
			data:jsonStr,
			success:function(data){
				if(data.code == 0){
					$("#menuFormDivId").css("display","none");
					if(menuNameAliasVal != ""){
						zTree.addNodes(parentNode, { id: data.data, pId: parentNode.id, name: menuNameAliasVal });
					}else{
						zTree.addNodes(parentNode, { id: data.data, pId: parentNode.id, name: nameVal });
					}
					var newNode = zTree.getNodeByParam("id",data.data, null);
					zTree.selectNode(newNode);
					$.scojs_message("添加成功!", $.scojs_message.TYPE_OK);
				}else{
					$.scojs_message("添加失败!", $.scojs_message.TYPE_ERROR);
				}
			}
		});
	}
	
}
/**
 * 删除菜单项
 */
function deltreeNode(msg,id){
	$("#menuFormDivId").css("display","none");
	var confirm = $.scojs_confirm({
		content:"您确定要删除菜单项【"+msg+"】吗？",
		action:function(){
			confirm.destroy();
			$.ajax({
				type:"POST",
				url:"menu/delMenu/",
				async:false,
				dataType:"json",
				data:"menuId="+id,
				success : function(data) {
					if(data.code == 0){
						var zTree = $.fn.zTree.getZTreeObj("menuTreeMty");
						var parentNode = zTree.getNodeByParam("id", id, null);
						zTree.removeNode(parentNode);
						$.scojs_message("删除成功!", $.scojs_message.TYPE_OK);
					}else{
						$.scojs_message("删除失败!", $.scojs_message.TYPE_ERROR);
					}
				}
			});
		}
	});
	confirm.show();
}
function initValues(){
	$("#name").val("");//菜单名称
	$("#menuName").val("");//菜单别名
	$("#menuUrl").val("");//菜单地址
	$("#memo").val("");//菜单备注
	$("#menuIcon").val("");//菜单图标
	$("#nid").val("");
	$("#extended1").val("");//审核级别
	$("#extended2").val("");//审核按钮信息
}