var zTreeObj="";
$(function(){
	
	/**
	 * 加载菜单
	 */
	loadTreeData();
	
	/**
	 * 动态选中
	 */
	checkNodes();
});

/**
 * 更改选中的所属商
 */
function changeOwnerBus(){
	loadTreeData();
	checkNodes();
}

/**
 * 加载菜单
 */
function loadTreeData(){
	var setting = {
	        view: {
	            selectedMulti: false// 禁止多点同时选中的功能
	        },
	        check: {
	            enable: true
	        },
			data: {
				simpleData: {
					enable: true
				}
			}
		};
	
	var appCode =$("#systemAppCodeId option:selected").val();
	var ownerBusCode =$("#ownerBusCode option:selected").val();
	$.ajax({
		type:"POST",
		url:"menu/getRoleMenuZtree/",
		async:false,
		dataType:"json",
		data:"appCode="+appCode+"&ownerBusCode="+ownerBusCode,
		success:function(data){
			if(data.code == 0){
				zNodes = data.data;
			}
		}
	});
	zTreeObj = $.fn.zTree.init($("#menuTreeMty"), setting, zNodes);
}

/**
 * 动态选中
 */
function checkNodes(){
	 //获取选中所属商标识
    var selOwnerBusCode = $("#ownerBusCode option:selected").val();
	//系统标识
    var systemAppCode = $("#systemAppCodeId option:selected").val();
    //角色标识
    var roleCode = $("#roleCodeId").val();
    var jsonObj = {
            "ownerBusCode": selOwnerBusCode,
            "systemAppCode": systemAppCode,
            "roleCode": roleCode
        };
    var jsonStr = jsonStr = JSON.stringify(jsonObj);;
    var checkStr ="";
    $.ajax({
        type: 'POST',
        cache: false,
        async:false,
        url: ctx + "/role/selectCheckRoleMenu/",
        contentType:"application/json ; charset=utf-8",
        data: jsonStr,
        dataType:"json",
        success: function (data) {
                if (data.code == 0) {
                	/**选中菜单的ID集合*/
                	var checkArray=[];
                    var checkData = data.data;
                    if(null != checkData && checkData.length>0){
                    	$(checkData).each(function(checkKey,checkVal){
                    		var menuId = checkVal.selectMenuId;
                    		checkArray.push(menuId);
                    	})
                    	checkStr = checkArray.join(",");
                    }
                }
            },
        error: function (a, b, c) {
        	 $.scojs_message('系统内部异常!', $.scojs_message.TYPE_ERROR);
        }
    });
    
	var treeObj = $.fn.zTree.getZTreeObj("menuTreeMty");
	var nodes = treeObj.transformToArray(treeObj.getNodes());
	if(null !=nodes && nodes.length>0){
		$.each(nodes,function(k,v){
			var nodeId = v.id;
			if(checkStr.indexOf(nodeId)!=-1){
				var node = treeObj.getNodeByParam("id", nodeId);//根据ID找到该节点
				treeObj.checkNode(node, true,false);
			}
		})
	}
    
}


/**
 * 给角色，所属商，应用，菜单绑定关系
 *
 */
function saveBindOwnerBus() {
    var systems = $("#listOwnerBusinessLen").val();
    if (systems <= 0) {
        $.scojs_message('请先创建所属商!', $.scojs_message.TYPE_ERROR);
        return;
    }
    //获取选中所属商标识
    var selOwnerBusCode = $("#ownerBusCode option:selected").val();

    //获取选中菜单节点
    var zTreeOjb = $.fn.zTree.getZTreeObj("menuTreeMty");
    var selectedNodes = zTreeObj.getCheckedNodes(true);
    if (0 === selectedNodes.length) {
        $.scojs_message('请先选择菜单项!', $.scojs_message.TYPE_ERROR);
        return;
    }
    var nodeArray = [];
    for (var i = 0; i < selectedNodes.length; i++) {
    	var selId = selectedNodes[i].id;
    	if(selId != 1){
    		nodeArray.push(selectedNodes[i].id);
    	}
    }
    
    if(nodeArray.length<=0){
    	$.scojs_message('请先配置菜单项!', $.scojs_message.TYPE_ERROR);
    	return;
    }
    
    var nodeIds = nodeArray.join(",");
    var roleCode = $("#roleCodeId").val();
    //系统标识
    var systemAppCode = $("#systemAppCodeId option:selected").val();
    var jsonObj = {
            "ownerBusCode": selOwnerBusCode,
            "systemAppCode": systemAppCode,
            "roleCode": roleCode,
            "selectMenuId": nodeIds
        };
    var jsonStr = jsonStr = JSON.stringify(jsonObj);;
    
    $.ajax({
        type: 'POST',
        cache: false,
        url: ctx + "/role/insertOwnerBusRole/",
        contentType:"application/json ; charset=utf-8",
        data: jsonStr,
        dataType:"json",
        success: function (data) {
            if (data.code == 0) {
                $.scojs_message('绑定成功!', $.scojs_message.TYPE_OK);
            }

        },
        error: function (a, b, c) {
        	 $.scojs_message('系统内部异常!', $.scojs_message.TYPE_ERROR);
        }
    });
}
