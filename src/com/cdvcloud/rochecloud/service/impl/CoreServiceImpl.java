package com.cdvcloud.rochecloud.service.impl;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.cdvcloud.rochecloud.common.Constants;
import com.cdvcloud.rochecloud.service.ICoreService;
import com.cdvcloud.rochecloud.service.UserMessageService;
import com.cdvcloud.rochecloud.service.WxUserService;
import com.cdvcloud.wechat.bean.reponse.Image;
import com.cdvcloud.wechat.bean.reponse.ImageMessage;
import com.cdvcloud.wechat.bean.reponse.TextMessage;
import com.cdvcloud.wechat.util.wechat.MessageUtil;

/**
 * 核心服务类 (保存用户信息)
 * 
 * @author chenchang
 * @date 2014-08-23
 */
@Service
public class CoreServiceImpl implements ICoreService {
	private static final Logger logger = Logger.getLogger(CoreServiceImpl.class);
	
	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return
	 */
	public  String processRequest(HttpServletRequest request, WxUserService userService, 
			UserMessageService userMessageService, 
			String productId) {
		String respMessage = null;
		try {
			
			/**
			 * <xml> <ToUserName>接收方微信号 <FromUserName>发送方微信号 <CreateTime>消息创建时间
			 * <MsgType>消息类型，event
			 * <Event>事件类型，subscribe(订阅)、unsubscribe(取消订阅)、CLICK(自定义菜单点击事件)
			 * <EventKey>事件KEY值，与自定义菜单接口中KEY值对应 </xml> 我们只要关心两个参数：MsgType和Event。
			 * 当MsgType=event时，就表示这是一条事件推送消息； 而Event表示事件类型，包括订阅、取消订阅和自定义菜单点击事件。
			 * 也就是说，无论用户是关注了公众帐号、取消对公众帐号的关注，还是在使用公众帐号的菜单，
			 * 微信服务器都会发送一条MsgType=event的消息给我们，
			 * 而至于具体这条消息表示关注、取消关注，还是菜单的点击事件，就需要通过Event的值来判断了。 （注意区分Event和event）
			 */
			// 默认返回的文本消息内容
			String respContent = "";
			//支持返回图片内容
			String autoReplyType = "";
			String imgId = "";
			
			// xml请求解析
			Map<String, String> requestMap = MessageUtil.parseXml(request);

			// 发送方帐号（open_id）
			String fromUserName = requestMap.get("FromUserName");
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");
			
			
			logger.info("http request from wechat. fromUserName=" + fromUserName + ",toUserName= " + toUserName + ",msgType=" + msgType);

			//首先信息入库
			userMessageService.addMessage(requestMap, userService, productId);
			
			// 文本消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)){
				 String content = requestMap.get("Content").trim();
				 logger.info("用户输入的内容为：content={"+content+"}.");
			}
			// 图片消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				// 暂不处理
			}
			// 地理位置消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
				// 暂不处理
			}
			// 链接消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				// 暂不处理
			}
			// 音频消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
				// 暂不处理
			// 视频消息
			} else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)) {
				// 暂不处理
			}
			// 事件推送
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
			}
			
			if(!"".equals(respContent) || !"".equals(imgId)) {
				if(Constants.SONE.equals(autoReplyType)) {//回复文本消息
					TextMessage textMessage = new TextMessage();
					textMessage.setToUserName(fromUserName);
					textMessage.setFromUserName(toUserName);
					textMessage.setCreateTime(new Date().getTime());
					textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
					textMessage.setFuncFlag(0);
					textMessage.setContent(respContent);
					respMessage = MessageUtil.textMessageToXml(textMessage);
					logger.info("回复的是文本消息={"+respMessage+"}");
				} else if(Constants.STWO.equals(autoReplyType)) { //回复图片消息
					ImageMessage imageMessage = new ImageMessage();
					imageMessage.setToUserName(fromUserName);
					imageMessage.setFromUserName(toUserName);
					imageMessage.setCreateTime(new Date().getTime());
					imageMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_IMAGE);
					Image image = new Image();
					image.setMediaId(imgId);
					imageMessage.setImage(image);
					respMessage = MessageUtil.imageMessageToXml(imageMessage);
					logger.info("回复的是图片消息={"+respMessage+"}");
				}
				respMessage = respMessage.replaceAll("&lt;", "<").replaceAll("&gt;", ">").
						replaceAll("<div>","").replaceAll("</div>","\n").
						replaceAll("<p>","").replaceAll("</p>","\n").
						replaceAll("&nbsp;", " ").replaceAll("<br>", "\n");
			}
		} catch (Exception e) {
			logger.error("处理用户信息请求出现错误, errMsg=" + e);
			e.printStackTrace();
		}

		return respMessage;
	}
	
	
	
	
}
