package com.cdvcloud.rochecloud.service;

import com.cdvcloud.rochecloud.common.Pages;
import com.cdvcloud.rochecloud.domain.BtvComment;

import java.util.List;

/**
* @ClassName:
* @Description: 评论管理service接口
* @Author: makang makang@cdvcloud.com
* @date:     2018/8/14 9:58
*/
public interface CommentService {

    /**
     * 分页查询总数
     * @param page
     * @return
     */
    int countFindAll(Pages<BtvComment> page);

    /**
     * 分页查询所有数据
     * @param page
     * @return
     */
    List<BtvComment> selectFindAll(Pages<BtvComment> page);

}
