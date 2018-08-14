<%@page import="com.cdvcloud.rochecloud.util.UserUtil"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<div class="widget-box g_mainc">
	<div class="widget-content tab-content g_mainc_main">

		<div style="clear:both;"></div>
		<table id="generateTable" class="table table-bordered table-striped" style="border-top:1px solid #DDDDDD;border-bottom:1px solid #DDDDDD;">
			<caption class="cap"></caption>
			<tbody>
				<c:forEach items="${page.list}" var="order" varStatus="i">
					<div class="message_list m_table_div">
						<div id="5b5fcfff1996b026088fa10f" class="message_item">
							<div class="message_info clearfloat">
								<div class="item_img fl"><img src="http://thirdwx.qlogo.cn/mmopen/EoyAs8cQgbECZMLyPDBapLVIRSOGNufVcwwRfzjr6HkvicXMHdjRZb6tyNWicUNicsVIvYicX6ReoibambYiaUQWsGZ5e5Aib0FtC0J/132" alt="user_info" onerror="dazzleUtil.imgError(this)"></div>
								<div class="fl message_gro">
									<ul class="about_msg clearfloat">
										<li class="fl">${order.commentName}</li>
										<li class="fl"><fmt:formatDate value="${order.createTime}" pattern="yyyy-MM-dd HH:mm" /></li>
										<c:if test="${order.status == 1}">
											<li class="message_status fl" v-if="item.hasOwnProperty('replayContent')&&item.replayContent.length>0">已回复</li>
										</c:if>
										<c:if test="${order.status == 0}">
											<li class="message_status fl" v-if="item.hasOwnProperty('replayContent')&&item.replayContent.length>0">未回复</li>
										</c:if>
										<li class="fr quick_replay"><img src="img/visitingCard.svg" shref="<%=request.getContextPath()%>/comment/queryLawyer/?commentId=${order.commentId}" onclick="openurl(this)" /><i id="quick_replay5b5fcfff1996b026088fa10f" class="icon-comment"></i></li>
									</ul>
									<p>${order.commentContent}</p>
								</div>
							</div>
							<div>
								<c:if test="${order.status == 1}">
									<ul class="reply_msg message_gro">
									<c:forEach items="${order.btvCommentReplyList}" var="order1" varStatus="i">
										<c:if test="${order1.status == 1}">
											<li><span>回复：</span><span>${order1.txtContent}</span></li>
										</c:if>
										<c:if test="${order1.status == 0}">
											<li><span>回复：</span><div class="lawyer_card">
												<div class="lawyer_details">
													<div class="lawyer_head">
														<img src="${order1.lawyerMap.portrait}" />
													</div>
													<div class="lawyer_introduce">
														<p class="change_line verticalcenter">${order1.lawyerMap.introduce}</p>
													</div>
												</div>
											</div></li>
										</c:if>
									</c:forEach>
									</ul>
								</c:if>
							</div>

							<div id="msag_replay_5b5fcfff1996b026088fa10f" class="js_quick_reply_box quick_reply_box" style="display: block;"><label for="" class="frm_label">快速回复:</label>
								<div class="emoion_editor_wrp js_editor">
									<div class="emotion_editor"><textarea id="txtContent" name="saytext" class="edit_area js_editorArea input"></textarea>
										<div class="editor_toolbar">
											<a id="emotion5b5fcfff1996b026088fa10f" href="javascript:void(0);" title="表情" class="icon_emotion emotion_switch js_switch emotion">表情</a>
											<p class="editor_tip js_editorTip replay_num"><span id="wordwrap5b5fcfff1996b026088fa10f" class="wordwrap">还可以输入<var class="word">140</var>个字</span></p>
										</div>
									</div>
								</div>
								<p class="quick_reply_box_tool_bar"><button id="sub_btn5b5fcfff1996b026088fa10f" class="js_reply_OK sub_btn  btn1 btn1_primary btn1_input" onclick="sendLawyer('','${order.commentId}')">发送</button>
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
