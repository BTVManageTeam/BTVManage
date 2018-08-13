package com.cdvcloud.rochecloud.web.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cdvcloud.rochecloud.service.CoreBeanFactory;
import com.cdvcloud.rochecloud.service.UserMessageService;
import com.cdvcloud.rochecloud.service.WxUserService;
import com.cdvcloud.wechat.util.wechat.SignUtil;

@Controller
public class WeChatCallbackController {
	
	private static final Logger logger = Logger.getLogger(WeChatCallbackController.class);

	@Autowired
	private WxUserService userService;
	
	@Autowired
	private UserMessageService userMessageService;
	
	/**
	 * 确认请求来自微信服务器
	 * 
	 * 公众平台用户提交信息后，微信服务器将发送GET请求到填写的URL上，并且带上四个参数signature/timestamp/nonce/echostr
	 * 开发者通过检验signature对请求进行校验（下面有校验方式）。
	 * 若确认此次GET请求来自微信服务器，请原样返回echostr参数内容，则接入生效，否则接入失败。 
	 */
	
	@RequestMapping(value="coreController/{productId}/",method=RequestMethod.GET)
	public  void doGet(HttpServletRequest request,HttpServletResponse response, 
			@PathVariable String productId) throws Exception{
		logger.info("from wechat productId={"+productId+"}.");
		// 随机字符串
		String echostr = request.getParameter("echostr");

		PrintWriter out = response.getWriter();
		// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
		if (checkSignature(request)) {
			logger.info("校验通过, 确认此次GET请求来自微信服务器，请原样返回随机字符串echostr：" + echostr);
			out.print(echostr);
		}
		out.close();
		out = null;
	}
	
	/**
	 * 处理微信服务器发来的消息
	 * @throws Exception 
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value="coreController/{productId}/",method=RequestMethod.POST)
	private  void doPost(HttpServletRequest request,HttpServletResponse response,
			@PathVariable String productId) throws Exception {
		String respMessage = "";

		if (checkSignature(request)) {
			respMessage = CoreBeanFactory.getInstance(productId).processRequest(request, userService, userMessageService,productId);
		} else {
			logger.error("请求不是来自微信服务器，不予以处理！");
			respMessage = "请求不是来自微信服务器，不予以处理！";
		}

		if(null != respMessage && !"".equals(respMessage)) {
			logger.info("返回微信端信息---["+respMessage+"].");
			// 响应消息
			response.setContentType("application/xml;charset=UTF-8"); 
			PrintWriter out = response.getWriter();
			out.print(respMessage);
			out.close();
		}
	}
	
	/**
	 * /** 校验消息是否来自微信服务器端
	 * 
	 * @param request
	 * @return
	 */
	private boolean checkSignature(HttpServletRequest request) {
		// 微信加密签名
		String signature = request.getParameter("signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");

		boolean bFromWinxin = SignUtil.checkSignature(signature, timestamp,
				nonce);

		logger.info("校验消息是否来自微信服务器端。验证传参：signature=" + signature
				+ "; timestamp=" + timestamp + "; nonce=" + nonce + "; 验证结果："
				+ bFromWinxin);
		return bFromWinxin;
	}

}
