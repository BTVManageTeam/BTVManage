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
		<span class="icon"> <i class="icon-th"></i> </span>
		<h5>添加所属商</h5>
	</div>
	<div class="widget-content tab-content">
		<div style="float: left;width: 60%">
			<form id="addUserForm" class="form-horizontal" action="${ctx}/department/addDepartment/">
				<div id="gridPager">
					<table style="width: 100%; padding: 8px; ">

						<tr>
							<td align="right"  width="20%">登录名：</td>
							<td width="80%"><input type="text" value="" name="accountName" id="accountName" datatype="loginId" nullmsg="请输入登录名" errormsg="登录名为1-20个非手机号任意字符"
								ajaxurl="<%=request.getContextPath()%>/users/checkLoginId/?operate=add" /> <span style="color: red; font-size: 16px;">*</span>
								<div class="Validform_checktip"></div>
							</td>
						</tr>
						<tr>
							<td align="right" width="20%">密码：</td>
							<td width="80%"><input type="text" value="" name="password" id="password" datatype="s1-20" errormsg="密码" /> <span
									style="color: red; font-size: 16px;">*</span>
								<div class="Validform_checktip"></div>
							</td>
						</tr>
						<tr>
							<td align="right">律所名称：</td>
							<td><input type="text" value="" name="departmentName" id="departmentName" datatype="m" errormsg="律所名称"
							ajaxurl="<%=request.getContextPath()%>/users/checkUserPhone/?operate=add" ignore="ignore" />
								<div class="Validform_checktip"></div>
							</td>
						</tr>
						<tr>
							<td align="right">地域：</td>
							<td><input type="text" value="" name="regionName" id="regionName" datatype="e" errormsg="地域" ignore="ignore" />
								<div class="Validform_checktip"></div>
							</td>
						</tr>
						<tr>
							<td align="right">角色：</td>
							<td><input type="text" value="" name="roleId" id="roleId" datatype="e" errormsg="角色" ignore="ignore" />
								<div class="Validform_checktip"></div>
							</td>
						</tr>
						<tr>
							<td align="right">律所介绍：</td>
							<td><input type="text" value="" name="departmentDescribe" id="departmentDescribe" datatype="e" errormsg="律所介绍" ignore="ignore" />
								<div class="Validform_checktip"></div>
							</td>
						</tr>

						<tr>
							<td colspan="4" align="center">&nbsp;</td>
						</tr>
						<tr>
							<td align="center" colspan="2">
								<button type="submit" class="btn btn-info" style="width: 120px; margin-right: 50px;">提交</button>
								<button type="button" onclick="giveup('/users/findall/');" class="btn" style="width: 120px; margin-left: 40px;">取消</button>
							</td>
						</tr>
					</table>
				</div>
				<input type="hidden" value="" name="departId" id="departId" />
				<input type="hidden" value="" name="ownerBusId" id="ownerBusId" />
			</form>
		</div>
		<div style="float: left;display: none;" id="depdivId">
			<ul id="menutree" class="ztree" style="height: 675px;margin:0;width: 100%;"></ul>
		</div>
	</div>
</div>
<script>
	$(function() {
		$("#addUserForm").Validform({
			tiptype : 4,
			ajaxPost : true,
			dataType : 'json',
			beforeSubmit:function(){
				var yunkeyval = $("#complistid").val();
				$("#ownerBusId").val(yunkeyval);
				return true;
			},
			datatype:{
				"loginId":function(gets,obj,curform,regxp){
					var reg1=regxp["m"],
					loginIdVal = $("#loginId").val();
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
				if (curform.responseText == "success") {
					$.scojs_message('添加用户成功!', $.scojs_message.TYPE_OK);
				} else {
					$.scojs_message('添加用户失败!', $.scojs_message.TYPE_ERROR);
				}
				giveup('/users/findall/');
			}
		});
	});
</script>