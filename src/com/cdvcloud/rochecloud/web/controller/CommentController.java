package com.cdvcloud.rochecloud.web.controller;

import com.cdvcloud.rochecloud.common.PageParams;
import com.cdvcloud.rochecloud.common.Pages;
import com.cdvcloud.rochecloud.common.ParamsUtil;
import com.cdvcloud.rochecloud.domain.BtvComment;
import com.cdvcloud.rochecloud.service.CommentService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
* @ClassName:
* @Description: 评论管理controller类
* @Author: makang makang@cdvcloud.com
* @date:     2018/8/14 9:56
*/
@Controller
@RequestMapping(value = "comment/")
public class CommentController {

    private static final Logger logger = Logger.getLogger(CommentController.class);

    @Autowired
    private CommentService commentService;

    /**
     * 分页查询订单信息-服务管理页面根据律师查询的订单列表
     * @param request
     * @param page
     * @param model
     * @return
     */
    @RequestMapping(value = "queryCommentPage")
    public String queryOrderPage(HttpServletRequest request, Pages<BtvComment> page, Model model) {
        Map<String, Object> params = new HashMap<String, Object>();
        String param = null;
        try {
            params = ParamsUtil.getParamsMapWithTrim(request);
            param = PageParams.getConditionByCAS(request, params);
            page.setCondition(param);
            Integer totalNum = commentService.countFindAll(page);
            page.setTotalNum(totalNum);
            page.setList(commentService.selectFindAll(page));
        } catch (Exception e) {
            logger.error("查询信息异常！[" + e.getMessage() + "]");
        }
        model.addAttribute("page", page);
        model.addAttribute("params", params);
        return "comment/commentList";
    }

}
