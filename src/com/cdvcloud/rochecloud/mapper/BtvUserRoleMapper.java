package com.cdvcloud.rochecloud.mapper;

import com.cdvcloud.rochecloud.domain.BtvUserRoleKey;

public interface BtvUserRoleMapper {
    int deleteByPrimaryKey(BtvUserRoleKey key);

    int insert(BtvUserRoleKey record);

    int insertSelective(BtvUserRoleKey record);
}