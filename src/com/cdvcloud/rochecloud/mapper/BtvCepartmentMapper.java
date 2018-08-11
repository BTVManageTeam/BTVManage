package com.cdvcloud.rochecloud.mapper;

import com.cdvcloud.rochecloud.domain.BtvCepartment;

public interface BtvCepartmentMapper {
    int deleteByPrimaryKey(String id);

    int insert(BtvCepartment record);

    int insertSelective(BtvCepartment record);

    BtvCepartment selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BtvCepartment record);

    int updateByPrimaryKey(BtvCepartment record);
}