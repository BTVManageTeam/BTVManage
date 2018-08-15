<%@page import="com.cdvcloud.rochecloud.util.UserUtil"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<div class="widget-box g_mainc">
	<div class="widget-content tab-content g_mainc_main">

		<form id="vmsform" class="mg" action="<%=request.getContextPath()%>/comment/queryCommentPage" method="post">

			<div style="position:relative;  display:inline-block;">
				<input class="conditions" style="width: 166px; height:28px; " placeholder="消息发送者" type="text" id="commentNameLIKE" name="commentNameLIKE" value="${params.commentNameLIKE }" /><i class="search_btn" onclick="selectformbyone()"></i>
			</div>
			<input style="display:none;" type="text" />
			<input type="hidden" id="order" name="order" value="${page.order}" />
			<input type="hidden" id="orderBy" name="orderBy" value="${page.orderBy}" /> <input type="hidden" id="currentPage" name="currentPage" value="${page.currentPage}" />
			<button value="" type="button" onclick="emptyCon()" class="btn-info btn  b_m_bottom clear_btn">清除</button>
		</form>

		<div style="clear:both;"></div>
		<table id="generateTable" class="table table-bordered table-striped" style="border-top:1px solid #DDDDDD;border-bottom:1px solid #DDDDDD;">
			<caption class="cap"></caption>
			<tbody>
				<c:forEach items="${page.list}" var="order" varStatus="i">
					<div class="message_list m_table_div">
						<div id="" class="message_item">
							<div class="message_info clearfloat">
								<div class="item_img fl"><img src="http://thirdwx.qlogo.cn/mmopen/EoyAs8cQgbECZMLyPDBapLVIRSOGNufVcwwRfzjr6HkvicXMHdjRZb6tyNWicUNicsVIvYicX6ReoibambYiaUQWsGZ5e5Aib0FtC0J/132" alt="user_info" onerror="dazzleUtil.imgError(this)"></div>
								<div class="fl message_gro">
									<ul class="about_msg clearfloat">
										<li class="fl">${order.nickname}</li>
										<li class="fl">${order.ctime}</li>
										<c:if test="${order.replayed == 1}">
											<li class="message_status fl" v-if="item.hasOwnProperty('replayContent')&&item.replayContent.length>0">已回复</li>
										</c:if>
										<c:if test="${order.replayed == 0}">
											<li class="message_status fl" v-if="item.hasOwnProperty('replayContent')&&item.replayContent.length>0">未回复</li>
										</c:if>
										<li class="fr quick_replay"><img src="../../../images/visitingCard.svg" shref="<%=request.getContextPath()%>/comment/queryLawyer/?commentId=${order.commentId}" onclick="openurl(this)" />
											<img style="height: 20px;" src="../../../images/messageIcon.svg" alt="img"  class="icon-comment" onclick="replayBtn(this)"></li>
									</ul>
									<p>${order.content}</p>
								</div>
							</div>
							<div>
								<c:if test="${order.replayed == 1}">
									<ul class="reply_msg message_gro">
									<c:forEach items="${order.replayContent}" var="order1" varStatus="i">

											<li><span>回复：</span><span>${order1.content}</span></li>


									</c:forEach>
									</ul>
								</c:if>
							</div>

							<div id="${order._id}" class="js_quick_reply_box quick_reply_box"><label for="" class="frm_label">快速回复:</label>
								<div class="emoion_editor_wrp js_editor">
									<div class="emotion_editor">
										<textarea id="txtContent" name="saytext" class="edit_area js_editorArea input" onfocus="statInputNum('${order._id}')" ></textarea>
										<div class="editor_toolbar">
											<a id="emotion5b5fcfff1996b026088fa10f" href="javascript:void(0);" title="表情" class="icon_emotion emotion_switch js_switch emotion">表情</a>
											<p class="editor_tip js_editorTip replay_num"><span  class="wordwrap">还可以输入<var class="word">140</var>个字</span></p>
										</div>
									</div>
								</div>
								<p class="quick_reply_box_tool_bar"><button id="" class="js_reply_OK sub_btn  btn1 btn1_primary btn1_input" onclick="sendLawyer('','${order._id}')">发送</button>
									<a href="javascript:;" id="replay_up5b5fcfff1996b026088fa10f" class="js_reply_pickup btn1 btn1_default pickup replay_up">收起</a>
								</p>
							</div>
						</div>
					</div>
				</c:forEach>
			</tbody>
		</table>
		<%@ include file="/common/page.jsp"%>
	</div>
</div>
<script src="<%=request.getContextPath()%>/js/viewJs/comment/commentList.js"></script>
