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

<script src="<%=request.getContextPath()%>/js/viewJs/lawyer/lawyer.js"></script>
<div class="widget-box112">
	<div class="widget-title">
		<span class="icon"> <i class="icon-th"></i> </span>
		<h5>修改律师</h5>
	</div>
	<div class="widget-content tab-content">
		<div style="float: left;width: 60%">
			<form id="updateLawyerForm" class="form-horizontal" action="${ctx}/lawyer/updateLawyer/">
				<div id="gridPager">
					<table style="width: 100%; padding: 8px; ">
						<tr>
							<td align="right"  width="20%">登录名：</td>
							<td width="80%">
								<label>${user.accountName}</label>
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
							<td align="right">律师名称：</td>
							<td><input type="text" value="${lawyer.lawyerName}" name="lawyerName" id="lawyerName" datatype="s1-20" errormsg="请输入律师名称" />
								<div class="Validform_checktip"></div>
							</td>
						</tr>
						<tr>
							<td align="right">职业年份：</td>
							<td><input type="text" value="${lawyer.professionalYear}" name="professionalYear" id="professionalYear" datatype="s1-20" errormsg="请输入职业年份" />
								<div class="Validform_checktip"></div>
							</td>
						</tr>
						<tr>
							<td align="right">职业专长：</td>
							<td><input type="text" value="${lawyer.speciality}" name="speciality" id="speciality" datatype="s1-20" errormsg="请输入职业专长" />
								<div class="Validform_checktip"></div>
							</td>
						</tr>
						<tr>
							<td align="right">地域：</td>
							<td>
								<input type="text" value="${lawyer.region}" name="region" id="region" datatype="s1-20" errormsg="请输入地域" />
								<span style="color: red; font-size: 16px;">*</span>
								<div class="Validform_checktip"></div>
							</td>
						</tr>
						<tr>
							<td align="right">律师名称：</td>
							<td>
								<select style="width: 215px;" name="departmentId" id="departmentId">
									<c:forEach items="${deptLists}" var="dept">
										<option value="${dept.departmentId}" <c:if test="${lawyer.departmentId eq dept.departmentId }">selected="selected" </c:if>>${dept.departmentName}</option>
									</c:forEach>
								</select>
								<span style="color: red; font-size: 16px;">*</span>
								<div class="Validform_checktip"></div>
							</td>
						</tr>
						<tr>
							<td align="right">律师介绍：</td>
							<td colspan="3"><textarea style="width: 78%; height: 80px;" name="introduce" id="introduce" datatype="*1-200" ignore="ignore"
											errormsg="律师介绍最多200字符">${lawyer.introduce}</textarea>
							</td>
						</tr>

						<tr>
							<td colspan="4" align="center">&nbsp;</td>
						</tr>
						<tr>
							<td align="center" colspan="2">
								<button type="submit" class="btn btn-info" style="width: 120px; margin-right: 50px;">提交</button>
								<button type="button" onclick="giveup('/lawyer/findall/');" class="btn" style="width: 120px; margin-left: 40px;">取消</button>
							</td>
						</tr>
					</table>
				</div>

				<input type="hidden" name="userId" value="${user.userId}">
				<input type="hidden" name="lawyerId" value="${lawyer.lawyerId}">
			</form>
		</div>
		<div style="float: left;display: none;" id="depdivId">
			<ul id="menutree" class="ztree" style="height: 675px;margin:0;width: 100%;"></ul>
		</div>
	</div>
</div>
<script>
	$(function() {
		$("#updateLawyerForm").Validform({
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
					$.scojs_message('更新律师成功!', $.scojs_message.TYPE_OK);
				} else {
					$.scojs_message('更新律师失败!', $.scojs_message.TYPE_ERROR);
				}
				giveup('/lawyer/findall/');
			}
		});
	});
</script>