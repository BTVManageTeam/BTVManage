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
</style>

<script src="<%=request.getContextPath()%>/js/business/users.js"></script>
<script src="<%=request.getContextPath()%>/js/business/choiceDepartment.js"></script>
<div class="widget-box112">
	<div class="widget-title">
		<span class="icon"> <i class="icon-th"></i> </span>
		<h5>修改用户</h5>
	</div>
	<div class="widget-content tab-content">
		<%--
		开始添加的内容
		--%>
		<div style="float: left;width: 60%">
			<form id="updateUsersForm" class="form-horizontal">
				<input type="hidden" id="id" name="userId" value="${opRmsUser.userId }" />
				<div id="gridPager">
					<table style="width: 100%; padding: 8px; ">
						<tr>
							<td align="right" width="20%">用户名：</td>
							<td width="80%"><input type="text" value="${opRmsUser.userName }" name="userName" id="userName" datatype="s1-20" errormsg="用户名为1-20个字符(下划线、空格、数字、字母和汉字)" /> <span
								style="color: red; font-size: 16px;">*</span>
								<div class="Validform_checktip"></div>
							</td>
						</tr>
						<tr>
							<td align="right" width="20%">登录名：</td>
							<td width="80%"><input type="text" value="${opRmsUser.loginId }" name="loginId" id="loginIdInput" datatype="loginId" nullmsg="请输入登录名" errormsg="登录名为1-20个非手机号任意字符"
								ajaxurl="<%=request.getContextPath()%>/users/checkLoginId/?operate=update&userId=${opRmsUser.userId}" /> <span
								style="color: red; font-size: 16px;">*</span>
								<div class="Validform_checktip"></div>
							</td>
						</tr>
						<tr>
							<td align="right">所属部门：</td>
							<td><input type="text" value="${departName }" name="departName" id="departNameId" onfocus="initTree();" readonly="readonly"
								datatype="*1-30" nullmsg="请选择所属部门" errormsg="请选择所属部门" /><span style="color: red; font-size: 16px;">*</span>
								<div class="Validform_checktip"></div>
							</td>
						</tr>
						<tr style="display: none;">
							<td align="right">所属商：</td>
							<td><select name="ownerBusCode" datatype="*1-100" style="width: 215px;">
									<option value="">请选择</option>
									<c:if test="${null !=listOwnerBus }">
										<c:forEach items="${listOwnerBus }" var="ownbus">
											<option value="${ownbus.ownerBusCode }" <c:if test="${opRmsUser.ownerBusCode==ownbus.ownerBusCode }">selected="selected"</c:if>>${ownbus.ownerBusName
												}</option>
										</c:forEach>
									</c:if>
							</select><span style="color: red; font-size: 16px;">*</span>
								<div class="Validform_checktip"></div>
							</td>
						</tr>
						<%-- <tr>
							<td align="right">VIP：</td>
							<td>
								<select name="vip" style="width: 215px;" id="vipId">
									<option value="0" <c:if test="${opRmsUser.vip==0 }">selected="selected"</c:if>>否</option>
									<option value="1" <c:if test="${opRmsUser.vip==1 }">selected="selected"</c:if>>是</option>
								</select>
								<div class="Validform_checktip"></div>
							</td>
						</tr> --%>
						<tr>
							<td align="right">电话：</td>
							<td><input type="text" value="${opRmsUser.userPhone }" name="userPhone" id="userPhone" datatype="m" errormsg="请输入正确的手机格式" 
							ajaxurl="<%=request.getContextPath()%>/users/checkUserPhone/?operate=update&userId=${opRmsUser.userId}" ignore="ignore" />
								<div class="Validform_checktip"></div>
							</td>
						</tr>
						<tr>
							<td align="right">邮箱：</td>
							<td><input type="text" value="${opRmsUser.userEmail }" name="userEmail" id="userEmail" datatype="e" errormsg="请输入正确的邮箱格式" ignore="ignore" />
								<div class="Validform_checktip"></div>
							</td>
						</tr>
						<tr>
							<td align="right">地址：</td>
							<td><input type="text" value="${opRmsUser.userAddress }" name="userAddress" id="userAddress" datatype="*1-100" errormsg="地址为1-100个字符"
								ignore="ignore" />
								<div class="Validform_checktip"></div>
							</td>
						</tr>
						<tr>
							<td align="right">QQ：</td>
							<td><input type="text" value="${opRmsUser.userqq }" name="userqq" id="userqq" datatype="n6-12" errormsg="QQ为6-12个数字" ignore="ignore" />
								<div class="Validform_checktip"></div>
							</td>
						</tr>
						<tr>
							<td align="right">备注：</td>
							<td colspan="3"><textarea style="width: 78%; height: 80px;" name="userDesc" id="userDesc">${opRmsUser.userDesc }</textarea>
							</td>
						</tr>
						<tr>
							<td colspan="4" align="center">&nbsp;</td>
						</tr>
						<tr>
							<td colspan="2" align="center">
								<button type="submit" class="btn btn-info" style="width: 120px; margin-right: 50px;">提交</button>
								<button type="button" onclick="javascript:$('#content').css('display','block');$('#contenttwo').remove();" class="btn " style="width: 120px; margin-left: 40px;">取消</button>
							</td>
						</tr>

					</table>
				</div>
				<input type="hidden" value="${departId }" name="departId" id="departId" />
			</form>
		</div>
		<div style="float: left;display: none;" id="depdivId">
			<ul id="menutree" class="ztree" style="height: 675px;margin:0;width: 100%;"></ul>
		</div>
		<%--
		结束
	--%>
	</div>
</div>

<script>
	$(function() {
		$("#updateUsersForm").Validform({
			tiptype : 4,
			ajaxPost : true,
			datatype:{
				"loginId":function(gets,obj,curform,regxp){
					var reg1=regxp["m"],
					loginIdVal = $("#loginIdInput").val();
					if(null ==loginIdVal || typeof(loginIdVal) == "undefined" || "" == loginIdVal.trim() ||  20 < loginIdVal.trim().length){
						return false;
					}
				    if(reg1.test(loginIdVal)){
				    	return false;
				    }
					return true;
				}	
			},
			beforeSubmit : function(curform) {
				updateUser();
				return false;
			}
		});
	});
</script>
