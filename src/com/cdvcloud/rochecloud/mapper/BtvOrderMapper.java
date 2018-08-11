package com.cdvcloud.rochecloud.mapper;

import com.cdvcloud.rochecloud.domain.BtvOrder;

public interface BtvOrderMapper {
    int deleteByPrimaryKey(String id);

    int insert(BtvOrder record);

    int insertSelective(BtvOrder record);

    BtvOrder selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BtvOrder record);

    int updateByPrimaryKey(BtvOrder record);
}