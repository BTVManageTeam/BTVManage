package com.cdvcloud.rochecloud.mapper;

import com.cdvcloud.rochecloud.domain.BtvOrder;

public interface BtvOrderMapper {
    int deleteByPrimaryKey(String orderId);

    int insert(BtvOrder record);

    int insertSelective(BtvOrder record);

    BtvOrder selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(BtvOrder record);

    int updateByPrimaryKey(BtvOrder record);
}