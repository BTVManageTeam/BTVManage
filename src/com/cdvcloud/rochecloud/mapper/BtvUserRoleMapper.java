package com.cdvcloud.rochecloud.mapper;

import com.cdvcloud.rochecloud.domain.BtvUserRole;

public interface BtvUserRoleMapper {
    int deleteByPrimaryKey(String userRoleId);

    int insert(BtvUserRole record);

    int insertSelective(BtvUserRole record);

    BtvUserRole selectByPrimaryKey(String userRoleId);

    int updateByPrimaryKeySelective(BtvUserRole record);

    int updateByPrimaryKey(BtvUserRole record);
}