package com.cdvcloud.rochecloud.mapper;

import com.cdvcloud.rochecloud.domain.BtvUserDep;

public interface BtvUserDepMapper {
    int deleteByPrimaryKey(String userDeptId);

    int insert(BtvUserDep record);

    int insertSelective(BtvUserDep record);

    BtvUserDep selectByPrimaryKey(String userDeptId);

    int updateByPrimaryKeySelective(BtvUserDep record);

    int updateByPrimaryKey(BtvUserDep record);
}