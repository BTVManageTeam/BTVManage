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
		<h5>律师事务所</h5>
	</div>
	<div class="widget-content tab-content">
		<form id="vmsform" class="mg" action="<%=request.getContextPath()%>/department/findall/" method="post">

			<div style="position:relative;  display:inline-block;">
				<input class="conditions" style="width: 166px; height:28px; " placeholder="律所名" type="text" id="departmentName" name="departmentNameLIKE" value="${params.departmentNameLIKE }" /><i class="search_btn" onclick="selectformbyone()"></i>
			</div>
			<input style="display:none;" type="text" /> <input type="hidden" id="order" name="order" value="${page.order}" /> <input type="hidden" id="orderBy"
				name="orderBy" value="${page.orderBy}" /> <input type="hidden" id="currentPage" name="currentPage" value="${page.currentPage}" />
			<button value="" type="button" onclick="emptyCon()" class="btn-info btn  b_m_bottom clear_btn">清除</button>
		</form>
		<form name="myform1" method="post" enctype="multipart/form-data" class="mg1">
			<c:if test="${roleCode == 2 }">
				<span class="mt10 fr">
					<button type="button" class="btn-info btn  b_m_bottom clear_btn" onclick="giveup('/department/toPage/?page=department/addDepartment')">增加</button>
				</span>
			</c:if>
		</form>
		<div style="clear:both;"></div>
		<table id="generateTable" class="table table-bordered table-striped" style="border-top:1px solid #DDDDDD;border-bottom:1px solid #DDDDDD;">
			<caption class="cap"></caption>
			<thead>
				<tr>
					<th width="5%">编号</th>
					<th width="10%">律所名称</th>
					<th width="10%">地域</th>
					<th width="13%">创建时间</th>
					<th width="20%">操作</th>
				</tr>
				<tr>
					<th class="white_th"></th>
					<th class="white_th"></th>
					<th class="white_th"></th>
					<th class="white_th"></th>
					<th class="white_th"></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${page.list}" var="user" varStatus="i">
					<tr id="tr${user.departmentId}">
						<td>${i.index+1}</td>
						<td>${user.departmentName}</td>
						<td>${user.regionName}</td>
						<td><fmt:formatDate value="${user.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
						<td>
							<button type="button" id="btnUpdatePwd" class="btn btn-info btn-mini" onclick="toUpdateDepartment('${user.departmentId}')">修改</button>
							<button type="button" id="btnDelete" class="btn btn-danger btn-mini" onclick="delDepartment('${user.departmentId}');">删除</button></td>
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
<script src="<%=request.getContextPath()%>/js/viewJs/department/department.js"></script>
