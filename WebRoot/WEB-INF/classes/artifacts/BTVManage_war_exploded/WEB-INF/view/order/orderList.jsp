<%@page import="com.cdvcloud.rochecloud.util.UserUtil"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<% 
	String loginId = UserUtil.getUserByRequest(request, "loginId");
	String companyId = UserUtil.getUserByRequest(request, "companyName");
%>
<div class="widget-box g_mainc">
	<div class="widget-title">
		<a href="javascript:;" id="serviceSituation" shref="<%=request.getContextPath()%>/lawyer/serviceManageList/"  onclick="openurl(this)">
			<span class="icon"> <img src="../../../images/backBtn.svg" alt="img"></span>
			<h5><i class="icon-retreat"></i>${lawyerName}</h5>
		</a>
	</div>
	<div class="widget-content tab-content g_mainc_main">

		<div class="overallData_cont alldata">
			<div class="overallData_div clearfloat">
				<div class="overallNum_div fl">
					<p>${serviceNum}</p>
					<p><span>服务次数</span></p>
				</div>
				<div class="overallNum_div fl">
					<p>${commentNum}</p>
					<p><span>评价人数</span></p>
				</div>
				<div class="overallNum_div fl">
					<p>${average}</p>
					<p><span>服务评价</span></p>
				</div>
			</div>
		</div>
		<div class="service_title">
			<h3>服务详情</h3>
		</div>

		<div style="clear:both;"></div>
		<table id="generateTable" class="table table-bordered table-striped" style="border-top:1px solid #DDDDDD;border-bottom:1px solid #DDDDDD;">
			<caption class="cap"></caption>
			<thead>
				<tr>
					<th width="10%">头像</th>
					<th width="10%">昵称</th>
					<th width="10%">服务类型</th>
					<th width="10%">服务评分</th>
					<th width="13%">创建时间</th>
					<th width="10%">服务状态</th>
					<th width="20%">操作</th>
				</tr>
				<tr>
					<th class="white_th"></th>
					<th class="white_th"></th>
					<th class="white_th"></th>
					<th class="white_th"></th>
					<th class="white_th"></th>
					<th class="white_th"></th>
					<th class="white_th"></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${page.list}" var="order" varStatus="i">
					<tr id="tr${order.orderId}">
						<td><div class="lawyer_head">
							<img src="${order.headUrl}"/>
						</div>
						</td>
						<td>${order.userName}</td>
						<td>${order.serviceType}</td>
						<td>
							<c:if test="${order.score== 0.0}">
								无评论
							</c:if>
							<c:if test="${order.score != 0.0}">
								${order.score}
							</c:if>
						</td>
						<td><fmt:formatDate value="${order.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
						<td>
							<c:if test="${order.serviceStatus== 0}">
								<button type="button" id="btnUpdate${i.index+1}" class="btn btn-info btn-mini" onclick="toUpdateServiceStatus('${order.orderId}','${order.createUserId}')">未完成</button>
							</c:if>
							<c:if test="${order.serviceStatus== 1}">
								已完成
							</c:if>
						</td>
						<td>
							<button type="button" id="btnQuery" class="btn btn-info btn-mini" onclick="toQueryPieceUrls('${order.orderId}','${order.createUserId}')">查看</button>
						</td>
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
<script src="<%=request.getContextPath()%>/js/viewJs/order/orderList.js"></script>
