package com.cdvcloud.rochecloud.service.impl;

import com.cdvcloud.rochecloud.common.Pages;
import com.cdvcloud.rochecloud.domain.BtvLawyer;
import com.cdvcloud.rochecloud.domain.BtvOrder;
import com.cdvcloud.rochecloud.mapper.BtvLawyerMapper;
import com.cdvcloud.rochecloud.mapper.BtvOrderMapper;
import com.cdvcloud.rochecloud.service.OrderService;
import com.cdvcloud.rochecloud.util.MailUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @ClassName:
* @Description: 订单管理
* @Author: makang makang@cdvcloud.com
* @date:     2018/8/12 10:33
*/
@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = Logger.getLogger(OrderServiceImpl.class);

    @Autowired
    private BtvOrderMapper btvOrderMapper;
    @Autowired
    private BtvLawyerMapper btvLawyerMapper;

    /**
     * 添加订单
     * @param btvOrder
     * @return
     */
    @Override
    public long insertOrder(BtvOrder btvOrder) {
        //发送邮件
        BtvLawyer btvLawyer = btvLawyerMapper.selectByPrimaryKey(btvOrder.getCreateUserId());
        if(btvLawyer != null){
            if(!"null".equals(String.valueOf(btvLawyer.getEmail()))){
                MailUtil mailUtil =  new MailUtil();
                String email = btvLawyer.getEmail();
                String lawyerName = btvLawyer.getLawyerName();
                Boolean mailStatus = mailUtil.setMail(email,lawyerName);
                logger.info("发送邮件状态："+mailStatus);
                if(mailStatus){
                    //将订单信息插入到表中
                    btvOrderMapper.insert(btvOrder);
                    //向律师表中更新服务人数
                    btvLawyer.setServiceNum(btvLawyer.getServiceNum()+1);
                    return btvLawyerMapper.updateByPrimaryKey(btvLawyer);
                }
            }
        }
        return 0L;
    }

    /**
     * 更新订单信息
     * @param btvOrder
     * @return
     */
    @Override
    public long updateOrder(BtvOrder btvOrder) {
        //更新订单信息
        long num = btvOrderMapper.updateByPrimaryKeySelective(btvOrder);
        //判断如果有分数则执行计算，以及加入评价个数
        if(!"null".equals(String.valueOf(btvOrder.getScore()))){
            //根据订单id查询律师id
            BtvOrder oldBtvOrder = btvOrderMapper.selectByPrimaryKey(btvOrder.getOrderId());
            //将分数更新到律师表
            int countNum = btvOrderMapper.countSum(oldBtvOrder.getCreateUserId());
            double countScoure = btvOrderMapper.countScoreSum(oldBtvOrder.getCreateUserId());
            BtvLawyer btvLawyer = new BtvLawyer();
            btvLawyer.setLawyerId(oldBtvOrder.getCreateUserId());
            btvLawyer.setAverage(countScoure/countNum);
            btvLawyer.setCommentNum(countNum);
            btvLawyerMapper.updateByPrimaryKey(btvLawyer);
        }
        return num;
    }

    /**
     * 根据id查询订单信息
     * @param id
     * @return
     */
    @Override
    public BtvOrder queryOrder(String id) {
        return btvOrderMapper.selectByPrimaryKey(id);
    }

    /**
     * 分页查询总数
     * @param page
     * @return
     */
    @Override
    public int countFindAll(Pages<BtvOrder> page) {
        return btvOrderMapper.countFindAll(page);
    }

    @Override
    public List<BtvOrder> selectFindAll(Pages <BtvOrder> page) {
        return btvOrderMapper.selectFindAll(page);
    }

}
