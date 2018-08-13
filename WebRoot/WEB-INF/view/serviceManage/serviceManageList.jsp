<%@page import="com.cdvcloud.rochecloud.util.UserUtil"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<% 
	String loginId = UserUtil.getUserByRequest(request, "loginId");
	String companyId = UserUtil.getUserByRequest(request, "companyName");
%>
<div class="widget-box">
	<div class="widget-title">
		<span class="icon"> <i class="icon-th"></i> </span>
		<h5>律师</h5>
	</div>
	<div class="widget-content tab-content">
		<form id="vmsform" class="mg" action="<%=request.getContextPath()%>/lawyer/serviceManageList/" method="post">
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
					<th width="5%">编号</th>
					<th width="10%">律师名称</th>
					<th width="10%">服务次数</th>
					<th width="10%">评分</th>
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
						<td>${i.index+1}</td>
						<td>${lawyer.lawyerName}</td>
						<td>${lawyer.serviceNum}</td>
						<td>${lawyer.average}</td>
						<td><fmt:formatDate value="${lawyer.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
						<td>
							<button type="button" id="btnUpdatePwd" class="btn btn-info btn-mini" onclick="toQueryOrder('${lawyer.lawyerId}')">查看</button>
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
<script src="<%=request.getContextPath()%>/js/viewJs/serviceManage/serviceManageList.js"></script>
