package com.cdvcloud.rochecloud.service;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cdvcloud.rochecloud.common.Pages;

/**
 * 公众号消息管理
 * @Auther: zhangyuelong
 * @Date: 2018/7/9 18:42
 * @ClassName: com.yunshi.xy.service
 * @Description:
 */
public interface XyWechatMessageService {

    /**
     * 根据条件分页查询消息列表
     * @param commonParameters
     * @param mapJson
     * @return
     */
	List<Map<String, Object>> queryWechatMessage4page(Map<String, Object> params,Pages<Map<String, Object>> mapJson)throws Exception;

    /**
     * 消息回复
     * @param commonParameters
     * @param mapJson
     * @return
     */
    long replyWechatMessage(Map<String,Object> mapJson ,HttpServletRequest request)throws Exception ;
}
