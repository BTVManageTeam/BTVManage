package com.cdvcloud.rochecloud.service;

import com.cdvcloud.rochecloud.domain.BtvOrder;

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


}
