package com.cdvcloud.rochecloud.web.controller;

import com.cdvcloud.rochecloud.common.Constants;
import com.cdvcloud.rochecloud.common.PageParams;
import com.cdvcloud.rochecloud.common.Pages;
import com.cdvcloud.rochecloud.common.ParamsUtil;
import com.cdvcloud.rochecloud.domain.BtvLawyer;
import com.cdvcloud.rochecloud.service.LawyerService;
import com.cdvcloud.rochecloud.util.UserUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author:lyh
 * @Description:
 * @Date:Created in 2018/8/11 17:49
 */
@Controller
@RequestMapping(value = "lawyer/")
public class LawyerController {


	private static final Logger logger = Logger.getLogger(LawyerController.class);

	@Autowired
	LawyerService lawyerService;

	/**
	 * 分页展示用户信息
	 *
	 * @param request
	 * @param page
	 * @param model
	 * @return
	 */


	@RequestMapping(value = "findall/")
	public String findall(HttpServletRequest request, Pages<BtvLawyer> page, Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		String param = null;

		try {
			params = ParamsUtil.getParamsMapWithTrim(request);
			param = PageParams.getConditionByCAS(request, params);
			page.setCondition(param);
			Integer totalNum = lawyerService.countFindAll(page);
			page.setCondition(param);
			page.setTotalNum(totalNum);
			page.setList(lawyerService.selectFindAll(page));


		} catch (Exception e) {
			logger.error("查询信息异常！[" + e.getMessage() + "]");
		}
		model.addAttribute("page", page);
		model.addAttribute("params", params);
		return "user/users";
	}


}
