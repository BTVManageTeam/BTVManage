package com.cdvcloud.rochecloud.mapper;

import com.cdvcloud.rochecloud.domain.BtvComment;

public interface BtvCommentMapper {
    int deleteByPrimaryKey(String commentId);

    int insert(BtvComment record);

    int insertSelective(BtvComment record);

    BtvComment selectByPrimaryKey(String commentId);

    int updateByPrimaryKeySelective(BtvComment record);

    int updateByPrimaryKey(BtvComment record);
}