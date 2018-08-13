package com.cdvcloud.rochecloud.schedule;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdvcloud.rochecloud.domain.wechar.AccessTokenM;
import com.cdvcloud.rochecloud.domain.wechar.TicketTokenM;
import com.cdvcloud.rochecloud.domain.wechar.WechatKey;
import com.cdvcloud.rochecloud.service.AccessTokenService;
import com.cdvcloud.rochecloud.service.TicketTokenService;
import com.cdvcloud.rochecloud.util.Configuration;
import com.cdvcloud.rochecloud.util.StringUtil;
import com.cdvcloud.wechat.bean.AccessToken;
import com.cdvcloud.wechat.bean.Ticket;
import com.cdvcloud.wechat.util.wechat.WeixinUtil;

/**
 * 初始化微信token
 * @author cdvcloud
 *
 */
@Service("importWechatTokenJob")
public class ImportWechatTokenJob {
	private static Logger logger = Logger.getLogger(ImportWechatTokenJob.class);	

	public static Map<String, Object> wechatKeyMap = new ConcurrentHashMap<String, Object>();


	@Autowired
	private AccessTokenService accessTokenService;

	@Autowired
	private TicketTokenService ticketTokenService;

	private static SimpleDateFormat sdf_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@PostConstruct
	public void execute(){
		logger.info("===========初始化微信token启动 start==========");
		initWechatKeyTask(); //初始化wechatKey对象
		logger.info("===========初始化微信token启动 end==========");
	}

	/**
	 * 业务描述：起一个线程，每隔30分钟从数据库获取wechatKey内容，更新一下缓存
	 */
	public void initWechatKeyTask() {
		try {
			//获取所有的项目对象
			//			ResponseObject resObject = wechatKeyService.find(filter);
			String productId = Configuration.getConfigValue("PRODUCTID");
			String appid = Configuration.getConfigValue("APPID");
			String appsecret = Configuration.getConfigValue("APPSECRET");
			if(null==productId || StringUtil.isEmpty(productId)){
				logger.error("初始化项目productId没有配置");
				return;
			}
			if(null==appid || StringUtil.isEmpty(appid)){
				logger.error("初始化项目appid没有配置");
				return;
			}
			if(null==appsecret || StringUtil.isEmpty(appsecret)){
				logger.error("初始化项目appsecret没有配置");
				return;
			}
			try {
				initWechatKey(productId, appid, appsecret);;
			} catch (Exception e) {
				logger.error("初始化项目productId=["+productId+"]出现异常，请排查原因，errMsg=["+e+"].");
				e.printStackTrace();
			}
		} catch (Exception e) {
			logger.error("quratz get weixin access_token is task error，{" + e + "}");
			e.printStackTrace();
		}
	}

	public void initWechatKey(String productId , String appid , String appsecret) throws Exception {

		AccessToken accessToken = new AccessToken();
		Ticket ticket = new Ticket();
		accessToken.setProductId(productId);
		ticket.setProductId(productId);

		//如果非0，则需要进行初始化
		if(!StringUtil.isEmpty(appid) && !StringUtil.isEmpty(appsecret)) {
			initAccessToken(productId, appid, appsecret, accessToken);
			if(null != accessToken && !StringUtil.isEmpty(accessToken.getAccess_token())) {
				initTicketToken(productId, appid, appsecret, ticket, accessToken);
			}
		}

				putWechatKeyCache(productId, accessToken, ticket);
	}

	private AccessToken initAccessToken(String productId, String appid, String appsecret, AccessToken accessToken) throws Exception {
		boolean updateFlag = true; //是否更新标记
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(WechatKey.PRODUCTID, productId);
		AccessTokenM where = null;
		AccessTokenM accessTokenM = accessTokenService.findAccessTokenM(where);

			int currentHours = Calendar.getInstance(Locale.CHINA).get(Calendar.HOUR_OF_DAY);
			int dbHours = Integer.parseInt(String.valueOf(accessTokenM.getHours()));
			Map<String, Object> vo = getWechatKeyByProductId(productId);
			AccessToken token = (null != vo)?(AccessToken)vo.get("accessToken"):null;


			if((null == getWechatKeyByProductId(productId)) && ((currentHours-dbHours) >= 1)) {
				updateFlag = true; //启动初始化且accessToken超过一个小时了，需要更新库
			} else if((null != vo) && ((currentHours-dbHours) != 0)
					&& (null != token && (String.valueOf(accessTokenM.getAccessToken())).equals(token.getAccess_token()))) {
				updateFlag = true; //启动一个小时了，且数据库的accessToken等于缓存的accessToken时，需要更新库
			} else {
				//剩下的情况，数据库信息覆盖一次缓存
				accessToken.setAccess_token(String.valueOf(accessTokenM.getAccessToken()));
				accessToken.setCreate_time(String.valueOf(accessTokenM.getCreateTime()));
				accessToken.setExpires_in(Integer.parseInt(String.valueOf(accessTokenM.getExpiresIn())));
				accessToken.setHours(Integer.parseInt(String.valueOf(accessTokenM.getHours())));
				accessToken.setProductId(productId);
			}

		if(updateFlag) {
			//每次休眠完成后，都需要重新获取access_token，确保信息正确。
			AccessToken object = null;
			try {
				object = WeixinUtil.getAccessToken(appid, appsecret);
			} catch (Exception e) {
				//再试一次
				try {
					object = WeixinUtil.getAccessToken(appid, appsecret);
				} catch (Exception e2) {
					logger.error("微信号["+productId+"]的appid=["+appid+"]和appsecret=["+appsecret+"]在授权时有错误，需要确定是否正确，errMsg=["+e2+"].");
					e2.printStackTrace();
				}
			}
			if(object == null) {
				return null;
			}
			logger.info("GET access_token success, productId is [" +productId+ "], " +
					"duration is {" + object.getExpires_in() + "}second, token:{" + object.getAccess_token() + "}");
			accessToken.setAccess_token(object.getAccess_token());
			accessToken.setExpires_in(object.getExpires_in());
			accessToken.setCreate_time(sdf_date.format(new Date()));
			accessToken.setHours(Calendar.getInstance(Locale.CHINA).get(Calendar.HOUR_OF_DAY));
			accessToken.setProductId(productId);

			//接着更新数据库
			AccessTokenM setParams = new AccessTokenM();
			setParams.setAccessToken(accessToken.getAccess_token());
			setParams.setCreateTime(accessToken.getCreate_time());
			setParams.setExpiresIn(accessToken.getExpires_in());
			setParams.setHours(accessToken.getHours());
			setParams.setProductId(productId);
			if(null == accessTokenM || !StringUtil.isEmpty(accessTokenM.getAccessToken())) {
				accessTokenService.addAccessTokenM(setParams);
			} else {
				Map<String, Object> whereMap = new HashMap<String, Object>();
				whereMap.put(AccessTokenM.PRODUCTID, productId);
				accessTokenService.updateAccessTokenM(setParams);
			}
			logger.info("GET access_token success, productId is [" +productId+ "], " +
					"执行完毕");
		}
		return accessToken;
	}

	private Ticket initTicketToken(String productId, String appid, String appsecret, Ticket ticket, AccessToken accessToken) throws Exception {
		boolean updateFlag = true; //是否更新标记
		TicketTokenM params = new TicketTokenM();
		params.setProductId(productId);
		TicketTokenM ticketTokenM = ticketTokenService.findTicketTokenM(params);

			int currentHours = Calendar.getInstance(Locale.CHINA).get(Calendar.HOUR_OF_DAY);
			int dbHours = Integer.parseInt(String.valueOf(ticketTokenM.getHours()));
			Map<String, Object> vo = getWechatKeyByProductId(productId);
			Ticket tk = (null != vo)?(Ticket)vo.get("ticket"):null;

			if((null == vo) && ((currentHours-dbHours) >= 1)) {
				updateFlag = true; //启动初始化且ticketToken超过一个小时了，需要更新库
			} else if((null != vo) && ((currentHours-dbHours) != 0)
					&& (null != tk &&(String.valueOf(ticketTokenM.getTicket())).equals(tk.getTicket()))) {
				updateFlag = true; //启动一个小时了，且数据库的ticketToken等于缓存的ticketToken时，需要更新库
			} else {
				//剩下的情况，数据库信息覆盖一次缓存
				ticket.setTicket(String.valueOf(ticketTokenM.getTicket()));
				ticket.setCreate_time(String.valueOf(ticketTokenM.getCreateTime()));
				ticket.setExpires_in(Integer.parseInt(String.valueOf(ticketTokenM.getExpiresIn())));
				ticket.setHours(Integer.parseInt(String.valueOf(ticketTokenM.getHours())));
				ticket.setProductId(productId);
			}

		if(updateFlag) {
			//每次休眠完成后，都需要重新获取ticket，确保信息正确。
			Ticket object = null;
			try {
				long startTime=System.currentTimeMillis();
				logger.info(productId+"调用微信获取开始时间："+startTime);
				object = WeixinUtil.getJSSDKTicket(accessToken.getAccess_token());
				long endTime=System.currentTimeMillis();
				logger.info(productId+"调用微信获取结束时间："+endTime+";当前程序耗时："+(endTime-startTime)+"ms");

			} catch (Exception e) {
				//再试一次
				try {
					long startTime=System.currentTimeMillis();
					logger.info(productId+"调用微信获取重试开始时间："+startTime);
					object = WeixinUtil.getJSSDKTicket(accessToken.getAccess_token());
					long endTime=System.currentTimeMillis();
					logger.info(productId+"调用微信获取重试结束时间："+endTime+";当前程序耗时："+(endTime-startTime)+"ms");
				} catch (Exception e2) {
					logger.error("微信号["+productId+"]在获取jsapi_ticket授权时有错误，需要确定是否正确，errMsg=["+e2+"].");
					e2.printStackTrace();
				}
			}
			if(object == null) {
				logger.info("###########调用微信获取返回object为空###########");
				return null;
			}
			logger.info("GET jsapi_ticket success, productId is [" +productId+ "], " +
					"duration is {" + object.getExpires_in() + "}second, ticket:{" + object.getTicket() + "}");
			ticket.setTicket(object.getTicket());
			ticket.setExpires_in(object.getExpires_in());
			ticket.setCreate_time(sdf_date.format(new Date()));
			ticket.setHours(Calendar.getInstance(Locale.CHINA).get(Calendar.HOUR_OF_DAY));
			ticket.setProductId(productId);

			//接着更新数据库
			TicketTokenM setParams = new TicketTokenM();
			setParams.setTicket(ticket.getTicket());
			setParams.setCreateTime(ticket.getCreate_time());
			setParams.setExpiresIn(ticket.getExpires_in());
			setParams.setHours(ticket.getHours());
			setParams.setProductId(productId);
			if(null == ticketTokenM || !StringUtil.isEmpty(ticketTokenM.getTicket())) {
				ticketTokenService.addTicketTokenM(setParams);
			} else {
				ticketTokenService.updateTicketTokenM(setParams);
			}
			logger.info("GET jsapi_ticket success, productId is [" +productId+ "], " +
					"执行完毕");
		}

		return ticket;
	}

	private void putWechatKeyCache(String productId, AccessToken accessToken, Ticket ticket) {
		Map<String, Object> value = new ConcurrentHashMap<String, Object>();
		value.put("accessToken", accessToken);
		value.put("ticket", ticket);
		wechatKeyMap.put(productId, value);
		
	}

	private Map<String, Object> getWechatKeyByProductId(String productId) {
		
		return (ConcurrentHashMap)wechatKeyMap.get(productId);
	}

}
