package com.cdvcloud.rochecloud.mapper;

import com.cdvcloud.rochecloud.domain.BtvDepartment;

public interface BtvDepartmentMapper {
    int deleteByPrimaryKey(String id);

    int insert(BtvDepartment record);

    int insertSelective(BtvDepartment record);

    BtvDepartment selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BtvDepartment record);

    int updateByPrimaryKey(BtvDepartment record);
}