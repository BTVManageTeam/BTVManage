package com.cdvcloud.rochecloud.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdvcloud.pass.upload.domin.UploadResponse;
import com.cdvcloud.rochecloud.domain.wechar.WechatKeyVo;
import com.cdvcloud.rochecloud.mdomain.User;
import com.cdvcloud.rochecloud.mdomain.UserMessage;
import com.cdvcloud.rochecloud.mongodao.BasicDao;
import com.cdvcloud.wechat.bean.AccessToken;
import com.cdvcloud.wechat.util.wechat.MessageUtil;
import com.cdvcloud.wechat.util.wechat.WeixinUtil;
import com.mongodb.DBObject;

/**
 * 业务描述：微信消息内容的相关操作
 * 
 * @author chenchang
 *
 */
@Service
public class UserMessageService{
	private static Logger logger = Logger.getLogger(UserMessageService.class);
	
	@Autowired
	private BasicDao baseDao;
	
	private static SimpleDateFormat sdf_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public String addMessage(Map<String, Object> params) throws Exception {
		return baseDao.insert(UserMessage.DB_COLLECTION, params);
	}
	

	public void updateMessageById(Map<String, Object> params, String id) throws Exception {
		Map<String, Object> filter = new ConcurrentHashMap<String, Object>();
		filter.put(UserMessage.ID, new ObjectId(id));
		baseDao.updateManyBySet(UserMessage.DB_COLLECTION, filter, params);
	}
	
	public void updateMessage(Map<String, Object> setParams, Map<String, Object> whereParams) throws Exception {
		baseDao.updateManyBySet(UserMessage.DB_COLLECTION, whereParams,setParams);
	}

	
	
	public String addMessage(Map<String, String> requestMap, WxUserService userService, String productId) throws Exception {
		WechatKeyVo wechatKey = InitConfigService.getWechatKeyByProductId(productId);
		AccessToken accessToken = wechatKey.getAccessToken();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String toDay = sdf.format(new Date());
		
		String result = "";
		// 消息类型
		String msgType = requestMap.get("MsgType");

		Map<String, Object> params = new HashMap<String, Object>();
		params.put(UserMessage.TOUSERNAME, requestMap.get("ToUserName")); //接收方微信号 
		params.put(UserMessage.FROMUSERNAME, requestMap.get("FromUserName")); //发送方帐号（open_id）
		params.put(UserMessage.MSGTYPE, requestMap.get("MsgType"));
		params.put(UserMessage.CREATETIME, requestMap.get("CreateTime"));
		params.put(UserMessage.PRODUCTID, productId);
		params.put(UserMessage.CREATE_TIME, sdf_date.format(new Date()));
		params.put(UserMessage.MSGID, requestMap.get("MsgId"));
		
		//获取用户信息,try catch是为了不影响使用
		Map<String, Object> whereParams = new HashMap<String, Object>();
		Map<String, Object> user = null;
		try {
			whereParams.put(User.OPENID, requestMap.get("FromUserName"));
			List<Map<String, Object>> users = userService.findUser(whereParams);
			if(users.size()!=0){
				user = users.get(0);
				params.put(UserMessage.NICKNAME, user.get(User.NICKNAME));
				params.put(UserMessage.HEADIMGURL, user.get(User.HEADIMGURL));
			}else{
				logger.error("保存用户信息，没有查询到当前用户，退出addMessage.");
				return result;
			}
		} catch (Exception e) {
			logger.error("get user info exception, errMsg={" +e+ "}.");
		}
		
		// 文本消息
		if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)){
			params.put(UserMessage.CONTENT, requestMap.get("Content"));
 
			params.put(UserMessage.PID, "1");
			params.put(UserMessage.REPLAYED, "0"); //0表示未回复状态，1表示已回复状态
			
			result = addMessage(params);
		}
		// 图片消息
		else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE))
		{
			String mediaId = requestMap.get("MediaId");
			
			params.put(UserMessage.PICURL, requestMap.get("PicUrl"));
			params.put(UserMessage.MEDIAID,mediaId);
			params.put(UserMessage.PID, "1");
			params.put(UserMessage.REPLAYED, "0"); //0表示未回复状态，1表示已回复状态
			
			//保存图片到本地
//			String savePath = Configuration.getConfigValue("WEIXIN_STORAGE")+"productId/fileType/toDay/";
//			savePath = savePath.replace("productId",productId).replace("fileType", MessageUtil.REQ_MESSAGE_TYPE_IMAGE).replace("toDay",toDay);
//			savePath = WeixinUtil.downloadResource(mediaId,accessToken.getAccess_token(),savePath);
//			
//			UploadResponse upResponse = FileUtil.uploadToOSS(savePath, productId);
//			params.put(UserMessage.MEDIALOCALURL,upResponse.getUrl());
//			logger.info("图片的访问路径为["+upResponse.getUrl()+"]");
			result = addMessage(params);
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
			String mediaId = requestMap.get("MediaId");
			
			params.put(UserMessage.MEDIAID,mediaId);
			params.put(UserMessage.FORMAT, requestMap.get("Format"));
			params.put(UserMessage.PID, "1");
			params.put(UserMessage.REPLAYED, "0"); //0表示未回复状态，1表示已回复状态
			
			//保存音频到本地
//			String savePath = Configuration.getConfigValue("WEIXIN_STORAGE")+"productId/fileType/toDay/";
//			savePath = savePath.replace("productId",productId).replace("fileType", MessageUtil.REQ_MESSAGE_TYPE_VOICE).replace("toDay",toDay);
//			savePath = WeixinUtil.downloadResource(mediaId,accessToken.getAccess_token(),savePath);
//			
//			UploadResponse upResponse = FileUtil.uploadToOSS(savePath, productId);
//			params.put(UserMessage.MEDIALOCALURL,upResponse.getUrl());
//			logger.info("语音的访问路径为["+upResponse.getUrl()+"]");
			result = addMessage(params);
			
		// 视频消息
		} else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)) {
			String mediaId = requestMap.get("MediaId");
			String thumbMediaid = requestMap.get("ThumbMediaId");
			
			params.put(UserMessage.MEDIAID,mediaId);
			params.put(UserMessage.THUMBMEDIAID,thumbMediaid);
			params.put(UserMessage.PID, "1");
			params.put(UserMessage.REPLAYED, "0"); //0表示未回复状态，1表示已回复状态
			
			//保存视频到本地，视频暂时不支持下载保存
			result = addMessage(params);
		}
		// 事件推送
		else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
			// 事件类型
			String eventType = requestMap.get("Event");
			// 订阅
			if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
				// 暂不处理
			}
			// 取消订阅
			else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
				// 暂不处理
			}
			// 自定义菜单点击事件
			else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
				// 暂不处理
			}
			// 自定义菜单点击事件
			else if (eventType.equals(MessageUtil.EVENT_TYPE_VIEW)) {
				// 暂不处理
			}
		}
		
		return result;
	}

}
