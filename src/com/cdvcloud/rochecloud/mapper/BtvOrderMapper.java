package com.cdvcloud.rochecloud.mapper;

import com.cdvcloud.rochecloud.common.Pages;
import com.cdvcloud.rochecloud.domain.BtvOrder;

import java.util.List;

public interface BtvOrderMapper{
    int deleteByPrimaryKey(String orderId);

    int insert(BtvOrder record);

    int insertSelective(BtvOrder record);

    BtvOrder selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(BtvOrder record);

    int updateByPrimaryKeyWithBLOBs(BtvOrder record);

    int updateByPrimaryKey(BtvOrder record);

    /**
     * 根据条件查询对象的集合
     * @return
     */
    List<BtvOrder> selectFindAll(Pages<BtvOrder> page);

    /**
     * 根据条件查询一共有多少条数
     * @param page
     * @return
     */
    int countFindAll(Pages<BtvOrder> page);
}