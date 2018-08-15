
 /**
 * 上传xml文件
 */
function ajaxFileUpload(){
	 $.Mark.show();
	 var fileNameVal = $("#file").val();
	 if(null !=fileNameVal && fileNameVal!=""){
		 if(0<fileNameVal.lastIndexOf(".")&&fileNameVal.substring(fileNameVal.lastIndexOf(".")+1)=="xml"){
			 var uri =ctx+ "/upload/?ansyType=2";
		     $.ajaxFileUpload ({
	          url:uri, //你处理上传文件的服务端
	          secureuri:false,
	          fileElementId:'file',
	          dataType: 'json',
	          success: function (data){
	        	      $.Mark.hide();
	        	      $("#file").val("");
	                  if(data == "success"){
	                	  $.scojs_message("上传成功!", $.scojs_message.TYPE_OK);
						  giveup("/department/toDepartment/");
	                  }else{
	                	  $.scojs_message("上传失败!", $.scojs_message.TYPE_ERROR);
	                  }
	                },  
	                error : function(data, status, e) {  
	                	$.scojs_message("上传失败!", $.scojs_message.TYPE_ERROR);
	                	 $.Mark.hide();
	                } 
	             })
		 }else{
			 $.scojs_message("只能上传xml文件!", $.scojs_message.TYPE_ERROR);
			 $.Mark.hide();
		 }
	 }else{
		 $.scojs_message("请选择要上传的xml文件!", $.scojs_message.TYPE_ERROR);
		 $.Mark.hide();
	 }
	
   }
   
/**
 * 切换tab
 * 
 * @param flag 切换类型
 */
function selectMyForm(flag) {
		if(flag == "update"){
			$("#flag").val("update");
		}else if(flag == "add"){
			$("#flag").val("add");
		}else if(flag == "delete"){
			$("#flag").val("delete");
		}
		$("#currentPage").val("1");
		$.Mark.show();
		var options = {
			async : false,
			success : function(data) {
				$("#divUserList").html(data);
			},
			error : function(a, b, c) {
				$.YSAjax.error(a, b, c);
			}
		};
		$.Mark.hide();
		$('#vmsform').ajaxSubmit(options);
}

/**
 * 将临时用户增加到本地
 */
function tempToLocal(){
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
			content:"您确定要导入列表中所有部门吗？",
			action:function(){
				confirm.destroy();
				importDepart(checkboxval);
			}
			
		});
		confirm.show();
		return;
	}else{//导入选中部门
		importDepart(checkboxval);
		
	}

}
/**
 * 导入部门信息至本地数据库
 * @param checkboxval
 */
function importDepart(checkboxval){
	var uri = ctx + "/department/tempToLocal/";//请求地址
	var typeval =$("#flag").val();//标识（add 添加，delete删除，update 修改）
	$.Mark.show();
	$.ajax({
		type : "POST",
		url : uri,
		cache:false,
		data:{
			"type":typeval,
			"departmentIds":checkboxval
		},
		success : function(data) {
			if(data.status == "success"){
				$.scojs_message('导入部门成功总数【'+data.successInt+'】,失败部门总数【'+data.failInt+'】', $.scojs_message.TYPE_OK);
				selectMyForm($("#flag").val());
				$.Mark.hide();
			}else{
				$.scojs_message('导入失败!', $.scojs_message.TYPE_ERROR);
				$.Mark.hide();
			}
		}
	});
}

/**
 * 上传文件触发事件
 */
function fileChangeInput(){
		$("#choiceFileId").val($("#file").val());
	}