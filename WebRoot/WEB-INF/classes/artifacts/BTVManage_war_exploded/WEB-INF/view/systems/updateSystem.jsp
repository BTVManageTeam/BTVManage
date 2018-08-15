<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/common/taglibs.jsp"%>
<style type="text/css">
.editLength {
	width: 205px;
}
.imgProgress{
	background-color: #3C3C3C;
	color:#ffffff;
	font-weight:bold;
	text-align: center;
	line-height:150px;
	font-size: 20px;
	max-height: 100%;
	max-width: 100%;
}
tr:HOVER {
	background-color: transparent;
}
</style>
<link href="<%=request.getContextPath()%>/js/upload/uploadify3.1/uploadify.css" rel="stylesheet" type="text/css" />

<script src="<%=request.getContextPath()%>/js/upload/uploadify3.1/jquery.uploadify.js"></script>
<div class="widget-box112">
	<div class="widget-title">
		<span class="icon"> <i class="icon-th"></i> </span>
		<h5>
			修改系统
		</h5>
	</div>
	<div class="widget-content tab-content">
	<%--
		开始添加的内容
		--%>
		<form id="updateSystemFrom" class="form-horizontal">
			<input type="hidden" name="systemId" id="systemId" value="${system.systemId}">
			<div id="gridPager">
				<table style="width: 100%; padding: 8px; ">
					<tr>
						<td colspan="6" style="padding-left: 20px;">
							<div class="cata_title isdeletetr">
								<p>系统logo</p>
							</div>
						</td>
					</tr>
					<tr>
						<td colspan="3" style="padding-left: 100px;padding-bottom: 30px;">
							<input type="file" name="file" id="fileUpload">
							<input type="hidden" name="systemLogoUrl" id="systemLogoUrl" value="${system.systemLogoUrl}"/>
							<div id="imgDiv" style="width:240px;height: 160px;">
								<c:if test="${fn:length(system.systemLogoUrl) == 0}">
									<img  id="thumbnail" style="max-height:100%;max-width:100%;"  src="<%=request.getContextPath()%>/images/loadingImage.png"></img>
								</c:if>
								<c:if test="${not empty system.systemLogoUrl}">
									<img  id="thumbnail" style="max-height:100%;max-width:100%;"  src="${system.systemLogoUrl}"></img>
								</c:if>
							</div>
							<span style="color: #bbb">系统logo</span>
						</td>
						<td style="padding-bottom: 30px;">
							<input type="file" name="filehoverlogo" id="fileUploadHoverLogo">
							<input type="hidden" name="systemHoverLogoUrl" id="systemHoverLogoUrl" value="${system.systemHoverLogoUrl}"/>
							<div id="hoverimgDiv" style="width:240px;height: 160px;">
								<c:if test="${fn:length(system.systemHoverLogoUrl) == 0}">
								<img  id="hoverlogothumbnail" style="max-height:100%;max-width:100%;"  src="<%=request.getContextPath()%>/images/loadingImage.png"></img>
								</c:if>
								<c:if test="${not empty system.systemHoverLogoUrl}">
								<img  id="hoverlogothumbnail" style="max-height:100%;max-width:100%;"  src="${system.systemHoverLogoUrl}"></img>
								</c:if>
							</div>
							<span style="color: #bbb">鼠标悬停显示的系统logo</span>
						</td>
					</tr>
					<tr>
						<td align="right" width="10%">
							系统编号：
						</td>
						<td width="30%">
							<input type="text" name="systemAppCode" id="systemAppCode"
								value="${system.systemAppCode}" datatype="s1-20" errormsg="系统编号为1-20个字符(下划线、空格、数字、字母和汉字)"
								nullmsg="请输入系统编号" />
							<span style="color: red; font-size: 16px;">*</span>
							<div class="Validform_checktip"></div>
						</td>
						<td align="right" width="10%">
							 所属企业：
						</td>
						<td width="30%">
						<input type="text" name="enterpriseName" id="enterpriseName" value="${system.enterpriseName}" 
						datatype="s1-20" errormsg="所属企业为1-20个字符(下划线、空格、数字、字母和汉字)" nullmsg="请输入所属企业"/>
						<span style="color: red; font-size: 16px;">*</span>
						<div class="Validform_checktip"></div>
						</td>
					</tr>
					<tr>
						<td align="right" width="10%">
							系统名称：
						</td>
						<td width="30%">
							<input type="text" name="systemName" id="systemName"
								value="${system.systemName}" datatype="s1-20" errormsg="系统名为1-20个字符(下划线、空格、数字、字母和汉字)"
								nullmsg="请输入系统名称" />
							<span style="color: red; font-size: 16px;">*</span>
							<div class="Validform_checktip"></div>
						</td>
						<td align="right">
							 系统标识：
						</td>
						<td>
						<input type="text" name="systemMark" id="systemMark" value="${system.systemMark}" 
						datatype="*1-20" errormsg="系统标识为1-20个任意字符" nullmsg="请输入系统标识"/>
						<span style="color: red; font-size: 16px;">*</span>
						<div class="Validform_checktip"></div>
						</td>
					</tr>
					<tr>
						<td align="right">
						<label class="">系统url:</label>
						</td>
						<td>
							<input type="text" name="systemUrl" id="systemUrl"   value="${system.systemUrl}" nullmsg="请输入系统url"/>
							<span style="color: red; font-size: 16px;">*</span>
							<div class="Validform_checktip"></div>
						</td>
						<td align="right">
							<label class="">高安全区的缓存地址:</label>
						</td>
						<td>
							<input type="text" name="" id="systemUrl"   value="">
						</td>
					</tr>
					<tr>
						<td align="right">
							展示URL：
						</td>
						<td colspan="3">
							<textarea style="width: 78%; height: 80px;" name="showUrl" id="showUrl" >${system.showUrl}</textarea>
							<div class="Validform_checktip"></div>
						</td>
					</tr>
					<tr>
						<td align="right">
							系统描述：
						</td>
						<td colspan="3">
							<textarea style="width: 78%; height: 80px;" value="" name="systemDesc" id="userDesc" datatype="*1-200" ignore="ignore" errormsg="系统描述最多200字符">${system.systemDesc}</textarea>
							<div class="Validform_checktip"></div>
						</td>
					</tr>	
					<tr>
						<td colspan="4" align="center">
							&nbsp;
						</td>
					</tr>
					<tr>
						<td colspan="2" align="right">
							<button type="submit"  class="btn btn-info" style="width: 120px; margin-right: 50px;">
								修改
							</button>
						</td>
						<td colspan="2" align="left">
							<button type="button" onclick="giveup('/systems/findall/');" class="btn " style="width: 120px; margin-left: 40px;">
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
		$("#updateSystemFrom").Validform({
			tiptype : 4,
			ajaxPost : true,
			beforeSubmit : function(curform) {
				punctuation();
				updateSystem();
				return false;
			}
		});
		uploadSystemLogo();//加载上传系统logo方法
		uploadSystemHoverLogo();//加载上传系统悬浮logo方法
	});
	
</script>
