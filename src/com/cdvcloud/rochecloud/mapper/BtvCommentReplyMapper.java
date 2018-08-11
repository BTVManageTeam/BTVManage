package com.cdvcloud.rochecloud.mapper;

import com.cdvcloud.rochecloud.domain.BtvCommentReply;

public interface BtvCommentReplyMapper {
    int deleteByPrimaryKey(String id);

    int insert(BtvCommentReply record);

    int insertSelective(BtvCommentReply record);

    BtvCommentReply selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BtvCommentReply record);

    int updateByPrimaryKey(BtvCommentReply record);
}