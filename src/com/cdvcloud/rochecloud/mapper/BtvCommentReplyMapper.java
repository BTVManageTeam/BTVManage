package com.cdvcloud.rochecloud.mapper;

import com.cdvcloud.rochecloud.common.Pages;
import com.cdvcloud.rochecloud.domain.BtvCommentReply;

import java.util.List;

public interface BtvCommentReplyMapper {
    int deleteByPrimaryKey(String replayId);

    int insert(BtvCommentReply record);

    int insertSelective(BtvCommentReply record);

    BtvCommentReply selectByPrimaryKey(String replayId);

    int updateByPrimaryKeySelective(BtvCommentReply record);

    int updateByPrimaryKey(BtvCommentReply record);

    /**
     * 根据外键查询列表信息
     * @param btvCommentReplyPages
     * @return
     */
    List<BtvCommentReply> selectByCondition(Pages<BtvCommentReply> btvCommentReplyPages);
}