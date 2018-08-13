package com.cdvcloud.rochecloud.service;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdvcloud.rochecloud.common.Constants;
import com.cdvcloud.rochecloud.domain.wechar.WechatKeyVo;
import com.cdvcloud.rochecloud.mdomain.User;
import com.cdvcloud.rochecloud.mongodao.BasicDao;
import com.cdvcloud.rochecloud.util.DateUtil;
import com.cdvcloud.rochecloud.util.JsonUtil;
import com.cdvcloud.rochecloud.util.StringUtil;
import com.cdvcloud.wechat.bean.AccessToken;
import com.cdvcloud.wechat.util.wechat.WeixinUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * 业务描述：微信用户信息相关操作
 * 
 * @author chenchang
 *
 */
@Service
public class WxUserService{
	private static final Logger logger = Logger.getLogger(WxUserService.class);
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	private static SimpleDateFormat sdf_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Autowired
	private BasicDao baseDao;
	
	public String addUser(Map<String, Object> params) throws Exception {
		return baseDao.insert(User.DB_COLLECTION, params);
	}

	public List<Map<String, Object>> findUser(Map<String, Object> whereParams) throws Exception {
		return baseDao.find(User.DB_COLLECTION, whereParams,1,Integer.MAX_VALUE);
	}
	/**
	 * 获取用户信息进行入库
	 * 
	 * @param openId
	 */
	public Map<String, Object> subscribeUser(String openId, String productId) {
		Map<String, Object>  userInfo = new ConcurrentHashMap<String, Object>();
		try {
			Map<String, Object> whereParams = new HashMap<String, Object>();
			whereParams.put(User.OPENID, openId);
			whereParams.put(User.PRODUCTID, productId);
			
			//首先查询数据库，是否已经存储了当前用户
			List<Map<String, Object>> dataObjectList = baseDao.find(User.DB_COLLECTION, whereParams, 1, Integer.MAX_VALUE);
			
			//如果关注状态不等于1,则首先删除
			String unsubscribe_time_sdf = ""; //获取到取消关注日期
			if(dataObjectList != null && dataObjectList.size() == 1 
					&& Integer.parseInt(String.valueOf(dataObjectList.get(0).get(User.SUBSCRIBE))) != 1) {
				unsubscribe_time_sdf = String.valueOf(dataObjectList.get(0).get(User.UNSUBSCRIBE_TIME_SDF));
//				baseDao.deleteObjectById(User.DB_COLLECTION, String.valueOf(dataObjectList.get(0).get(User.ID)));
			}
			
			//首先从缓存中获取到access_token
			AccessToken accessToken = InitConfigService.getWechatKeyByProductId(productId).getAccessToken();
			logger.info("GET UserInfo from wechat, productId=["+productId+"], openId=["+openId+"], access_token{"+accessToken.getAccess_token()+"}.");
			
			// 获取用户信息
//			userInfo = WeixinUtil.getUserInfo(accessToken.getAccess_token(), openId);
			/*
			 * 返回结果列表信息为：{ "subscribe":1,
			 * "openid":"oAsfGjoCINTknQ6zFHn9G0XSFzU8", "nickname":"kobicc(陈昌)",
			 * "sex":1, "language":"zh_CN", "city":"昌平", "province":"北京",
			 * "country":"中国", "headimgurl":"h t t p:\/\/wx.qlogo.cn\/mmopen\/
			 * dEr5LrDkAmbaMHoo2Ih4M6lCVXXCwO22neHJksZ28CY5QHMPm2KRJvoB76R59d4hVIzBjh481TePfAibhte8WicaxquAEXuVLt
			 * \/0", "subscribe_time":1409215507, "remark":""}
			 */
			logger.info("return UserInfo is {"+userInfo.toString()+"}");
			
			userInfo.put(User.PRODUCTID, productId);
			userInfo.put(User.CREATE_TIME, sdf_date.format(new Date()));
			
//			userInfo.put(User.SUBSCRIBE_TIME_SDF, dataFormat(userInfo.getLong(User.SUBSCRIBE_TIME)));
			userInfo.put(User.UNSUBSCRIBE_TIME_SDF, unsubscribe_time_sdf);
			
			String result = baseDao.insert(User.DB_COLLECTION, userInfo);
			if(result != null){
				logger.info("关注，保存用户{"+ userInfo.toString() +"}成功!");
			}else{
				logger.info("关注，保存用户失败!");
			}
		} catch (Exception e) {
			logger.error("用户信息入库发生错误：" + e);
		}
		return userInfo;
	}
	
	/**
	 *将取消关注删除 
	 * @param openId
	 */				
//	public void unsubscribeUser(String openId, String productId) {
//		try {
//			Map<String, Object> whereParams = new HashMap<String, Object>();
//			whereParams.put(User.OPENID, openId);
//			whereParams.put(User.PRODUCTID, productId);
//			//删除用户
//			baseDao.delete(User.DB_COLLECTION, whereParams);	
//		} catch (Exception e) {
//			logger.error("取消关注,删除用户错误!：" + e);
//		}
//	}
	
	/**
	 * @param code 用户同意授权之后	的code
	 * @param state 授权链接中的state的值
	 * @param productId 项目的id
	 * @return 用户存在返回该用户，没有返回null
	 * @throws Exception 
	 */
//	public DBObject userIsExist(String code,String state,String productId,HttpServletRequest request,HttpServletResponse response) throws Exception{
//		DBObject user = null;
//		try {
//			WechatKeyVo wechatKeyVo = InitConfigService.getWechatKeyByProductId(productId);
//			String appid = wechatKeyVo.getAppid();
//			String appsecret = wechatKeyVo.getAppsecret();
//			
//			JSONObject authInfo = WeixinUtil.getAuthorAccessToken(appid,appsecret, code);
//			logger.info("productId为｛"+productId+"｝, authInfo----------------------"+authInfo);
//			
//			String openId = authInfo.getString("openid");
//			HashMap<String, Object> queryUser = new HashMap<String, Object>();
//			queryUser.put(User.OPENID, openId);
//			queryUser.put(User.PRODUCTID, productId);
//			
//			List<DBObject> userList = baseDao.findObject(User.DB_COLLECTION,queryUser);
//			
//			if(null == userList || userList.size() == 0) {
//				String scope = authInfo.getString("scope"); // scope值为：snsapi_base/snsapi_userinfo
//				if(!StringUtil.isEmpty(scope) && "snsapi_userinfo".equals(scope)) {
//					user = getUserBySnsapiUserinfo(wechatKeyVo, authInfo);
//				}
//				if(null == user) {
////					user = createVirtualUser(request,response); //by CC 20150824 如果是未关注用户，则创建一个虚拟用户。
//				}
//			} else {
//				user = userList.get(0);
//				if(user == null || user.get(User.OPENID) == null || user.get(User.OPENID).equals("")){
//					logger.info("2productId为｛"+productId+"｝, not current user, now save user.");
////					user = createVirtualUser(request,response); //by CC 20150824 如果是未关注用户，则创建一个虚拟用户。
//				} else {
//					String headImgUrl = String.valueOf((null != user.get(User.HEADIMGURL)?user.get(User.HEADIMGURL):""));
//					headImgUrl = (headImgUrl.endsWith("/0"))?(headImgUrl.substring(0, headImgUrl.length()-2)+"/64"):headImgUrl;
//					user.put(User.HEADIMGURL, headImgUrl);
//				}
//			}
//		} catch (Exception e) {
////			user = createVirtualUser(request,response);
//		}
//		return user;
//	}
	
	private DBObject getUserBySnsapiUserinfo(WechatKeyVo wechatKeyVo, JSONObject authInfo) throws Exception {
		String accessToken = authInfo.getString("access_token");
		String openId = authInfo.getString("openid");
		JSONObject userInfo = WeixinUtil.getUserInfoByAccessToken(accessToken, openId);
		logger.info("get userInfo by snsapi_userinfo is {"+userInfo.toString()+"}");
		
		userInfo.put(User.PRODUCTID, wechatKeyVo.getProductId());
		userInfo.put(User.CREATE_TIME, DateUtil.toISODateTimeString(new Date()));
		
		userInfo.put(User.SUBSCRIBE_TIME_SDF, DateUtil.toISODateTimeString(new Date()));
		userInfo.put(User.UNSUBSCRIBE_TIME_SDF, "");
		
		userInfo.put(User.SUBSCRIBE, Constants.ZERO); 
//		String result = baseDao.addObject(User.DB_COLLECTION, userInfo.toString());
//		DBObject user = baseDao.findObjectById(User.DB_COLLECTION, result);
		
		Map<String, Object> userMap = JsonUtil.readJSON2Map(String.valueOf(userInfo));
		return new BasicDBObject(userMap);
		
	}
	
	/**
	 * 首先确保你的long型日期/时间值是正确的，比如检测长度，是否少了最后的毫秒数，这个跟System.currentTimeMillis()返回的值对比一下就知道了，
	 * 比如1403931367，就少了最后的毫秒数，你可以手动补充完整，末尾加3个0，1403931367000
	 * @param l
	 * @return
	 */
	private String dataFormat(long l) {
		if(String.valueOf(System.currentTimeMillis()).length() > String.valueOf(l).length()) {
			l = Long.parseLong(String.valueOf(l) + "000");
		}
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		calendar.setTimeInMillis(l);
		
		return sdf.format(calendar.getTime());
	}
	
}
