<%@page import="com.cdvcloud.rochecloud.util.UserUtil"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<% 
	String loginId = UserUtil.getUserByRequest(request, "loginId");
	String companyId = UserUtil.getUserByRequest(request, "companyName");
%>
<div class="widget-box">
	<div class="widget-title">
		<h2><a href="javascript:;" id="serviceSituation" shref="<%=request.getContextPath()%>/comment/queryCommentPage/"  onclick="openurl(this)"><i class="icon-retreat"></i>返回</a></h2>
	</div>
	<div class="widget-content tab-content">
		<form id="vmsform" class="mg" action="<%=request.getContextPath()%>/comment/queryLawyer/?commentId=${commentId}" method="post">

			<div style="position:relative;  display:inline-block;">
				<input class="conditions" style="width: 166px; height:28px; " placeholder="律师名" type="text" id="lawyerName" name="lawyerNameLIKE" value="${params.lawyerNameLIKE }" /><i class="search_btn" onclick="selectformbyone()"></i>
			</div>
			<input style="display:none;" type="text" /> <input type="hidden" id="order" name="order" value="${page.order}" /> <input type="hidden" id="orderBy"
				name="orderBy" value="${page.orderBy}" /> <input type="hidden" id="currentPage" name="currentPage" value="${page.currentPage}" />
			<button value="" type="button" onclick="emptyCon()" class="btn-info btn  b_m_bottom clear_btn">清除</button>
		</form>
		<div style="clear:both;"></div>
		<table id="generateTable" class="table table-bordered table-striped" style="border-top:1px solid #DDDDDD;border-bottom:1px solid #DDDDDD;">
			<caption class="cap"></caption>
			<thead>
				<tr>
					<th width="5%">头像</th>
					<th width="10%">律师名称</th>
					<th width="10%">职业年份</th>
					<th width="10%">简介</th>
					<th width="13%">创建时间</th>
					<th width="20%">操作</th>
				</tr>
				<tr>
					<th class="white_th"></th>
					<th class="white_th"></th>
					<th class="white_th"></th>
					<th class="white_th"></th>
					<th class="white_th"></th>
					<th class="white_th"></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${page.list}" var="lawyer" varStatus="i">
					<tr id="tr${lawyer.lawyerId}">
						<td><div class="lawyer_head">
							<img src="${lawyer.portrait}"/>
						</div></td>
						<td>${lawyer.lawyerName}</td>
						<td>${lawyer.professionalYear}</td>
						<td>${lawyer.introduce}</td>
						<td><fmt:formatDate value="${lawyer.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
						<td>
							<button type="button" id="btnUpdatePwd" class="btn btn-info btn-mini" onclick="sendLawyer('${lawyer.lawyerId}','${commentId}')">发送</button>
					</tr>
					<tr>
						<td class="white_td"></td>
						<td class="white_td"></td>
						<td class="white_td"></td>
						<td class="white_td"></td>
						<td class="white_td"></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<%@ include file="/common/page.jsp"%>
	</div>
</div>
<script src="<%=request.getContextPath()%>/js/viewJs/commont/commontList.js"></script>
