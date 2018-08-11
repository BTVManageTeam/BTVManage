<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<style type="text/css">
	.ellipsis{
		white-space: nowrap;
		overflow: hidden;
		text-overflow: ellipsis;
	}
</style>
<div class="widget-box">
         <div class="widget-title"> <span class="icon"> <i class="icon-th"></i> </span>
           <h5>应用系统列表</h5>
         </div>
         <div class="widget-content tab-content">
           <form id="vmsform" class="mg" action="<%=request.getContextPath()%>/systems/findall/" method="post" style="float: none;" >
  	      <div style="position:relative; display:inline-block;"> <input class="conditions" style="width: 166px; height:28px; padding-right:44px;" type="text" placeholder="系统名称" id="systemName" name="systemNameLIKE" value="${params.systemNameLIKE}"/><i class="search_btn" onclick="selectformbyone()"></i></div>
				<input style="display:none;" type="text"/>
          	  <input type="hidden" id="order" name="order" value="${page.order}"/>
              <input type="hidden" id="orderBy" name="orderBy" value="${page.orderBy}"/>
              <input type="hidden" id="currentPage" name="currentPage" value="${page.currentPage}"/>
              <button value="" type="button" onclick="emptyCon()" class="btn-info btn  b_m_bottom clear_btn">清除</button>
<%--              <button value="" type="button" onclick="selectformbyone()" class="btn-info btn  b_m_bottom">搜索</button>--%>
              <button value="" type="button"  onclick="giveup('/systems/toPage/?page=systems/addSystem')"  class="btn-info btn  b_m_bottom clear_btn" style="float: right;">增加</button>
			</form>
			<table id="generateTable" class="table table-bordered table-striped" style="border-top:1px solid #DDDDDD;border-bottom:1px solid #DDDDDD;">
			<caption class="cap"></caption>
			  <thead>
				<tr>
<!-- 				  <th width="5%"><input id="checkBoxAll" type="checkbox" /></th> -->
				  <th width="5%">编号</th>
				  <th width="10%" >系统编号</th>
				  <th width="10%" >所属企业</th>
				  <th width="10%" >系统名称</th>
				  <th width="10%" >系统标识</th>
				  <th width="10%" >系统Url</th>
				  <th width="10%" >系统logo</th>
<!-- 				  <th width="10%" >系统描述</th> -->
				  <th width="7%" >创建人</th>
				  <th width="15%" >创建时间</th>
				  <th width="15%" >操作</th>
				</tr>
			 <tr><th class="white_th"></th><th class="white_th"></th><th class="white_th"></th><th class="white_th"></th><th class="white_th"></th><th class="white_th"></th></tr>
			  </thead>
			  <tbody>
				 <c:forEach items="${page.list}" var="system" varStatus="i">
					<tr id="tr${system.systemId}">
<!-- 					  <td><input type="checkbox"  /></td> -->
					  <td>${i.index+1} </td>
				      <td >${system.systemAppCode}</td>
					  <td >${system.enterpriseName}</td>
					  <td >${system.systemName}</td>
					  <td >${system.systemMark}</td>
					  <td ><div class="ellipsis" style="width: 150px;" title="${system.systemUrl}">${system.systemUrl}</div></td>
					  <td><img alt="暂无" src="${system.systemLogoUrl}" style="width: 50%;"></td>
<!-- 					  <td><div class="ellipsis" style="width: 150px;" title="${system.systemDesc}">${system.systemDesc}</div></td> -->
					  <td >${system.createUserName}</td>
					  <td ><fmt:formatDate value="${system.createTime}" pattern="yyyy-MM-dd hh:mm:ss"/></td>
					  <td >
						<button type="button" class="btn btn-info btn-mini" onclick="toUpdateSystem('${system.systemId}');" >编辑</button>
               			<button type="button"  id="btnDelete" class="btn btn-danger btn-mini" onclick="delUser('${system.systemId}');">删除</button>
					  </td>
					</tr>
				  <tr><td class="white_td"></td><td class="white_td"></td><td class="white_td"></td><td class="white_td"></td><td class="white_td"></td><td class="white_td"></td></tr>
				</c:forEach>
			  </tbody>
			</table>
           <%@ include file="/common/page.jsp"%>
         </div>
 </div>
 <script src="<%=request.getContextPath()%>/js/business/systems.js"></script>
