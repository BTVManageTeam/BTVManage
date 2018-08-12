package com.cdvcloud.rochecloud.service.impl;

import com.cdvcloud.rochecloud.domain.BtvOrder;
import com.cdvcloud.rochecloud.mapper.BtvOrderMapper;
import com.cdvcloud.rochecloud.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @ClassName:
* @Description: 订单管理
* @Author: makang makang@cdvcloud.com
* @date:     2018/8/12 10:33
*/
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private BtvOrderMapper btvOrderMapper;

    /**
     * 添加订单
     * @param btvOrder
     * @return
     */
    @Override
    public long insertOrder(BtvOrder btvOrder) {
        return btvOrderMapper.insert(btvOrder);
    }

    /**
     * 更新订单信息
     * @param btvOrder
     * @return
     */
    @Override
    public long updateOrder(BtvOrder btvOrder) {
        return btvOrderMapper.updateByPrimaryKeySelective(btvOrder);
    }

    @Override
    public BtvOrder queryOrder(String id) {
        return null;
    }


}
