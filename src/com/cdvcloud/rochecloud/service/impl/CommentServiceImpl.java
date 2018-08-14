package com.cdvcloud.rochecloud.service.impl;

import com.cdvcloud.rochecloud.common.Pages;
import com.cdvcloud.rochecloud.domain.BtvComment;
import com.cdvcloud.rochecloud.domain.BtvCommentReply;
import com.cdvcloud.rochecloud.domain.BtvLawyer;
import com.cdvcloud.rochecloud.mapper.BtvCommentMapper;
import com.cdvcloud.rochecloud.mapper.BtvCommentReplyMapper;
import com.cdvcloud.rochecloud.mapper.BtvLawyerMapper;
import com.cdvcloud.rochecloud.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @ClassName: 
* @Description: 评论管理service实现类
* @Author: makang makang@cdvcloud.com
* @date:     2018/8/14 9:57
*/
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private BtvCommentMapper btvCommentMapper;
    @Autowired
    private BtvCommentReplyMapper btvCommentReplyMapper;
    @Autowired
    private BtvLawyerMapper btvLawyerMapper;

    /**
     * 分页查询总数
     * @param page
     * @return
     */
    @Override
    public int countFindAll(Pages<BtvComment> page) {
        return btvCommentMapper.countFindAll(page);
    }

    /**
     * 分页查询所有数据
     * @param page
     * @return
     */
    @Override
    public List<BtvComment> selectFindAll(Pages <BtvComment> page) {
        List<BtvComment> btvCommentList = btvCommentMapper.selectFindAll(page);
        List<BtvComment> btvCommentList1 = new ArrayList <BtvComment>();
        for (BtvComment btvCommentMap:btvCommentList) {
            Pages<BtvCommentReply> btvCommentReplyPages = new Pages<BtvCommentReply>();
            String condition = "commentId = '"+btvCommentMap.getCommentId()+"'";
            btvCommentReplyPages.setCondition(condition);
            List<BtvCommentReply> btvCommentReplyList = btvCommentReplyMapper.selectByCondition(btvCommentReplyPages);
            for (BtvCommentReply btvCommentReply:btvCommentReplyList) {
                if(btvCommentReply.getStatus() == 0){
                    BtvLawyer btvLawyer = btvLawyerMapper.selectByPrimaryKey(btvCommentReply.getUserId());
                    btvCommentReply.setLawyerMap(btvLawyer);
                }
            }
            btvCommentMap.setBtvCommentReplyList(btvCommentReplyList);
            btvCommentList1.add(btvCommentMap);
        }
        return btvCommentList1;
    }

    /**
     * 向回复表插入数据并向微信推送数据
     * @param btvCommentReply
     * @return
     */
    @Override
    public int insertCommentReply(BtvCommentReply btvCommentReply) {
        //TODO 向微信推送数据
        return btvCommentReplyMapper.insert(btvCommentReply);
    }

}
