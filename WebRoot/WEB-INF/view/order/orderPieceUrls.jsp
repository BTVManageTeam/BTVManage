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
		<h2><a href="javascript:;" id="serviceSituation" shref="<%=request.getContextPath()%>/order/queryOrderPage"  onclick="toQueryOrder('${lawyerId}')"><i class="icon-retreat"></i>返回</a></h2>
	</div>

	<div class="g_mainc">
		<div class="g_mainc_main">
			<div class="photocopies_list clearfloat">
				<c:forEach items="${pieceUrls}" var="order" varStatus="i">
					<div class="photocopies_img">
						<img src="${order}"/>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
</div>