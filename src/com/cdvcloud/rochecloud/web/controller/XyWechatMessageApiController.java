package com.cdvcloud.rochecloud.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cdvcloud.rochecloud.common.Pages;
import com.cdvcloud.rochecloud.common.ParamsUtil;
import com.cdvcloud.rochecloud.common.ResponseObject;
import com.cdvcloud.rochecloud.mdomain.WechatTemplate;
import com.cdvcloud.rochecloud.schedule.ImportWechatTokenJob;
import com.cdvcloud.rochecloud.service.XyWechatMessageService;
import com.cdvcloud.rochecloud.util.Configuration;
import com.cdvcloud.rochecloud.util.JsonUtil;
import com.cdvcloud.wechat.bean.AccessToken;
import com.cdvcloud.wechat.util.wechat.WeixinUtil;

/**
 * 公众号消息管理
 *
 * @Auther: zhangyuelong
 * @Date: 2018/7/9 18:38
 * @Description:
 */
@Controller
@RequestMapping("api/xy/wechatMessage")
public class XyWechatMessageApiController {


	private static final Logger logger = Logger.getLogger(XyWechatMessageApiController.class);

	private final XyWechatMessageService xyWechatMessageService;

	/**
	 * @Description: spring注入
	 */
	@Autowired
	public XyWechatMessageApiController(XyWechatMessageService xyWechatMessageService) {
		this.xyWechatMessageService = xyWechatMessageService;
	}

	/**
	 * 条件分页查询消息
	 *
	 * @param instanceId
	 * @param requestId
	 * @param ip
	 * @param uid
	 * @param commonParameters 公共参数
	 * @param str              请求参数
	 * @param request
	 * @return
	 */
	@RequestMapping("v1/queryWechatMessage4page")
	public String queryWechatMessage4page(HttpServletRequest request, Pages<Map<String, Object>> page, Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			params = ParamsUtil.getParamsMapWithTrim(request);
			List<Map<String, Object>> list = xyWechatMessageService.queryWechatMessage4page(params,page);
			
			page.setList(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "";
	}

	/**
	 * 回复微信公众号信息
	 *
	 * @param instanceId
	 * @param requestId
	 * @param ip
	 * @param uid
	 * @param commonParameters
	 * @param str
	 * @return
	 */
	@RequestMapping("v1/replyWechatMessage")
	@ResponseBody
	public ResponseObject replyWechatMessage(HttpServletRequest request, @RequestBody String str) {

		ResponseObject resObj = new ResponseObject();

		try {
			logger.info(";json:" + str);
			Map<String, Object> mapJson = JsonUtil.readJSON2Map(str);

			long influenceNum  = xyWechatMessageService.replyWechatMessage( mapJson,request);
			Map<String, Object> result = new ConcurrentHashMap<>(16);
			result.put("influenceNum", influenceNum);
			resObj.setCode(0);
			resObj.setData(result);
			return resObj;
		} catch (Exception e) {
			resObj.setCode(-1);
			e.printStackTrace();
			logger.error("context", e);
			return resObj;
		}
	}
	
	/**
	 * 推送模板消息
	 * @param request
	 * @param callbackParams
	 * @param productId
	 * @return
	 */
	@RequestMapping("sendTemlateMessage/")
	@ResponseBody
	public ResponseObject sendTemlateMessage(HttpServletRequest request,HttpServletResponse response, @RequestBody String callbackParams){
		ResponseObject resObj = new ResponseObject();
		try {
			String wxTemplateId = Configuration.getConfigValue("WXTEMPLATEID");
			AccessToken accessToken ;
			String accessTokenStr="";
			String productId = Configuration.getConfigValue("PRODUCTID");
			Map<String, Object> obj = (Map<String, Object>)ImportWechatTokenJob.wechatKeyMap.get(productId);
			accessToken=(AccessToken)obj.get("accessToken");
			accessTokenStr=accessToken.getAccess_token();
			//将参数传给公众号
			logger.info("推送模板参数 callbackParams"+callbackParams);
			Map<String, Object> mapPara=new HashMap<String, Object>();
			mapPara.put(WechatTemplate.USERID, "");
			mapPara.put(WechatTemplate.TOUSER, "o8Cth1tMLxZBCNlyeo9co-HocZe8");
			mapPara.put(WechatTemplate.URL, "https://baidu.com");
			mapPara.put(WechatTemplate.TEMPLATEID, wxTemplateId);
			mapPara.put(WechatTemplate.FIRST, "呵呵哒");
			mapPara.put(WechatTemplate.REMARK, "点击链接查看");
			mapPara.put(WechatTemplate.KEYWORD+"1","1234");
			mapPara.put(WechatTemplate.KEYWORD+"2","5678" );
			Map<String,Object> map=WechatTemplate.weChatTemplate(mapPara);
			String json=JsonUtil.map2Json(map);
			
			JSONObject data= WeixinUtil.sendTemplate(accessTokenStr, json);
			logger.info("推送模板参数 errcode"+String.valueOf(data.get("errcode")));
			if("0".equals(String.valueOf(data.get("errcode")))){
				logger.info("走进成功");
				resObj.setCode(0);
				resObj.setMessage("成功");
				resObj.setData(data);
			}else{
				logger.info("走进失败");
				resObj.setCode(1);
				resObj.setMessage("失败");
				resObj.setData(data);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			resObj.setCode(1);
			resObj.setMessage("失败");
			resObj.setData(e.getMessage());
		}
		return resObj;
	
	}
}
