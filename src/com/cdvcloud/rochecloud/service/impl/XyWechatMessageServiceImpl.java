package com.cdvcloud.rochecloud.service.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cdvcloud.rochecloud.common.Constants;
import com.cdvcloud.rochecloud.common.Pages;
import com.cdvcloud.rochecloud.common.WechatInterfaceUtil;
import com.cdvcloud.rochecloud.mdomain.XyWechatMessage;
import com.cdvcloud.rochecloud.mongodao.BasicDao;
import com.cdvcloud.rochecloud.mongodao.QueryOperators;
import com.cdvcloud.rochecloud.schedule.ImportWechatTokenJob;
import com.cdvcloud.rochecloud.service.XyWechatMessageService;
import com.cdvcloud.rochecloud.util.DateUtil;
import com.cdvcloud.wechat.bean.AccessToken;

/**
 * 公众号消息管理
 * @Auther: zhangyuelong
 * @Date: 2018/7/9 18:46
 * @ClassName: com.yunshi.xy.service.impl
 * @Description:
 */
@Service
public class XyWechatMessageServiceImpl implements XyWechatMessageService{
    private final Logger logger = Logger.getLogger("ValidateCommonParam");
    @Autowired
    private BasicDao baseDao;
    /**
     * 根据条件分页查询消息列表
     * @param mapJson
     * @return
     */
    @Override
    public List<Map<String, Object>> queryWechatMessage4page(Map<String, Object> params,Pages<Map<String, Object>> mapJson) throws Exception{
        // 查询参数抽取
        Map<String, Object> queryFilter = new ConcurrentHashMap<String, Object>();
        if(params.containsKey(XyWechatMessage.NICKNAME)){
            queryFilter.put(XyWechatMessage.NICKNAME, params.get("nickname"));
        }
        // 排序
        Map<String, Object> sortFilter = new ConcurrentHashMap<String, Object>();
        sortFilter.put(XyWechatMessage.CTIME, -1);
        // 当前页
        Integer currentPage = 1;
        if(params.containsKey("currentPage")){
            currentPage = Integer.valueOf(String.valueOf(params.get("currentPage")));
        }
        // 每页条数
        Integer pageNum = 10;
        if(params.containsKey("pageNum")){
            pageNum = Integer.valueOf(String.valueOf(params.get("pageNum")));
        }

        Map<String, Object> backFilter = new ConcurrentHashMap<String, Object>();
        return baseDao.findList(XyWechatMessage.XYWECHATMESSAGE, queryFilter, backFilter, currentPage, pageNum);
    }

    @Override
    public int queryWechatMessage4pageCount(Map<String, Object> params, Pages<Map<String, Object>> mapJson) throws Exception {
        // 查询参数抽取
        Map<String, Object> queryFilter = new ConcurrentHashMap<String, Object>();
        if(params.containsKey(XyWechatMessage.NICKNAME)){
            queryFilter.put(XyWechatMessage.NICKNAME, params.get("nickname"));
        }

        return (int)baseDao.count(XyWechatMessage.XYWECHATMESSAGE, queryFilter);
    }

    /**
     * 消息回复
     * @param mapJson 请求参数
     * @return
     */
    @Override
    public long replyWechatMessage(Map<String, Object> mapJson, HttpServletRequest request)throws Exception {
        String productId = "";
        String token = getToken(productId);
        String content = String.valueOf(mapJson.get(XyWechatMessage.CONTENT));
        String openId = String.valueOf(mapJson.get(XyWechatMessage.OPENID));
        //步骤一：首先发送内容信息给微信，说明回复情况。
        /**{"touser":"odUG-twcfXDvcG8bwN1P_-r9jBaY","msgtype":"text","text":{"content":"感谢您关注新奥特云视,[微笑]"}**/
        Map<String,Object> jsonData = new ConcurrentHashMap<>(16);
        Map<String,Object> contentMap = new ConcurrentHashMap<>(16);
        jsonData.put("touser", openId);
        jsonData.put("msgtype", "text");
        content = content.replaceAll("&lt;", "<").replaceAll("&gt;", ">").
                replaceAll("<div>","").replaceAll("</div>","\n").
                replaceAll("<p>","").replaceAll("</p>","\n").
                replaceAll("&nbsp;", " ").replaceAll("<br>", "\n");
        contentMap.put("content", content);
        jsonData.put("text", contentMap);
        String json = JSONObject.toJSONString(jsonData);
        String result = WechatInterfaceUtil.sendCustomMessage(token,json);
        logger.info("send custom message result={"+result.toString()+"}.");
        Map<String,Object> robj = JSONObject.parseObject(result, Map.class);
        if(robj!=null && String.valueOf(robj.get("errcode")).equals("0")) {
            Map<String, Object> filter = new ConcurrentHashMap<>(16);
            String id = String.valueOf(mapJson.get(Constants.ID));
            filter.put(XyWechatMessage.ID,new ObjectId(id));
            Map<String, Object> params = new ConcurrentHashMap<>(16);
            Map<String, Object> replayContent = new ConcurrentHashMap<>(16);
            //            replayContent.put(XyWechatMessage.USERID,commonParameters.getUserId());
            //            replayContent.put(XyWechatMessage.USERNAME,userName);
            replayContent.put(XyWechatMessage.CONTENT,mapJson.get(XyWechatMessage.CONTENT));
            replayContent.put(XyWechatMessage.CTIME, DateUtil.getCurrentDateTime());
            params.put(QueryOperators.ADDTOSET,new Document(XyWechatMessage.REPLAYCONTENT,replayContent));
            params.put(QueryOperators.SET,new Document(XyWechatMessage.REPLAYED, Constants.SONE));
            return baseDao.updateOneBySet(XyWechatMessage.XYWECHATMESSAGE, filter, params);
        }
        return 0;
    }

    /**
     * 获取微信授权token
     * @return
     */
    private String getToken(String productId) {
        Map<String, Object> wechatMap = (Map<String, Object>)ImportWechatTokenJob.wechatKeyMap.get(productId);
        AccessToken accessToken = (AccessToken)wechatMap.get("accessToken");
        return accessToken.getAccess_token();
    }
}
