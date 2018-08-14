package com.cdvcloud.rochecloud.service;

import com.cdvcloud.rochecloud.common.Pages;
import com.cdvcloud.rochecloud.domain.BtvComment;
import com.cdvcloud.rochecloud.domain.BtvCommentReply;

import java.util.List;

/**
* @ClassName:
* @Description: 评论管理service接口
* @Author: makang makang@cdvcloud.com
* @date:     2018/8/14 9:58
*/
public interface CommentService {

    /**
     * 分页查询总数
     * @param page
     * @return
     */
    int countFindAll(Pages<BtvComment> page);

    /**
     * 分页查询所有数据
     * @param page
     * @return
     */
    List<BtvComment> selectFindAll(Pages<BtvComment> page);

    /**
     * 向回复表插入数据并向微信推送数据
     * @param btvCommentReply
     * @return
     */
    int insertCommentReply(BtvCommentReply btvCommentReply);

}
