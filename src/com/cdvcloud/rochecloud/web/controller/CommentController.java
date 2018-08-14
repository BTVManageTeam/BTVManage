package com.cdvcloud.rochecloud.web.controller;

import com.cdvcloud.rochecloud.common.Constants;
import com.cdvcloud.rochecloud.common.PageParams;
import com.cdvcloud.rochecloud.common.Pages;
import com.cdvcloud.rochecloud.common.ParamsUtil;
import com.cdvcloud.rochecloud.domain.BtvComment;
import com.cdvcloud.rochecloud.domain.BtvCommentReply;
import com.cdvcloud.rochecloud.service.CommentService;
import com.cdvcloud.rochecloud.service.LawyerService;
import com.cdvcloud.rochecloud.util.DateUtil;
import com.cdvcloud.rochecloud.util.UUIDUtil;
import com.cdvcloud.rochecloud.util.UserUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @Autowired
    private LawyerService lawyerService;

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
            param = PageParams.getConditionByCASOld(request, params);
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

    /**
     * 分页展示律师相关内容
     * @param request
     * @param page
     * @param model
     * @return
     */
    @RequestMapping(value = "queryLawyer/")
    public String queryLawyer(HttpServletRequest request, Pages<Map<String, Object>> page, Model model,
                            @RequestParam(value = "commentId") String commentId) {
        Map<String, Object> params = new HashMap<String, Object>();
        String param = null;

        try {
            params = ParamsUtil.getParamsMapWithTrim(request);
            param = PageParams.getConditionByCAS(request, params);
            String roleCode = UserUtil.getUserByRequest(request, Constants.ROLE_CODE);
            int code = Integer.valueOf(roleCode);
            page.setCondition(param);
            String strUserId = UserUtil.getUserByRequest(request, Constants.CURRENT_USER_ID);
            if (code == Constants.ZERO) {
                page.setTempParam(strUserId);
            }
            model.addAttribute("roleCode", roleCode);
            model.addAttribute("strUserId", strUserId);
            model.addAttribute("commentId", commentId);
            Integer totalNum = lawyerService.countFindAll(page);
            page.setTotalNum(totalNum);
            page.setList(lawyerService.selectFindAll(page));
        } catch (Exception e) {
            logger.error("查询信息异常！[" + e.getMessage() + "]");
        }
        model.addAttribute("page", page);
        model.addAttribute("params", params);
        return "comment/lawyerList";
    }

    /**
     * 向回复表插入数据并向微信推送数据
     * @param request
     * @param commentId
     * @param lawyerId
     * @param txtContent
     * @return
     */
    @RequestMapping(value = "insertCommentReply")
    @ResponseBody
    public String insertCommentReply(HttpServletRequest request,
                                     @RequestParam(value = "commentId") String commentId,
                                     @RequestParam(value = "lawyerId") String lawyerId,
                                     @RequestParam(value = "txtContent") String txtContent) {
        try {
            BtvCommentReply btvCommentReply = new BtvCommentReply();
            String strUserId = UserUtil.getUserByRequest(request, Constants.CURRENT_USER_ID);
            String replayId = UUIDUtil.randomUUID();
            btvCommentReply.setReplayId(replayId);
            btvCommentReply.setCommentId(commentId);
            if("".equals(lawyerId)){
                btvCommentReply.setTxtContent(txtContent);
                btvCommentReply.setStatus(Constants.ONE);
            }else{
                btvCommentReply.setUserId(lawyerId);
                btvCommentReply.setStatus(Constants.ZERO);
            }
            btvCommentReply.setCreateTime(DateUtil.getCurrentDateTime());
            btvCommentReply.setCreateUserId(strUserId);
            int num = commentService.insertCommentReply(btvCommentReply);
            if(num>0){
                return "success";
            }
        } catch (Exception e) {
            logger.error("向回复表插入数据并向微信推送数据异常！[" + e.getMessage() + "]");
        }
        return "error";
    }

}
