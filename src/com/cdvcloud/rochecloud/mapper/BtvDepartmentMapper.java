package com.cdvcloud.rochecloud.mapper;

import com.cdvcloud.rochecloud.domain.BtvDepartment;

public interface BtvDepartmentMapper {
    int deleteByPrimaryKey(String departmentId);

    int insert(BtvDepartment record);

    int insertSelective(BtvDepartment record);

    BtvDepartment selectByPrimaryKey(String departmentId);

    BtvDepartment findDeparmentByUserId(String userId);

    int updateByPrimaryKeySelective(BtvDepartment record);

    int updateByPrimaryKey(BtvDepartment record);
}