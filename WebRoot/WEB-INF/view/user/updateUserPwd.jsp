<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/common/taglibs.jsp"%>
<style>
tr:HOVER {
	background-color: transparent;
}
</style>
<div class="widget-box112" >
	<div class="widget-title">
		<span class="icon"><i class="icon-th"></i> </span>
		<h5>修改密码</h5>
	</div>
	<div class="live_list_dialog" style="padding-left: 200px">
		<form id="addform" class="form-horizontal" action="${ctx}/users/updatepwd/">
			<input type="hidden" id="userId" name="userId" value="${opRmsUser.userId}" />
			<div class="control-group">
				<label class="control-label">新密码</label>
				<div class="controls">
					<input type="password" value="" id="password" name="password" datatype="*6-18" 
						errormsg="密码为6-16个任意字符" nullmsg="请输入新密码"/>
					<span style="color: red; font-size: 16px;">*</span>
					<div class="Validform_checktip"></div>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">确认密码</label>
				<div class="controls">
					<input type="password" value="" id="repassword" name="repassword" datatype="*6-18" 
						recheck="password" errormsg="两次输入的密码不一致！" nullmsg="请您再输入一次密码！"/>
					<span style="color: red; font-size: 16px;">*</span>
					<div class="Validform_checktip"></div>
				</div>
			</div>
			<div class="control-group " > 
				<div class="controls" style="padding-right: 230px">
					<button  type="submit" class="btn btn-info"
								style="width: 120px; margin-right: 50px;">
								提交
							</button>
					<button type="button" onclick="giveup('/users/findall/');"
								class="btn " style="width: 120px; margin-left: 40px;">
								取消
							</button>
				</div>
			</div>
		</form>
	</div>
</div>
<script>
/** *********添加单位Validform****** */

$(document).ready(function() {
		$("#addform").Validform({
			tiptype : 4,
			ajaxPost : true,
			dataType:'json',
		    callback : function(data) {
				if(data.responseText=="success"){
					$.scojs_message('修改密码成功!', $.scojs_message.TYPE_OK);
				}else{
					$.scojs_message('修改密码失败!', $.scojs_message.TYPE_ERROR);
				}
// 				giveup('/users/findall/');
				/**修改后隐藏当前页面，返回主界面，并刷新用户列表*/
				$('#content').css('display','block');
				$('#contenttwo').remove();
				selectform();
			}
		});
});
</script>
