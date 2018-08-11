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
		<form id="vmsform" class="mg" action="<%=request.getContextPath()%>/users/findall/" method="post">

			<div style="position:relative;  display:inline-block;">
				<input class="conditions" style="width: 166px; height:28px; " placeholder="登录名" type="text" id="loginId" name="loginIdLIKE" value="${params.loginIdLIKE }" /> 
				<input class="conditions" style="width: 166px; height:28px; padding-right:44px;" placeholder="用户名" type="text" id="userName" name="usernameLIKE" value="${params.usernameLIKE }" /> <i class="search_btn" onclick="selectformbyone()"></i>
				<input class="conditions" style="width: 166px; height:28px; padding-right:44px;" placeholder="所属部门" type="text" id="deparmentName" name="deparmentNameLIKE" value="${params.deparmentNameLIKE }" /> <i class="search_btn" onclick="selectformbyone()"></i>
			</div>
			<input style="display:none;" type="text" /> <input type="hidden" id="order" name="order" value="${page.order}" /> <input type="hidden" id="orderBy"
				name="orderBy" value="${page.orderBy}" /> <input type="hidden" id="currentPage" name="currentPage" value="${page.currentPage}" />
			<button value="" type="button" onclick="emptyCon()" class="btn-info btn  b_m_bottom clear_btn">清除</button>
		</form>
		<form name="myform1" method="post" enctype="multipart/form-data" class="mg1">
			<span class="mt10 fr">
				<button type="button" class="btn-info btn  b_m_bottom clear_btn" onclick="toAddDepartment();">增加</button>
			</span>
		</form>
		<div style="clear:both;"></div>
		<table id="generateTable" class="table table-bordered table-striped" style="border-top:1px solid #DDDDDD;border-bottom:1px solid #DDDDDD;">
			<caption class="cap"></caption>
			<thead>
				<tr>
					<th width="5%">编号</th>
					<th width="10%">登录名</th>
					<th width="10%">用户名</th>
					<th width="8%">所属部门</th>
					<th width="10%">电话</th>
					<th width="7%">创建人</th>
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
				<c:forEach items="${page.list}" var="user" varStatus="i">
					<tr id="tr${user.userId}">
						<td>${i.index+1}</td>
						<td>${user.loginId}</td>
						<td>${user.userName}</td>
						<td>${user.depName}</td>
						<td>${user.userPhone}</td>
						<td>${user.createUserName}</td>
						<td><fmt:formatDate value="${user.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
						<td>
							<button type="button" id="btnUpdatePwd" class="btn btn-info btn-mini" onclick="toUpdateUser('${user.userId}')">修改</button>
							<button type="button" id="btnDelete" class="btn btn-danger btn-mini" onclick="delUser('${user.userId}');">删除</button></td>
					</tr>
					<tr>
						<td class="white_td"></td>
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
<script src="<%=request.getContextPath()%>/js/ajaxfileupload.js"></script>
