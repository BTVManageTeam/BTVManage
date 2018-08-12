package com.cdvcloud.rochecloud.mapper;

import com.cdvcloud.rochecloud.domain.BtvRrole;

public interface BtvRroleMapper {
    int deleteByPrimaryKey(String roleId);

    int insert(BtvRrole record);

    int insertSelective(BtvRrole record);

    BtvRrole selectByPrimaryKey(String roleId);

    int updateByPrimaryKeySelective(BtvRrole record);

    int updateByPrimaryKey(BtvRrole record);
}