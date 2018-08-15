<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/common/taglibs.jsp"%>
<style type="text/css">
.editLength {
	width: 205px;
}

tr:HOVER {
	background-color: transparent;
}

tr{
	line-height: 20px;
}
</style>

<script src="<%=request.getContextPath()%>/js/viewJs/department/department.js"></script>
<div class="widget-box112">
	<div class="widget-title">
		<span class="icon"> <img src="../../../images/backBtn.svg" alt="img"></span>
		<h5>添加律所</h5>
	</div>
	<div class="widget-content tab-content">
		<div style="float: left;width: 60%">
			<form id="addDepartmentForm" class="form-horizontal" action="${ctx}/department/addDepartment/">
				<div id="gridPager">
					<table style="width: 100%; padding: 8px; ">
						<tr>
							<td align="right"  width="20%">登录名：</td>
							<td width="80%"><input type="text" value="" name="accountName" id="accountName" datatype="accountName" nullmsg="请输入登录名" errormsg="登录名为1-20个非手机号任意字符"
								ajaxurl="<%=request.getContextPath()%>/department/checkAccountName/?operate=add" /> <span style="color: red; font-size: 16px;">*</span>
								<div class="Validform_checktip"></div>
							</td>
						</tr>
						<tr>
							<td align="right" width="20%">密码：</td>
							<td width="80%"><input type="password" value="" name="password" id="password" datatype="s1-20" errormsg="密码" /> <span
									style="color: red; font-size: 16px;">*</span>
								<div class="Validform_checktip"></div>
							</td>
						</tr>
						<tr>
							<td align="right">律所名称：</td>
							<td><input type="text" value="" name="departmentName" id="departmentName" datatype="s1-20" errormsg="请输入律所名称" />
								<div class="Validform_checktip"></div>
							</td>
						</tr>
						<tr>
							<td align="right">地域：</td>
							<td>
								<select style="width: 215px;" name="regionName" id="regionName">
									<option value="东城区">东城区</option>
									<option value="西城区">西城区</option>
									<option value="朝阳区">朝阳区</option>
									<option value="崇文区">崇文区</option>
									<option value="海淀区">海淀区</option>
									<option value="宣武区">宣武区</option>
									<option value="丰台区">丰台区</option>
									<option value="房山区">房山区</option>
									<option value="大兴区">大兴区</option>
									<option value="通州区">通州区</option>
									<option value="顺义区">顺义区</option>
									<option value="平谷区">平谷区</option>
									<option value="昌平区">昌平区</option>
									<option value="怀柔区">怀柔区</option>
									<option value="延庆县">延庆县</option>
									<option value="密云县">密云县</option>
									<option value="石景山区">石景山区</option>
									<option value="门头沟区">门头沟区</option>
								</select>
								<span style="color: red; font-size: 16px;">*</span>
								<div class="Validform_checktip"></div>
							</td>
						</tr>
						<tr>
							<td align="right">律所介绍：</td>
							<td colspan="3"><textarea style="width: 78%; height: 80px;" name="departmentDescribe" id="departmentDescribe" datatype="*1-200" ignore="ignore"
											errormsg="律所介绍最多200字符"></textarea>
							</td>
						</tr>

						<tr>
							<td colspan="4" align="center">&nbsp;</td>
						</tr>
						<tr>
							<td align="center" colspan="2">
								<button type="submit" class="btn btn-info" style="width: 120px; margin-right: 50px;">提交</button>
								<button type="button" onclick="giveup('/department/findall/');" class="btn" style="width: 120px; margin-left: 40px;">取消</button>
							</td>
						</tr>
					</table>
				</div>
			</form>
		</div>
		<div style="float: left;display: none;" id="depdivId">
			<ul id="menutree" class="ztree" style="height: 675px;margin:0;width: 100%;"></ul>
		</div>
	</div>
</div>
<script>
	$(function() {
		$("#addDepartmentForm").Validform({
			tiptype : 4,
			ajaxPost : true,
			dataType : 'json',
			beforeSubmit:function(){
				return true;
			},
			datatype:{
				"accountName":function(gets,obj,curform,regxp){
					var reg1=regxp["m"],
					loginIdVal = $("#accountName").val();
					if(null ==loginIdVal || typeof(loginIdVal) == "undefined" || "" == loginIdVal.trim() ||  20 < loginIdVal.trim().length){
						return false;
					}
				    if(reg1.test(loginIdVal)){
				    	return false;
				    }
					return true;
				}	
			},
			callback : function(curform) {
				console.log(curform);
				if (curform.responseText == "success") {
					$.scojs_message('添加律所成功!', $.scojs_message.TYPE_OK);
				} else {
					$.scojs_message('添加律所失败!', $.scojs_message.TYPE_ERROR);
				}
				giveup('/department/findall/');
			}
		});
	});
</script>