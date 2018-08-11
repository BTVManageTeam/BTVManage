<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<div class="widget-box">
         <div class="widget-title"> <span class="icon"> <i class="icon-th"></i> </span>
           <h5>所属商列表</h5>
         </div>
         <div class="widget-content tab-content">
           <form id="vmsform" class="mg" action="<%=request.getContextPath()%>/ownerbusiness/findallOwnerBusiness/" method="post" style="float: none;" >
            <div style="position:relative; display:inline-block;"> <input class="conditions" placeholder="所属商名称" style="width: 166px; height:28px; padding-right:44px;" type="text" id="ownerBusName" name="ownerBusNameLIKE" value="${param.ownerBusNameLIKE }"/><i class="search_btn" onclick="selectformbyone()"></i></div>
           	  <input style="display:none;" type="text"/>
          	  <input type="hidden" id="order" name="order" value="${page.order}"/>
              <input type="hidden" id="orderBy" name="orderBy" value="${page.orderBy}"/>
              <input type="hidden" id="currentPage" name="currentPage" value="${page.currentPage}"/>
              <button value="" type="button" onclick="emptyCon()" class="btn-info btn  b_m_bottom clear_btn">清除</button>
              <!-- <button value="" type="button" onclick="selectformbyone()" class="btn-info btn  b_m_bottom">搜索</button> -->
              	<button  type="button" class="btn-info btn  b_m_bottom clear_btn" onclick="toAddOwnerBusiness();" style="float: right;">增加</button>
			</form>
			<table id="generateTable" class="table table-bordered table-striped" style="border-top:1px solid #DDDDDD;border-bottom:1px solid #DDDDDD;">
			<caption class="cap"></caption>
			  <thead>
				<tr>
<!-- 				  <th width="5%"><input id="checkBoxAll" type="checkbox" /></th> -->
				  <th width="5%">编号</th>
				  <th width="15%" >所属商名称</th>
				  <th width="15%" >所属商标识</th>
				  <th width="15%" >租户组名称</th>
				  <th width="10%" >客户名</th>
				  <th width="13%" >客户手机号</th>
				  <th width="13%" >最大用户数</th>
				  <th width="5%" >状态</th>
				<!--   <th width="15%" >创建人</th>
				  <th width="15%" >创建时间</th> -->
				  <th width="25%" >操作</th>
				</tr>
			    <tr><th class="white_th"></th><th class="white_th"></th><th class="white_th"></th><th class="white_th"></th><th class="white_th"></th><th class="white_th"></th></tr>
			  </thead>
			  <tbody>
				 <c:forEach items="${page.list}" var="ownerbus" varStatus="i">
					<tr id="tr${ownerbus.ownerBusId}">
<!-- 					  <td><input type="checkbox"  /></td> -->
					  <td>${i.index+1} </td>                
					  <td >${ownerbus.ownerBusName}   </td>
					  <td >${ownerbus.ownerBusCode}   </td>
					  <td >
					    <c:if test="${ownerbus.ownerRangeId==''}">--</c:if>
					  	<c:if test="${ownerbus.ownerRangeId!=''}">${ownerbus.ownerRangeId}</c:if>
					  </td>
					   <td>${ownerbus.customerName}</td>
					   <td>${ownerbus.mobilePhone}</td>
					   <td>${ownerbus.maxUserNum}</td>
					   <td>
					  	<c:choose>
					  		<c:when test="${ownerbus.type == 0}">
					  			正常
					  		</c:when>
					  		<c:when test="${ownerbus.type == 1}">
					  			过期
					  		</c:when>
					  		<c:when test="${ownerbus.type == 2}">
					  			失效
					  		</c:when>
					  		<c:otherwise>
					  			未知
					  		</c:otherwise>
					  	</c:choose>
					  </td>
					 <%--  <td >
					  <c:if test="${ownerbus.createUserName==''}">--</c:if>
					  <c:if test="${ownerbus.createUserName!=''}">${ownerbus.createUserName}</c:if>
					  </td>
					  <td > <fmt:formatDate value="${ownerbus.createTime}" pattern="yyyy-MM-dd hh:mm:ss"/></td>  --%>
					  <td >
						<%-- <button type="button" class="btn btn-info btn-mini" onclick="toSelSection('/users/findDistSelSection/','${ownerbus.ownerBusId}')" >查看</button> --%>
						<button type="button" class="btn btn-info btn-mini" onclick="toUpdateOwnerBusiness('${ownerbus.ownerBusId}')" >编辑</button>
               			<button type="button"  id="btnDelete" class="btn btn-danger btn-mini" onclick="delOwnerBusiness('${ownerbus.ownerBusId}')">删除</button>
					  </td>
					</tr>
				    <tr><td class="white_td"></td><td class="white_td"></td><td class="white_td"></td><td class="white_td"></td><td class="white_td"></td><td class="white_td"></td></tr>
				</c:forEach>
			  </tbody>
			</table>
           <%@ include file="/common/page.jsp"%>
         </div>
 </div>
 <script src="<%=request.getContextPath()%>/js/business/ownerbusiness.js"></script>
