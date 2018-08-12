package com.cdvcloud.rochecloud.service;

import com.cdvcloud.rochecloud.common.Pages;
import com.cdvcloud.rochecloud.domain.BtvOrder;

import java.util.List;

/**
* @ClassName:
* @Description: 订单管理service接口
* @Author: makang makang@cdvcloud.com
* @date:     2018/8/12 10:34
*/
public interface OrderService {

    /**
     * 添加订单
     * @param btvOrder
     * @return
     */
    long insertOrder(BtvOrder btvOrder);

    /**
     * 更新订单信息
     * @param btvOrder
     * @return
     */
    long updateOrder(BtvOrder btvOrder);

    /**
     * 查询
     * @param id
     * @return
     */
    BtvOrder queryOrder(String id);

    /**
     * 分页查询总数
     * @param page
     * @return
     */
    int countFindAll(Pages<BtvOrder> page);

    /**
     * 分页查询所有数据
     * @param page
     * @return
     */
    List<BtvOrder> selectFindAll(Pages<BtvOrder> page);

}
