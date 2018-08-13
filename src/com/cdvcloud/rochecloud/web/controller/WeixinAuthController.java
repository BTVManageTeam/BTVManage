package com.cdvcloud.rochecloud.web.controller;

import java.security.MessageDigest;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cdvcloud.rochecloud.common.Constants;
import com.cdvcloud.rochecloud.common.ResponseObject;
import com.cdvcloud.rochecloud.domain.wechar.WechatKey;
import com.cdvcloud.rochecloud.domain.wechar.WechatKeyVo;
import com.cdvcloud.rochecloud.mdomain.User;
import com.cdvcloud.rochecloud.service.InitConfigService;
import com.cdvcloud.rochecloud.service.WxUserService;
import com.cdvcloud.rochecloud.util.StringUtil;
import com.cdvcloud.wechat.bean.Ticket;
import com.mongodb.BasicDBObject;

/**
 * 微信授权，获取用户信息
 * @author cc
 *
 */
@Controller
@RequestMapping("weixinauth/")
public class WeixinAuthController {
	private static final Logger logger = Logger.getLogger(WeixinAuthController.class);
	
	@Autowired
	private WxUserService userService;
	
	/**
	 * 授权获取用户信息
	 * 
	 * @param request
	 * @param response
	 * @param productId
	 * @param url
	 * @return
	 */
	@RequestMapping("getUserInfo/")
	@ResponseBody
	public ResponseObject getUserInfo(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("productId") String productId, @RequestParam("code") String code) {
		ResponseObject resObject = new ResponseObject(Constants.SUCCESS, "", null);
		try {
			Map<String, Object> userInfo = userService.userIsExist(code, "state", productId, request, response);
			resObject.setData(userInfo);
		} catch (Exception e) {
			resObject.setCode(Constants.ERROR);
			logger.error("获取用户信息出现错误，errMsg=["+e+"].");
			e.printStackTrace();
		}
		return resObject;
	}
	
	@RequestMapping("getAppId/")
	@ResponseBody
	public ResponseObject getAppId(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("productId") String productId) {
		ResponseObject resObject = new ResponseObject(Constants.SUCCESS, "", null);
		try {
			WechatKeyVo wechat = InitConfigService.getWechatKeyByProductId(productId);
			Map<String, Object> result = new HashMap<String, Object>();
			result.put(WechatKey.APPID, wechat.getAppid());
			result.put(WechatKey.PRODUCTID, productId);
			result.put(WechatKey.INITACCESSTOKENFLAG, wechat.getInitAccessTokenFlag());
			resObject.setData(result);
		} catch (Exception e) {
			resObject.setCode(Constants.ERROR);
			logger.error("获取微信认证信息出现错误，errMsg=["+e+"].");
			e.printStackTrace();
		}
		return resObject;
	}
	
	/**
	 * 业务描述：获取js-ticket
	 * 
	 * @param request
	 * @param response
	 * @param productId
	 * @param url 当前分享的url
	 * @return
	 */
	@RequestMapping("getJsTiketParam/")
	@ResponseBody
	public ResponseObject getJS_SDKParam(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("productId") String productId, @RequestParam("url") String url) {
		ResponseObject resObject = new ResponseObject(Constants.SUCCESS, "", null);
		try {
			WechatKeyVo vo = InitConfigService.getWechatKeyByProductId(productId);
			Ticket jsapi_ticket = vo.getTicket();
			// 随机字符串
			String nonceStr = UUID.randomUUID().toString();
			// 时间戳
			String timestamp = Long.toString(System.currentTimeMillis() / 1000);
			// 注意这里参数名必须全部小写，且必须有序
			String string1 = "jsapi_ticket=" + jsapi_ticket.getTicket() + "&noncestr="+ nonceStr + "&timestamp=" + timestamp + "&url=" + url;
			String signature = "";
			try {
				MessageDigest crypt = MessageDigest.getInstance("SHA-1");
				crypt.reset();
				crypt.update(string1.getBytes("UTF-8"));
				signature = byteToHex(crypt.digest());
			} catch (Exception e) {
				logger.error("get js-ticket exception, errMsg={"+e+"}.");
				e.printStackTrace();
			}
			Map<String, String> params = new HashMap<String, String>();
			params.put("url", url);
			params.put("jsapi_ticket", jsapi_ticket.getTicket());
			params.put("nonceStr", nonceStr);
			params.put("timestamp", timestamp);
			params.put("signature", signature);
			params.put("appId",vo.getAppid());
			resObject.setData(params);
		} catch (Exception e) {
			resObject.setCode(Constants.ERROR);
			logger.error("获取微信JsTicket认证信息出现错误，errMsg=["+e+"].");
			e.printStackTrace();
		}
		return resObject;
	}
	
	/**
	 * @Title: getNewUserInfo
	 * @Description: 根据openid和productId获取最新的用户信息
	 * @param request
	 * @param response
	 * @param productId
	 * @param openid
	 * @return   
	 * @throws
	 * @author syd
	 * @date 2017-5-9 下午3:50:50
	 */
	@RequestMapping("getNewUserInfo/")
	@ResponseBody
	public ResponseObject getNewUserInfo(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("productId") String productId,@RequestParam("openid") String openid) {
		ResponseObject resObject = new ResponseObject(Constants.SUCCESS, "", null);
		try {
			if(StringUtil.isEmpty(productId) || StringUtil.isEmpty(openid)){
				resObject.setCode(Constants.ERROR);
				resObject.setMessage("查询参数为空!");
				return resObject;
			}
			
			Map<String, Object> filterMap = new HashMap<String, Object>();
			filterMap.put(User.OPENID, openid);
			filterMap.put(User.PRODUCTID, productId);
			Map<String, Object> user = userService.findUserOne(filterMap);
			if(null == user){
				user = new BasicDBObject();
			}
			resObject.setData(user);
		} catch (Exception e) {
			resObject.setCode(Constants.ERROR);
			logger.error("获取最新的用户的信息出现错误!["+ e +"]");
			e.printStackTrace();
		}
		return resObject;
	}
	
	private String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}
}
