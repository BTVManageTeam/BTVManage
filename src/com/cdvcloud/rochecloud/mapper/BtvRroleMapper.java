package com.cdvcloud.rochecloud.mapper;

import com.cdvcloud.rochecloud.domain.BtvRrole;

public interface BtvRroleMapper {
    int deleteByPrimaryKey(String id);

    int insert(BtvRrole record);

    int insertSelective(BtvRrole record);

    BtvRrole selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BtvRrole record);

    int updateByPrimaryKey(BtvRrole record);
}