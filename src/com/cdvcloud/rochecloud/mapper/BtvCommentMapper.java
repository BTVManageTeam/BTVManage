package com.cdvcloud.rochecloud.mapper;

import com.cdvcloud.rochecloud.common.Pages;
import com.cdvcloud.rochecloud.domain.BtvComment;

import java.util.List;

public interface BtvCommentMapper {
    int deleteByPrimaryKey(String commentId);

    int insert(BtvComment record);

    int insertSelective(BtvComment record);

    BtvComment selectByPrimaryKey(String commentId);

    int updateByPrimaryKeySelective(BtvComment record);

    int updateByPrimaryKey(BtvComment record);

    /**
     * 根据条件查询对象的集合
     * @return
     */
    List<BtvComment> selectFindAll(Pages<BtvComment> page);

    /**
     * 根据条件查询一共有多少条数
     * @param page
     * @return
     */
    int countFindAll(Pages<BtvComment> page);
}