package com.cdvcloud.rochecloud.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author:lyh
 * @Description:
 * @Date:Created in 2018/8/11 17:49
 */
@Controller
@RequestMapping(value = "lawyer/")
public class LawyerController {

/*

	private static final Logger logger = Logger.getLogger(UserController.class);

	@Autowired
	UserService userService;

	*/
/**
	 * 分页展示用户信息
	 *
	 * @param request
	 * @param page
	 * @param model
	 * @return
	 *//*

	@RequestMapping(value = "findall/")
	public String findall(HttpServletRequest request, Pages<OpRmsUser> page, Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		String param = null;
		String strOperateFlag = "no";//是否允许用户继续创建用户
		try {
			params = ParamsUtil.getParamsMapWithTrim(request);
			param = PageParams.getConditionByCAS(request, params);
			param = param.replace("ownerBusCode", "u.ownerBusCode");
			page.setCondition(param);
			Integer totalNum = userService.countFindAll(page);
			page.setCondition(param);
			page.setTotalNum(totalNum);
			page.setList(userService.selectFindAll(page));

			*/
/**根据当前所属商标识获取所属商允许最大创建用户数量，超出则不允许继续新增用户 (by lyh 2018年3月22日20:25:31) *//*

			String companyName = UserUtil.getUserByRequest(request, Constants.COMPANY_NAME);
			int maxNum = 0;// 所属商允许创建的最大用户数量
			List<OpRmsOwnerBusiness> ownerBus = ownerBusinssService.selectByOwnerBusCode(companyName);
			if (null != ownerBus && 0 < ownerBus.size()) {
				OpRmsOwnerBusiness ownerBusiness = ownerBus.get(0);
				if (null != ownerBusiness) {
					maxNum = ownerBusiness.getMaxUserNum();
				}
			}
			if (page.getTotalNum() < maxNum) {
				strOperateFlag = "yes";
			}
		} catch (Exception e) {
			logger.error("查询用户信息异常！[" + e.getMessage() + "]");
		}
		model.addAttribute("page", page);
		model.addAttribute("params", params);
		model.addAttribute("strOperateFlag", strOperateFlag);
		return "user/users";
	}
*/


}
