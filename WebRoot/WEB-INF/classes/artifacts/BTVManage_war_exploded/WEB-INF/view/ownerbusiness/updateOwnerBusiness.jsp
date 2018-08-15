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

<div class="widget-box112">
	<div class="widget-title">
		<span class="icon"> <i class="icon-th"></i> </span>
		<h5>
			修改所属商
		</h5>
	</div>
	<div class="widget-content tab-content">
	<%--
		开始添加的内容
		--%>
		<form id="updateOwnerBusForm" class="form-horizontal" action="${ctx}/ownerbusiness/updateOwnerBusiness/">
		<input type="hidden" name="ownerBusId" value="${opRmsOwnerBusiness.ownerBusId }"/>
			<div id="gridPager">
				<table style="width: 100%; padding: 8px; ">
					<tr>
						<td align="right">
							所属商名称：
						</td>
						<td>
							<input type="text" name="ownerBusName" id="ownerBusName"  datatype="s1-20" errormsg="所属商名称为1-20个字符" value="${opRmsOwnerBusiness.ownerBusName }"
								nullmsg="请输入所属商名称" />
							<span style="color: red; font-size: 16px;">*</span>
							<div class="Validform_checktip"></div>
						</td>
					</tr>
					<tr>
						<td align="right">
							所属商标识：
						</td>
						<td>
							<input type="text" name="ownerBusCode" id="ownerBusCode"  datatype="*1-20" errormsg="所属商标识为1-20个字符" value="${opRmsOwnerBusiness.ownerBusCode }"
								nullmsg="请输入所属商标识" ajaxurl="<%=request.getContextPath()%>/ownerbusiness/checkBusCode/?operate=update&ownerBusCode=${opRmsOwnerBusiness.ownerBusCode }"/>
						<span style="color: red; font-size: 16px;">*</span>
						<div class="Validform_checktip"></div>
						</td>
					</tr>
					<tr>
						<td align="right">
							客户名：
						</td>
						<td>
							<input type="text" name="customerName" id="customerName"  value="${opRmsOwnerBusiness.customerName }" datatype="s1-20" errormsg="客户名为1-20个字符" />
							<span style="color: red; font-size: 16px;">*</span>
							<div class="Validform_checktip"></div>
						</td>
					</tr>
					<tr>
						<td align="right">
							客户手机号：
						</td>
						<td>
							<input type="text" name="mobilePhone" id="mobilePhone"  value="${opRmsOwnerBusiness.mobilePhone }" datatype="m" errormsg="请输入有效手机号码" />
							<span style="color: red; font-size: 16px;">*</span>
							<div class="Validform_checktip"></div>
						</td>
					</tr>
					<tr>
						<td align="right">
							最大用户数：
						</td>
						<td>
							<input type="text"  name="maxUserNum" id="maxUserNum"  value="${opRmsOwnerBusiness.maxUserNum }" datatype="n" errormsg="最大用户数为1-8位有效数字" />
							<span style="color: red; font-size: 16px;">*</span>
							<div class="Validform_checktip"></div>
						</td>
					</tr>
					<tr>
						<td align="right">
							标签：
						</td>
						<td >
							<input type="text" name="busTag" id="busTag" value="${opRmsOwnerBusiness.busTag }" ignore="ignore" datatype="s0-10" errormsg="标签为1-10个字符" 
							ajaxurl="<%=request.getContextPath()%>/ownerbusiness/checkTag/?operate=update&busTag=${opRmsOwnerBusiness.busTag }"/>
							<div class="Validform_checktip"></div>
						</td>
					</tr>
					<tr>
						<td  align="right">
							租户组：
						</td>
						<td >
							<select style="width: 215px;" name="ownerRangeId" id="ownerRangeId"  datatype="*1-40" nullmsg="请选择租户组" errormsg="请选择租户组" >
								<option value="">请选择</option>
								<c:forEach items="${listRanges}" var="rang">
									<option value="${rang.rangeId}" <c:if test="${opRmsOwnerBusiness.ownerRangeId eq rang.rangeId}">selected="selected"</c:if>>${rang.rangeName}</option>
								</c:forEach>
							</select>
							<span style="color: red; font-size: 16px;">*</span>
							<div class="Validform_checktip"></div>
						</td>
					</tr>
					<tr>
						<td colspan="4" align="center">
							&nbsp;
						</td>
					</tr>
					<tr>
						<td align="center" colspan="2">
							<button type="submit"  class="btn btn-info" style="width: 120px; margin-right: 50px;">
								提交
							</button>
							<button type="button" onclick="giveup('/ownerbusiness/findallOwnerBusiness/');" class="btn" style="width: 120px; margin-left: 40px;">
								取消
							</button>
						</td>
					</tr>
				</table>
			</div>
		</form>

		<%--
		结束
	--%>
	 </div>
</div>

<script>
	$(function() {
		$("#updateOwnerBusForm").Validform({
			tiptype : 4,
			ajaxPost : true,
			dataType:'json',
		 	callback : function(curform) {
			  if(curform.responseText=="success"){
					$.scojs_message('修改所属商成功!', $.scojs_message.TYPE_OK);
				}else{
					$.scojs_message('修改所属商失败!', $.scojs_message.TYPE_ERROR);
				}
				loadComps();//刷新主页顶部所属商列表
				giveup('/ownerbusiness/findallOwnerBusiness/');
			}	
		});
	});
	
</script>
