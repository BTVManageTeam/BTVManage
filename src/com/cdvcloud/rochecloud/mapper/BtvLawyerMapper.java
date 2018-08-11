package com.cdvcloud.rochecloud.mapper;

import com.cdvcloud.rochecloud.domain.BtvLawyer;

public interface BtvLawyerMapper {
    int deleteByPrimaryKey(String id);

    int insert(BtvLawyer record);

    int insertSelective(BtvLawyer record);

    BtvLawyer selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BtvLawyer record);

    int updateByPrimaryKey(BtvLawyer record);
}