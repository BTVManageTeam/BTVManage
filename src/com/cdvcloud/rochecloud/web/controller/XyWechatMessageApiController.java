package com.cdvcloud.rochecloud.web.controller;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cdvcloud.rochecloud.common.*;
import com.cdvcloud.rochecloud.mdomain.XyWechatMessage;
import com.cdvcloud.rochecloud.mongodao.BasicDao;
import com.cdvcloud.rochecloud.mongodao.QueryOperators;
import com.cdvcloud.rochecloud.util.DateUtil;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cdvcloud.rochecloud.domain.BtvLawyer;
import com.cdvcloud.rochecloud.mdomain.WechatTemplate;
import com.cdvcloud.rochecloud.schedule.ImportWechatTokenJob;
import com.cdvcloud.rochecloud.service.LawyerService;
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
	private final LawyerService lawyerService;
	private final BasicDao baseDao;

	/**
	 * @Description: spring注入
	 */
	@Autowired
	public XyWechatMessageApiController(XyWechatMessageService xyWechatMessageService,LawyerService lawyerService,BasicDao baseDao) {
		this.xyWechatMessageService = xyWechatMessageService;
		this.lawyerService = lawyerService;
		this.baseDao = baseDao;
	}

	/**
	 * 条件分页查询消息
	 *

	 * @param request
	 * @return
	 */
	@RequestMapping("v1/queryWechatMessage4page")
	public String queryWechatMessage4page(HttpServletRequest request, Pages<Map<String, Object>> page, Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		String param = null;
		try {
			params = ParamsUtil.getParamsMapWithTrim(request);
			List<Map<String, Object>> list = xyWechatMessageService.queryWechatMessage4page(params,page);
			page.setList(list);
			param = PageParams.getConditionByCASOld(request, params);
			page.setCondition(param);
			page.setCurrentPage(1);
			page.setTotalNum(xyWechatMessageService.queryWechatMessage4pageCount(params,page));
			model.addAttribute("page", page);
			model.addAttribute("params", params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "comment/commentList";
	}

	/**
	 * 回复微信公众号信息
	 *
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
	 * @return
	 */
	@RequestMapping("sendTemlateMessage/")
	@ResponseBody
	public ResponseObject sendTemlateMessage(HttpServletRequest request,HttpServletResponse response, @RequestBody String params){
		ResponseObject resObj = new ResponseObject();
		try {
			logger.info("推送模板参数 params"+params);
			Map<String, Object> paramMap = JsonUtil.readJSON2Map(params);
			String wxTemplateId = Configuration.getConfigValue("WXTEMPLATEID");
			AccessToken accessToken ;
			String accessTokenStr="";
			String productId = Configuration.getConfigValue("PRODUCTID");
			Map<String, Object> obj = (Map<String, Object>)ImportWechatTokenJob.wechatKeyMap.get(productId);
			accessToken=(AccessToken)obj.get("accessToken");
			accessTokenStr=accessToken.getAccess_token();
			//查询律师信息
			BtvLawyer btvLawyer = lawyerService.selectByPrimaryKey(String.valueOf(paramMap.get("lawyerId")));

			//将参数传给公众号

			Map<String, Object> mapPara=new HashMap<String, Object>();
			mapPara.put(WechatTemplate.USERID, "");
			mapPara.put(WechatTemplate.TOUSER, paramMap.get("openId"));
			mapPara.put(WechatTemplate.URL, Configuration.getConfigValue("TEMPLATEURL")+btvLawyer.getLawyerId());
			mapPara.put(WechatTemplate.TEMPLATEID, wxTemplateId);
			mapPara.put(WechatTemplate.FIRST, "精选律师推荐");
			mapPara.put(WechatTemplate.REMARK, "点击链接预约该律师");
			mapPara.put(WechatTemplate.KEYWORD+"1",btvLawyer.getLawyerName());
			mapPara.put(WechatTemplate.KEYWORD+"2",btvLawyer.getProfessionalYear());
			mapPara.put(WechatTemplate.KEYWORD+"3",btvLawyer.getSpeciality());
			Map<String,Object> map=WechatTemplate.weChatTemplate(mapPara);
			String json=JsonUtil.map2Json(map);
			
			JSONObject data= WeixinUtil.sendTemplate(accessTokenStr, json);
			logger.info("推送模板参数 errcode"+String.valueOf(data.get("errcode")));
			if("0".equals(String.valueOf(data.get("errcode")))){
				logger.info("走进成功");
				//保存评论数据
				Map<String, Object> filter = new ConcurrentHashMap<>(16);
				String id = String.valueOf(paramMap.get(Constants.ID));
				filter.put(XyWechatMessage.ID,new ObjectId(id));
				Map<String, Object> update = new ConcurrentHashMap<>(16);
				Map<String, Object> replayContent = objectToMap(btvLawyer);

				replayContent.put(XyWechatMessage.CARD,"1");

				replayContent.put(XyWechatMessage.CTIME, DateUtil.getCurrentDateTime());
				update.put(QueryOperators.ADDTOSET,new Document(XyWechatMessage.REPLAYCONTENT,replayContent));
				update.put(QueryOperators.SET,new Document(XyWechatMessage.REPLAYED, Constants.SONE));
				baseDao.updateOne(XyWechatMessage.XYWECHATMESSAGE, filter, update);
				resObj.setCode(0);
//				resObj.setData(data);
			}else{
				logger.info("走进失败");
				resObj.setCode(1);

//				resObj.setData(data);
			}
			return resObj;
		} catch (Exception e) {
			e.printStackTrace();
			resObj.setCode(1);

//			resObj.setData(e.getMessage());
			return resObj;
		}

	
	}


	/**
	 * 获取利用反射获取类里面的值和名称
	 *
	 * @param obj
	 * @return
	 * @throws IllegalAccessException
	 */
	public static Map<String, Object> objectToMap(Object obj) throws IllegalAccessException {
		Map<String, Object> map = new HashMap<>();
		Class<?> clazz = obj.getClass();
		System.out.println(clazz);
		for (Field field : clazz.getDeclaredFields()) {
			field.setAccessible(true);
			String fieldName = field.getName();
			Object value = field.get(obj);
			map.put(fieldName, value);
		}
		return map;
	}

}
