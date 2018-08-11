package com.cdvcloud.rochecloud.mapper;

import com.cdvcloud.rochecloud.domain.BtvComment;

public interface BtvCommentMapper {
    int deleteByPrimaryKey(String id);

    int insert(BtvComment record);

    int insertSelective(BtvComment record);

    BtvComment selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BtvComment record);

    int updateByPrimaryKey(BtvComment record);
}